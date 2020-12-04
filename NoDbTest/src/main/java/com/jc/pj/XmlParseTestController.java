package com.jc.pj;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Controller
public class XmlParseTestController {
	
	@RequestMapping(value="/xmlLoad", method = RequestMethod.GET)
	public String XmlParse() throws SAXException, IOException, ParserConfigurationException {
		String url = "https://www.mafra.go.kr/gonews/xml/xml2.jsp?startDate=20200416&endDate=20200416";
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactoty.newDocumentBuilder();
		Document doc = dBuilder.parse(url);
		
		doc.getDocumentElement().normalize();
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); 
		NodeList nList = doc.getElementsByTagName("result");
		
		for(int temp = 0; temp < nList.getLength(); temp++){		
			Node nNode = nList.item(temp);
			if(nNode.getNodeType() == Node.ELEMENT_NODE){
								
				Element eElement = (Element) nNode;
				System.out.println("######################");
				//System.out.println(eElement.getTextContent());
				System.out.println("policyType  : " + getTagValue("policyType", eElement));
				System.out.println("repcategory  : " + getTagValue("repcategory", eElement));
				
				System.out.println("policyType========" + eElement.getElementsByTagName("policyType").toString());
				System.out.println("repcategory========" + eElement.getElementsByTagName("repcategory").toString());
			}	// for end
		}	// if end
		
		return "home";
	}
	
	
	private static String getTagValue(String tag, Element eElement) {
	    
		
		System.out.println("===============ElementsByTagName");
		System.out.println(eElement.getElementsByTagName(tag).getLength());
		
		
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    for(int i=0; i<nlList.getLength(); i++) {
	    	System.out.println("===============");
	    	System.out.println("nlList : " + nlList.item(i).getNodeValue());
	    }
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
}
