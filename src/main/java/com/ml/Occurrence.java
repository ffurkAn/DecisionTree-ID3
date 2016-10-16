package com.ml;


/**
 * Occurrence class holds the statistics for a particular value in attribute
 * such as number of value occurrences, number of positive ones. 
 * @author furkan
 *
 */
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

	public int getNumberOfNegativeOccurrences(){
		return numberOfoccurrences - numberOfPositiveOccurrences;
	}
}
