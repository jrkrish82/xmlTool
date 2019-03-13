package io.sample.xmltool.domain;

public class XsdValidationInput {
	
	String xml;
	String xsd;
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getXsd() {
		return xsd;
	}
	public void setXsd(String xsd) {
		this.xsd = xsd;
	}
	@Override
	public String toString() {
		return "XsdValidationInput [xml=" + xml + ", xsd=" + xsd + "]";
	}
	
	

}
