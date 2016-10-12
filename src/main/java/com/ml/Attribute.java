package com.ml;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Attribute {

	private LinkedHashSet<String> values;
	private String name;
	private int columnIndex;
	private int mostCommonValueSampleIndex;
	private boolean isProccessed = false;
	
	public Attribute(LinkedHashSet<String> values, String name, int columnIndex) {
		super();
		this.values = values;
		this.name = name;
		this.columnIndex = columnIndex;
	}
	
	public Attribute(){}
	
	public boolean isProccessed() {
		return isProccessed;
	}

	public void setProccessed(boolean isProccessed) {
		this.isProccessed = isProccessed;
	}

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
