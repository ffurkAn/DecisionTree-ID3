package com.ml;

import java.util.ArrayList;

/**
* This class holds the line of data with its classifier attribute 'Yes-or-No'
*
*/
public class SampleObject {

	private int id;
	private String classLabel;
	private Boolean classLabelValue; // True or False
	private ArrayList<String> sampleValues; // holds the values separated with comma

	public SampleObject(int id, String label, ArrayList<String> sampleValues) {
	
		super();
		this.id = id;
		this.classLabel = label;
		this.sampleValues = sampleValues;
	}
	
	public SampleObject(){}

	public Boolean getClassLabelValue() {
		return classLabelValue;
	}

	public void setClassLabelValue(Boolean classLabelValue) {
		this.classLabelValue = classLabelValue;
	}
	
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
		result = prime * result + ((classLabelValue == null) ? 0 : classLabelValue.hashCode());
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
		if (classLabelValue == null) {
			if (other.classLabelValue != null)
				return false;
		} else if (!classLabelValue.equals(other.classLabelValue))
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
		return "SampleObject [id=" + id + ", classLabel=" + classLabel + ", classLabelValue=" + classLabelValue
				+ ", sampleValues=" + sampleValues + "]";
	}
	
	
	
	
	
}
