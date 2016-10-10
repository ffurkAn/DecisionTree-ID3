package com.ml;

import java.util.ArrayList;

public class SampleObject {

	private int id;
	private String classLabel;
	private ArrayList<String> sampleValues;
	
	public SampleObject(int id, String label, ArrayList<String> sampleValues) {
	
		super();
		this.id = id;
		this.classLabel = label;
		this.sampleValues = sampleValues;
	}
	
	public SampleObject(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public ArrayList<String> getSampleValues() {
		return sampleValues;
	}

	public void setSampleValues(ArrayList<String> sampleValues) {
		this.sampleValues = sampleValues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classLabel == null) ? 0 : classLabel.hashCode());
		result = prime * result + id;
		result = prime * result + ((sampleValues == null) ? 0 : sampleValues.hashCode());
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
		SampleObject other = (SampleObject) obj;
		if (classLabel == null) {
			if (other.classLabel != null)
				return false;
		} else if (!classLabel.equals(other.classLabel))
			return false;
		if (id != other.id)
			return false;
		if (sampleValues == null) {
			if (other.sampleValues != null)
				return false;
		} else if (!sampleValues.equals(other.sampleValues))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SampleObject [id=" + id + ", label=" + classLabel + ", sampleValues=" + sampleValues + "]";
	}
	
	
	
	
	
}
