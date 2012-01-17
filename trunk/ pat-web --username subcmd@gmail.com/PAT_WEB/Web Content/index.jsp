<%@ page info="index"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<html:form method="POST" action="/login">
<html:hidden name="LoginForm" property="operation" value=""/>
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("error"))) { %>
	Error: <%=request.getAttribute("error")%><br>
<% } %>
<html:text name="LoginForm" property="username"/><br>
<html:password name="LoginForm" property="password"/><br>
<html:submit property="operation">Login</html:submit>

</html:form>
