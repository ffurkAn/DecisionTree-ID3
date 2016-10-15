package com.ml;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class TableManager {

	
	public static DataTable getTrimmedDataSet(int columnIndex, String value, DataTable dataSet) {

		DataTable trimmedDataSet = new DataTable();
		trimmedDataSet.setRelationName(dataSet.getRelationName());
		
		// add samples only matches the attribute value
		for (SampleObject sampleObject : dataSet.getSamples()) {
			if(sampleObject.getSampleValues().get(columnIndex).equals(value)){
				trimmedDataSet.getSamples().add(sampleObject);
			}
		}
		
		trimmedDataSet.setEnumToStr(dataSet.getEnumToStr());
		trimmedDataSet.setStrToEnum(dataSet.getStrToEnum());
		trimmedDataSet.setTargetAttributes(dataSet.getTargetAttributes());
		return trimmedDataSet;
	}
	
	/**
	 * Attribute içindeki valuelardan kaçar tane kullanýlmýþ
	 * Missing deðerler için MOST COMMON VALUE kullanýlmýþtýr.
	 * @param attIndex
	 * @param samples2
	 * @return
	 */
	public ArrayList<Occurrence> getAttributeValueOccurrences(int attIndex, DataTable dataSet) {

		// attribute.value occurrences, #of positive sample
		ArrayList<Occurrence> occurrences = new ArrayList<>();

		int valueCountOfAttribute = dataSet.getEnumToStr().get(attIndex).size();

		List<SampleObject> samples = dataSet.getSamples();
		
		for (int i = 0; i < valueCountOfAttribute; i++) {
			int occurrenceCount = 0;
			occurrences.add(new Occurrence(0,0));
			for(int j = 0; j < samples.size(); j++){

				// Returns the value at the specified row<j> and column<attIndex>
				// and compares it with the attribute value
				if(samples.get(j).getSampleValues().get(attIndex).equals(dataSet.getEnumToStr().get(attIndex).get(i))){

					occurrences.get(i).setNumberOfoccurrences(occurrences.get(i).getNumberOfoccurrences()+1);
					if(samples.get(j).getClassLabelValue()){
						occurrences.get(i).setNumberOfPositiveOccurrences(occurrences.get(i).getNumberOfPositiveOccurrences()+1);
					}
				}
			}
		}

		return occurrences;
	}
	

	// trim ' \ / ( ) [ ] ; characters from value
	private String trimUndesiredCharacters(String dirtyValue){
		dirtyValue = dirtyValue.replaceAll("[\\'\\;/]", "");
		dirtyValue = dirtyValue.replace("\\", "");

		return dirtyValue;
	}

}
