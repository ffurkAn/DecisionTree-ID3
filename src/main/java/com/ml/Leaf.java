package com.ml;

import java.util.List;

public class Leaf extends Node {

	private Label classLabel;
	private List<SampleObject> samples;
	
	public Leaf(Label label,int id, List<SampleObject> samples) {
		this.classLabel = label;
		super.setNodeId(id);
		this.samples = samples;
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

	@Override
	public String toString() {
		return "Leaf [classLabel=" + classLabel + "]";
	}
	
	

}
