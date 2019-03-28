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
	public Set<VirtualTableRecords> findAllByPk(long pkId) {
		
		Set<VirtualTableRecords> set = new HashSet<VirtualTableRecords>();
		virtualTableRecordsDao.findByPkValue(pkId).iterator().forEachRemaining(set::add);
		return set;
	}


	@Override
	public List<Map> findAllByTable(long tableId) {
		
		List<VirtualTableRecords> list = new ArrayList<VirtualTableRecords>();
		virtualTableRecordsDao.findAllByTableJPQL(tableId).iterator().forEachRemaining(list::add);
		List<Map> dtoList=new ArrayList<>();
		Map<String,String> map=new HashMap<>();
		for(VirtualTableRecords record:list) {
			if(map.containsKey(record.getVirtualTableFields().getFieldName())) {
				dtoList.add(map);
				map=new HashMap<>();
			}
			map.put(record.getVirtualTableFields().getFieldName(),record.getStringValue());
		}
		return dtoList;
	}
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
