package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.WorkflowTrackDetDao;
import com.focowell.dao.UserDao;
import com.focowell.model.WorkflowTrackDet;
import com.focowell.model.User;
import com.focowell.service.WorkflowTrackDetService;

@Service(value = "workflowTrackDetService")
public class WorkflowTrackDetServiceImpl implements WorkflowTrackDetService {

	@Autowired
	private WorkflowTrackDetDao workflowTrackDetDao;
	
	@Override
	public List<WorkflowTrackDet> findAll() {
		List<WorkflowTrackDet> list = new ArrayList<>();
		workflowTrackDetDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowTrackDet> findAllByOpen(boolean open) {
		List<WorkflowTrackDet> list = new ArrayList<>();
		workflowTrackDetDao.findAllByOpen(open).iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		workflowTrackDetDao.deleteById(id);
		
	}
	
	@Override
	public WorkflowTrackDet findOne(String workflowTrackDetName) {
		return null;
	}

	@Override
	public WorkflowTrackDet findById(Long id) {
		return workflowTrackDetDao.findById(id).get();
	}

	@Override
	public WorkflowTrackDet save(WorkflowTrackDet workflowTrackDet)  {
		
		return workflowTrackDetDao.save(workflowTrackDet);
	}

	@Override
	public WorkflowTrackDet update(WorkflowTrackDet workflowTrackDet) {
		WorkflowTrackDet updateWorkflowTrackDet =workflowTrackDetDao.findById(workflowTrackDet.getId()).get();
		updateWorkflowTrackDet.setOpen(workflowTrackDet.isOpen());
					
        return workflowTrackDetDao.save(updateWorkflowTrackDet);
	}
	@Override
	public WorkflowTrackDet updateOpenStatus(WorkflowTrackDet workflowTrackDet) {
		WorkflowTrackDet updateWorkflowTrackDet =workflowTrackDetDao.findById(workflowTrackDet.getId()).get();
		updateWorkflowTrackDet.setOpen(workflowTrackDet.isOpen());
					
        return workflowTrackDetDao.save(updateWorkflowTrackDet);
	}
	

}
