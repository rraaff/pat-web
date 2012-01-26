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
Bienvenido: <%=user.getUsername()%> - <a href="./logout.do">Logout</a>
<fieldset>
<legend>Twitter account</legend>
<html:form method="POST" action="/twitterAccount">
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("TwitterAccountForm.error"))) { %>
	Error: <%=request.getAttribute("TwitterAccountForm.error")%><br>
<% } %>
<table>
	<tr>
		<td>Usuario:</td>
		<td>
			<html:text name="TwitterAccountForm" property="edited.username"/>
		</td>
	</tr>
	<tr>
		<td>Contrase&ntilde;a:</td>
  <td>
			<html:text name="TwitterAccountForm" property="edited.password"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table>
				<tr>
					<td><html:submit property="operation">Guardar</html:submit></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
</fieldset>

<fieldset>
<legend>Encuesta</legend>
<html:form method="POST" action="/poll">
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("PollForm.error"))) { %>
	Error: <%=request.getAttribute("PollForm.error")%><br>
<% } %>
<table>
	<tr>
		<td>Consigna:</td>
		<td>
			<html:text name="PollForm" property="edited.name"/>
		</td>
	</tr>
	<tr>
		<td>Refresco (milisegundos):</td>
		<td><html:text name="PollForm" property="edited.refreshInterval"/></td>
	</tr>
	<tr>
		<td>Opciones:</td>
		<td><logic:equal name="PollForm" property="edited.active" value="true">
				<html:text name="PollForm" property="edited.optionsList" disabled="true"/>
				</logic:equal>
				<logic:equal name="PollForm" property="edited.active" value="false">
				<html:text name="PollForm" property="edited.optionsList"/>
				</logic:equal>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table>
				<tr>
					<td><html:submit property="operation">Guardar</html:submit></td>
					<td>&nbsp;</td>
					<td><logic:equal name="PollForm" property="edited.active" value="true">
						<html:submit property="operation">Desactivar</html:submit>
						</logic:equal>
						<logic:equal name="PollForm" property="edited.active" value="false">
						<html:submit property="operation">Activar</html:submit>
						</logic:equal></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
</fieldset>

<fieldset>
<legend>Hashtag</legend>
<html:form method="POST" action="/hashtag">
<% if (!org.apache.commons.lang.StringUtils.isEmpty((String)request.getAttribute("HashtagForm.error"))) { %>
	Error: <%=request.getAttribute("HashtagForm.error")%><br>
<% } %>
<table>
	<tr>
		<td>Hashtag:</td>
		<td><logic:equal name="HashtagForm" property="edited.active" value="true">
				<html:text name="HashtagForm" property="edited.hashtag" disabled="true"/>
				</logic:equal>
				<logic:equal name="HashtagForm" property="edited.active" value="false">
				<html:text name="HashtagForm" property="edited.hashtag"/>
				</logic:equal>
		</td>
	</tr>
	<tr>
		<td>Refresco (milisegundos):</td>
		<td><html:text name="HashtagForm" property="edited.refreshInterval"/></td>
	</tr>
	<tr>
		<td>Lectura (milisegundos):</td>
		<td><html:text name="HashtagForm" property="edited.readingInterval"/></td>
	</tr>
	<tr>
		<td>Tweets por refresco:</td>
		<td><html:text name="HashtagForm" property="edited.maxTweetsToAnswer"/></td>
	</tr>
	<tr>
		<td>Filtro de groserias activo:</td>
		<td><html:checkbox name="HashtagForm" property="edited.filtering"/></td>
		<% if (com.tdil.pat.model.Hashtag.uniqueInstance().isFiltering()) { %>
			<script>
				document.getElementsByName("edited.filtering")[0].checked = true;
			</script>
		<% } %>
	</tr>
	
	<tr>
		<td>Tipo de filtro:</td>
		<td><html:select name="HashtagForm" property="edited.filteringMode" styleClass="textfield_effect">
			<logic:equal name="HashtagForm" property="edited.filteringMode" value="REPLACE_WORD">
				<option value="REPLACE_WORD" selected>Reemplazar las palabras</option>
				<option value="REJECT_TEXT">Rechazar el tweet</option>
			</logic:equal>
			<logic:equal name="HashtagForm" property="edited.filteringMode" value="REJECT_TEXT">
				<option value="REPLACE_WORD">Reemplazar las palabras</option>
				<option value="REJECT_TEXT" selected>Rechazar el tweet</option>
			</logic:equal>
		</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table>
				<tr>
					<td><html:submit property="operation">Guardar</html:submit></td>
					<td>&nbsp;</td>
					<td><logic:equal name="HashtagForm" property="edited.active" value="true">
						<html:submit property="operation">Desactivar</html:submit>
						</logic:equal>
						<logic:equal name="HashtagForm" property="edited.active" value="false">
						<html:submit property="operation">Activar</html:submit>
						</logic:equal></td>
                    <td width="30"></td>
                    <td width="150"><a href="screen.html" target="_blank">Iniciar pantalla</a></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
</fieldset>

</body>
</html>