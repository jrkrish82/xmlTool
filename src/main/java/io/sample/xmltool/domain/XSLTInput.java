package io.sample.xmltool.domain;

import java.util.HashMap;
import java.util.Map;

public class XSLTInput {
	
	private String xsltPayload;
	private String inputXml;
	private Map<String,String> parameters=new HashMap<>();
	public String getXsltPayload() {
		return xsltPayload;
	}
	public void setXsltPayload(String xsltPayload) {
		this.xsltPayload = xsltPayload;
	}
	public String getInputXml() {
		return inputXml;
	}
	public void setInputXml(String inputXml) {
		this.inputXml = inputXml;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	@Override
	public String toString() {
		return "XSLTInput [xsltPayload=" + xsltPayload + ", inputXml=" + inputXml + ", parameters=" + parameters + "]";
	}
	
	
	}
