package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableRecordsDao;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.dto.VirtualTableRecordForGridDto;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableRecordsService;

@Service(value = "virtualTableRecordsService")
public class VirtualTableRecordsServiceImpl implements VirtualTableRecordsService {

	@Autowired
	private VirtualTableRecordsDao virtualTableRecordsDao;
	
	@Autowired
	private VirtualTableFieldsService virtualTableFieldsService;
	
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
	public Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId,String key,String value) {
		
		List<Map> tableRecords=findAllByTable(tableId);
		Set<FormComponentRefValue> refValueList=null;
		if(tableRecords!=null)
			refValueList=tableRecords.stream()
			.filter(record->record.containsKey(key) && record.containsKey(value))
			.map(record->new FormComponentRefValue(record.get(key).toString(),record.get(value).toString())).collect(Collectors.toSet());
		
		return refValueList;
	}
//	@Override
//	public Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId,String key,String value) {
//		
//		List<VirtualTableRecords> list = new ArrayList<VirtualTableRecords>();
//		virtualTableRecordsDao.findAllByTableJPQL(tableId).iterator().forEachRemaining(list::add);
//		Set<FormComponentRefValue> refValueList=new HashSet<>();
//		FormComponentRefValue refValue=null;
//		long prevPk=0;
//		for(VirtualTableRecords record:list) {
//			if(prevPk!=record.getPkValue()) {
//				if(refValue!=null)
//					refValueList.add(refValue);
//				refValue=new FormComponentRefValue();
//				prevPk=record.getPkValue();
//			}
//			if(record.getVirtualTableFields().getFieldName().equals(key))
//					refValue.setRefKey(record.getStringValue()); 
//			else if(record.getVirtualTableFields().getFieldName().equals(value))
//				refValue.setRefValue(record.getStringValue());
//		}
//		if(refValue!=null)
//			refValueList.add(refValue);
//		return refValueList;
//	}
	
	@Override
	public VirtualTableRecordForGridDto findAllByTableForGrid(long tableId) {
		VirtualTableRecordForGridDto recordsForGrid=new VirtualTableRecordForGridDto();
		recordsForGrid.setRecords(findAllByTable(tableId));
		
		recordsForGrid.setColumns(virtualTableFieldsService.findAllByTableId(tableId).stream().map(VirtualTableField::getFieldName).collect(Collectors.toList()));
		return recordsForGrid;
	}
	
	@Override
	public void delete(long id) {
		virtualTableRecordsDao.deleteById(id);
		
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
