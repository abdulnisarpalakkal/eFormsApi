package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.WorkflowLinkDao;
import com.focowell.dao.WorkflowNodeDao;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowNode;
import com.focowell.model.WorkflowNodeType;
import com.focowell.service.WorkflowLinkService;
import com.focowell.service.WorkflowNodeService;

@Service(value = "workflowLinkService")
public class WorkflowLinkServiceImpl implements WorkflowLinkService {
	private static final Logger logger = LogManager.getLogger(WorkflowLinkServiceImpl.class);
	@Autowired
	private WorkflowLinkDao workflowLinkDao;
	
	@Autowired
	private WorkflowNodeService workflowNodeService;
	
	@Override
	public List<WorkflowLink> findAll() {
		List<WorkflowLink> list = new ArrayList<>();
		workflowLinkDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowLink> findAllByWorkflow(long workflowId) {
		List<WorkflowLink> list = new ArrayList<>();
		workflowLinkDao.findAllByWorkflowIdJPQL(workflowId).iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		workflowLinkDao.deleteById(id);
		
	}
	
	@Override
	public void removeByWorkflow(long workflowId) {
		workflowLinkDao.deleteByWorkflowId(workflowId);
		
	}
	
	@Override
	public WorkflowLink findOne(String workflowLinkName) {
//		return workflowLinkDao.findByWorkflowLinkName(workflowLinkName).get(0);
		return new WorkflowLink();
	}

	@Override
	public WorkflowLink findById(Long id) {
		return workflowLinkDao.findById(id).get();
	}
	@Override
	public WorkflowLink findBySourceId(long sourceNodeid) {
		return workflowLinkDao.findBySourceNode_NodeId(sourceNodeid);
	}

	@Override
	public WorkflowLink save(WorkflowLink workflowLink) throws AlreadyExistsException {
//		if(workflowLinkExist(workflowLink.getWorkflowLinkName()))
//		{
//			throw new AlreadyExistsException(
//		              "There is an WorkflowLink with the same name "
//		              +  workflowLink.getWorkflowLinkName() );
//		}
		return workflowLinkDao.save(workflowLink);
	}
	
	
	@Override
	@Transactional
	public boolean saveAll(List<WorkflowLink> workflowLinks) throws AlreadyExistsException {
//		if(workflowLinkExist(workflowLink.getWorkflowLinkName()))
//		{
//			throw new AlreadyExistsException(
//		              "There is an WorkflowLink with the same name "
//		              +  workflowLink.getWorkflowLinkName() );
//		}
		long workflowId=workflowLinks.isEmpty()?0:workflowLinks.get(0).getWorkflowMaster().getId();

		removeByWorkflow(workflowId);
		workflowNodeService.removeByWorkflow(workflowId);
		workflowLinkDao.saveAll(workflowLinks);
		return true;
	}

	@Override
	public WorkflowLink update(WorkflowLink workflowLink) {
		WorkflowLink updateWorkflowLink =workflowLinkDao.findById(workflowLink.getLinkId()).get();
				
		updateWorkflowLink.setLabel(workflowLink.getLabel());
							
        return workflowLinkDao.save(updateWorkflowLink);
	}
	
	private boolean workflowLinkExist(String workflowLinkName) {
//        List<WorkflowLink> nodes = workflowLinkDao.findByWorkflowLinkName(workflowLinkName);
//        if (categories != null && categories.size()!=0 ) {
//            return true;
//        }
        return false;
    }
	@Override
	public WorkflowLink findStartNodeByWorkflow(long workflowId) {
		
		return workflowLinkDao.findFirstLinkByWorkflowIdJPQL(workflowId,WorkflowNodeType.START);
	}

}
