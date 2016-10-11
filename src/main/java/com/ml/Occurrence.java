package com.ml;

public class Occurrence {
	
	private int numberOfoccurrences;
	private int numberOfPositiveOccurrences;
	
	
	public Occurrence(int occ, int posOcc) {
		numberOfoccurrences = occ;
		numberOfPositiveOccurrences = posOcc;
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
