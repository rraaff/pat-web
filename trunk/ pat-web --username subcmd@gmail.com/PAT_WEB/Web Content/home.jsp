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
<legend>Twitter account</legend>
</fieldset>

<fieldset>
<legend>Encuesta</legend>
</fieldset>

<fieldset>
<legend>Hashtag</legend>
<html:form method="POST" action="/hashtag">
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("HashtagForm.error"))) { %>
	Error: <%=request.getAttribute("error")%><br>
<% } %>
Hacer esto readonly/disabled si esta activo
Hashtag:<html:text name="HashtagForm" property="edited.hashtag"/><br>
Filtro:<html:checkbox name="HashtagForm" property="edited.filtering"/><br>
Tipo de filtro:<html:select name="HashtagForm" property="edited.filteringMode" styleClass="textfield_effect">
	<logic:equal name="HashtagForm" property="edited.filteringMode" value="REPLACE_WORD">
		<option value="REPLACE_WORD" selected>Reemplazar las palabras</option>
		<option value="REJECT_TEXT">Rechazar el tweet</option>
	</logic:equal>
	<logic:equal name="HashtagForm" property="edited.filteringMode" value="REJECT_TEXT">
		<option value="REPLACE_WORD">Reemplazar las palabras</option>
		<option value="REJECT_TEXT" selected>Rechazar el tweet</option>
	</logic:equal>
</html:select><br>
<html:submit property="operation">Guardar</html:submit>

Boton para activar (ojo con activar, alerta que si la encuesta esta activa...)/boton para desactivar
</html:form>
</fieldset>

</body>
</html>