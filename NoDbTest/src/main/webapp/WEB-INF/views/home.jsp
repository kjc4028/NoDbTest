<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<c:out value="${testTxt}"/>

<br>
==================
<br>

<c:out value="${testTxt}" escapeXml = "false"/>


<c:set value="test<br>test2" var="testStr"/>

<c:out value="${testStr}" escapeXml="false"/>
</body>
</html>
