package com.ml;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class Node {

	private LinkedHashMap<String, Node> children;
	private Attribute attribute;
	private int nodeId;
	private Node parent;
	private int level;
	

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


	

	
	
	
	
}
