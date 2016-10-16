package com.ml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Class for responsible for holding the samples.
 * Missing values '?' remains same
 * @author furkan
 *
 */
public class DataTable {

	Logger logger = Logger.getGlobal();
	
	private static final String CLASS = "class";
	private static final String CLASS_LABEL_FALSE = "false";
	private static final String CLASS_LABEL_TRUE = "true";
	private static final String DATA = "@data";
	private static final String RELATION = "@relation";
	private static final String ATTRIBUTE = "@attribute";

	private String relationName;
	private List<Attribute> attributes;
	private List<SampleObject> samples;
	private HashMap<String, Boolean> targetAttributes;
	
	// holds the attribute values as <value,index> pair
	private List<TreeMap<String, Integer>> strToEnum;
	private List<TreeMap<Integer, String>> enumToStr;
	private double entropy;


	public HashMap<String, Boolean> getTargetAttributes() {
		return targetAttributes;
	}


	public void setTargetAttributes(HashMap<String, Boolean> targetAttributes) {
		this.targetAttributes = targetAttributes;
	}

	public DataTable() {
		entropy = 0.0;
		relationName = "";
		attributes = new ArrayList<>();
		strToEnum = new ArrayList<>();
		enumToStr = new ArrayList<>();
		samples = new ArrayList<>();
		targetAttributes = new HashMap<>();
		targetAttributes.put(CLASS_LABEL_FALSE, Boolean.FALSE);
		targetAttributes.put(CLASS_LABEL_TRUE, Boolean.TRUE);

	}



	public double getEntropy() {
		return entropy;
	}


	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}


	public List<Attribute> getAttributes() {
		return attributes;
	}


	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}


	public String getRelationName() {
		return relationName;
	}


	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}



	public List<SampleObject> getSamples() {
		return samples;
	}


	public void setSamples(List<SampleObject> samples) {
		this.samples = samples;
	}


	public List<TreeMap<String, Integer>> getStrToEnum() {
		return strToEnum;
	}


	public void setStrToEnum(List<TreeMap<String, Integer>> strToEnum) {
		this.strToEnum = strToEnum;
	}


	public List<TreeMap<Integer, String>> getEnumToStr() {
		return enumToStr;
	}


	public void setEnumToStr(List<TreeMap<Integer, String>> enumToStr) {
		this.enumToStr = enumToStr;
	}



	@Override
	public String toString() {
		return "DataTable [relationName=" + relationName + ", attributes=" + attributes + ", samples=" + samples
				+ ", targetAttributes=" + targetAttributes + ", strToEnum=" + strToEnum + ", enumToStr=" + enumToStr
				+ ", entropy=" + entropy + "]";
	}

	/**
	 * 
	 * Loads the arff file to DataTable
	 * @param fileName
	 * @throws Exception
	 */
	public void loadArffToDataTable(String fileName) throws Exception{

		int sampleId = 0;
		boolean readData = false;
		int attributeIndex = 0;

		Scanner s = new Scanner(new File(fileName));

		System.out.println("Started to parsing file..");
		logger.info("Started to parsing file..");
		while (s.hasNext()) {

			String line = s.nextLine().trim();

			// if it is not a empty line
			if(line.length() > 0){

				// if line is not under the @data section
				if(!readData){ 

					Scanner a = new Scanner(line);
					String firstToken = a.next().toLowerCase();

					// parse the relation
					if (firstToken.toLowerCase().equals(RELATION)) {
						relationName = a.nextLine();
						
					}
					// parse the attributes
					else if(firstToken.toLowerCase().equals(ATTRIBUTE)){
						TreeMap<String, Integer> ste = new TreeMap<String, Integer>();
						strToEnum.add(ste);
						TreeMap<Integer, String> ets = new TreeMap<Integer, String>();
						enumToStr.add(ets);

						Scanner b = new Scanner(line);
						if (line.indexOf("'") == 11) {
							b.useDelimiter("'");
						}
						b.next();
						String attributeName = b.next();
						if (line.indexOf("'") == 11){
							attributeName = "'" + attributeName + "'";
						}

						//	ignore the Class attribute
						if(!attributeName.equalsIgnoreCase(CLASS)){

							Attribute attribute = new Attribute();
							attribute.setName(attributeName);
							attribute.setValues(new LinkedHashSet<>());
							attribute.setColumnIndex(attributeIndex++);

							// parse the values for attribute
							int enumValue = 0;
							try{
								String values = line.substring(line.indexOf("{")+1,line.indexOf("}"));
								Scanner c = new Scanner(values);
								c.useDelimiter(",");
								while (c.hasNext()) {
									String value = c.next().trim();

									if(value.length() > 0)
									{
										ste.put(value, new Integer(enumValue));
										ets.put(new Integer(enumValue), value);
										enumValue++;
									}

									attribute.getValues().add(value);
								}
								attributes.add(attribute);	

							}catch(Exception e){
								logger.info("Error occured while parsinge @attribute at line: " + line + "\n" + e.toString() +  "///// " + e.getMessage());
							}
						}

					}else if (firstToken.toLowerCase().equals(DATA)) { // attribute secition is over.
						readData = true;
					}


				}else{ // data section starts from now

					try{

						SampleObject sample = new SampleObject();
						sample.setId(sampleId++);
						sample.setSampleValues(new ArrayList<>());

						Scanner d = new Scanner(line);
						d.useDelimiter(",");
						while(d.hasNext()){

							String textValue = d.next().trim();

							if(textValue.length() > 0){

								sample.getSampleValues().add(textValue/*trimUndesiredCharacters(textValue)*/);
							}

						}

						// remove the classifier
						String classLabel = sample.getSampleValues().remove(sample.getSampleValues().size()-1);
						sample.setClassLabel(classLabel.toLowerCase());
						sample.setClassLabelValue(Boolean.valueOf(classLabel.toLowerCase()));

						if(sample.getSampleValues().size() != 274){
							System.out.println("eksik deðer.. SampleId:" + sample.getId());
						}
						samples.add(sample);

					}catch(Exception e){
						logger.info("Error occured while parsinge @data at line: " + line + "\n" + e.toString() +  "///// " + e.getMessage());
					}
				}
			}



		}

	}

	/**
	 * Calculates the entropy for table
	 */
	public void calculateGeneralEntropy() {

		int size = getSamples().size();
		int positives = 0;
		int negatives = 0;

		for (SampleObject sampleObject : getSamples()) {

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
	 * Creates entire attributes for the ID3 algorithm
	 * This set does not include the target attribute 
	 * @param dataSet
	 * @return
	 */
	public LinkedHashSet<Attribute> createAllAttributesAndFindMostCommonUsedValue () {
		LinkedHashSet<Attribute> attributes = new LinkedHashSet<Attribute>();
		for (Attribute attribute : getAttributes()) {

			List<Occurrence> valueOccurrences = TableManager.getAttributeValueOccurrences(attribute.getColumnIndex(),this);

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


	/**
	 * Number of instances with class targetFlag
	 * @param targetFlag
	 * @return
	 */
	public int getNumberOfInstances(Boolean targetFlag) {

		int numberOf = 0;

		for (SampleObject sampleObject : getSamples()) {

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
	public Label getMostCommonUsedClassLabel() {

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
}
