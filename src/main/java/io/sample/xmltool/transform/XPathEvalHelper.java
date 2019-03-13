package io.sample.xmltool.transform;

import java.io.StringReader;
import java.text.MessageFormat;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.WhitespaceStrippingPolicy;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;

@Component("xPathEvalHelper")
public class XPathEvalHelper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public String evaluateXpathExpressionForVersionOnePointZero(Document doc, String expression)
			throws XPathExpressionException {
		String result = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			result = (String) xPath.compile(expression).evaluate(doc);
		} catch (XPathExpressionException e) {
			String errorMessage = MessageFormat
					.format("evaluateXpathExpressionForVersionOnePointZero while evaluating the xpath expression={1}", expression);
			log.error(errorMessage, e);
			throw e;
		}
		return result;
	}

	public String evaluateXpathExpressionForVersionTwoPointZero(String xml, String expression)
			throws SaxonApiException {
		String result = null;
		try {
			Processor proc = new Processor(false);
			XPathCompiler xPath = proc.newXPathCompiler();
			DocumentBuilder builder = proc.newDocumentBuilder();
			builder.setLineNumbering(true);
			builder.setWhitespaceStrippingPolicy(WhitespaceStrippingPolicy.ALL);
			Source src = new StreamSource(new StringReader(xml));
			XdmNode doc = builder.build(src);

			XPathSelector selector = xPath.compile(expression).load();
			selector.setContextItem(doc);
			XdmItem xdmItem = selector.evaluateSingle();
			result = xdmItem == null ? null : xdmItem.getStringValue();

		} catch (SaxonApiException e) {
			String errorMessage = MessageFormat
					.format("evaluateXpathExpressionForVersionTwoPointZero while evaluating the xpath expression={1}", expression);
			log.error(errorMessage, e);
			throw e;
		}
		return result;
	}

}