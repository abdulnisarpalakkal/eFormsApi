package com.focowell.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.focowell.dto.WorkflowTrackDetDto;
import com.focowell.model.WorkflowTrackDet;

@Component
public class WorkflowTrackDetConvertor {

	@Autowired
	ModelMapper modelMapper;
	
	
	public WorkflowTrackDetDto convertWorkflowTrackDetToWorkflowTrackDetDto(WorkflowTrackDet workflowTrackDet) {
		
		WorkflowTrackDetDto workflowTrackDetDto=modelMapper.map(workflowTrackDet,WorkflowTrackDetDto.class);
		return workflowTrackDetDto;
	}
	public WorkflowTrackDet convertWorkflowTrackDetDtoToWorkflowTrackDet(WorkflowTrackDetDto workflowTrackDetDto) {
		
		WorkflowTrackDet workflowTrackDet=modelMapper.map(workflowTrackDetDto,WorkflowTrackDet.class);
		return workflowTrackDet;
	}
}
