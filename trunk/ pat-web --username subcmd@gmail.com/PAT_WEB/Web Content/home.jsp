<%@ page info="home"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

Home
<html>
<body>

<fieldset>
<legend>Hashtag</legend>
<html:form method="POST" action="/hashtag">
<html:hidden name="HashtagForm" property="operation" value=""/>
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("error"))) { %>
	Error: <%=request.getAttribute("error")%><br>
<% } %>
<html:text name="HashtagForm" property="edited.hashtag"/><br>
<html:submit property="operation">Save</html:submit>
</html:form>
</fieldset>

</body>
</html>