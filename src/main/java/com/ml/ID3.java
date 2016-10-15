package com.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;


public class ID3 {

	private TableManager tableManager;
	private double entropy;
	private int nodeCount;
	
	public ID3() {
		nodeCount = 0;
		entropy = 0.0;
		tableManager = new TableManager();
	}

	
	public int getNodeCount() {
		return nodeCount;
	}


	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}


	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public Node buildTree(DataTable dataSet){
		
		System.out.println("Calculating general entropy..");
		calculateGeneralEntropy(dataSet);
		LinkedHashSet<Attribute> attributes = createAllAttributes(dataSet.getAttributes());
		System.out.println("Building tree..");
		return runID3(dataSet,attributes);
	}
	
	// this set does not include the target attribute
		private LinkedHashSet<Attribute> createAllAttributes (List<Attribute> list) {
			LinkedHashSet<Attribute> attributes = new LinkedHashSet<Attribute>();
			for (Attribute attribute : list) {
				attributes.add(attribute);
			}
			return attributes;
		}
		
	public Node runID3 (DataTable dataSet, LinkedHashSet<Attribute> attributes) {
		
		nodeCount++;
		
		if(areAllSamplesPositive(dataSet.getSamples(), Boolean.TRUE)){
			return new Leaf(new Label(Boolean.TRUE),nodeCount);
		}
		
		if(areAllSamplesNegative(dataSet.getSamples(), Boolean.TRUE)){
			return new Leaf(new Label(Boolean.FALSE),nodeCount);
		}
		
		// else
		Attribute bestAttribute = findBestAttribute(dataSet,attributes);
		bestAttribute.setProccessed(true);
		Node root = new Node(bestAttribute);
		
		for (String value : bestAttribute.getValues()) {
			// TODO her datatable için entropy hesabý yapýlmalý
			DataTable valueDataSet = TableManager.getTrimmedDataSet(bestAttribute.getColumnIndex(),value,dataSet);
			
			if(valueDataSet.getSamples().isEmpty()){
				
				Leaf leaf = new Leaf(getMostCommonUsedClassLabel(dataSet.getSamples()), nodeCount);
				root.addBranch(value,leaf);
				
			}else{
				attributes.remove(bestAttribute);
				root.addBranch(value, runID3(valueDataSet,attributes));
				attributes.add(bestAttribute);
			}
		}
		
		return root;
		
		
	}
	
	/**
	 * returns the most common used target attribute in given samples
	 * @param samples
	 * @return
	 */
	private Label getMostCommonUsedClassLabel(List<SampleObject> samples) {

		int positives = 0;
		int negatives = 0;
		
		for (SampleObject sampleObject : samples) {
			
			if(sampleObject.getClassLabelValue()){
				positives++;
			}else{
				negatives++;
			}
		}
		
		return positives > negatives ? new Label(Boolean.TRUE) : new Label(Boolean.FALSE);
	}

	private Attribute findBestAttribute(DataTable dataSet, LinkedHashSet<Attribute> attributes) {
		
		double bestGain = -999;
		int bestAttribute = -999;
		
		for (Attribute attribute : attributes) {
			
			int attIndex = attribute.getColumnIndex();
			double gain = calculateGain(attIndex,dataSet);
			
			
			if (gain >= bestGain) {
				bestGain = gain;
				bestAttribute = attIndex;
			}
		}
		
		for (Attribute attribute : attributes) {
			if (attribute.getColumnIndex() == bestAttribute)
				return attribute;
		}
		return null;
	}



	// TODO paydayý samples.size() mý yapmalýyýz /totalOccurrenceOfAttribute
	private double calculateGain(int attIndex, DataTable dataSet) {

		// attribute value occurrences for the attribute with index @attIndex
		List<Occurrence> valueOccurrences = tableManager.getAttributeValueOccurrences(attIndex,dataSet);
		
		int totalOccurrenceOfAttribute = getTotalOccurrencesOfAttribute(valueOccurrences);
		
		double gain = 0;
		for (Occurrence occurrence : valueOccurrences) {
			
			if(occurrence.getNumberOfoccurrences()>0){
				int sizeOfTotalOccurrences = occurrence.getNumberOfoccurrences();
				int positiveOccurrences = occurrence.getNumberOfPositiveOccurrences();
				int negativeOccurrences = occurrence.getNumberOfoccurrences()-occurrence.getNumberOfPositiveOccurrences();
				
				gain += (-1) * ((double)occurrence.getNumberOfoccurrences() / totalOccurrenceOfAttribute) * calculateEntropy(positiveOccurrences,negativeOccurrences,sizeOfTotalOccurrences);
			}
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

	public void calculateGeneralEntropy(DataTable dataSet) {
		
		int size = dataSet.getSamples().size();
		int positives = 0;
		int negatives = 0;
		
		for (SampleObject sampleObject : dataSet.getSamples()) {
			
			if( sampleObject.getClassLabelValue()){
				positives++;
			}else{
				negatives++;
			}
		}
		
		entropy = calculateEntropy(positives,negatives,size);
		
	}
	
	public double calculateEntropy(int positives, int negatives, int size){
		
		double positiveRatio = (double)positives/size;
		double negativeRatio = (double)negatives/size;

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
