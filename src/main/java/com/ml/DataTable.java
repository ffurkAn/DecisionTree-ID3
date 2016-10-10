package com.ml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Logger;

public class DataTable {
	
	private static final String CLASS_LABEL_FALSE = "false";
	private static final String CLASS_LABEL_TRUE = "true";
	private static final String MISSING_VALUE = "?";
	private static final String DATA = "@data";
	private static final String RELATION = "@relation";
	private static final String ATTRIBUTE = "@attribute";
	
	private String relationName;
	private ArrayList<Attribute> attributes;
	private ArrayList<SampleObject> samples;
	private ArrayList<TreeMap<String, Integer>> strToEnum;
	private ArrayList<TreeMap<Integer, String>> enumToStr;
	
	
	public DataTable() {
		relationName = "";
		attributes = new ArrayList<>();
		strToEnum = new ArrayList<>();
		enumToStr = new ArrayList<>();
		samples = new ArrayList<>();
		
	}


	public String getRelationName() {
		return relationName;
	}


	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}


	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}


	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}


	public ArrayList<SampleObject> getSamples() {
		return samples;
	}


	public void setSamples(ArrayList<SampleObject> samples) {
		this.samples = samples;
	}


	public ArrayList<TreeMap<String, Integer>> getStrToEnum() {
		return strToEnum;
	}


	public void setStrToEnum(ArrayList<TreeMap<String, Integer>> strToEnum) {
		this.strToEnum = strToEnum;
	}


	public ArrayList<TreeMap<Integer, String>> getEnumToStr() {
		return enumToStr;
	}


	public void setEnumToStr(ArrayList<TreeMap<Integer, String>> enumToStr) {
		this.enumToStr = enumToStr;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((enumToStr == null) ? 0 : enumToStr.hashCode());
		result = prime * result + ((relationName == null) ? 0 : relationName.hashCode());
		result = prime * result + ((samples == null) ? 0 : samples.hashCode());
		result = prime * result + ((strToEnum == null) ? 0 : strToEnum.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataTable other = (DataTable) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (enumToStr == null) {
			if (other.enumToStr != null)
				return false;
		} else if (!enumToStr.equals(other.enumToStr))
			return false;
		if (relationName == null) {
			if (other.relationName != null)
				return false;
		} else if (!relationName.equals(other.relationName))
			return false;
		if (samples == null) {
			if (other.samples != null)
				return false;
		} else if (!samples.equals(other.samples))
			return false;
		if (strToEnum == null) {
			if (other.strToEnum != null)
				return false;
		} else if (!strToEnum.equals(other.strToEnum))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "DataTable [relationName=" + relationName + ", attributes=" + attributes + ", samples=" + samples
				+ ", strToEnum=" + strToEnum + ", enumToStr=" + enumToStr + "]";
	}
	
	
	public void loadArffToDataTable(String fileName) throws Exception{
		
		int sampleId = 0;
		boolean readData = false;
		
		Scanner s = new Scanner(new File(fileName));
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
						
						Attribute attribute = new Attribute();
						attribute.setName(attributeName);
						attribute.setValues(new ArrayList<>());
						
						// parse the values for attribute
						
						int enumValue = 0;
						try{
							String values = line.substring(line.indexOf("{")+1,line.indexOf("}"));
							Scanner c = new Scanner(values);
							c.useDelimiter(",");
							while (c.hasNext()) {
								String value = trimUndesiredCharacters(c.next().trim());
								
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
						
					}else if (firstToken.toLowerCase().equals(DATA)) {
						readData = true;
					}
					
					
				}else{ // data section starts from now
					
					int valueIndex = 0;
					
					try{

						SampleObject sample = new SampleObject();
						sample.setId(++sampleId);
						sample.setSampleValues(new ArrayList<>());
						
						Scanner d = new Scanner(line);
						d.useDelimiter(",");
						while(d.hasNext()){
							
							String textValue = d.next().trim();
							
							if(textValue.length() > 0){
								
								if(MISSING_VALUE.equals(textValue)){
									textValue = MISSING_VALUE;
								}else if(CLASS_LABEL_TRUE.equals(textValue.toLowerCase()) || CLASS_LABEL_FALSE.equals(textValue.toLowerCase())){
									sample.setClassLabel(textValue.toLowerCase());
								}
								
							}
							sample.getSampleValues().add(trimUndesiredCharacters(textValue));
						}
						
						samples.add(sample);
						
					}catch(Exception e){
						throw new Exception("Error occured while parsinge @data at line: " + line + "\n" + e.toString());
					}
				}
			}
			
			
			
		}
				
			
	}
	
	// trim ' \ / ( ) [ ] ; characters from value
	private String trimUndesiredCharacters(String dirtyValue){
		dirtyValue = dirtyValue.replaceAll("[\\'\\;/]", "");
		dirtyValue = dirtyValue.replace("\\", "");
		
		return dirtyValue;
	}

}
