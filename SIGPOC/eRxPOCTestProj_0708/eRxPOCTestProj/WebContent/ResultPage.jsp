<%@page import="commom.SIGConvertor"%>
<%@page import="commom.GeneralConstants"%>
<%@page import="commom.CommonUtils"%>
<%@page import="commom.SIGToken"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results1</title>

<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
.footer {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	font-weight: bold;
	text-align: center;
	color: black;
}
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:10px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	width: 100%;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
</head>
<body>
<% 
List<SIGToken> parseBeanList = (List<SIGToken>)request.getAttribute("resultListBean");
List<String> inputShortSigList = (List<String>)request.getAttribute("inputShortSigList");
List<Long> elapsedTimeList = (List<Long>)request.getAttribute("elapsedTimeList");
Long elapsedTimeTotal = (Long)request.getAttribute("elapsedTimeTotal");
%>
<table border="2" bordercolor="black" class="gridtable" width="100%">
<tr bgcolor="orange">
<th align="center"><b>S# </b></th>
<th align="center"><b>Input String</b></th>
<th align="center"><b>Standardized String</b></th>
<th align="center"> <b>Non Parsed Tokens</b></th>
<th align="center"> <b>Successful Parse Score</b></th>
<th align="center"> <b>Parse Status</b></th>
<th align="center"> <b>Reason for Fail</b></th>
<th align="center"> <b>Verb </b></th>
<th align="center"><b>Frequency </b></th>
<th align="center"><b>Frequency Range</b></th>
<th align="center"><b>Time Of Day </b></th>
<th align="center"> <b>Dosage </b></th>
<th align="center"> <b>Dosage Form </b></th>
<th align="center"> <b>Dosage Range </b></th>
<th align="center"> <b>Duration</b></th>
<th align="center"> <b>Duration Range</b></th>
<th align="center"> <b>Drug Strength</b></th>
<th align="center"><b> Route of Administration</b></th>
<th align="center"> <b>Site of Administration</b></th>
<th align="center"> <b>Vehicle</b></th>
<th align="center"> <b>Indication</b></th>
<th align="center"> <b>Direction</b></th>
<th align="center"> <b>Instruction</b></th>
<th align="center"> <b>Parsed Short SIG</b></th>
<th align="center"> <b>IC+ Short SIG</b></th>
<th align="center"> <b>Parsed Long SIG</b></th>
<th align="center"> <b>IC+ Long SIG</b></th>
<th align="center"> <b>Accuracy Variation</b></th>
<th align="center"> <b>Elapsed Time (ms)</b></th>
</tr>

<%
int count = 0, completeCount = 0, partialCount = 0, failCount = 0;
String parsedLongSig, icpLongSig;
double parseScore = 0, totalParseScore = 0;
for(SIGToken p: parseBeanList)
{
	parseScore = p.getParseScore();
	totalParseScore += parseScore;
	
	if(GeneralConstants.PARSE_STATUS_COMPLETE.equals(p.getParseStatus())) {
		completeCount++;
	}
	else if(GeneralConstants.PARSE_STATUS_PARTIAL.equals(p.getParseStatus())) {
		partialCount++;
	}
	else if(GeneralConstants.PARSE_STATUS_FAIL.equals(p.getParseStatus())) {
		failCount++;
	}
%>
<tr bgcolor="cornsilk">
<td align="center"> <b><%= ++count%></b> </td>
<td align="center"> <b><%= p.getInputSIG()%></b> </td>
<td align="center"> <b><%= p.getStandardInputSIG()%></b> </td>
<td align="center"> <b><%= p.getNonParsedTokens()%> </b></td>
<td align="center"> <b><%= String.format("%.2f", parseScore)%> </b></td>
<td align="center"> <b><%= p.getParseStatus()%> </b></td>
<td align="center"> <b><%= p.getReasonForFail()%> </b></td>
<td align="center"><b> <%= CommonUtils.toString(p.getVerb())%> </b></td>
<td align="center"><b> <%= CommonUtils.toString(p.getFrequency())%></b> </td>
<td align="center"><b> <%= CommonUtils.toString(p.getFrequencyRange())%></b> </td>
<td align="center"> <b><%= CommonUtils.toString(p.getTimeOfDay())%></b> </td>
<td align="center"> <b><%= CommonUtils.toString(p.getDosage())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getDosageForm())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getDosageRange())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getDuration())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getDurationRange())%> </b></td>
<td align="center"><b> <%= CommonUtils.toString(p.getDrugStrength())%></b> </td>
<td align="center"> <b><%= CommonUtils.toString(p.getRouteOfAdmin())%></b> </td>
<td align="center"><b> <%= CommonUtils.toString(p.getSiteOfAdmin())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getVehicle())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getIndication())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getDirection())%> </b></td>
<td align="center"> <b><%= CommonUtils.toString(p.getInstruction())%> </b></td>
<%
parsedLongSig = SIGConvertor.toLongSIG(p.getShortSig());
icpLongSig = SIGConvertor.toLongSIG(inputShortSigList.get(count-1));
%>
<td align="center"> <b><%= p.getShortSig()%> </b></td>
<td align="center"> <b><%= inputShortSigList.get(count-1)%> </b></td>
<td align="center"> <b><%= parsedLongSig%> </b></td>
<td align="center"> <b><%= icpLongSig%> </b></td>
<td align="center"> <b><%= CommonUtils.compareTokens(icpLongSig, parsedLongSig)%> </b></td>
<td align="center"> <b><%= elapsedTimeList.get(count-1)%> </b></td>
<!--  <td align="center"> <input type = "button" name = "update" value = "Update"/></td>-->
</tr>
<%}
%>
</table>
<%
totalParseScore = (totalParseScore / count);
%>
<br>
<table width="50%" align="center" class="footer">
	<td>Complete - <%= completeCount%></td>
	<td>Partial - <%= partialCount%></td>
	<td>Fail - <%= failCount%></td>
	<td>Total - <%= count%></td>
</table>
<br>
<div class="footer">Average Successful Parse Score - <%= String.format("%.2f", totalParseScore)%></div>
<br>
<div class="footer">Total Elapsed Time - <%= elapsedTimeTotal/1000.0%> seconds</div>
</body>
</html>