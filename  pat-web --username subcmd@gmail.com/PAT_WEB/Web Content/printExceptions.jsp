<%@ page info="printExceptions"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<html>
<body>
<table border="1">
<% for (com.tdil.pat.log4j.LogError logError : com.tdil.pat.log4j.LoggerMonitorAppender.getErrors()) { %>
	<tr>
		<td>
			<%=logError.getExceptionMessage()%> at <%=logError.getDate()%><br>
			<%=logError.getExceptionStackTrace()%>
		</td>
	</tr>
<% } %>
</table>
</body>
</html>