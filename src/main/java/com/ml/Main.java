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
//		
//		 BufferedReader reader = new BufferedReader(
//                 new FileReader("C:/Users/furkan/desktop/weather.arff"));
//		 Instances data = new Instances(reader);
//		 reader.close();
//		 // setting class attribute
//		 data.setClassIndex(data.numAttributes() - 1);
//		 
//		 Attribute attribudes =
//		 
//		
//		 for (Instance instance : data) {
//			
//			 System.out.println(instance.);
//		}
		String fileName = "C:/Users/furkan/desktop/training_subsetD.arff";
//		Matrix matrix = new Matrix();
//		matrix.loadArff(fileName);
		DataTable table = new DataTable();
		table.loadArffToDataTable(fileName);
		System.out.println("parsed completed!");
	

	}


}
