package io.sample.xmltool.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import io.sample.xmltool.domain.TranformerInput;
import io.sample.xmltool.domain.XPathInput;
import io.sample.xmltool.domain.XsdValidationInput;
import io.sample.xmltool.service.TransformerService;
import net.sf.saxon.s9api.SaxonApiException;

@RestController
@RequestMapping("/api/xml")
public class XmlToolController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransformerService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/xquery")
	public String transformXQuery(@RequestBody TranformerInput transformer) {

		log.debug("Input :{}", transformer);
		log.trace("input xml \n {}", transformer.getXml());
		if (!StringUtils.isEmpty(transformer.getParam()))
			transformer.setParameters(service.stringtoMapParam(transformer.getParam()));

		String result = "Processing.,..";
		try {
			result = service.transformXQuery(transformer.getXqueryValue(), transformer.getXml(),
					transformer.getParameters());
		} catch (SaxonApiException | IOException e) {
			log.error("Error in xquery transform {}", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();

		}

		return result;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/xpath")
	public String evaluateXPath(@RequestBody XPathInput xPathInput) {
		String result = null;
		try {
			if (xPathInput != null) {
				if ("1.0".equals(xPathInput.getVersion())) {

					result = service.evaluateXpathExpressionForVersionOnePointZero(xPathInput.getXpathValue(),
							xPathInput.getXml());

				} else if ("2.0".equals(xPathInput.getVersion())) {
					result = service.evaluateXpathExpressionForVersionTwoPointZero(xPathInput.getXpathValue(),
							xPathInput.getXml());
				} else {
					log.error("unsupported version {}", xPathInput.getVersion());
					return "Unsupported xpath Version";
				}
			}
		} catch (Exception e) {
			log.error("Error in evaluateXPath {}", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}

		return result;
	}

	@PostMapping(produces = MediaType.TEXT_XML_VALUE, path = "/xsd")
	public String valudateXmlSchema(@RequestBody XsdValidationInput input) {
		String result;
		try {
			result = service.validateSchema(input.getXml(), input.getXsd());
		} catch (SAXException | IOException e) {
			log.error("Error while evaluating against xsd", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/xslt")
	public String transformXSLT(@RequestBody TranformerInput transformer) {

		log.debug("Input :{}", transformer);
		log.trace("input xml \n {}", transformer.getXml());
		if (!StringUtils.isEmpty(transformer.getParam()))
			transformer.setParameters(service.stringtoMapParam(transformer.getParam()));

		String result = "Processing.,..";
		try {
			result = service.transformXSLT(transformer.getXqueryValue(), transformer.getXml(),
					transformer.getParameters());
		} catch (Exception e) {
			log.error("Error in xslt transform {}", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}

		return result;
	}
}
