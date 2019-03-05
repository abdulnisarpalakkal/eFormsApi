package com.focowell.model.deserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;

public class VirtualTableConstraintsDeserializer extends StdDeserializer<VirtualTableConstraints> {
	
	@Autowired
	ObjectMapper jsonObjectMapper;
	
	public VirtualTableConstraintsDeserializer() { 
	        this(null); 
	} 
	public VirtualTableConstraintsDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public VirtualTableConstraints deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		jsonObjectMapper.setDateFormat(df);
		
		JsonNode node = jp.getCodec().readTree(jp);

		long id = node.get("id")!=null && !node.get("id").isNull()?jsonObjectMapper.treeToValue(node.get("id"),Long.class):0L;
       
		VirtualTableConstraintType constraintType=node.get("constraintType")!=null && !node.get("constraintType").isNull()?jsonObjectMapper.treeToValue(node.get("constraintType"),VirtualTableConstraintType.class):null;
       
        
		String constraintValue=node.get("constraintValue")!=null && !node.get("constraintValue").isNull()?jsonObjectMapper.treeToValue(node.get("constraintValue"),String.class):null;
		VirtualTableField virtualTableField=node.get("virtualTableField")!=null && !node.get("virtualTableField").isNull()?jsonObjectMapper.treeToValue(node.get("virtualTableField"),VirtualTableField.class):null;
        
		VirtualTableConstraints foreignConstraint=node.get("foreignConstraint")!=null && !node.get("foreignConstraint").isNull()?jsonObjectMapper.treeToValue(node.get("foreignConstraint"),VirtualTableConstraints.class):null;
        VirtualTableConstraints virtualTableConstraints=new VirtualTableConstraints(id,constraintType,constraintValue,virtualTableField,foreignConstraint);

        return virtualTableConstraints;
	}

}
