package com.focowell.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl.ConstraintType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.focowell.EFormsApiApplication;
import com.focowell.dao.WorkflowTrackMasterDao;
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.model.User;
import com.focowell.model.UserRoles;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;
import com.focowell.model.WorkflowNodeType;
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackDet;
import com.focowell.model.WorkflowTrackMaster;
import com.focowell.model.dto.VirtualRowRecordsDto;
import com.focowell.service.FileService;
import com.focowell.service.FormDesignService;
import com.focowell.service.UserService;
import com.focowell.service.VirtualTableConstraintsService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableRecordsMongoService;
import com.focowell.service.VirtualTableSequenceService;
import com.focowell.service.WorkflowLinkService;
import com.focowell.service.WorkflowMasterService;
import com.focowell.service.WorkflowTrackDetService;
import com.focowell.service.WorkflowTrackMasterService;

import javassist.NotFoundException;

@Service(value = "workflowTrackMasterService")
public class WorkflowTrackMasterServiceImpl implements WorkflowTrackMasterService {
	private static final Logger logger = LogManager.getLogger(WorkflowTrackMasterServiceImpl.class);
	
	@Autowired
	private WorkflowTrackMasterDao workflowTrackMasterDao;
	
	@Autowired
	private WorkflowTrackDetService workflowTrackDetService;
	
	@Autowired
	private WorkflowLinkService workflowLinkService;
	
	@Autowired
	private VirtualTableSequenceService virtualTableSequenceService;
	
	@Autowired
	private VirtualTableRecordsMongoService virtualTableRecordsService;
	
	@Autowired
	private VirtualTableFieldsService virtualTableFieldService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkflowMasterService workflowMasterService;
	
	@Autowired
	private FormDesignService formDesignService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private VirtualTableConstraintsService virtualTableConstraintsService;
	
	
	
	@Override
	public List<WorkflowTrackMaster> findAll() {
		List<WorkflowTrackMaster> list = new ArrayList<>();
		workflowTrackMasterDao.findAll().iterator().forEachRemaining(trackMaster->{
			Hibernate.initialize(trackMaster.getRequestedUser());
			Hibernate.initialize(trackMaster.getWorkflowMaster());
			list.add(trackMaster);
		});
		return list;
	}
	@Override
	public List<WorkflowTrackMaster> findAllByWorkflow(Long workflowId) {
		List<WorkflowTrackMaster> list = new ArrayList<>();
		workflowTrackMasterDao.findAllByWorkflowIdJPQL(workflowId).iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<WorkflowTrackMaster> findAllByProcess(Long processId) {
		List<WorkflowTrackMaster> list = new ArrayList<>();
		workflowTrackMasterDao.findAllByProcessIdJPQL(processId).iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public List<WorkflowTrackMaster> findAllByUser() {
		List<WorkflowTrackMaster> list = new ArrayList<>();
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findOne(auth.getName());
		workflowTrackMasterDao.findAllByUserJPQL(user.getId()).iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public void delete(long id) {
		workflowTrackMasterDao.deleteById(id);
		
	}
	
	@Override
	public WorkflowTrackMaster findOne(String workflowTrackMasterName) {
		return null;
	}

	@Override
	public WorkflowTrackMaster findById(Long id) {
		
		return workflowTrackMasterDao.findById(id).get();
	}

	@Override
	public WorkflowTrackMaster save(WorkflowTrackMaster workflowTrackMaster)  {
		
		return workflowTrackMasterDao.save(workflowTrackMaster);
	}

	@Override
	public WorkflowTrackMaster update(WorkflowTrackMaster workflowTrackMaster) {
		WorkflowTrackMaster updateWorkflowTrackMaster =workflowTrackMasterDao.findById(workflowTrackMaster.getId()).get();
		
					
        return workflowTrackMasterDao.save(updateWorkflowTrackMaster);
	}
	@Override
	public WorkflowTrackMaster completeWorkflow(WorkflowTrackMaster workflowTrackMaster) {
		WorkflowTrackMaster updateWorkflowTrackMaster =workflowTrackMasterDao.findById(workflowTrackMaster.getId()).get();
		updateWorkflowTrackMaster.setCompleted(true);
					
        return workflowTrackMasterDao.save(updateWorkflowTrackMaster);
	}
	
	//find all published and open workflows
	
	@Override
	public List<WorkflowStage> findAllPublishedWorkflows(){
		List<WorkflowStage> workflowStages=new ArrayList<WorkflowStage>();
		getAllPublishedWorkflows(workflowStages);
		
		return workflowStages;
	}
	@Override
	public List<WorkflowStage> findAllOpenWorkflowTracks(){
		List<WorkflowStage> workflowStages=new ArrayList<WorkflowStage>();
		
		getAllOpenWorkflows(workflowStages);
		
		return workflowStages;
	}
	private void getAllPublishedWorkflows(List<WorkflowStage> workflowStages) {
		workflowMasterService.findAllPublished().forEach(workflow->{
			
			WorkflowStage stage=new WorkflowStage();

			final WorkflowLink firstLink=workflowLinkService.findStartNodeByWorkflow(workflow.getId());
			if(firstLink!=null) {
				Hibernate.initialize(firstLink.getSourceNode());
				Hibernate.initialize(firstLink.getTargetNode());
//				stage.setPrevTrack(null);
				stage.setFormNode(firstLink.getTargetNode());
				stage.setWorkflowMaster(workflow);
				
				if(formIsAccessable(stage.getFormNode().getFormMaster())) //check whether this form is accessable for user
					workflowStages.add(stage);
			}
		});
	}
	private void getAllOpenWorkflows(List<WorkflowStage> workflowStages) {
//		HashMap<Long,List<WorkflowTrackDet>> allTrackDetListUnderTrackMaster=new HashMap<>();
		for(WorkflowTrackDet track: workflowTrackDetService.findAllByOpen(true)){
			
			if(!isWorkflowValid(track))
				continue;
						
			WorkflowStage stage=new WorkflowStage();
			stage.setPrevTracks(new ArrayList<>());
			
			stage.setWorkflowTrackDet(track);
			Hibernate.initialize(track.getWorkflowTrackMaster().getWorkflowMaster());
			Hibernate.initialize(track.getWorkflowTrackMaster().getWorkflowMaster().getProcess());
			stage.setWorkflowMaster(track.getWorkflowTrackMaster().getWorkflowMaster());
			
//			stage.setFormNode(track.getWorkflowNextNode()); //Assuming next node will be form TODO: more validation required
//			if(formIsAccessable(stage.getFormNode().getFormMaster())) //check whether this form is accessable for user
//				workflowStages.add(stage);
			
			
//			if(!allTrackDetListUnderTrackMaster.containsKey(track.getWorkflowTrackMaster().getId())) {
//				allTrackDetListUnderTrackMaster.put(track.getWorkflowTrackMaster().getId(), workflowTrackDetService.findAllByWorkflowTrackMaster(track.getWorkflowTrackMaster().getId())) ;
//			}
			List<WorkflowLink> links=workflowLinkService.findBySourceId(track.getWorkflowActionNode().getNodeId());
			if(links!=null) {
				links.forEach(link->{
					Hibernate.initialize(link.getSourceNode());
					Hibernate.initialize(link.getTargetNode());
					Hibernate.initialize(track.getWorkflowPrevTrackDetList());
//					stage.setPrevTrack(track);
					stage.setFormNode(link.getTargetNode());
					
					
					if(formIsAccessable(stage.getFormNode().getFormMaster())){ //check whether this form is accessable for user
						
//						stage.setPrevTracks(getPrevTracksForOpenStage(track,allTrackDetListUnderTrackMaster.get(track.getWorkflowTrackMaster().getId())));
						workflowStages.add(stage);
					}
				});
				
			}
		}
	}
	private List<WorkflowTrackDet> getPrevTracksForOpenStage(WorkflowTrackDet openTrack,List<WorkflowTrackDet> workflowTrackDetialsList) {	
		List<WorkflowTrackDet> prevTracks=new ArrayList<>();
		WorkflowTrackDet prevTrack=openTrack;

		do {
			prevTracks.add(prevTrack);
			prevTrack=prevTrack.getWorkflowPrevTrack();
			if(prevTrack!=null) {
				long prevTrackId= prevTrack.getWorkflowPrevTrack().getId();
				prevTrack=workflowTrackDetialsList.stream().filter(track->track.getId()==prevTrackId).findFirst().orElse(null);
			}
		}while(prevTrack!=null);
		return prevTracks;
	}
	private boolean isWorkflowValid(WorkflowTrackDet track) {
		boolean valid=true;
		if(track.getWorkflowActionNode().getNodeType()==WorkflowNodeType.CHILD_WORKFLOW) { //check child workflow is completed if it is child workflow node
			Hibernate.initialize(track.getChildWorkflowTrackMaster());
			valid=track.getChildWorkflowTrackMaster().isCompleted();
			
			
		}
		return valid;
	}
	
//	private boolean IsAllChildWorkflowCompletedBeforeThisNode(WorkflowNode workflowNode,long workflowTrackMasterId) {
//		
//		for(WorkflowTrackDet  childWorkflowTrackDet:workflowTrackDetService.findAllByWorkflowTrackMasterWhichHavingChildWorkflow(workflowTrackMasterId)) {
//			Hibernate.initialize(childWorkflowTrackDet.getChildWorkflowTrackMaster());
//			if(childWorkflowTrackDet.getWorkflowNextNode().getId()==workflowNode.getId() && !childWorkflowTrackDet.getChildWorkflowTrackMaster().isCompleted()) 
//				return false;
//		}
//		return true;
//		
//	}
	//this function will be called when user click on the workflow link
	@Override
	public WorkflowStage execute(WorkflowStage workflowStage) throws NotFoundException {
		
		WorkflowNode formNode=null;
		WorkflowStage nextStage=null;
		
		List<WorkflowLink> workflowLinks= workflowLinkService.findAllByWorkflow(workflowStage.getWorkflowMaster().getId()); //get all workflow links under this workflow
		formNode=getNextFormNode(workflowStage.getWorkflowTrackDet(),workflowLinks); //get next available form
		
		
		if(formNode!=null ) {
			
			if(!formIsAccessable(formNode.getFormMaster())) //check whether this form is accessable for user
				throw new AccessDeniedException("You dont have access privilage to this form");
			Hibernate.initialize(formNode.getFormMaster().getVirtualTableMaster()); //lazy initialization of vrtual table
			Hibernate.initialize(formNode.getFormMaster().getFormDesignList()); //lazy initialization of form design
			boolean isWorkflowStarting=workflowStage.getWorkflowTrackDet()==null;
			if(!isWorkflowStarting ) {  //if it is not start form then fetch data that is submitted previously
				formNode.getFormMaster().setVirtualRowRecordsDto(
						virtualTableRecordsService.findRowByTableAndPk(formNode.getFormMaster().getVirtualTableMaster().getId(),workflowStage.getWorkflowTrackDet().getWorkflowTrackMaster().getDataId()));
				if(formNode.getFormMaster().getVirtualRowRecordsDto()==null) {
					WorkflowTrackMaster trackMaster= findById(workflowStage.getWorkflowTrackDet().getWorkflowTrackMaster().getId());
					workflowStage.getWorkflowTrackDet().setWorkflowTrackMaster(trackMaster);
					trackMaster.setCompleted(true);
					workflowStage.getWorkflowTrackDet().setOpen(false);
//					workflowTrackMasterDao.save(trackMaster);
					workflowTrackDetService.save(workflowStage.getWorkflowTrackDet());
					throw new NotFoundException("This workflow closed forcily since it coudn't find corresponding virtual table record ");
				}
				else
					setValuesToForm(formNode.getFormMaster().getVirtualRowRecordsDto().getRecords(), formNode.getFormMaster().getFormDesignList());	 //setting form value if form is view
			}
			
			formNode.getFormMaster().getFormDesignList().forEach(design->{
				formDesignService.populateRefValuesIfForeignKey(design); //setting the reference values if it is a foreign key
			});
			
			Set<WorkflowNode> actionNodes=getAllActionNodesByForm(workflowLinks,formNode.getNodeId()); //get all action nodes under form
			
			
			nextStage=new WorkflowStage();  
			nextStage.setFormNode(formNode);
		    nextStage.setActionNodes(actionNodes);
		    nextStage.setWorkflowTrackDet(workflowStage.getWorkflowTrackDet());
		    nextStage.setWorkflowMaster(workflowStage.getWorkflowMaster());
//		    nextStage.setPrevTrack(workflowStage.getPrevTrack());
			
		}
		return nextStage;
		
		
	}
	private WorkflowNode getNextFormNode(WorkflowTrackDet prevTrackDet,List<WorkflowLink> workflowLinks) {
		WorkflowNode formNode=null;
		if(prevTrackDet==null) {
			
			
			formNode=workflowLinks.stream()
					.filter(w->w.getSourceNode().getNodeType()==WorkflowNodeType.START && w.getTargetNode().getNodeType()==WorkflowNodeType.FORM)
					.map(WorkflowLink::getTargetNode)
					.findFirst().orElse(null);

						
		}
		else {
			WorkflowNode lastNode=prevTrackDet.getWorkflowActionNode();
			formNode=workflowLinks.stream()
					.filter(w->w.getSourceNode().getNodeId()==lastNode.getNodeId() && w.getTargetNode().getNodeType()==WorkflowNodeType.FORM)
					.map(WorkflowLink::getTargetNode)
					.findFirst().orElse(null);
			
		}
		return formNode;
	}
	
	private void setValuesToForm(List<VirtualTableRecords> records, Set<FormDesign> designList) {
		Set<FormDesign> nonGriddesignList=designList.stream()
				.filter(design->design.getComponentType()!=FormComponentType.GRID)
				.collect(Collectors.toSet());
		if(records!=null) { //setting form value if form is view
			nonGriddesignList.forEach(design->{
				final Optional<String> val=records.stream()
						.filter(record->
								design.getFormComponent().getVirtualTableField()
								.getFieldName().equals(record.getVirtualTableFields().getFieldName()))
								.map(VirtualTableRecords::getStringValue)
								.map(Optional::ofNullable).findFirst().orElse(null);
				if(design.getComponentType()==FormComponentType.IMG) {
					if(val!=null & val.isPresent()) {
						File file =fileService.getFile(val.get());
						try {
							String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
							design.getFormComponent().setComponentValue(encodeImage);
						} catch (IOException e) {
							
						}
					}
				}
				else
					design.getFormComponent().setComponentValue(val!=null?(val.isPresent()?val. get():null):null);
				
				
			});
		}
	}
	private Set<WorkflowNode> getAllActionNodesByForm(List<WorkflowLink> workflowLinks,long formId){
		return workflowLinks.stream()
			.filter(w->w.getSourceNode().getNodeId()==formId
			&& (w.getTargetNode().getNodeType()==WorkflowNodeType.ACTION || w.getTargetNode().getNodeType()==WorkflowNodeType.STOP))
			.map(WorkflowLink::getTargetNode)
			.collect(Collectors.toSet());
	}
	/*
	 * Saving the current form data, save data for tracking workflow, initiate child workflows if available
	 */
	@Override
	@Transactional
	public void submitAction(WorkflowStage WorkflowStage) throws Exception {
		logger.debug("Entering submitAction workflow id: "+WorkflowStage.getWorkflowMaster().getId()+" ;  name: "+WorkflowStage.getWorkflowMaster().getWorkflowName());
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findOne(auth.getName());
		
		long pkValue=0;
		
		WorkflowTrackMaster workflowTrackMaster=null;
		WorkflowNode currentFormNode=null;
		WorkflowNode currentWorkflowNode=null;
		WorkflowNode currentSourceNode=null;
		WorkflowTrackDet prevTrack=WorkflowStage.getWorkflowTrackDet();
		
		
		
		boolean isWorkflowStarting=WorkflowStage.getWorkflowTrackDet()==null;
		boolean isNextNodeChildWorkflow=false;
		currentFormNode=WorkflowStage.getFormNode();
		currentSourceNode=WorkflowStage.getSelectedActionNode();
		List<WorkflowLink> nextLinks=workflowLinkService.findBySourceId(currentSourceNode.getNodeId()); //get next link
		boolean isWorkflowTrackCompleted=isWorkflowCompleted(nextLinks);
		
		//data saving from form and setting workflow track master and closing current stage if it is not starting of workflow
		if(isWorkflowStarting) {
			VirtualRowRecordsDto savedVirtualRowRecordsDto=virtualTableRecordsService.saveVirtualRecordsFromForm(currentFormNode.getFormMaster()); //Saving form data
			pkValue=savedVirtualRowRecordsDto.getPkValue();
			workflowTrackMaster=new WorkflowTrackMaster(WorkflowStage.getWorkflowMaster(),user,pkValue,new HashSet<WorkflowTrackDet>());
			
		}
		else {
			workflowTrackMaster=findById(WorkflowStage.getWorkflowTrackDet().getWorkflowTrackMaster().getId()); //find track master if it is not first stage
			workflowTrackMaster.getWorkflowTrackDetList().size(); //initialize existing track records
			VirtualRowRecordsDto savedVirtualRowRecordsDto=virtualTableRecordsService.updateVirtualRecordsFromForm(currentFormNode.getFormMaster(),workflowTrackMaster.getDataId()); //Updating form data
			pkValue=savedVirtualRowRecordsDto.getPkValue();
			closeCurrentStage(WorkflowStage.getWorkflowTrackDet()); //closing the current stage
			
		}
		//end data saving from form and setting workflow track master and closing current stage if it is not starting of workflow
		
		if(isWorkflowTrackCompleted) {
			List<WorkflowTrackDet> openWorkflowTrackDetList =workflowTrackDetService.findAllByWorkflowTrackMaster(workflowTrackMaster.getId()); //check any other node is running in any other branch
			if(openWorkflowTrackDetList==null || !openWorkflowTrackDetList.stream().anyMatch(trackDet->trackDet.isOpen()))
				workflowTrackMaster.setCompleted(true); //update status if workflow is completed
		}
		
		
		//end setting workflow track master 
		
		
		prevTrack=setChildNodeTrackDetailsToWorkflowTrack(user,workflowTrackMaster,pkValue,true,currentFormNode,null
				,currentFormNode,prevTrack);//tracking (node before form) to form
		
		
		if(nextLinks!=null && nextLinks.size()!=0 ) {
			boolean AreAllChildWorkflows=nextLinks.stream().allMatch(link->link.getTargetNode().getNodeType()==WorkflowNodeType.CHILD_WORKFLOW);
			prevTrack=setChildNodeTrackDetailsToWorkflowTrack(user,workflowTrackMaster,pkValue,AreAllChildWorkflows
					,currentSourceNode,null,currentFormNode,prevTrack); //this is for tracking:form to action. 
																			  //if all children are workflows, then this track should be closed
																			  //if any of child is form, then this track should be opened
			for(WorkflowLink nextLink:nextLinks) {
				WorkflowNode currentTargetNode=null;
				
				WorkflowMaster childWorkflow=null;
				WorkflowTrackMaster childWorkflowTrack=null;
				
				isNextNodeChildWorkflow=isChildWorkflow(nextLink);
				
				currentTargetNode=nextLink.getTargetNode();
				currentWorkflowNode=isNextNodeChildWorkflow?currentTargetNode:currentSourceNode; //if next node is child workflow, then this workflow should continue after the completion of child workflow 
				
				
				if(isNextNodeChildWorkflow) {
//					List<WorkflowLink> nextLinksAfterChildWorkflow=workflowLinkService.findBySourceId(currentWorkflowNode.getNodeId()); //get next link
					childWorkflow=nextLink.getTargetNode().getChildWorkflow(); //get child workflow if available
					childWorkflowTrack=initiateChildWorkflow(childWorkflow,user,pkValue,currentFormNode);
					
					
					setChildNodeTrackDetailsToWorkflowTrack(user,workflowTrackMaster,pkValue,isWorkflowTrackCompleted
							,currentWorkflowNode,childWorkflowTrack,currentFormNode,prevTrack); //action to child workflow
					
				}
				
			}
		}
		else {
			currentWorkflowNode=currentSourceNode;
			setChildNodeTrackDetailsToWorkflowTrack(user,workflowTrackMaster,pkValue,isWorkflowTrackCompleted,currentWorkflowNode,null,currentFormNode,prevTrack);
		}
		
								
		save(workflowTrackMaster);
		
		logger.debug("exitting submitAction: workflow id: "+WorkflowStage.getWorkflowMaster().getId()+" ,pkValue: "	+pkValue);
				
	}
	private boolean isWorkflowCompleted(List<WorkflowLink> nextLinks) {
		//if source node is stop
		//all child nodes are stop
		return nextLinks==null || nextLinks.size()==0 || nextLinks.get(0).getSourceNode().getNodeType()==WorkflowNodeType.STOP
				|| nextLinks.stream().allMatch(link->link.getTargetNode().getNodeType()==WorkflowNodeType.STOP);
	}
	private boolean isChildWorkflow(WorkflowLink nextLink) {
		if(nextLink!=null && nextLink.getTargetNode().getNodeType()==WorkflowNodeType.CHILD_WORKFLOW) 
			return true;
		return false;
	}
	private WorkflowTrackMaster initiateChildWorkflow(WorkflowMaster childWorkflow,User user,long pkValue,WorkflowNode currentFormNode) throws Exception {
				
		Hibernate.initialize(childWorkflow);
		
		VirtualTableMaster parentTable=currentFormNode.getFormMaster().getVirtualTableMaster(); //table of the form which called the child workflow
		List<WorkflowLink> workflowLinks= workflowLinkService.findAllByWorkflow(childWorkflow.getId()); //get all workflow links under this workflow
		WorkflowNode childFormNode=getNextFormNode(null,workflowLinks); //get next available form
		VirtualTableMaster childTable=childFormNode.getFormMaster().getVirtualTableMaster(); //table of the form which will be showing for the child workflow
		
		if(parentTable.getId()!=childTable.getId()) { //if tables of parent and child are different, then the child should refer the parent 
			
//			VirtualTableField parentWorkflowRefField=findParentWorkflowReferringField(parentTable,childTable); //find field of child table which is referring parent table
		
			
			List<VirtualTableRecords> records=new ArrayList<>();
						
			for(VirtualTableField field:virtualTableFieldService.findAllByTableId(childTable.getId())) {
				VirtualTableRecords virtualTableRecord=new VirtualTableRecords();
				virtualTableRecord.setVirtualTableFields(field);
				
				if(field.getFieldConstraintList()!=null && field.getFieldConstraintList().size()!=0) {
					boolean IsReferringParentTable=false;
					IsReferringParentTable= field.getFieldConstraintList().stream()
					.anyMatch(constraint->constraint.getConstraintType()==VirtualTableConstraintType.FOREIGN_KEY 
					&& constraint.getForeignConstraint().getVirtualTableField().getVirtualTableMaster().getId()==parentTable.getId());
					if(IsReferringParentTable)
						virtualTableRecord.setStringValue(pkValue+"");
				}
				
				records.add(virtualTableRecord);
			}
			VirtualRowRecordsDto childVirtualRowRecordsDto=new VirtualRowRecordsDto(records,0,childTable);
			
			VirtualRowRecordsDto savedVirtualRowRecordsDto=virtualTableRecordsService.saveOneRowRecordAfterCheckPkValue(childVirtualRowRecordsDto); //inserting one record child workflow
			pkValue=savedVirtualRowRecordsDto.getPkValue();
		}
		
		
		WorkflowLink firstLink=workflowLinkService.findStartNodeByWorkflow(childWorkflow.getId());
		WorkflowTrackMaster childWorkflowTrackMaster=new WorkflowTrackMaster(childWorkflow,user,pkValue,new HashSet<WorkflowTrackDet>(),new HashSet<WorkflowTrackDet>());
		
		setChildNodeTrackDetailsToWorkflowTrack(user,childWorkflowTrackMaster,pkValue,isWorkflowCompleted(new ArrayList<WorkflowLink>(Arrays.asList(firstLink))),
				firstLink.getSourceNode(),null,currentFormNode,null);
		
		save(childWorkflowTrackMaster);
		return childWorkflowTrackMaster;
	}
	private VirtualTableField findParentWorkflowReferringField(VirtualTableMaster parentTable,VirtualTableMaster childTable) {
		List<VirtualTableConstraints> childWorkflowTableConstraints= virtualTableConstraintsService.findAllByTableId(childTable.getId());
		VirtualTableField parentWorkflowRefField= childWorkflowTableConstraints.stream()
		.filter(constraint->constraint.getConstraintType()==VirtualTableConstraintType.FOREIGN_KEY 
		&& constraint.getForeignConstraint().getVirtualTableField().getVirtualTableMaster().getId()==parentTable.getId())
		.map(VirtualTableConstraints::getVirtualTableField).findFirst().orElse(null); //get field which is referring parent table
		return parentWorkflowRefField;
	}
	
	private WorkflowTrackDet setChildNodeTrackDetailsToWorkflowTrack(User user,WorkflowTrackMaster workflowTrackMaster,long pkValue,boolean isWorkflowCompleted
			,WorkflowNode currentWorkflowNode,WorkflowTrackMaster childWorkflowTrack,WorkflowNode currentFormNode,WorkflowTrackDet prevWorkflowTrackDet) {
		
		WorkflowTrackDet workflowTrackDet=new WorkflowTrackDet();
		workflowTrackDet.setWorkflowPrevTrackDetList(new HashSet<WorkflowTrackDet>());
		
		workflowTrackMaster.getWorkflowTrackDetList().add(workflowTrackDet);	
		
		
		workflowTrackDet.setWorkflowTrackMaster(workflowTrackMaster);
		workflowTrackDet.setWorkflowActionNode(currentWorkflowNode);
		workflowTrackDet.setWorkflowFormNode(currentFormNode);
		workflowTrackDet.setOpen(!isWorkflowCompleted); //open next stage if source or dest node is not STOP
				
		workflowTrackDet.setAccessUser(user);
		if(prevWorkflowTrackDet!=null) {
			workflowTrackDet.setWorkflowPrevTrack(prevWorkflowTrackDet);
			prevWorkflowTrackDet.getWorkflowPrevTrackDetList().add(workflowTrackDet);
		}
		if(childWorkflowTrack!=null) {
			workflowTrackDet.setChildWorkflowTrackMaster(childWorkflowTrack);
			childWorkflowTrack.getParentWorkflowTrackDetList().add(workflowTrackDet);
		}
		return workflowTrackDet;
		
	}
	private WorkflowTrackMaster updateWorkflow(User user,WorkflowTrackMaster workflowTrackMaster,long pkValue, boolean isWorkflowCompleted,WorkflowNode currentWorkflowNode,WorkflowTrackMaster childWorkflowTrack,WorkflowNode currentFormNode) {
		
	
		WorkflowTrackDet workflowTrackDet=new WorkflowTrackDet();
		
		workflowTrackMaster.getWorkflowTrackDetList().add(workflowTrackDet);	
		workflowTrackMaster.setCompleted(isWorkflowCompleted); //update status if workflow is completed
		
		workflowTrackDet.setWorkflowTrackMaster(workflowTrackMaster);
		workflowTrackDet.setWorkflowActionNode(currentWorkflowNode);
		workflowTrackDet.setWorkflowFormNode(currentFormNode);
		workflowTrackDet.setOpen(!isWorkflowCompleted); //open next stage if source or dest node is not STOP
				
		workflowTrackDet.setAccessUser(user);
		workflowTrackDetService.save(workflowTrackDet);
		workflowTrackDet.setChildWorkflowTrackMaster(childWorkflowTrack); //set child workflow if available
		return workflowTrackMaster;
	}
	private void closeCurrentStage(WorkflowTrackDet currentWorkflowTrackDet) {
		currentWorkflowTrackDet.setOpen(false);
		workflowTrackDetService.update(currentWorkflowTrackDet); //closing the current stage
	}
	@Override
	public long mergeVirtualRecords(FormMaster form,long pkValue ) throws Exception {
		
		if(form.getFormDesignList()!=null && !form.getFormDesignList().isEmpty()) {
			return 0;
		}
		
		if(pkValue==0) 
			return virtualTableRecordsService.saveVirtualRecordsFromForm(form).getPkValue();
	
		return virtualTableRecordsService.updateVirtualRecordsFromForm(form,pkValue).getPkValue();
			
	}
	
	
	
	
	
	
	@Override
	public boolean formIsAccessable(FormMaster form) {
		//check whether this form is accessable for user
		if(form==null)
			return false;
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findOne(auth.getName());
		form.getAccessGroups().size();

		for(UserRoles group:form.getAccessGroups()) {
			if(user.getUserRoles().stream().filter(role->role.getType().equals(group.getType())).findFirst().orElse(null)!=null) {
				return true;
			}
		}
		return false;
	}
	

}
