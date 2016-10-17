package com.ml;

import java.util.LinkedHashSet;


public class DecisionTree {

	
	private Node rootNode;
	
	public DecisionTree(){
		rootNode = null;
	}
	
	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}

	/**
	* uses the test dataset and visits nodes by its matching value
	*/
	public void testTree(DataTable testDataTable) {
		
		int numberOfAccurate = 0;
		int numberOfNotAccurate = 0;
		
		for (SampleObject sampleObject : testDataTable.getSamples()) {
			
			if(sampleObject.getClassLabel().toLowerCase().equals(rootNode.makeDecision(sampleObject.getSampleValues(),rootNode.getAttribute().getColumnIndex()))){
				numberOfAccurate++;
			}else{
				numberOfNotAccurate++;
			}
		}
		
		double accuracy = ((double)numberOfAccurate / (numberOfAccurate + numberOfNotAccurate)) * 100;
		
		System.out.printf("\nAccuracy of the tree is: %.3f\n",accuracy);
		
		
		
	}


	
	
	
	
	
}
