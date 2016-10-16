package com.ml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class Node {

	private static final String MISSING_VALUE = "?";
	private LinkedHashMap<String, Node> children;
	private Attribute attribute;
	private int nodeId;
	private Node parent;
	private int level;
	private Label classLabel;
	private List<SampleObject> samples;
	

	public Node(LinkedHashMap<String, Node> children, Attribute attribute, Node parent) {
		super();
		this.children = children;
		this.attribute = attribute;
		this.parent = parent;
	}
	
	public Node() {
		children = new LinkedHashMap<>();
	}

	public Node(Attribute attribute){
		this.attribute = attribute;
		children = new LinkedHashMap<>();
	}
	

	public Node(int nodeCount) {
		nodeId = nodeCount;
		children = new LinkedHashMap<>();
	}

	
	public Label getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(Label classLabel) {
		this.classLabel = classLabel;
	}

	public List<SampleObject> getSamples() {
		return samples;
	}

	public void setSamples(List<SampleObject> samples) {
		this.samples = samples;
	}

	public LinkedHashMap<String, Node> getChildren() {
		return children;
	}

	public void setChildren(LinkedHashMap<String, Node> children) {
		this.children = children;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNodeId() {
		return nodeId;
	}


	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}


	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Node [children=" + children + ", attribute=" + attribute + ", parent=" + parent + "]";
	}

	public void addBranch(String value, Node childNode) {
		children.put(value, childNode);
	}


	/**
	 * travels the tree and checks if it is matching with the leaf
	 * @param sample
	 * @param columnIndex
	 * @return
	 */
	public String makeDecision(ArrayList<String> sample, int columnIndex) {

		String classDecision = "false";
		
		if(children.isEmpty()){
			return classLabel.getLabelStr();
		}else{
			
			// if value is missing, choose the most cammon value branch
			if(sample.get(columnIndex).equals(MISSING_VALUE)){
				Node childNode = children.get(attribute.getMostCommonValue());
				if(childNode.getAttribute() == null){
					classDecision = childNode.getClassLabel().getLabelStr();
				}else{
					classDecision = childNode.makeDecision(sample, childNode.getAttribute().getColumnIndex());
				}
			}else{
				for(String branchValue : children.keySet()){
					String value = sample.get(columnIndex);
					if(value.equals(branchValue)){
						
						Node childNode = children.get(branchValue);
						if(childNode.getAttribute() == null){
							classDecision = childNode.getClassLabel().getLabelStr();
						}else{
							classDecision = childNode.makeDecision(sample, childNode.getAttribute().getColumnIndex());
						}
					}
				}
			}
			
			
		}
		
		return classDecision;
	}

	
	
	
	
}
