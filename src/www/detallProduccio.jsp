<%@page import="java.util.*,bean.*,entitats.produccio.*, entitats.article.*, entitats.ordre.*"%>
<%@ include file="includes/top.jsp" %>
<center><br>
<h2>DETALL DE LA PRODUCCIÓ</h2><br>
	
<%
	ProduccioVO prod = (ProduccioVO)request.getAttribute("produccio");
	if (prod!=null) {
		ArticleVO artP = (ArticleVO)prod.getArticle();%>
		<p><h3>PRODUCCIO <%=((ProduccioPk)prod.getKey()).getId()%> PER <%=((ArticlePk)artP.getKey()).getId()+": "+artP.getNom()%></h3>
		<table border="1">
			<tr>
			<th>Quantitat produccio</th>
			<th>Cost mercaderia</th>
			<th>Cost final</th>
			<th>PVP tarifa</th>
			</tr>
			<tr align="center">
				<td><%=prod.getEntrada()%></td>
				<td><%=artP.getCost()%></td>
				<td><%=prod.getCost()%></td>
				<td><%=artP.getPreus().get(0)%></td>
			</tr>
		</table>
		</p>
		<p>
		<%ArrayList<LiniaP> c= (ArrayList<LiniaP>)prod.getDetall();
		if (c!=null) {%>
			<table border="1">
				<tr>
					<th colspan="2">Component</th>
					<th>Unidades</th>
					<th>Cost unitari</th>
				</tr>
			<%	Iterator<LiniaP> i = c.iterator();
				while (i.hasNext()) {
					LiniaP vo = (LiniaP)i.next();
			%>
					<tr>
						<td><%=vo.getArticle() %></td>
						<td align="left"><%=vo.getNom() %></td>
						<td align="center"><%=vo.getSortida() %></td>
						<td align="right"><%=vo.getCost() %></td>
					</tr>
			<%	}%>
			</table>
	<%	}
	OrdreVO ord = prod.getOrdre();
	if (((OrdrePk)ord.getKey()).getId()!=0) {%>
		<div style="float: center; height: 25px; padding-top: 5px; margin: 15px; border: 1px solid #cecece;">
			<span align="left">Ordre assignada: <%=((OrdrePk)ord.getKey()).getId()%> / </span><span align="right">Manipulador: <%=ord.getProveidor()%></span>
			<a align="right" href="actualitzarProduccio.do?op=1&doc=<%=((ProduccioPk)prod.getKey()).getId()%>"><img src="<%=request.getContextPath() %>/presentation/Delete.png" title="desfer assiganció ordre"></a>
		</div>
	<%}
	}%>
	<br>
	<a href="javascript: window.back();">Torna <img src="<%=request.getContextPath()%>/presentation/Back.png" border="0" alt="" title="Tornar"/></a>
</center>
<%@ include file="includes/bottom.jsp" %>