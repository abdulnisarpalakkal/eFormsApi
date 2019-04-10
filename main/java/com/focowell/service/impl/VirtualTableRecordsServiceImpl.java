package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableRecordsDao;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.dto.VirtualTableRecordForGridDto;
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

	
	@Override
	public VirtualTableRecordForGridDto findAllByTableForGrid(long tableId) {
		VirtualTableRecordForGridDto recordsForGrid=new VirtualTableRecordForGridDto();
		recordsForGrid.setRecords(findAllRecordsByTable(tableId));
		
		recordsForGrid.setColumns(virtualTableFieldsService.findAllByTableId(tableId));
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

}
