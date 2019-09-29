package com.focowell.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.focowell.dto.WorkflowMasterDto;
import com.focowell.model.WorkflowMaster;

@Component
public class WorkflowMasterConvertor {
	@Autowired
	ModelMapper modelMapper;
	
	
	public WorkflowMasterDto convertWorkflowMasterToWorkflowMasterDto(WorkflowMaster workflowMaster) {
		
		WorkflowMasterDto workflowMasterDto=modelMapper.map(workflowMaster,WorkflowMasterDto.class);
		return workflowMasterDto;
	}
	public WorkflowMaster convertWorkflowMasterDtoToWorkflowMaster(WorkflowMasterDto workflowMasterDto) {
		
		WorkflowMaster workflowMaster=modelMapper.map(workflowMasterDto,WorkflowMaster.class);
		return workflowMaster;
	}
}
