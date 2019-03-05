package com.focowell.model;

public enum WorkflowNodeType {
	START("START"),
    STOP("STOP"),
    FORM("FORM"),
    ACTION("ACTION");
	
    private final String nodeType;
    private WorkflowNodeType(String nodeType){
        this.nodeType = nodeType;
        
    }
	public String getNodeType() {
		return nodeType;
	}
    
	public static WorkflowNodeType parse(String nodeType) {
		WorkflowNodeType workflowNodeType = null; // Default
        for (WorkflowNodeType item : WorkflowNodeType.values()) {
            if (item.getNodeType()==nodeType) {
            	workflowNodeType = item;
                break;
            }
        }
        return workflowNodeType;
    }
}
