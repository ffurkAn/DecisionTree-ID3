package com.ml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;



public class ID3 {

	private TableManager tableManager;
	private int nodeCount;
	private double threshold = 0.05;

	public ID3() {
		nodeCount = 0;
		tableManager = new TableManager();
	}


	public int getNodeCount() {
		return nodeCount;
	}


	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}


	public Node buildTree(DataTable dataSet){

		System.out.println("Calculating general entropy..");
		dataSet.calculateGeneralEntropy();
		LinkedHashSet<Attribute> attributes = createAllAttributesAndFindMostCommonUsedValue(dataSet);
		System.out.println("Building tree..");
		return runID3(dataSet,attributes);
	}

	// this set does not include the target attribute
	private LinkedHashSet<Attribute> createAllAttributesAndFindMostCommonUsedValue (DataTable dataSet) {
		LinkedHashSet<Attribute> attributes = new LinkedHashSet<Attribute>();
		for (Attribute attribute : dataSet.getAttributes()) {

			List<Occurrence> valueOccurrences = tableManager.getAttributeValueOccurrences(attribute.getColumnIndex(),dataSet);

			String mostCommonValue = "";
			int mostCommonValueCount = 0;
			for (Occurrence occurrence : valueOccurrences) {

				int numberOfOccurred = occurrence.getNumberOfoccurrences();

				if(numberOfOccurred > mostCommonValueCount){
					mostCommonValue = occurrence.getValueName();
					mostCommonValueCount = numberOfOccurred;
				}
			}

			attribute.setMostCommonValue(mostCommonValue);
			attributes.add(attribute);

		}
		return attributes;
	}

	public Node runID3 (DataTable dataSet, LinkedHashSet<Attribute> attributes) {

		Node root = new Node(nodeCount);
		nodeCount++;

		if(areAllSamplesPositive(dataSet.getSamples(), Boolean.TRUE)){
			Label lbl = new Label(Boolean.TRUE);
			root.setClassLabel(lbl);
			root.setSamples(dataSet.getSamples());

		}else if(areAllSamplesNegative(dataSet.getSamples(), Boolean.FALSE)){
			Label lbl = new Label(Boolean.FALSE);
			root.setClassLabel(lbl);
			root.setSamples(dataSet.getSamples());

		}else if(attributes.isEmpty()){
			Label lbl = new Label(getMostCommonUsedClassLabel(dataSet.getSamples()).getLabelValue());
			root.setClassLabel(lbl);
			root.setSamples(dataSet.getSamples());

		}else{

			Attribute bestAttribute = findBestAttribute(dataSet,attributes);
			bestAttribute.setProccessed(true);
			root.setAttribute(bestAttribute);

			// TODO what is going to be a cutoff ?
			//			int numberOfValuesInAttribute = bestAttribute.getValues().size();
			//			double chiSquareStatistic = chiSquareTest(bestAttribute,dataSet);
			//			
			//			
			//			if(chiSquareStatistic > threshold){
			//				Label lbl = new Label(getMostCommonUsedClassLabel(dataSet.getSamples()).getLabelValue());
			//				root.setClassLabel(lbl);
			//				root.setSamples(dataSet.getSamples());
			//				
			//			}else{

			// split the set
			for (String value : bestAttribute.getValues()) {

				DataTable valueDataSet = TableManager.getTrimmedDataSet(bestAttribute.getColumnIndex(),value,dataSet);

				if(valueDataSet.getSamples().isEmpty()){

					Node leafNode = new Node(nodeCount);
					leafNode.setClassLabel(getMostCommonUsedClassLabel(dataSet.getSamples()));
					leafNode.setSamples(new ArrayList<>());
					root.addBranch(value,leafNode);

				}else{
					valueDataSet.calculateGeneralEntropy();
					attributes.remove(bestAttribute);
					root.addBranch(value, runID3(valueDataSet,attributes));
					attributes.add(bestAttribute);
				}
			}
			//			}
		}
		return root;
	}

	private double chiSquareTest(Attribute bestAttribute, DataTable dataSet) {

		int attributeColumnIndex = bestAttribute.getColumnIndex();

		List<Occurrence> occurrences = tableManager.getAttributeValueOccurrences(attributeColumnIndex, dataSet);

		int numberOfPositiveSamples = getNumberOfInstances(dataSet,Boolean.TRUE);
		int numberOfNegativeSamples = getNumberOfInstances(dataSet,Boolean.FALSE);	
		int numberOfAttributesOccured = 0;

		for (Occurrence occurrence : occurrences) {
			numberOfAttributesOccured += occurrence.getNumberOfoccurrences();
		}

		double deviation = 0.0;
		for (Occurrence occurrence : occurrences) {
			double positiveOccuured = occurrence.getNumberOfPositiveOccurrences();
			double negativeOccurred = occurrence.getNumberOfoccurrences() - occurrence.getNumberOfPositiveOccurrences();
			double positiveExpected = ((double)numberOfPositiveSamples / (numberOfNegativeSamples + numberOfPositiveSamples)) * numberOfAttributesOccured;
			double megativeExpected = ((double)numberOfNegativeSamples / (numberOfNegativeSamples + numberOfPositiveSamples)) * numberOfAttributesOccured;
			deviation +=  (Math.pow(positiveOccuured - positiveExpected, 2) / positiveExpected) + (Math.pow(negativeOccurred - megativeExpected, 2) / megativeExpected); 
		}

		return deviation;
	}


	private int getNumberOfInstances(DataTable dataSet, Boolean targetFlag) {

		int numberOf = 0;

		for (SampleObject sampleObject : dataSet.getSamples()) {

			if(sampleObject.getClassLabel().toLowerCase().equals(targetFlag.toString().toLowerCase())){
				numberOf++;
			}
		}
		return numberOf;
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

				gain += (-1) * ((double)occurrence.getNumberOfoccurrences() / totalOccurrenceOfAttribute) * dataSet.calculateEntropy(positiveOccurrences,negativeOccurrences,sizeOfTotalOccurrences);
				//				gain += (-1) * ((double)occurrence.getNumberOfoccurrences() / dataSet.getSamples().size()) * dataSet.calculateEntropy(positiveOccurrences,negativeOccurrences,dataSet.getSamples().size());
			}
		}
		return gain + dataSet.getEntropy();
	}


	private int getTotalOccurrencesOfAttribute(List<Occurrence> valueOccurrences) {

		int total = 0;
		for (Occurrence occurrence : valueOccurrences) {

			total += occurrence.getNumberOfoccurrences();
		}

		return total;
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
