package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focowell.EFormsApiApplication;
import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.UserDao;
import com.focowell.dao.WorkflowNodeDao;
import com.focowell.model.User;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;
import com.focowell.service.ActionEventObjectService;
import com.focowell.service.ActionEventParamObjectService;
import com.focowell.service.WorkflowLinkService;
import com.focowell.service.WorkflowNodeService;

@Service(value = "workflowNodeService")
public class WorkflowNodeServiceImpl implements WorkflowNodeService {

	private static final Logger logger = LogManager.getLogger(WorkflowNodeServiceImpl.class);
	@Autowired
	private WorkflowNodeDao workflowNodeDao;
	
	@Autowired
	private WorkflowLinkService workflowLinkService;
	
	@Autowired
	private ActionEventObjectService actionEventObjectService;
	
	@Autowired
	private ActionEventParamObjectService actionEventParamObjectService;
	
	@Override
	public List<WorkflowNode> findAll() {
		List<WorkflowNode> list = new ArrayList<>();
		workflowNodeDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowNode> findAllByWorkflow(long workflowId) {
		List<WorkflowNode> list = new ArrayList<>();
		workflowNodeDao.findAllByWorkflowIdJPQL(workflowId).iterator().forEachRemaining(list::add);
		
//		workflowNodeDao.findAllByWorkflowMaster_Id(workflowId).iterator().forEachRemaining(list::add);
//		list.forEach(node->{
//			node.getActionEventObjects().size();
//			
//		});
		return list;
	}

	@Override
	public void delete(long id) {
		workflowNodeDao.deleteById(id);
		
	}
	@Override
//	@Transactional
	public void removeByWorkflow(long workflowId) {
		workflowNodeDao.deleteByWorkflowIdJPQL(workflowId);
		
	}
	
	@Override
	public WorkflowNode findOne(String workflowNodeName) {
//		return workflowNodeDao.findByWorkflowNodeName(workflowNodeName).get(0);
		return new WorkflowNode();
	}

	@Override
	public WorkflowNode findById(Long id) {
		return workflowNodeDao.findById(id).get();
	}

	@Override
	public WorkflowNode save(WorkflowNode workflowNode) throws AlreadyExistsException {
//		if(workflowNodeExist(workflowNode.getWorkflowNodeName()))
//		{
//			throw new AlreadyExistsException(
//		              "There is an WorkflowNode with the same name "
//		              +  workflowNode.getWorkflowNodeName() );
//		}
		return workflowNodeDao.save(workflowNode);
	}
	@Override
	@Transactional
	public boolean saveAll(WorkflowMaster workflow) throws AlreadyExistsException {
//		if(workflowNodeExist(workflowNode.getWorkflowNodeName()))
//		{
//			throw new AlreadyExistsException(
//		              "There is an WorkflowNode with the same name "
		
//		              +  workflowNode.getWorkflowNodeName() );
//		}
		actionEventParamObjectService.removeByWorkflow(workflow.getId());
		actionEventObjectService.removeByWorkflow(workflow.getId());
		workflowLinkService.removeByWorkflow(workflow.getId());
		removeByWorkflow(workflow.getId());
		
		if(workflow.getWorkflowLinkList()!=null && !workflow.getWorkflowLinkList().isEmpty())
			workflow.getWorkflowLinkList().forEach(u -> {
					u.setLinkId(0L);
					u.getSourceNode().setNodeId(0L);
					u.getTargetNode().setNodeId(0L);
				});
		
		
		
		Set<WorkflowLink> sourceLinkList=new HashSet<WorkflowLink>();
		Set<WorkflowLink> targetLinkList=new HashSet<WorkflowLink>();
		Set<WorkflowNode> nodeList=workflow.getWorkflowNodeList();
		for (WorkflowNode workflowNode : nodeList) {
			workflowNode.setNodeId(0L);
			try
			{
				sourceLinkList=workflow.getWorkflowLinkList().stream()
					    .filter(l -> l.getSourceNode().getId().equalsIgnoreCase(workflowNode.getId())).collect(Collectors.toSet());
				targetLinkList=workflow.getWorkflowLinkList().stream()
					    .filter(l -> l.getTargetNode().getId().equalsIgnoreCase (workflowNode.getId())).collect(Collectors.toSet());
			}
			catch(Exception ex)
			{
				logger.debug("--saveAll--"+ex.getMessage());
			}
			sourceLinkList.forEach(u -> {
				u.setSourceNode(workflowNode);
				u.setWorkflowMaster(workflow);
			});
			targetLinkList.forEach(u -> {
				u.setTargetNode(workflowNode);
				u.setWorkflowMaster(workflow);
			});
			
			workflowNode.setSourceLinkList(sourceLinkList);
			workflowNode.setTargetLinkList(targetLinkList);
			
			if(workflowNode.getActionEventObjects()!=null )
				workflowNode.getActionEventObjects().forEach(eventObj->{
					eventObj.setId(0L);
					eventObj.setWorkflowNode(workflowNode);
					eventObj.getActionEventParamObjects().forEach(paramObj->{
						paramObj.setId(0L);
						paramObj.setActionEventObject(eventObj);
					});
				});
			
			workflowNode.setWorkflowMaster(workflow);
		}
		
		workflowNodeDao.saveAll( workflow.getWorkflowNodeList());
		return true;
	}

	@Override
	public WorkflowNode update(WorkflowNode workflowNode) {
		WorkflowNode updateWorkflowNode =workflowNodeDao.findById(workflowNode.getNodeId()).get();
				
		updateWorkflowNode.setLabel(workflowNode.getLabel());
		updateWorkflowNode.setNodeType(workflowNode.getNodeType());
					
        return workflowNodeDao.save(updateWorkflowNode);
	}
	
	private boolean workflowNodeExist(String workflowNodeName) {
//        List<WorkflowNode> nodes = workflowNodeDao.findByWorkflowNodeName(workflowNodeName);
//        if (categories != null && categories.size()!=0 ) {
//            return true;
//        }
        return false;
    }

}
