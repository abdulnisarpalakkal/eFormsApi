package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableRecordsDao;
import com.focowell.model.VirtualTableRecords;
import com.focowell.service.VirtualTableRecordsService;

@Service(value = "virtualTableRecordsService")
public class VirtualTableRecordsServiceImpl implements VirtualTableRecordsService {

	@Autowired
	private VirtualTableRecordsDao virtualTableRecordsDao;
	
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
