package com.ml;

import java.util.ArrayList;

public class Attribute {

	private ArrayList<String> values;
	private String name;
	private int columnIndex;
	private int mostCommonValueSampleIndex;
	
	public Attribute(ArrayList<String> values, String name, int columnIndex) {
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
	
	
	public int getMostCommonValueSampleIndex() {
		return mostCommonValueSampleIndex;
	}

	public void setMostCommonValueSampleIndex(int mostCommonValueSampleIndex) {
		this.mostCommonValueSampleIndex = mostCommonValueSampleIndex;
	}

	public ArrayList<String> getValues() {
		return values;
	}
	public void setValues(ArrayList<String> values) {
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
