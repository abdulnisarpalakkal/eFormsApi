package com.focowell.model.deserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.focowell.model.WorkflowMaster;
import com.focowell.model.WorkflowNode;
import com.focowell.model.WorkflowStage;
import com.focowell.model.WorkflowTrackDet;

public class WorkflowStageDeserializer extends StdDeserializer<WorkflowStage> {
	
	@Autowired
	ObjectMapper jsonObjectMapper;
	
	public WorkflowStageDeserializer() { 
	        this(null); 
	} 
	public WorkflowStageDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public WorkflowStage deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		jsonObjectMapper.setDateFormat(df);
		
		JsonNode node = jp.getCodec().readTree(jp);

		long id = node.get("id")!=null && !node.get("id").isNull()?jsonObjectMapper.treeToValue(node.get("id"),Long.class):0L;
       
        WorkflowNode formNode=node.get("formNode")!=null && !node.get("formNode").isNull()?jsonObjectMapper.treeToValue(node.get("formNode"),WorkflowNode.class):null;
        Set<WorkflowNode> actionNodes=node.get("actionNodes")!=null && !node.get("actionNodes").isNull()?
        		new HashSet<WorkflowNode>(Arrays.asList(jsonObjectMapper.treeToValue(node.get("actionNodes"),WorkflowNode[].class))):null;
        
        WorkflowMaster workflowMaster=node.get("workflowMaster")!=null && !node.get("workflowMaster").isNull()?jsonObjectMapper.treeToValue(node.get("workflowMaster"),WorkflowMaster.class):null;
        WorkflowTrackDet workflowTrackDet=node.get("workflowTrackDet")!=null && !node.get("workflowTrackDet").isNull()?jsonObjectMapper.treeToValue(node.get("workflowTrackDet"),WorkflowTrackDet.class):null;
        WorkflowNode selectedActionNode=node.get("selectedActionNode")!=null && !node.get("selectedActionNode").isNull()?jsonObjectMapper.treeToValue(node.get("selectedActionNode"),WorkflowNode.class):null;
        WorkflowStage stage=new WorkflowStage(id,formNode,actionNodes,workflowMaster,workflowTrackDet,selectedActionNode);

        return stage;
	}

}
