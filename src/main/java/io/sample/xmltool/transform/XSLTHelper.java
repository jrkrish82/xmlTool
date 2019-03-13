package io.sample.xmltool.transform;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

@Component("xSLTHelper")
public class XSLTHelper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String transform(String inputXml,String xsltPayload, Map<String,String> parameters) throws SaxonApiException{
        
        Processor proc = new Processor(false);
        StringWriter sw = new StringWriter(); 
        Serializer out = proc.newSerializer(sw); 
        XsltCompiler comp = proc.newXsltCompiler();
        XsltExecutable exec = comp.compile(new StreamSource(new StringReader(xsltPayload)));
        XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(inputXml)));
        XsltTransformer transformer = exec.load();
        if(!CollectionUtils.isEmpty(parameters)) {
                parameters.forEach((k,v)->{
                          QName paramName = new QName(k); 
                         transformer.setParameter(paramName, new XdmAtomicValue(v));
                     });
            }
        transformer.setInitialContextNode(source); 
        transformer.setDestination(out);
        transformer.transform();
        return sw.toString();
 }


}