package com.ml;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Attribute {

	private LinkedHashSet<String> values; // holds the values
	private String name;
	private int columnIndex; // index of the @data
	private String mostCommonValue; // most occurred value in the training set
	
	public Attribute(LinkedHashSet<String> values, String name, int columnIndex) {
		super();
		this.values = values;
		this.name = name;
		this.columnIndex = columnIndex;
	}
	
	public Attribute(){}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	
	public String getMostCommonValue() {
		return mostCommonValue;
	}

	public void setMostCommonValue(String mostCommonValueSampleIndex) {
		this.mostCommonValue = mostCommonValueSampleIndex;
	}

	public LinkedHashSet<String> getValues() {
		return values;
	}
	public void setValues(LinkedHashSet<String> values) {
		this.values = values;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Attribute [values=" + values + ", name=" + name + ", columnIndex=" + columnIndex + "]";
	}
	
	
	
	
	
}
