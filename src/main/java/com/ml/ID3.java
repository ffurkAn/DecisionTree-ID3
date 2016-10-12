package com.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ID3 {

	private DataTable dataTable;
	private double entropy;
	private int nodeCount = 0;
	
	public ID3() {

	}

	public DataTable getDataTable() {
		return dataTable;
	}


	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public Node buildTree() {
		
		return runID3(dataTable.getSamples(), dataTable.getTargetAttributes(), dataTable.getAttributes());
	}
	
	public Node runID3 (List<SampleObject> samples, HashMap<String, Boolean> targetAttributes, List<Attribute> attributes) {
		
		nodeCount++;
		
		if(areAllSamplesPositive(samples, Boolean.TRUE)){
			return new Leaf(new Label(Boolean.TRUE),nodeCount);
		}
		
		if(areAllSamplesNegative(samples, Boolean.TRUE)){
			return new Leaf(new Label(Boolean.FALSE),nodeCount);
		}
		
		// else
		Attribute bestAttribute = findBestAttribute(samples,targetAttributes,attributes);
		bestAttribute.setProccessed(true);
		Node root = new Node(bestAttribute);
		
		for (String value : bestAttribute.getValues()) {
			
			List<SampleObject> valueSamples = dataTable.getTrimmedSamples(bestAttribute.getColumnIndex(),value,samples);
		}
		
		
		return null;
		
		
	}
	
	private Attribute findBestAttribute(List<SampleObject> list, HashMap<String, Boolean> targetAttributes,
			List<Attribute> list2) {
		
		double bestGain = -999;
		int bestAttribute = -999;
		
		for (Attribute attribute : list2) {
			
			int attIndex = attribute.getColumnIndex();
			double gain = calculateGain(attIndex,list,targetAttributes);
			
			
			if (gain >= bestGain) {
				bestGain = gain;
				bestAttribute = attIndex;
			}
		}
		
		for (Attribute attribute : list2) {
			if (attribute.getColumnIndex() == bestAttribute)
				return attribute;
		}
		return null;
	}



	// TODO paydayý samples.size() mý yapmalýyýz
	private double calculateGain(int attIndex, List<SampleObject> list,
			HashMap<String, Boolean> targetAttributes) {

		// attribute value occurrences for the attribute with index @attIndex
		List<Occurrence> valueOccurrences = dataTable.getAttributeValueOccurrences(attIndex,list);
		
		int totalOccurrenceOfAttribute = getTotalOccurrencesOfAttribute(valueOccurrences);
		
		double gain = 0;
		for (Occurrence occurrence : valueOccurrences) {
			gain += (-1) * ((double)occurrence.getNumberOfoccurrences() / totalOccurrenceOfAttribute) * calculateEntropy(occurrence.getNumberOfPositiveOccurrences(),occurrence.getNumberOfoccurrences()-occurrence.getNumberOfPositiveOccurrences());
		}
		return gain + entropy;
	}

	
	private int getTotalOccurrencesOfAttribute(List<Occurrence> valueOccurrences) {

		int total = 0;
		for (Occurrence occurrence : valueOccurrences) {
			
			total += occurrence.getNumberOfoccurrences();
		}
		
		return total;
	}

	public void calculateEntropy() {
		
		int total = dataTable.getSamples().size();
		int positives = 0;
		int negatives = 0;
		
		for (SampleObject sampleObject : dataTable.getSamples()) {
			
			if( sampleObject.getClassLabelValue()){
				positives++;
			}else{
				negatives++;
			}
		}
		
		entropy = calculateEntropy(positives,negatives);
		
	}
	
	public double calculateEntropy(int positives, int negatives){
		
		double positiveRatio = (double)positives/dataTable.getSamples().size();
		double negativeRatio = (double)negatives/dataTable.getSamples().size();

		if (positiveRatio != 0)
			positiveRatio = -(positiveRatio) * (Math.log(positiveRatio)/Math.log(2));
		if (negativeRatio != 0)
			negativeRatio = - (negativeRatio) * (Math.log(negativeRatio)/Math.log(2));

		return positiveRatio + negativeRatio;
		
	}
	
	/**
	 * 
	 * @param list
	 * @param targetValue
	 * @return
	 */
	private boolean areAllSamplesNegative(List<SampleObject> list, Boolean targetValue) {
		return areAllSamplesBelongToSameClass(list,targetValue);
	}

	/**
	 * 
	 * @param list
	 * @param targetValue
	 * @return
	 */
	private boolean areAllSamplesPositive(List<SampleObject> list, Boolean targetValue) {
		return areAllSamplesBelongToSameClass(list,targetValue);
	}

	private boolean areAllSamplesBelongToSameClass(List<SampleObject> list, Boolean targetValue ){
		
		for (SampleObject sampleObject : list) {
			
			if(sampleObject.getClassLabelValue().booleanValue() != targetValue.booleanValue()){
				return false;
			}
		}
		
		return true;
		
	}

	

	
	
	
	

	
}
