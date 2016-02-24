<%@page import="java.util.*,bean.*,entitats.produccio.*, entitats.article.*, entitats.ordre.*, entitats.proveidor.*"%>
<%@ include file="includes/top.jsp" %>
<center><br>
<h2>DETALL DE LA ORDRE DE PRODUCCIÓ</h2><br>
	
<%
	OrdreVO ordre = (OrdreVO) request.getAttribute("ordre");
	if (ordre!=null) {%>
		<p><h3>ORDRE DE PRODUCCIO <%=((OrdrePk)ordre.getKey()).getId()%></h3>
		<table border="1">
			<tr>
			<th>Data ordre</th>
			<th>Manipulador</th>
			<th>Num. Albarà</th>
			<th>Data fin</th>
			</tr>
			<tr align="center">
				<td><%=ordre.getData()%></td>
				<td><%=ordre.getProveidor()%></td>
				<td><%=ordre.getAlbara()%></td>
				<td><%=ordre.getTancament()%></td>
			</tr>
		</table>
		</p>
		<p>
		<%ArrayList<ProduccioVO> c= (ArrayList<ProduccioVO>)ordre.getDetall();
		if (c!=null) {%>
			<form name="finalitzar" method="post" action="tancarOrdre.do?ordre=<%=((OrdrePk)ordre.getKey()).getId()%>">
				<table border="1">
					<tr>
						<th>Produccio</th>
						<th>Article</th>
						<th>Unidades</th>
						<th>Cost unitari</th>
						<th>Fabricat</th>
						<th>Sel</th>
					</tr>
				<%	Iterator<ProduccioVO> i = c.iterator();
					while (i.hasNext()) {
						ProduccioVO vo = (ProduccioVO)i.next();	%>
						<tr>
							<td><a href="detallarProduccio.do?doc=<%=((ProduccioPk)vo.getKey()).getId()%>"><%=((ProduccioPk)vo.getKey()).getId()%></a></td>
							<td><%=((ArticlePk)((ArticleVO)vo.getArticle()).getKey()).getId()%> - <%=vo.getArticle().getNom() %></td>
							<td align="center"><%=vo.getEntrada() %></td>
							<td align="right"><%=vo.getCost() %></td>
							<td align="center" style="display: none"><input type="text" value="<%=vo.getEntrada() %>" name="f<%=((ProduccioPk)vo.getKey()).getId()%>"></td>
							<td><input type="checkbox" name="xecsel;<%=((ProduccioPk)vo.getKey()).getId()%>" <%if (ordre.getAlbara().equals("")) {%>enabled<%}else{ %>disabled<%}%> onClick="javascript: termina(<%=((ProduccioPk)vo.getKey()).getId()%>);"></td>
						</tr>
				<%	}%>
				</table>
				<div name="opcions" id="opcions" style="float: center; height: 25px; padding-top: 5px; margin: 15px; border: 1px solid #cecece;">
					<input type="button" name="imprimir" value="Imprimir Ordre" onClick='javascript: if(confirm("Segur que vols imprimir la ordre de producció")){ window.location="exportarPDF.do?doc=<%=((OrdrePk)ordre.getKey()).getId()%>";}'>
					<input type="text" name="albara" value="<%=ordre.getAlbara()%>">
					<input type="text" name="proveidor" value="<%=ordre.getProveidor()%>">
					<input type="submit" name="tancar" value="Tancar">
				</div>
			</form>
	<%	}
	}%>
	
	<br>
	<a href="javascript: window.back();">Torna <img src="<%=request.getContextPath()%>/presentation/Back.png" border="0" alt="" title="Tornar"/></a>
</center>
<%@ include file="includes/bottom.jsp" %>