package com.ml;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;

public class ID3 {
	private int nodeCount;
	private double threshold = 10;
	Logger logger = Logger.getGlobal();

	public ID3() {
		nodeCount = 0;
	}


	public int getNodeCount() {
		return nodeCount;
	}


	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}


	public Node buildTree(DataTable dataSet){

		dataSet.calculateGeneralEntropy();
		logger.info("General entropy is: " + dataSet.getEntropy());
		LinkedHashSet<Attribute> attributes = dataSet.createAllAttributesAndFindMostCommonUsedValue();
		logger.info("Started to building tree..");
		return runID3(dataSet,attributes);
	}

	public Node runID3 (DataTable dataSet, LinkedHashSet<Attribute> attributes) {

		Node root = new Node(nodeCount);
		nodeCount++;

		//If all examples are positive, Return the single-node tree Root, with label = +.
		if(areAllSamplesBelongToSameClass(dataSet.getSamples(), Boolean.TRUE)){
			Label lbl = new Label(Boolean.TRUE);
			root.setClassLabel(lbl);
			root.setSamples(dataSet.getSamples());
			
		}
		// If all examples are negative, Return the single-node tree Root, with label = -.
		else if(areAllSamplesBelongToSameClass(dataSet.getSamples(), Boolean.FALSE)){
			Label lbl = new Label(Boolean.FALSE);
			root.setClassLabel(lbl);
			root.setSamples(dataSet.getSamples());

		}
		//If number of predicting attributes is empty, then Return the single node tree Root,
	    //with label = most common value of the target attribute in the examples.
		else if(attributes.isEmpty()){
			Label lbl = new Label(dataSet.getMostCommonUsedClassLabel().getLabelValue());
			root.setClassLabel(lbl);
			root.setSamples(dataSet.getSamples());

		}else{

			//The Attribute that best classifies examples.
			Attribute bestAttribute = findBestAttribute(dataSet,attributes);
			bestAttribute.setProccessed(true);
			root.setAttribute(bestAttribute);

			// FIXME there is sth wrong with chi-square
			double chiSquareStatistic = TableManager.chiSquareTest(bestAttribute,dataSet);
			if(chiSquareStatistic < threshold){
				Label lbl = new Label(dataSet.getMostCommonUsedClassLabel().getLabelValue());
				root.setClassLabel(lbl);
				root.setSamples(dataSet.getSamples());

			}else{

				// split the set
				for (String value : bestAttribute.getValues()) {

					// filter dataset with 'value' and create subset
					DataTable valueDataSet = TableManager.getTrimmedDataSet(bestAttribute.getColumnIndex(),value,dataSet);

					// if there is not any sample for that 'value', just return
					if(valueDataSet.getSamples().isEmpty()){
						Node leafNode = new Node(nodeCount);
						leafNode.setClassLabel(dataSet.getMostCommonUsedClassLabel());
						leafNode.setSamples(new ArrayList<>());
						root.addBranch(value,leafNode);

					}else{
						// calculate the entropy for subset
						valueDataSet.calculateGeneralEntropy();
						attributes.remove(bestAttribute);
						root.addBranch(value, runID3(valueDataSet,attributes));
						attributes.add(bestAttribute);
					}
				}
			}
		}
		return root;
	}


	
	/**
	 * Finds an attribute that best classifies examples by comparing its information gain
	 * @param dataSet
	 * @param attributes
	 * @return
	 */
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
		List<Occurrence> valueOccurrences = TableManager.getAttributeValueOccurrences(attIndex,dataSet);

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

	private boolean areAllSamplesBelongToSameClass(List<SampleObject> list, Boolean targetValue ){

		for (SampleObject sampleObject : list) {

			if(sampleObject.getClassLabelValue().booleanValue() != targetValue.booleanValue()){
				return false;
			}
		}

		return true;

	}









}
