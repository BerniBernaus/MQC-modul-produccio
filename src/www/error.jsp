<%@ page isErrorPage = "true"%>
<h1>ERROR EN L'APLICACI�:</h1>
<br>
<% if (exception != null) { %>
<b><u>S'ha capturat la excepci�:</u></b>
<br/>
<br/><%=exception.getMessage() %>

<br/>
<br/>
<br/>
<b><u>Atributs de la petici� HTTP:</u></b>
<br/>
<table border="1">
	<tr>
		<th>Atribut</th>
		<th>Valor</th>
	</tr>
<%
	java.util.Enumeration atributs;
	String atribut;
	atributs = request.getAttributeNames(); // Tots els atributs del request.
	while (atributs.hasMoreElements()) {
		atribut = (String)atributs.nextElement();
%>
	<tr>
		<td><%=atribut %></td>
		<td><%=request.getAttribute(atribut) %></td>
	</tr>
<%
	}
%>
</table>
<br/>
<br/>
<br/>
<b><u>Par�metres de la petici� HTTP:</u></b>
<br/>
<table border="1">
	<tr>
		<th>Par�metre</th>
		<th>Valor</th>
	</tr>
<%
	java.util.Enumeration params;
	String param;
	params = request.getParameterNames(); // Tots els atributs del request.
	while (params.hasMoreElements()) {
		param = (String)params.nextElement();
%>
	<tr>
		<td><%=param %></td>
		<td><%=request.getParameter(param) %></td>
	</tr>
<%
	}
%>
</table>
<%
} // del if
%>