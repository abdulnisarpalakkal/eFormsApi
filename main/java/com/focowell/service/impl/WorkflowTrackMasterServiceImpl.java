package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.WorkflowTrackMasterDao;
import com.focowell.dao.WorkflowLinkDao;
import com.focowell.dao.UserDao;
import com.focowell.model.WorkflowTrackMaster;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.model.User;
import com.focowell.model.UserRoles;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.VirtualTableSequence;
import com.focowell.model.WorkflowLink;
import com.focowell.model.WorkflowNode;
import com.focowell.model.WorkflowNodeType;
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackDet;
import com.focowell.service.UserService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableRecordsService;
import com.focowell.service.VirtualTableSequenceService;
import com.focowell.service.WorkflowLinkService;
import com.focowell.service.WorkflowMasterService;
import com.focowell.service.WorkflowTrackDetService;
import com.focowell.service.WorkflowTrackMasterService;

@Service(value = "workflowTrackMasterService")
public class WorkflowTrackMasterServiceImpl implements WorkflowTrackMasterService {

	@Autowired
	private WorkflowTrackMasterDao workflowTrackMasterDao;
	
	@Autowired
	private WorkflowTrackDetService workflowTrackDetService;
	
	@Autowired
	private WorkflowLinkService workflowLinkService;
	
	@Autowired
	private VirtualTableSequenceService virtualTableSequenceService;
	
	@Autowired
	private VirtualTableRecordsService virtualTableRecordsService;
	
	@Autowired
	private VirtualTableFieldsService virtualTableFieldService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkflowMasterService workflowMasterService;
	
	@Override
	public List<WorkflowTrackMaster> findAll() {
		List<WorkflowTrackMaster> list = new ArrayList<>();
		workflowTrackMasterDao.findAll().iterator().forEachRemaining(list::add);
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
	
	//find all published and open workflows
	
	@Override
	public List<WorkflowStage> findAllOpenWorkflowTracks(){
		List<WorkflowStage> workflowStages=new ArrayList<WorkflowStage>();
		getAllPublishedWorkflows(workflowStages);
		getAllOpenWorkflows(workflowStages);
		
		return workflowStages;
	}
	private void getAllPublishedWorkflows(List<WorkflowStage> workflowStages) {
		workflowMasterService.findAllPublished().forEach(workflow->{
			
			WorkflowStage stage=new WorkflowStage();

			final WorkflowLink firstLink=workflowLinkService.findStartNodeByWorkflow(workflow.getId());
			
			Hibernate.initialize(firstLink.getTargetNode());
			stage.setFormNode(firstLink.getTargetNode());
			stage.setWorkflowMaster(workflow);
			
			if(formIsAccessable(stage.getFormNode().getFormMaster())) //check whether this form is accessable for user
				workflowStages.add(stage);
		});
	}
	private void getAllOpenWorkflows(List<WorkflowStage> workflowStages) {
		workflowTrackDetService.findAllByOpen(true).forEach(track->{
			
			WorkflowStage stage=new WorkflowStage();
			stage.setWorkflowTrackDet(track);
			Hibernate.initialize(track.getWorkflowTrackMaster().getWorkflowMaster());
			Hibernate.initialize(track.getWorkflowTrackMaster().getWorkflowMaster().getProcess());
			stage.setWorkflowMaster(track.getWorkflowTrackMaster().getWorkflowMaster());
			
			WorkflowLink link=workflowLinkService.findBySourceId(track.getWorkflowNode().getNodeId());
			Hibernate.initialize(link.getTargetNode());
			stage.setFormNode(link.getTargetNode());
			if(formIsAccessable(stage.getFormNode().getFormMaster())) //check whether this form is accessable for user
				workflowStages.add(stage);
		});
	}
	
	//this function will be called when user click on the workflow link
	@Override
	public WorkflowStage execute(WorkflowStage workflowStage) {
		
		WorkflowNode formNode=null;
		WorkflowStage nextStage=null;
		
		List<WorkflowLink> workflowLinks= workflowLinkService.findAllByWorkflow(workflowStage.getWorkflowMaster().getId()); //get all workflow links under this workflow
		formNode=getNextFormNode(workflowStage.getWorkflowTrackDet(),workflowLinks); //get next available form
		
		
		if(formNode!=null ) {
			
			if(!formIsAccessable(formNode.getFormMaster())) //check whether this form is accessable for user
				throw new AccessDeniedException("You dont have access privilage to this form");
			Hibernate.initialize(formNode.getFormMaster().getVirtualTableMaster()); //lazy initialization of vrtual table
			Hibernate.initialize(formNode.getFormMaster().getFormDesignList()); //lazy initialization of form design

			if(workflowStage.getWorkflowTrackDet()!=null ) {  //if it is not start form then fetch data that is submitted previously
				formNode.getFormMaster().setVirtualTableRecords(
						virtualTableRecordsService.findAllByPk(workflowStage.getWorkflowTrackDet().getWorkflowTrackMaster().getDataId()));
				setValuesToForm(formNode.getFormMaster().getVirtualTableRecords(), formNode.getFormMaster().getFormDesignList());	 //setting form value if form is view
			}
			
			formNode.getFormMaster().getFormDesignList().forEach(design->{
				populateRefValuesIfForeignKey(design); //setting the reference values if it is a foreign key
			});
			
			Set<WorkflowNode> actionNodes=getAllActionNodesByForm(workflowLinks,formNode.getNodeId()); //get all action nodes under form
			
			
			nextStage=new WorkflowStage();  
			nextStage.setFormNode(formNode);
		    nextStage.setActionNodes(actionNodes);
		    nextStage.setWorkflowTrackDet(workflowStage.getWorkflowTrackDet());
		    nextStage.setWorkflowMaster(workflowStage.getWorkflowMaster());
			
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
			WorkflowNode lastNode=prevTrackDet.getWorkflowNode();
			formNode=workflowLinks.stream()
					.filter(w->w.getSourceNode().getNodeId()==lastNode.getNodeId() && w.getTargetNode().getNodeType()==WorkflowNodeType.FORM)
					.map(WorkflowLink::getTargetNode)
					.findFirst().orElse(null);
			
		}
		return formNode;
	}
	
	private void setValuesToForm(Set<VirtualTableRecords> records, Set<FormDesign> designList) {
		if(records!=null) { //setting form value if form is view
			designList.forEach(design->{
				final Optional<String> val=records.stream()
						.filter(record->
								design.getVirtualTableField()
								.getFieldName().equals(record.getVirtualTableFields().getFieldName()))
								.map(VirtualTableRecords::getStringValue)
								.map(Optional::ofNullable).findFirst().orElse(null);
				design.setComponentValue(val!=null?(val.isPresent()?val. get():null):null);
				
				
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
	@Override
	@Transactional
	public WorkflowTrackDet submitAction(WorkflowStage WorkflowStage) {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.findOne(auth.getName());
		
		long pkValue=0;
		
		WorkflowTrackMaster workflowTrackMaster=null;
		
		WorkflowTrackDet workflowTrackDet=new WorkflowTrackDet();
		if(WorkflowStage.getWorkflowTrackDet()==null) { 	// if it is start of workflow, then workflowTrackMaster need to be saved
			pkValue=saveVirtualRecords(WorkflowStage.getFormNode().getFormMaster()); //Saving form data
			
			Set<WorkflowTrackDet> trackDetails=new HashSet<WorkflowTrackDet>();
			trackDetails.add(workflowTrackDet);
			
			workflowTrackMaster=new WorkflowTrackMaster(WorkflowStage.getWorkflowMaster(),user,pkValue,trackDetails);
		}
		else {
			
			WorkflowStage.getWorkflowTrackDet().setOpen(false);
			workflowTrackDetService.update(WorkflowStage.getWorkflowTrackDet()); //closing the current stage
			
			workflowTrackMaster=findById(WorkflowStage.getWorkflowTrackDet().getWorkflowTrackMaster().getId()); //find track master if it is not first stage
			workflowTrackMaster.getWorkflowTrackDetList().size(); //initialize existing track records
			workflowTrackMaster.getWorkflowTrackDetList().add(workflowTrackDet);
			
			pkValue=updateVirtualRecords(WorkflowStage.getFormNode().getFormMaster(),workflowTrackMaster.getDataId()); //Updating form data
			
			
		}
		
		
		workflowTrackDet.setWorkflowNode(WorkflowStage.getSelectedActionNode());
		workflowTrackDet.setAccessUser(user);
		
	
		
		WorkflowLink link=workflowLinkService.findBySourceId(WorkflowStage.getSelectedActionNode().getNodeId()); //get next link
		workflowTrackDet.setOpen(WorkflowStage.getSelectedActionNode().getNodeType()!=WorkflowNodeType.STOP 
				&& link!=null && link.getTargetNode().getNodeType()!=WorkflowNodeType.STOP); //open next stage if source or dest node is not STOP
				
		workflowTrackDet.setWorkflowTrackMaster(workflowTrackMaster); // set parent track to details track
		
		return workflowTrackDetService.save(workflowTrackDet);
		
		
		
	}
	
	@Override
	public long mergeVirtualRecords(FormMaster form,long pkValue ) {
		
		if(form.getFormDesignList()!=null && !form.getFormDesignList().isEmpty()) {
			return 0;
		}
		
		if(pkValue==0) {
			return saveVirtualRecords(form);
		}
		else {
			return updateVirtualRecords(form,pkValue);
		}
		

		
	}
	
	private long saveVirtualRecords(FormMaster form) {
		
		Set<VirtualTableRecords> virtualTableRecords=new HashSet<VirtualTableRecords>();
		List<VirtualTableField> fields=	virtualTableFieldService.findAllByTableId(form.getVirtualTableMaster().getId());
		
		
		long pkValue=virtualTableSequenceService.getNextSeqByName(form.getVirtualTableMaster().getTableName()+"_seq");
		for (VirtualTableField virtualTableField : fields) {
			
			
			boolean isPk=false;
			VirtualTableRecords record=new VirtualTableRecords();
			record.setVirtualTableFields(virtualTableField);
			if(virtualTableField.getFieldConstraintList()!=null)
				isPk=virtualTableField.getFieldConstraintList().stream()
				.filter(con->con.getConstraintType() ==VirtualTableConstraintType.PRIMARY_KEY).findAny().isPresent();
			if(isPk)
				record.setStringValue(Long.toString(pkValue));
			else
			{ 
				FormDesign formDesign=form.getFormDesignList().stream()
						.filter(design->design.getVirtualTableField().equals(virtualTableField)).findFirst().orElse(null);
				if(formDesign!=null)
					record.setStringValue(formDesign.getComponentValue());
			}
			record.setPkValue(pkValue);
			virtualTableRecords.add(record);
		}
		virtualTableRecordsService.saveAll(virtualTableRecords);
		return pkValue;
	}
	private long updateVirtualRecords(FormMaster form,long pkValue) {
		Set<VirtualTableRecords> virtualTableRecords=null;
		virtualTableRecords=new HashSet<VirtualTableRecords>();
		virtualTableRecords=virtualTableRecordsService.findAllByPk(pkValue);
		
		
		virtualTableRecords.forEach(record->{
			Optional<FormDesign> formDesign=form.getFormDesignList().stream()
					.filter(design->design.getVirtualTableField().getFieldName().equals(record.getVirtualTableFields().getFieldName())).findFirst();
			if(formDesign.isPresent())
				record.setStringValue(formDesign.get().getComponentValue());
		});
		virtualTableRecordsService.saveAll(virtualTableRecords);
		return pkValue;
	}
	
	@Override
	public boolean formIsAccessable(FormMaster form) {
		//check whether this form is accessable for user
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
	private void populateRefValuesIfForeignKey(FormDesign formDesign) {
		if(formDesign.getVirtualTableField().getFieldConstraintList()==null || formDesign.getVirtualTableField().getFieldConstraintList().isEmpty() )
			return;
		VirtualTableConstraints foreignConstraint=formDesign.getVirtualTableField().getFieldConstraintList()
				.stream().filter(constraint->constraint.getConstraintType()==VirtualTableConstraintType.FOREIGN_KEY).findFirst().orElse(null);
		if(foreignConstraint!=null) {
			long tableId=foreignConstraint.getForeignConstraint().getVirtualTableField().getVirtualTableMaster().getId();
			FormComponentRefValue compRefValue=formDesign.getComponentRefValues().stream().findFirst().get();
			formDesign.setComponentRefValues(virtualTableRecordsService.findAllFormComponentRefValueByTableAndFields(tableId,compRefValue.getRefKey(),compRefValue.getRefValue()));
			
		}
	}

}
