<%@ page isErrorPage = "true"%>
<h1>ERROR EN L'APLICACIÓ:</h1>
<br>
<% if (exception != null) { %>
<b><u>S'ha capturat la excepció:</u></b>
<br/>
<br/><%=exception.getMessage() %>

<br/>
<br/>
<br/>
<b><u>Atributs de la petició HTTP:</u></b>
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
<b><u>Paràmetres de la petició HTTP:</u></b>
<br/>
<table border="1">
	<tr>
		<th>Paràmetre</th>
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