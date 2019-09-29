package com.focowell.convertor;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.focowell.dto.WorkflowStageDto;
import com.focowell.dto.WorkflowStageExpandedDto;
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackDet;

@Component
public class WorkflowStageConvertor {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	WorkflowNodeConvertor workflowNodeConvertor;
	
	public WorkflowStageDto convertWorkflowStageToWorkflowStageDto(WorkflowStage workflowStage) {
		
		WorkflowStageDto workflowStageDto=modelMapper.map(workflowStage,WorkflowStageDto.class);
		//workflowStageDto.setFormNode(workflowNodeConvertor.convertWorkflowNodeToWorkflowNodeDto(workflowStage.getFormNode()));
		return workflowStageDto;
	}
	

	public WorkflowStage convertWorkflowStageDtoToWorkflowStage(WorkflowStageDto workflowStageDto) {
//		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		WorkflowStage workflowStage=modelMapper.map(workflowStageDto,WorkflowStage.class);
		return workflowStage;
	}
	
	public WorkflowStageExpandedDto convertWorkflowStageToWorkflowStageExpandedDto(WorkflowStage workflowStage) {
		
		WorkflowStageExpandedDto workflowStageDto=modelMapper.map(workflowStage,WorkflowStageExpandedDto.class);
		if(workflowStage.getWorkflowTrackDet()!=null && workflowStage.getWorkflowTrackDet().getId()!=0)
			workflowStageDto.setWorkflowTrackId(workflowStage.getWorkflowTrackDet().getId());
		
		return workflowStageDto;
	}
	
	public WorkflowStage convertWorkflowStageExpandedDtoToWorkflowStage(WorkflowStageExpandedDto workflowStageExpandedDto) {
//		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		WorkflowStage workflowStage=modelMapper.map(workflowStageExpandedDto,WorkflowStage.class);
		if(workflowStageExpandedDto.getWorkflowTrackId()!=0) {
			WorkflowTrackDet worklflowTrackDet=new WorkflowTrackDet();
			worklflowTrackDet.setId(workflowStageExpandedDto.getWorkflowTrackId());
			workflowStage.setWorkflowTrackDet(worklflowTrackDet);
		}
		return workflowStage;
	}
}
