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
import com.focowell.model.FormDesign;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.WorkflowTrackDet;

public class VirtualTableFieldDeserializer extends StdDeserializer<VirtualTableField> {
	
	@Autowired
	ObjectMapper jsonObjectMapper;
	
	public VirtualTableFieldDeserializer() { 
	        this(null); 
	} 
	public VirtualTableFieldDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public VirtualTableField deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		jsonObjectMapper.setDateFormat(df);
		
		JsonNode node = jp.getCodec().readTree(jp);
		
		long id = (node.get("id")!=null && !node.get("id").isNull())?jsonObjectMapper.treeToValue(node.get("id"),Long.class):0L;
       
		String fieldName=node.get("fieldName")!=null && !node.get("fieldName").isNull()?jsonObjectMapper.treeToValue(node.get("fieldName"),String.class):null;
		VirtualTableFieldDataType fieldDataType=node.get("fieldName")!=null && !node.get("fieldDataType").isNull()?jsonObjectMapper.treeToValue(node.get("fieldDataType"),VirtualTableFieldDataType.class):null;
		String fieldDesc=node.get("fieldDesc")!=null && !node.get("fieldDesc").isNull()?jsonObjectMapper.treeToValue(node.get("fieldDesc"),String.class):null;
		VirtualTableMaster virtualTableMaster=node.get("virtualTableMaster")!=null && !node.get("virtualTableMaster").isNull()?jsonObjectMapper.treeToValue(node.get("virtualTableMaster"),VirtualTableMaster.class):null;
		
        Set<FormDesign> formDesignList=node.get("formDesignList")!=null && !node.get("formDesignList").isNull()?
        		new HashSet<FormDesign>(Arrays.asList(jsonObjectMapper.treeToValue(node.get("formDesignList"),FormDesign[].class))):null;
		Set<VirtualTableConstraints> virtualTableConstraints=node.get("fieldConstraintList")!=null && !node.get("fieldConstraintList").isNull()?
        		new HashSet<VirtualTableConstraints>(Arrays.asList(jsonObjectMapper.treeToValue(node.get("fieldConstraintList"),VirtualTableConstraints[].class))):null;
       
//        Set<VirtualTableConstraints> refConstraintList=null;
//       
//		refConstraintList=node.get("refConstraintList")!=null && !node.get("refConstraintList").isNull()?
//		new HashSet<VirtualTableConstraints>(Arrays.asList(jsonObjectMapper.treeToValue(node.get("refConstraintList"),VirtualTableConstraints[].class))):null;

        
        VirtualTableField virtualTableField=new VirtualTableField(id,fieldName,fieldDesc,fieldDataType,virtualTableMaster,formDesignList,virtualTableConstraints);

        return virtualTableField;
	}

}
