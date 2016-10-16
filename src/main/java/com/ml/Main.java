package com.ml;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) throws Exception {

		Logger logger = Logger.getGlobal();
		long trainingStartTime = System.currentTimeMillis();
		String fileName = "C:/Users/furkan/desktop/training_subsetD.arff";

		logger.info("Reading training data..");
		DataTable datasetTable = new DataTable();
		datasetTable.loadArffToDataTable(fileName);

		ID3 id3 = new ID3();
		DecisionTree tree = new DecisionTree();
		tree.setRootNode(id3.buildTree(datasetTable));
		long trainTime = System.currentTimeMillis() - trainingStartTime;
		logger.info("Building is completed in: " + TimeUnit.MILLISECONDS.toSeconds(trainTime) + " seconds.");
		
		long testStartTime = System.currentTimeMillis();
		String testFileName = "C:/Users/furkan/desktop/testingD.arff";
		
		logger.info("Reading test data..");
		DataTable testDataTable = new DataTable();
		testDataTable.loadArffToDataTable(testFileName);
		tree.testTree(testDataTable);
		long testTime = System.currentTimeMillis() - testStartTime;
		logger.info("Test is completed in " + TimeUnit.MILLISECONDS.toSeconds(testTime) + " seconds.");
		
		logger.info("Total time of building, pruning and testing of algotihm is: "+ TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - trainingStartTime)) + " seconds.");
	}

}
