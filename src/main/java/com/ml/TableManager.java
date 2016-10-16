package com.ml;

import java.util.ArrayList;
import java.util.List;

public class TableManager {

	
	/**
	 * 
	 * @param columnIndex
	 * @param value
	 * @param dataSet
	 * @return
	 */
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
	 * Returns the list of each value occurrence
	 * @param attIndex, attribute columnIndex
	 * @param dataSet
	 * @return
	 */
	public static ArrayList<Occurrence> getAttributeValueOccurrences(int attIndex, DataTable dataSet) {

		// attribute.value occurrences, #of positive sample
		ArrayList<Occurrence> occurrences = new ArrayList<>();

		int valueCountOfAttribute = dataSet.getEnumToStr().get(attIndex).size();

		List<SampleObject> samples = dataSet.getSamples();
		
		for (int i = 0; i < valueCountOfAttribute; i++) {
			occurrences.add(new Occurrence(0,0,dataSet.getEnumToStr().get(attIndex).get(i)));
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
	

	/**
	 * trim ' \ / ( ) [ ] ; characters from value
	 * @param dirtyValue
	 * @return
	 */
	@Deprecated
	private String trimUndesiredCharacters(String dirtyValue){
		dirtyValue = dirtyValue.replaceAll("[\\'\\;/]", "");
		dirtyValue = dirtyValue.replace("\\", "");

		return dirtyValue;
	}

	/**
	 * calculates chi-squared statistic of the data
	 * @param bestAttribute
	 * @param dataSet
	 * @return
	 */
	public static double chiSquareTest(Attribute bestAttribute, DataTable dataSet) {

		int attributeColumnIndex = bestAttribute.getColumnIndex();

		List<Occurrence> occurrences = TableManager.getAttributeValueOccurrences(attributeColumnIndex, dataSet);

		int numberOfPositiveSamples = dataSet.getNumberOfInstances(Boolean.TRUE);
		int numberOfNegativeSamples = dataSet.getNumberOfInstances(Boolean.FALSE);	
		int numberOfAttributesOccured = 0;

		for (Occurrence occurrence : occurrences) {
			numberOfAttributesOccured += occurrence.getNumberOfoccurrences();
		}

		double deviation = 0.0;
		if(numberOfAttributesOccured == 0){
			return deviation;
		}else{
			// for every value in attribute value set
			for (Occurrence occurrence : occurrences) {
				double positiveOccuured = occurrence.getNumberOfPositiveOccurrences();
				double negativeOccurred = occurrence.getNumberOfoccurrences() - occurrence.getNumberOfPositiveOccurrences();
				double positiveExpected = ((double)numberOfPositiveSamples / (numberOfNegativeSamples + numberOfPositiveSamples)) * numberOfAttributesOccured;
				double megativeExpected = ((double)numberOfNegativeSamples / (numberOfNegativeSamples + numberOfPositiveSamples)) * numberOfAttributesOccured;
				deviation +=  (Math.pow(positiveOccuured - positiveExpected, 2) / positiveExpected) + (Math.pow(negativeOccurred - megativeExpected, 2) / megativeExpected); 
			}
		}
		return deviation;
	}
}
