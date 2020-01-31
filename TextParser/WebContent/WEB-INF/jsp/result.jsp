<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TextParser - Result</title>
</head>
<body>
${textParserTO.attributes}
<br><br>
<table border=1>
	<tr>
		<th>Attribute Name</th>
		<th>Value</th>
	</tr>
	<c:forEach var="attribute" items="${textParserTO.attributes}">
		<tr>
			<td>${attribute.key}</td>
			<td>
				<c:forEach var="attributeValue" items="${attribute.value}">
					${attributeValue.value}<br>
				</c:forEach>
			</td>
		</tr>
	</c:forEach>
</table>
<br>
${dictionaryFactory.getValue("${list.testList.value}")}
</body>
</html>