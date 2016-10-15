package com.ml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.management.relation.RelationNotification;

import Main.Matrix;
import weka.core.Instance;
import weka.core.Instances;


public class Main {

	public static void main(String[] args) throws Exception {

		String fileName = "C:/Users/furkan/desktop/training_subsetD.arff";

		TableManager manager = new TableManager();

		DataTable datasetTable = new DataTable();
		datasetTable.loadArffToDataTable(fileName);

		ID3 id3 = new ID3();
		DecisionTree tree = new DecisionTree();
		tree.setRootNode(id3.buildTree(datasetTable));
		
		
		
		tree.prune();
		

	}



}
