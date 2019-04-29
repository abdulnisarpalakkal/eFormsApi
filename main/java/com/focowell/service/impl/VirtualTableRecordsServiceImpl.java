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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableRecordsDao;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.dto.VirtualTableRecordForGridDto;
import com.focowell.service.FileService;
import com.focowell.service.FormDesignService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableRecordsService;
import com.focowell.service.VirtualTableSequenceService;

@Service(value = "virtualTableRecordsService")
public class VirtualTableRecordsServiceImpl implements VirtualTableRecordsService {

	@Autowired
	private VirtualTableRecordsDao virtualTableRecordsDao;
	
	@Autowired
	private VirtualTableFieldsService virtualTableFieldsService;
	
	@Autowired
	private VirtualTableSequenceService virtualTableSequenceService;
	
	@Autowired
	private FormDesignService formDesignService;

	@Autowired
	private VirtualTableFieldsService virtualTableFieldService;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public List<VirtualTableRecords> findAll() {
		List<VirtualTableRecords> list = new ArrayList<>();
		virtualTableRecordsDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public Set<VirtualTableRecords> findAllByTableAndPk(long tableId,long pkValue) {
		
		Set<VirtualTableRecords> set = new HashSet<VirtualTableRecords>();
		virtualTableRecordsDao.findAllByTableAndPkValueJPQL(tableId, pkValue).iterator().forEachRemaining(set::add);
		return set;
	}

	@Override
	public List<Map> findAllRecordsByTable(long tableId) {
		
		List<VirtualTableRecords> list = new ArrayList<VirtualTableRecords>();
		virtualTableRecordsDao.findAllByTableJPQL(tableId).iterator().forEachRemaining(list::add);
		List<Map> dtoList=new ArrayList<>();
		Map<String,VirtualTableRecords> map=null;
		long prevPk=0;
		for(VirtualTableRecords record:list) {
			
			if(prevPk!=record.getPkValue()) {
				if(map!=null)
					dtoList.add(map);
				map=new HashMap<>();
				prevPk=record.getPkValue();
			}
			map.put(record.getVirtualTableFields().getFieldName(),record);
		}
		if(map!=null)
			dtoList.add(map);
		return dtoList;
	}
	@Override
	public List<Map> findAllByTable(long tableId) {
		
		List<VirtualTableRecords> list = new ArrayList<VirtualTableRecords>();
		virtualTableRecordsDao.findAllByTableJPQL(tableId).iterator().forEachRemaining(list::add);
		List<Map> dtoList=new ArrayList<>();
		Map<String,String> map=null;
		long prevPk=0;
		for(VirtualTableRecords record:list) {
			
			if(prevPk!=record.getPkValue()) {
				if(map!=null)
					dtoList.add(map);
				map=new HashMap<>();
				prevPk=record.getPkValue();
			}
			map.put(record.getVirtualTableFields().getFieldName(),record.getStringValue());
		}
		if(map!=null)
			dtoList.add(map);
		return dtoList;
	}
	
	@Override
	public Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId,String keyField,String valueField) {
		
		List<Map> tableRecords=findAllByTable(tableId);
		Set<FormComponentRefValue> refValueList=null;
		if(tableRecords!=null)
			refValueList=tableRecords.stream()
			.filter(record->record.containsKey(keyField) && record.containsKey(valueField))
			.map(record->new FormComponentRefValue(record.get(keyField).toString(),record.get(valueField).toString())).collect(Collectors.toSet());
		
		return refValueList;
	}

	private List<FormDesign> getFormDesignFromTableFields(List<VirtualTableField> fields){
		List<FormDesign> formDesigns=null;
		
		if(fields!=null) {
			int index=0;
			formDesigns=new ArrayList<>();
			for(VirtualTableField field:fields){
				FormDesign design=new FormDesign(field.getFieldName(),field.getFieldName(),FormComponentType.TEXT,index++,field);
				if(field.getFieldDataType()==VirtualTableFieldDataType.DATE)
					design.setComponentType(FormComponentType.DATE);
				if(field.getFieldDataType()==VirtualTableFieldDataType.BLOB)
					design.setComponentType(FormComponentType.FILE);
				else
					formDesignService.populateRefValuesIfForeignKey(design);
				
				formDesigns.add(design);
			}
		}
		
		return formDesigns;
	}
	 
	@Override
	public VirtualTableRecordForGridDto findAllByTableForGrid(long tableId) {
		VirtualTableRecordForGridDto recordsForGrid=new VirtualTableRecordForGridDto();
		recordsForGrid.setRecords(findAllRecordsByTable(tableId));
		
		recordsForGrid.setColumns(virtualTableFieldsService.findAllByTableId(tableId));
		recordsForGrid.setFormDesigns(getFormDesignFromTableFields(recordsForGrid.getColumns()));
		return recordsForGrid;
	}
	
	@Override
	public void delete(long id) {
		virtualTableRecordsDao.deleteById(id);
		
	}
		 
	@Override
	public void deleteByPkAndTable(long tableId,long pkValue) {
		
		virtualTableRecordsDao.deleteAll(virtualTableRecordsDao.findAllByTableAndPkValueJPQL(tableId, pkValue));
		
	}

	@Override
	public VirtualTableRecords findOne(String virtualTableRecordsName) {
		return null;
	}

	@Override
	public VirtualTableRecords findById(Long id) {
		return virtualTableRecordsDao.findById(id).get();
	}

	@Override
	public VirtualTableRecords save(VirtualTableRecords virtualTableRecords) throws AlreadyExistsException {
		
		return virtualTableRecordsDao.save(virtualTableRecords);
	}
	@Override
	public List<VirtualTableRecords> saveAll(Set<VirtualTableRecords> virtualTableRecords) {
		List<VirtualTableRecords> list = new ArrayList<>();
		virtualTableRecordsDao.saveAll(virtualTableRecords).iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<VirtualTableRecords> saveOneRowRecordAfterCheckPkValue(List<VirtualTableRecords> records) throws Exception {
//		List<VirtualTableRecords> records = new ArrayList<>(recordsMap.values());
		long pkValue=getPkValueFromRecords(records);
		
		records.forEach(record->{
		
			record.setPkValue(pkValue);
		});
		virtualTableRecordsDao.saveAll(records);
		return records;
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
	public VirtualTableRecords update(VirtualTableRecords virtualTableRecords) {
		VirtualTableRecords updateVirtualTableRecords =virtualTableRecordsDao.findById(virtualTableRecords.getId()).get();
				
		
        return virtualTableRecordsDao.save(updateVirtualTableRecords);
	}
	
	private boolean virtualTableRecordsExist(String virtualTableRecordsName) {
//        List<VirtualTableRecords> actions = virtualTableRecordsDao.findByActionName(virtualTableRecordsName);
//        if (actions != null && actions.size()!=0 ) {
//            return true;
//        }
        return false;
    }
	@Override
	public long saveVirtualRecordsFromForm(FormMaster form) throws Exception {
		
		Set<VirtualTableRecords> virtualTableRecords=new HashSet<VirtualTableRecords>();
		List<VirtualTableField> fields=	virtualTableFieldService.findAllByTableId(form.getVirtualTableMaster().getId());
		
		VirtualTableField pkField=fields.stream()
				.filter(field->field.getFieldConstraintList()!=null && field.getFieldConstraintList().stream().filter(constraint->constraint.getConstraintType()==VirtualTableConstraintType.PRIMARY_KEY).findAny().isPresent())
				.findFirst().orElse(null);  	//finding primary key field
		long pkValue=0;
		pkValue=getPkValueFromForm(form.getFormDesignList(),pkField.getFieldName());
				
		if(pkValue==0)
			pkValue=virtualTableSequenceService.getNextSeqByName(form.getVirtualTableMaster().getTableName()+"_seq");
		for (VirtualTableField virtualTableField : fields) {
			
			VirtualTableRecords record=new VirtualTableRecords();
			record.setVirtualTableFields(virtualTableField);
			
			if(pkField.getFieldName().equals(virtualTableField.getFieldName()))
			{
				record.setStringValue(Long.toString(pkValue));
			}
			else
			{ 
				record.setStringValue(getFieldValueFromFormComponent(form.getFormDesignList(),virtualTableField.getFieldName()));
			}
			record.setPkValue(pkValue);
			virtualTableRecords.add(record);
		}
		saveAll(virtualTableRecords);
		return pkValue;
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
				.filter(design->design.getVirtualTableField().getFieldName().equals(fieldName)).findFirst();
		if(formDesignOptional.isPresent())
		{
			FormDesign formDesign=formDesignOptional.get();
			String componentValue=formDesign.getComponentValue();
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
	public long updateVirtualRecordsFromForm(FormMaster form,long pkValue) {
		Set<VirtualTableRecords> virtualTableRecords=null;
		virtualTableRecords=new HashSet<VirtualTableRecords>();
		virtualTableRecords=findAllByTableAndPk(form.getVirtualTableMaster().getId(),pkValue);
		
		
		virtualTableRecords.forEach(record->{
			String val=getFieldValueFromFormComponent(form.getFormDesignList(),record.getVirtualTableFields().getFieldName());
			if(val!=null)
				record.setStringValue(val);
		});
		saveAll(virtualTableRecords);
		return pkValue;
	}
}
