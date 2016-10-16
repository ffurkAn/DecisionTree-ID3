package com.ml;

public class Occurrence {
	
	private String valueName;
	private int numberOfoccurrences;
	private int numberOfPositiveOccurrences;
	
	
	public Occurrence(int occ, int posOcc, String valueName) {
		numberOfoccurrences = occ;
		numberOfPositiveOccurrences = posOcc;
		this.valueName = valueName;
	}

	
	public String getValueName() {
		return valueName;
	}


	public void setValueName(String valueName) {
		this.valueName = valueName;
	}


	public int getNumberOfoccurrences() {
		return numberOfoccurrences;
	}
	public void setNumberOfoccurrences(int numberOfoccurrences) {
		this.numberOfoccurrences = numberOfoccurrences;
	}
	public int getNumberOfPositiveOccurrences() {
		return numberOfPositiveOccurrences;
	}
	public void setNumberOfPositiveOccurrences(int numberOfPositiveOccurrences) {
		this.numberOfPositiveOccurrences = numberOfPositiveOccurrences;
	}

	
}
