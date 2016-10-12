package com.ml;

import java.util.ArrayList;

public class Leaf extends Node {

	private Label classLabel;
	
	public Leaf(Label label,int id) {
		this.classLabel = label;
		super.setNodeId(id);
	}

	public Label getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(Label classLabel) {
		this.classLabel = classLabel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((classLabel == null) ? 0 : classLabel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leaf other = (Leaf) obj;
		if (classLabel == null) {
			if (other.classLabel != null)
				return false;
		} else if (!classLabel.equals(other.classLabel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Leaf [classLabel=" + classLabel + "]";
	}
	
	

}
