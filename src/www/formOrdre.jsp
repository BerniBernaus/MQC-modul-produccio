<%@page import="java.util.*,bean.*,entitats.produccio.*, entitats.article.*, entitats.ordre.*, entitats.proveidor.*"%>
<%@ include file="includes/top.jsp" %>
<center><br>
<h2>GENERACIÓ AUTOMÀTICA DE ORDRES DE PRODUCCIÓ</h2><br>
	
<%
Collection c = (LinkedList<ArticleVO>) request.getAttribute("articles");
Collection d = (ArrayList<ProduccioVO>) request.getAttribute("detall");
String data=(String) request.getAttribute("data");
String alm=(String) request.getAttribute("magatzem");

if (c!=null) {%>
	<form name="produccio" method="post" action="crearOrdre.do">
		<p>Producció:
			<table border=1>
				<tr>
					<td>
					<input type="checkbox" name="01" checked>CORDA
					<input type="checkbox" name="02" checked>CADENA
					<input type="checkbox" name="03" checked>ACC. UNIO
					<input type="checkbox" name="04" checked>ACC. PERSIANA
					<input type="checkbox" name="05" checked>ACC. CAMPING
					</td>
				</tr>
				<tr>
					<td name="article">
						<select onChange="javascript: document">
							<option value="">Selecciona un</option>
							<% Iterator<ArticleVO> it = c.iterator();
							while (it.hasNext()) {
								ArticleVO art = (ArticleVO)it.next();%>
								<option value="<%=((ArticlePk)art.getKey()).getId()+" - "+art.getNom()%>">
									<%=((ArticlePk)art.getKey()).getId()%>
								</option>
							<%}%>
						</select>
					<td>
					<td><input type="text" name="unitats" value="" size="5" maxlength="4"></td>
					<td><input type="submit" name="Afegir" value="Afegir"></td>
				</tr>
			</table>
		</p>
	</form>
<%}%>
	<form name="ordre" method="post" action="crearOrdre.do">
	<ul>
		<li>
			<%if(data!=null && !data.equals("")) {%>
				Data: <input type="text" name="data" value="<%=data%>" readonly>
			<%}else{%>
				Data: <input type="text" name="data" value="">
			<%}%>
		</li>
		<li>
			<%if (alm==null || (alm!=null && alm.equals("")) ){%>
				Magatzem: <select name="alm"><option value="01">01 - Roda</option><option value="02" selected>02 - Manipulats</option></select>
			<%}else{%>
				Magatzem: <input type="text" name="alm" value="<%=alm%>" readonly>
			<%}%>
		</li>
		<p>
		<%if (d!=null) {
			Iterator<ProduccioVO> it2 = d.iterator();%>
			<li>
			<table name="ordre">
				<th></th>
				<th colspan="2">Article</th>
				<th>Unitats</th>
				<th></th>
				<%int cont = 0;
				while (it2.hasNext()) {
					cont++;
					ProduccioVO prod = (ProduccioVO)it2.next();%>
					<tr>
						<td><%=cont%></td>
						<td><%=((ArticlePk)((ArticleVO)prod.getArticle()).getKey()).getId()%></td>
						<td><%=((ArticleVO)prod.getArticle()).getNom()%></td>
						<td><%=prod.getEntrada()%></td>
						<td><a href="#"><img src="" title="eliminar" alt="ELIM."></a></td>
					</tr>
				<%}%>
			</table>
			</li>
		<%}%>
		</p>
		<td><input type="submit" name="Generar" value="Genrerar"></td>
	</ul>
	</form>
	<br>
	<a href="javascript: window.back();">Torna <img src="<%=request.getContextPath()%>/presentation/Back.png" border="0" alt="" title="Tornar"/></a>
</center>
<%@ include file="includes/bottom.jsp" %>