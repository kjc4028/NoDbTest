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
	
	static String xml = "<document>\r\n" + 
			"<root>\r\n" + 
			"<resultCode>1</resultCode>\r\n" + 
			"<resultMsg>접속 성공</resultMsg>\r\n" + 
			"<resultCnt>5</resultCnt>\r\n" + 
			"<result>\r\n" + 
			"<policyType>G00304</policyType>\r\n" + 
			"<repcategory>정책분야별자료</repcategory>\r\n" + 
			"<contentId>323865</contentId>\r\n" + 
			"<brmTrans>1</brmTrans>\r\n" + 
			"<repcategory>정책분야별자료</repcategory>\r\n" + 
			"<brmCode>F01</brmCode>\r\n" + 
			"<publishOrg>농림축산식품부</publishOrg>\r\n" + 
			"<originUrl>http://www.mafra.go.kr/bbs/mafra/71/323865/artclView.do?layout=unknown</originUrl>\r\n" + 
			"<regDate>2020-05-29 16:49:27</regDate>\r\n" + 
			"<subject>행정기관 소속 위원회 정비 이행계획 및 추진현황(2020.5월 말 기준)</subject>\r\n" + 
			"<contentsKor>\r\n" + 
			"<![CDATA[ <p>&nbsp;</p><p>&nbsp;* 우리부 정비대상 위원회(5개)의 정비 이행계획 및 추진현황을 게시합니다.('20.5월 말 기준)<br></p> ]]>\r\n" + 
			"</contentsKor>\r\n" + 
			"<atchfileNm>('20.5월말)행정기관 소속 위원회 정비 이행계획 및 추진현황(농식품부).hwp</atchfileNm>\r\n" + 
			"<atchfileUrl>http://www.mafra.go.kr/bbs/mafra/71/242680/download.do</atchfileUrl>\r\n" + 
			"</result>\r\n" + 
			"</root>\r\n" + 
			"</document>";
	
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
