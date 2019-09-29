package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.VirtualTableRecordForGridDto;
import com.focowell.model.Constants;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.model.VirtualRowRecord;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableRecords;
import com.focowell.service.FileService;
import com.focowell.service.FormDesignService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableMasterService;
import com.focowell.service.VirtualTableRecordsMongoService;
import com.focowell.service.VirtualTableSequenceService;
import com.focowell.service.functionalInterface.VirtualTableFieldFilter;

import javassist.NotFoundException;

@Service(value = "virtualTableRecordsMongoService")
public class VirtualTableRecordsMongoServiceImpl implements VirtualTableRecordsMongoService {

	private static final Logger logger = LogManager.getLogger(VirtualTableRecordsMongoServiceImpl.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${mongo.rest-url}")
	private String mongoRestUrl;
	
//	@Autowired
//	private VirtualTableRecordsDao virtualTableRecordsDao;
	
	
	@Autowired
	private VirtualTableFieldsService virtualTableFieldsService;
	
	@Autowired
	private VirtualTableSequenceService virtualTableSequenceService;
	
	@Autowired
	private FormDesignService formDesignService;

	@Autowired
	private VirtualTableFieldsService virtualTableFieldService;
	
	@Autowired
	private VirtualTableMasterService virtualTableMasterService;
	
	@Autowired
	private FileService fileService;
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
		headers.add(Constants.TENANT_MONGO_DB, attr.getAttribute(Constants.TENANT_ID, RequestAttributes.SCOPE_REQUEST).toString());
		return headers;
	}
	private String createURLWithPort(String uri) {
        return mongoRestUrl+ uri;
    }
	
	/**
	 * Load table and table fields into records
	 * @param virtualRowRecordsDtoList
	 */
	private void LoadTableAndFieldsToVirtualRowRecords(List<VirtualRowRecord> virtualRowRecordsDtoList){
		if(virtualRowRecordsDtoList==null || virtualRowRecordsDtoList.isEmpty() )
			return;
		virtualRowRecordsDtoList.forEach(rowRecord->{
			rowRecord.setVirtualTableMaster(virtualTableMasterService.findById(rowRecord.getVirtualTableMaster().getId()));
			
			final List<VirtualTableField> fields=virtualTableFieldService.findAllByTableId(rowRecord.getVirtualTableMaster().getId());
			VirtualTableFieldFilter fieldFilter=(id)->fields.stream().filter(field->field.getId()==id).findFirst().orElse(null);
			rowRecord.getRecords().forEach(record->record.setVirtualTableFields(fieldFilter.filter(record.getVirtualTableFields().getId())));
			
		});
		
	}
	/**
	 * Load table and table fields into records
	 * @param virtualRowRecordsDtoList
	 * @param tableId
	 */
	private void LoadTableAndFieldsToVirtualRowRecords(List<VirtualRowRecord> virtualRowRecordsDtoList,Long tableId){
		if(virtualRowRecordsDtoList==null || virtualRowRecordsDtoList.isEmpty() )
			return;
		final List<VirtualTableField> fields=virtualTableFieldService.findAllByTableId(tableId); //get all table fields by table id
		VirtualTableFieldFilter fieldFilter=(id)->fields.stream().filter(field->field.getId()==id).findFirst().orElse(null); //lambda definition of functional interface
		virtualRowRecordsDtoList.forEach(rowRecord->{
			rowRecord.setVirtualTableMaster(virtualTableMasterService.findById(rowRecord.getVirtualTableMaster().getId()));
			List<VirtualTableRecords> recordsToBeDeleted=new ArrayList<>();					
			rowRecord.getRecords().forEach(record->{
				VirtualTableField field=fieldFilter.filter(record.getVirtualTableFields().getId()); //get table field using table field if
				if(field==null)
					
					recordsToBeDeleted.add(record);
				else
					record.setVirtualTableFields(field);
			});
			rowRecord.getRecords().removeAll(recordsToBeDeleted); //remove record if table field is not available
		});
	}
	/**
	 * Load table and table fields into records
	 * @param virtualRowRecordsDto
	 * @param tableId
	 */
	private void LoadTableAndFieldsToVirtualRowRecords(VirtualRowRecord virtualRowRecordsDto,Long tableId){
		
		if(virtualRowRecordsDto==null )
			return;
		final List<VirtualTableField> fields=virtualTableFieldService.findAllByTableId(tableId); //get all table fields by table id
		VirtualTableFieldFilter fieldFilter=(id)->fields.stream().filter(field->field.getId()==id).findFirst().orElse(null); //lambda definition of functional interface
		
		virtualRowRecordsDto.setVirtualTableMaster(virtualTableMasterService.findById(virtualRowRecordsDto.getVirtualTableMaster().getId()));
							
		virtualRowRecordsDto.getRecords().forEach(record->{
			VirtualTableField field=fieldFilter.filter(record.getVirtualTableFields().getId()); //get table field using table field if
			if(field==null)
				virtualRowRecordsDto.getRecords().remove(record); //remove record if table field is not available
			else
				record.setVirtualTableFields(field);
		});
			
		
	}
	
	@Override
	public List<VirtualRowRecord> findAll() {
		List<VirtualRowRecord> virtualRowRecordsDtoList = new ArrayList<>();
		HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());
//		Map<String, Long> urlVariables=new HashMap<>();
//   	 	urlVariables.put("id", 1L);
		ResponseEntity<List<VirtualRowRecord>> response=restTemplate.exchange(
  	          createURLWithPort("/row/records"), HttpMethod.GET, entity, new ParameterizedTypeReference<List<VirtualRowRecord>>(){});
		virtualRowRecordsDtoList=response.getBody();
		
		LoadTableAndFieldsToVirtualRowRecords(virtualRowRecordsDtoList);
		return virtualRowRecordsDtoList;
	}
	
	
	@Override
	public  VirtualRowRecord findAllRowsByTableAndPk(long tableId,long pkValue) {
				
		 VirtualRowRecord virtualRowRecordsDto =null;
		 HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());
		 Map<String, String> urlVariables=new HashMap<>();
		 urlVariables.put("tableId", Long.toString(tableId));
		 urlVariables.put("pkValue", Long.toString(pkValue));
		 
	//	 	urlVariables.put("id", 1L);
		 ResponseEntity<VirtualRowRecord> response=restTemplate.exchange(
	  	          createURLWithPort("/row/records/tableId/pk/{tableId}/{pkValue}"), HttpMethod.GET, entity
	  	          , VirtualRowRecord.class,urlVariables);
		 virtualRowRecordsDto=response.getBody();
		 
		 LoadTableAndFieldsToVirtualRowRecords(virtualRowRecordsDto,tableId);
		 return virtualRowRecordsDto;
	}
	@Override
	public VirtualRowRecord findRowByTableAndPk(long tableId,long pkValue) {
		VirtualRowRecord existRow=findAllRowsByTableAndPk(tableId,pkValue);
		if(existRow==null || existRow.getId()==null)
			return null;
		return existRow;
	}

	@Override
	public List<VirtualRowRecord> findAllRowsByTable(long tableId) {
		
		List<VirtualRowRecord> virtualRowRecordsDtoList = new ArrayList<>();
		HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());
		 Map<String, String> urlVariables=new HashMap<>();
		 urlVariables.put("tableId", Long.toString(tableId));
		
		ResponseEntity<List<VirtualRowRecord>> response=restTemplate.exchange(
	  	          createURLWithPort("/row/records/tableId/{tableId}"), HttpMethod.GET, entity, new ParameterizedTypeReference<List<VirtualRowRecord>>(){},urlVariables);
		virtualRowRecordsDtoList=response.getBody();
		LoadTableAndFieldsToVirtualRowRecords(virtualRowRecordsDtoList,tableId);
		return virtualRowRecordsDtoList;
		
		
	}

	
	@Override
	public Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId,String keyField,String valueField) {
		
		List<VirtualRowRecord> tableRows=findAllRowsByTable(tableId);
	
		Set<FormComponentRefValue> refValueList=null;
		if(tableRows!=null && tableRows.size()!=0)	
			refValueList=new HashSet<>();
			for(VirtualRowRecord tableRow:tableRows) {
				Map<String,String> refMap=tableRow.getRecords().stream()
						.filter(record->record.getVirtualTableFields().getFieldName().equals(keyField) || record.getVirtualTableFields().getFieldName().equals(valueField))
						.collect(Collectors.toMap(record->record.getVirtualTableFields().getFieldName(),VirtualTableRecords::getStringValue));
				refValueList.add(new FormComponentRefValue(refMap.get(keyField),refMap.get(valueField)));
			}

		return refValueList;
	}

	
	 
	@Override
	public VirtualTableRecordForGridDto findAllByTableForGrid(long tableId) {
		VirtualTableRecordForGridDto recordsForGrid=new VirtualTableRecordForGridDto();
		recordsForGrid.setRowRecords(findAllRowsByTable(tableId));
		
		recordsForGrid.setColumns(virtualTableFieldsService.findAllByTableId(tableId));
		recordsForGrid.setFormDesigns(formDesignService.getFormDesignFromTableFields(recordsForGrid.getColumns()));
		return recordsForGrid;
	}
	
	
		 
	@Override
	public void deleteByPkAndTable(long tableId,long pkValue) {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());
		 Map<String, String> urlVariables=new HashMap<>();
		 urlVariables.put("tableId", Long.toString(tableId));
		 urlVariables.put("pkValue", Long.toString(pkValue));
		ResponseEntity<Void> response=restTemplate.exchange(
	  	          createURLWithPort("/row/records/tableId/pk/{tableId}/{pkValue}"), HttpMethod.DELETE, entity,Void.class,urlVariables);
					
	}

	
	@Override
	public VirtualRowRecord saveOneRowRecordAfterCheckPkValue(VirtualRowRecord virtualRowRecordsDto) throws Exception {

		long pkValue=getPkValueFromRecords(virtualRowRecordsDto.getRecords());
		
		if(virtualTableRowRecordsExist(virtualRowRecordsDto.getVirtualTableMaster().getId(),pkValue))
			throw new AlreadyExistsException("Primary key constraint violattion");
		
		virtualRowRecordsDto.setPkValue(pkValue);
					
		HttpEntity<VirtualRowRecord> entity = new HttpEntity<VirtualRowRecord>(virtualRowRecordsDto, getHeaders());
		
		ResponseEntity<VirtualRowRecord> response=restTemplate.exchange(
	  	          createURLWithPort("/row/records"), HttpMethod.POST, entity,VirtualRowRecord.class);
		
		return response.getBody();
	}
	
	private long getPkValueFromRecords(List<VirtualTableRecords> records) throws Exception {
		long pkValue=0;
		VirtualTableRecords pkRecord=records.stream()
				.filter(record->record.getVirtualTableFields().getFieldConstraintList()!=null && record.getVirtualTableFields().getFieldConstraintList().stream().filter(constraint->constraint.getConstraintType()==VirtualTableConstraintType.PRIMARY_KEY).findAny().isPresent())
				.findFirst().orElse(null);  	//finding primary key field
		if(pkRecord.getStringValue()!=null) {
			try
			{
				pkValue=Long.parseLong(pkRecord.getStringValue());
			}
			catch (NumberFormatException e) {
				throw new Exception("Primary key value should be numeric");
			}
		}
		if(pkValue==0)
			pkValue=virtualTableSequenceService.getNextSeqByName(pkRecord.getVirtualTableFields().getVirtualTableMaster().getTableName()+"_seq");
		pkRecord.setStringValue(Long.toString(pkValue));
		return pkValue;
	}

	@Override
	public VirtualRowRecord update(VirtualRowRecord virtualRowRecordsDto) throws NotFoundException {
		VirtualRowRecord existingRow =findAllRowsByTableAndPk(virtualRowRecordsDto.getVirtualTableMaster().getId(), virtualRowRecordsDto.getPkValue());
		if(existingRow==null)
			throw new NotFoundException("Record not exist");
		HttpEntity<VirtualRowRecord> entity = new HttpEntity<VirtualRowRecord>(virtualRowRecordsDto, getHeaders());
		
		ResponseEntity<VirtualRowRecord> response=restTemplate.exchange(
	  	          createURLWithPort("/row/records"), HttpMethod.PUT, entity,VirtualRowRecord.class);
		
        return response.getBody();
	}
	
	private boolean virtualTableRowRecordsExist(long tableId, long pkValue) {
        VirtualRowRecord existRow = findAllRowsByTableAndPk(tableId, pkValue);
        if (existRow != null  ) {
            return true;
        }
        return false;
    }
	@Override
	@Transactional
	public VirtualRowRecord saveVirtualRecordsFromForm(FormMaster form) throws Exception {
		
		List<VirtualTableRecords> virtualTableRecords=new ArrayList<VirtualTableRecords>();
		List<VirtualTableField> fields=	virtualTableFieldService.findAllByTableId(form.getVirtualTableMaster().getId());
		
		for (VirtualTableField virtualTableField : fields) {
			
			VirtualTableRecords record=new VirtualTableRecords();
			record.setVirtualTableFields(virtualTableField);
			record.setStringValue(getFieldValueFromFormComponent(form.getFormDesignList(),virtualTableField.getFieldName()));
		
			virtualTableRecords.add(record);
		}
		
		
		
		for(FormDesign formDesign:form.getFormDesignList().stream().filter(design->design.getComponentType()==FormComponentType.GRID).collect(Collectors.toList()))
		{
//			for(VirtualTableRecords childRecord:formDesign.getGridRecords()) {
//				if(childRecord.getPkValue()==0){
//					
//				}
//			}
		}
		VirtualRowRecord virtualRowRecordsDto=new VirtualRowRecord();
		virtualRowRecordsDto.setVirtualTableMaster(form.getVirtualTableMaster());
		virtualRowRecordsDto.setRecords(virtualTableRecords);
		return saveOneRowRecordAfterCheckPkValue(virtualRowRecordsDto);
	}
	
	private long getPkValueFromForm(Set<FormDesign> formDesignList,String pkFieldName) throws Exception {
		long pkValue=0;
		try
		{
			String pkString=getFieldValueFromFormComponent(formDesignList,pkFieldName);
			if(pkString!=null)
				pkValue=Long.parseLong(pkString);
		}
		catch (NumberFormatException e) {
			throw new Exception("Primary key value should be numeric");
		}
		return pkValue;
	}
	private String getFieldValueFromFormComponent(Set<FormDesign> formDesignList,String fieldName) {
		Optional<FormDesign> formDesignOptional=formDesignList.stream()
				.filter(design->design.getComponentType()!=FormComponentType.GRID && design.getFormComponent().getVirtualTableField().getFieldName().equals(fieldName)).findFirst();
		if(formDesignOptional.isPresent())
		{
			FormDesign formDesign=formDesignOptional.get();
			String componentValue=formDesign.getFormComponent().getComponentValue();
			if(formDesign.getComponentType()==FormComponentType.FILE) {
				componentValue=fileService.getFileNameFromPath(componentValue);
			}
			else if(formDesign.getComponentType()==FormComponentType.IMG || formDesign.getComponentType()==FormComponentType.LABEL) // these are read only components
				componentValue=null;
			return componentValue;
		}
		return null;
	}
	@Override
	public VirtualRowRecord updateVirtualRecordsFromForm(FormMaster form,long pkValue) throws NotFoundException {
		logger.debug("updateVirtualRecordsFromForm: entered form: "+form+", pkValue: "+pkValue);
		VirtualRowRecord virtualRowRecordsDto=null;
		virtualRowRecordsDto=new VirtualRowRecord();
		VirtualRowRecord existVirtualRowRecord=findAllRowsByTableAndPk(form.getVirtualTableMaster().getId(),pkValue);
		logger.debug("updateVirtualRecordsFromForm: findAllRowsByTableAndPk: "+existVirtualRowRecord);
		if(existVirtualRowRecord==null )
			throw new NotFoundException("Record not found");
		virtualRowRecordsDto=existVirtualRowRecord;
		
		
		virtualRowRecordsDto.getRecords().forEach(record->{
			String val=getFieldValueFromFormComponent(form.getFormDesignList(),record.getVirtualTableFields().getFieldName());
			logger.debug("get value from component: field: "+record.getVirtualTableFields()+" value : "+val);
			if(val!=null)
				record.setStringValue(val);
		});
		virtualRowRecordsDto=update(virtualRowRecordsDto);
		logger.debug("returning updateVirtualRecordsFromForm: virtualRowRecordsDto: "+virtualRowRecordsDto);
		return virtualRowRecordsDto;
		
	}
	
	
	@Override
	public List<VirtualRowRecord> saveAllRowRecords(List<VirtualRowRecord> virtualRowRecords) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
