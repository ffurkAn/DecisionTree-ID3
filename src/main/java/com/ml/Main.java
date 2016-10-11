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
		
		ID3 id3 = new ID3();
		id3.setDataTable(new DataTable());
		id3.getDataTable().loadArffToDataTable(fileName);
		id3.calculateEntropy();
		
		DecisionTree tree = new DecisionTree();
		tree.setRootNode(id3.buildTree());
		
	

	}


}
