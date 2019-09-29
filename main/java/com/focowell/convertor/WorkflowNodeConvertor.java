package com.focowell.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.focowell.dto.WorkflowNodeDto;
import com.focowell.model.WorkflowNode;

@Component
public class WorkflowNodeConvertor {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	FormMasterConvertor formMasterConvertor;
	
	public WorkflowNodeDto convertWorkflowNodeToWorkflowNodeDto(WorkflowNode workflowNode) {
		
		WorkflowNodeDto workflowNodeDto=modelMapper.map(workflowNode,WorkflowNodeDto.class);
		workflowNodeDto.setFormMaster(formMasterConvertor.convertFormMasterToFormMasterDto(workflowNode.getFormMaster()));
		return workflowNodeDto;
	}
	public WorkflowNode convertWorkflowNodeDtoToWorkflowNode(WorkflowNodeDto workflowNodeDto) {
		
		WorkflowNode workflowNode=modelMapper.map(workflowNodeDto,WorkflowNode.class);
		return workflowNode;
	}
}
