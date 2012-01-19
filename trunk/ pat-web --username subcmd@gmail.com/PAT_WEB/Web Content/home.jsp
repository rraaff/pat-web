<%@ page info="home"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%
com.tdil.pat.model.User user = (com.tdil.pat.model.User)session.getAttribute("user");
	if (user == null) { %>
	<jsp:forward page="./index.jsp">
		<jsp:param name="error" value="notlogged"/>
	</jsp:forward>
<% 	
	} %>
<html>
<body>

<fieldset>
<legend>Hashtag</legend>
<html:form method="POST" action="/hashtag">
<html:hidden name="HashtagForm" property="operation" value=""/>
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("error"))) { %>
	Error: <%=request.getAttribute("error")%><br>
<% } %>
Hashtag:<html:text name="HashtagForm" property="edited.hashtag"/><br>
Filtro:<html:checkbox name="HashtagForm" property="edited.filtering"/><br>

Tipo de filtro:<html:select name="HashtagForm" property="edited.filteringMode" styleClass="textfield_effect">
	<logic:equal name="HashtagForm" property="edited.filteringMode" value="replace">
		<option value="replace" selected>Reemplazar</option>
		<option value="reject">Rechazar</option>
	</logic:equal>
	<logic:equal name="HashtagForm" property="edited.filteringMode" value="reject">
		<option value="replace">Reemplazar</option>
		<option value="reject" selected>Rechazar</option>
	</logic:equal>
</html:select><br>

Frecuencia de consulta (?):<html:text name="HashtagForm" property="edited.feedCheckInterval"/><br>

<html:submit property="operation">Save</html:submit>
</html:form>
</fieldset>

</body>
</html>