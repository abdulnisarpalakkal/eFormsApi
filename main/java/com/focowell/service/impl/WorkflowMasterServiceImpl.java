package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.UserDao;
import com.focowell.dao.WorkflowMasterDao;
import com.focowell.model.User;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;
import com.focowell.service.ActionEventObjectService;
import com.focowell.service.ActionEventParamObjectService;
import com.focowell.service.WorkflowLinkService;
import com.focowell.service.WorkflowMasterService;
import com.focowell.service.WorkflowNodeService;

@Service(value = "workflowMasterService")
public class WorkflowMasterServiceImpl implements WorkflowMasterService {

	@Autowired
	private WorkflowMasterDao workflowMasterDao;
	
	@Autowired
	private WorkflowNodeService workflowNodeService;
	
	@Autowired
	private WorkflowLinkService workflowLinkService;
	
	@Autowired
	private ActionEventObjectService actionEventObjectService;
	
	@Autowired
	private ActionEventParamObjectService actionEventParamObjectService;
	
	@Override
	public List<WorkflowMaster> findAll() {
		List<WorkflowMaster> list = new ArrayList<>();
		workflowMasterDao.findAllByJPQL().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowMaster> findAllByProcess(long processId) {
		List<WorkflowMaster> list = new ArrayList<>();
		workflowMasterDao.findAllByProcessJPQL(processId).iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public List<WorkflowMaster> findAllPublished() {
		List<WorkflowMaster> list = new ArrayList<>();
		workflowMasterDao.findAllPublishedByJPQL().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowMaster> findAllPublishedChildWorkflowsByProcess(long processId) {
		List<WorkflowMaster> list = new ArrayList<>();
		workflowMasterDao.findAllPublishedChildWorkflowsByProcessJPQL(processId).iterator().forEachRemaining(list::add);
		return list;
	}
	
	
	@Override
	public void delete(long id) {
		workflowMasterDao.deleteById(id);
		
	}

	@Override
	public WorkflowMaster findOne(String workflowMasterName) {
		return workflowMasterDao.findByWorkflowName(workflowMasterName).get(0);
	}

	@Override
	public WorkflowMaster findById(Long id) {
		return workflowMasterDao.findById(id).get();
	}

	@Override
	public WorkflowMaster save(WorkflowMaster workflowMaster) throws AlreadyExistsException {
		if(workflowMasterExist(workflowMaster.getWorkflowName()))
		{
			throw new AlreadyExistsException(
		              "There is an WorkflowMaster with the same name "
		              +  workflowMaster.getWorkflowName() );
		}
		return workflowMasterDao.save(workflowMaster);
	}

/*	Save or update design with all dependencies set in bidirectional
	WorkflowNode
	WorkflowLink
	WorkflowActions
	WorkflowActionParams
	
*/
	@Override
	@Transactional
	public void saveDesign(WorkflowMaster workflowMaster) throws AlreadyExistsException  {
		
		for (WorkflowNode workflowNode : workflowMaster.getWorkflowNodeList()) {
			workflowNode.setWorkflowMaster(workflowMaster);
			if(workflowNode.getSourceLinkList()!=null)
				workflowNode.getSourceLinkList().clear();
			if(workflowNode.getTargetLinkList()!=null)
				workflowNode.getTargetLinkList().clear();
//		WorkflowNode savedWorkflowNode=workflowNodeService.save(workflowNode);
//			savedWorkflowNode.setId(workflowNode.getId());
			
			if(workflowNode.getActionEventObjects()!=null )
			{
				
				workflowNode.getActionEventObjects().forEach(eventObj->{
					eventObj.setWorkflowNode(workflowNode);
					if(eventObj.getActionEventParamObjects() !=null ) {
						eventObj.getActionEventParamObjects().forEach(eventParamObj->{
							eventParamObj.setActionEventObject(eventObj);
						});
						
					}
					
				});
				
			}
		}
		for (WorkflowLink workflowLink : workflowMaster.getWorkflowLinkList()) {
			workflowLink.setWorkflowMaster(workflowMaster);
			workflowLink.setSourceNode(workflowMaster.getWorkflowNodeList().stream().filter(node->node.getId().equalsIgnoreCase(workflowLink.getSourceNode().getId())).findFirst().get());
			workflowLink.setTargetNode(workflowMaster.getWorkflowNodeList().stream().filter(node->node.getId().equalsIgnoreCase(workflowLink.getTargetNode().getId())).findFirst().get());
			
		}
		workflowMaster.setWorkflowTrackList(Collections.emptySet());
		workflowMaster.setPublished(false);
//		if(workflowMaster.getWorkflowLinkList()!=null)
//			workflowMaster.getWorkflowLinkList().clear();
		workflowMasterDao.save(workflowMaster);
	}

	@Override
	public WorkflowMaster update(WorkflowMaster workflowMaster) {
		WorkflowMaster updateWorkflowMaster =workflowMasterDao.findById(workflowMaster.getId()).get();
				
		updateWorkflowMaster.setWorkflowName(workflowMaster.getWorkflowName());
		updateWorkflowMaster.setWorkflowDesc(workflowMaster.getWorkflowDesc());
		updateWorkflowMaster.setProcess(workflowMaster.getProcess());
		updateWorkflowMaster.setChild(workflowMaster.isChild());
		updateWorkflowMaster.setPublished(false);
					
        return workflowMasterDao.save(updateWorkflowMaster);
	}
	@Override
	public WorkflowMaster publishWorkflow(WorkflowMaster workflowMaster) {
		WorkflowMaster updateWorkflowMaster =workflowMasterDao.findById(workflowMaster.getId()).get();
				
		updateWorkflowMaster.setPublished(workflowMaster.isPublished());
		updateWorkflowMaster.setPublishDate(workflowMaster.getPublishDate());
		updateWorkflowMaster.setPublishUser(workflowMaster.getPublishUser());
					
        return workflowMasterDao.save(updateWorkflowMaster);
	}
	private boolean workflowMasterExist(String workflowMasterName) {
        List<WorkflowMaster> workflowMasters = workflowMasterDao.findByWorkflowName(workflowMasterName);
        if (workflowMasters != null && workflowMasters.size()!=0 ) {
            return true;
        }
        return false;
    }

}
