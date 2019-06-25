package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.UserDao;
import com.focowell.dao.WorkflowTrackDetDao;
import com.focowell.model.User;
import com.focowell.model.WorkflowTrackDet;
import com.focowell.service.UserService;
import com.focowell.service.WorkflowTrackDetService;

@Service(value = "workflowTrackDetService")
public class WorkflowTrackDetServiceImpl implements WorkflowTrackDetService {

	@Autowired
	private WorkflowTrackDetDao workflowTrackDetDao;
	
	@Autowired
	private UserService userService;
	
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
	public List<WorkflowTrackDet> findAllByUser() {
		List<WorkflowTrackDet> list = new ArrayList<>();
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findOne(auth.getName());
		workflowTrackDetDao.findAllByUserJPQL(user.getId()).iterator().forEachRemaining(list::add);
		list.forEach(track->{
			Hibernate.initialize(track.getWorkflowTrackMaster().getWorkflowMaster());
		});
		return list;
	}
	@Override
	public List<WorkflowTrackDet> findAllByProcess(long processId) {
		List<WorkflowTrackDet> list = new ArrayList<>();
		
		workflowTrackDetDao.findAllByProcessJPQL(processId).iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowTrackDet> findAllByWorkflowTrackMaster(Long workflowTrackMasterId) {
		List<WorkflowTrackDet> list = new ArrayList<>();
		
		workflowTrackDetDao.findAllByWorkflowTrackMasterJPQL(workflowTrackMasterId).iterator().forEachRemaining(list::add);
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
