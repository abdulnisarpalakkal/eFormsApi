package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.dao.VirtualTableConstraintsDao;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.service.VirtualTableConstraintsService;

@Service(value = "virtualTableConstraintsService")
public class VirtualTableConstraintsServiceImpl implements VirtualTableConstraintsService {

	@Autowired
	private VirtualTableConstraintsDao virtualTableConstraintsDao;
	
	@Override
	public List<VirtualTableConstraints> findAll(long tableId) {
		List<VirtualTableConstraints> list = new ArrayList<>();
		virtualTableConstraintsDao.findAllByTableIdJPQL(tableId).iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public List<VirtualTableConstraints> findAllByVirtualTableFieldByProcess(long processId){
		List<VirtualTableConstraints> list = new ArrayList<>();
		virtualTableConstraintsDao.findAllByVirtualTableFieldConstraintsByProcess_id(processId).iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public List<VirtualTableConstraints> findAllByTableId(long tableId){
		List<VirtualTableConstraints> list = new ArrayList<>();
		virtualTableConstraintsDao.findAllByTableIdJPQL(tableId).iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public void delete(long id) {
		virtualTableConstraintsDao.deleteById(id);
		
	}



	@Override
	public VirtualTableConstraints findById(Long id) {
		return virtualTableConstraintsDao.findById(id).get();
	}

	@Override
	public VirtualTableConstraints save(VirtualTableConstraints virtualTableConstraints)  {
	
		return virtualTableConstraintsDao.save(virtualTableConstraints);
	}
	@Override
	public List<VirtualTableConstraints> saveAll(Set<VirtualTableConstraints> virtualTableConstraints)  {
		List<VirtualTableConstraints> list = new ArrayList<>();
		virtualTableConstraintsDao.saveAll(virtualTableConstraints).iterator().forEachRemaining(list::add);;
		return list;
	}



}
