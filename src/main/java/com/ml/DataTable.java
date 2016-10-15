package com.ml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

public class DataTable {

	private static final String CLASS = "class";
	private static final String CLASS_LABEL_FALSE = "false";
	private static final String CLASS_LABEL_TRUE = "true";
	private static final String MISSING_VALUE = "?";
	private static final String DATA = "@data";
	private static final String RELATION = "@relation";
	private static final String ATTRIBUTE = "@attribute";

	private String relationName;
	private List<Attribute> attributes;
	private List<SampleObject> samples;
	private HashMap<String, Boolean> targetAttributes;
	private List<TreeMap<String, Integer>> strToEnum;
	private List<TreeMap<Integer, String>> enumToStr;


	public HashMap<String, Boolean> getTargetAttributes() {
		return targetAttributes;
	}


	public void setTargetAttributes(HashMap<String, Boolean> targetAttributes) {
		this.targetAttributes = targetAttributes;
	}

	public DataTable() {
		relationName = "";
		attributes = new ArrayList<>();
		strToEnum = new ArrayList<>();
		enumToStr = new ArrayList<>();
		samples = new ArrayList<>();
		targetAttributes = new HashMap<>();
		targetAttributes.put(CLASS_LABEL_FALSE, Boolean.FALSE);
		targetAttributes.put(CLASS_LABEL_TRUE, Boolean.TRUE);

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
				+ "]";
	}


	public void loadArffToDataTable(String fileName) throws Exception{

		int sampleId = 0;
		boolean readData = false;
		int attributeIndex = 0;

		Scanner s = new Scanner(new File(fileName));
		
		System.out.println("Started to parsing file..");
		while (s.hasNext()) {

			String line = s.nextLine().trim();

			// if it is not a empty line
			if(line.length() > 0){

				// if line is not under the @data section
				if(!readData){ 

					Scanner a = new Scanner(line);
					String firstToken = a.next().toLowerCase();

					
					if (firstToken.toLowerCase().equals(RELATION)) {
						relationName = a.nextLine();
						System.out.println("@Relation extracted.");
					}else if(firstToken.toLowerCase().equals(ATTRIBUTE)){
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
						
//						attributes.add(attributeName);
						if(!attributeName.toLowerCase().equals(CLASS)){
							
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
								throw new Exception("Error occured while parsinge @attribute at line: " + line + "\n" + e.toString());
							}
						}

					}else if (firstToken.toLowerCase().equals(DATA)) {
						readData = true;
						System.out.println("@Attributes extracted.");
					}


				}else{ // data section starts from now

					int valueIndex = 0;

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
//						System.out.println(sample.getSampleValues().size()+"");
						
						if(sample.getSampleValues().size() != 274){
							System.out.println("eksik deðer.. SampleId:" + sample.getId());
						}
						samples.add(sample);

					}catch(Exception e){
						throw new Exception("Error occured while parsinge @data at line: " + line + "\n" + e.toString());
					}
				}
			}



		}
		
		System.out.println("@Data extracted.");


	}


	

}
