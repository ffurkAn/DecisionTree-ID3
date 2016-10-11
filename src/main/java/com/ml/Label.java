package com.ml;

public class Label {
	
	private String labelStr;
	private Boolean labelValue;
	
	public Label(Boolean classLabel){
		
		labelValue = classLabel;
		labelStr = classLabel.toString();
		
	}
	
	public String getLabelStr() {
		return labelStr;
	}
	public void setLabelStr(String labelStr) {
		this.labelStr = labelStr;
	}
	public Boolean getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(Boolean labelValue) {
		this.labelValue = labelValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labelStr == null) ? 0 : labelStr.hashCode());
		result = prime * result + ((labelValue == null) ? 0 : labelValue.hashCode());
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
		Label other = (Label) obj;
		if (labelStr == null) {
			if (other.labelStr != null)
				return false;
		} else if (!labelStr.equals(other.labelStr))
			return false;
		if (labelValue == null) {
			if (other.labelValue != null)
				return false;
		} else if (!labelValue.equals(other.labelValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Label [labelStr=" + labelStr + ", labelValue=" + labelValue + "]";
	}
	

}
