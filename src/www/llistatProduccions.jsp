<%@page import="java.util.*,bean.*,entitats.produccio.*, entitats.ordre.*, entitats.article.*"%>
<%@ include file="includes/top.jsp" %>
<script>
function validaForm(form) {
	var seleccio = document.getElementByName("xecsel");
	codis="article: ";
	for (i=0;i<seleccio.length;i++) {
		var xec = seleccio[i];
		if ( xec.value=="on" ) {
			codis=codis+"x, ";
		}
	}
	if (confirm("Vols crear una ordre de producció pels "+codis+ "?") ) {
		form.submit();
	}else {
		return false;
	}
}
</script>
<H2>LLISTAT DE PRODUCCIONS</H2>

<form name="formulari" method="post" action="generarOrdre.do" onsubmit="validaForm(this)">
	<table border="1" align="center">
		<br><br>
		<tr>
			<th>Nº Ordre</th>
			<th>Produccio</th>
			<th>Data Produccio</th>
			<th>Article Principal</th>
			<th>Entrada</th>
			<!--<th>Acabat</th>-->
			<th>Manipulador</th>
			<th>Data Ordre</th>
			<th>Albarà</th>
			<th>Sel</th>
		</tr>
		<%
		Collection c = (ArrayList<ProduccioVO>)request.getAttribute("coleccio");
		if (c!=null) {
			Iterator i = c.iterator();
			int n = 0;
			int num;
			while (i.hasNext()) {
				ProduccioVO vo = (ProduccioVO)i.next();
				num = ((ProduccioPk)vo.getKey()).getId();
				int numordre = ((OrdrePk)vo.getOrdre().getKey()).getId();
				if (numordre!=0 && !vo.isAcabat()) {%>
					<tr align="center" style="background-color: #FEC1BA">
				<%}else if (numordre!=0 && vo.isAcabat()) {%>
					<tr align="center" style="background-color: #7CFC00 ">
				<%}else{%>
					<tr align="center">
				<%}%>
					
					<td>
						<%if (numordre!=0) {%><a href="detallarOrdre.do?doc=<%=numordre%>"><%=numordre%></a><%}else{out.print(numordre);}%>
					</td>
					<td><a href="detallarProduccio.do?doc=<%=num%>"><%=num%></a></td>
					<td><%=vo.getData()%></td>
					<td><%=((ArticlePk)((ArticleVO)vo.getArticle()).getKey()).getId()%></td>
					<td align="right"><%=vo.getEntrada()%></td>
					<!--<td><%if(vo.isAcabat()){%><input type="checkbox" name="acabat" disabled checked><%}else{%><input type="checkbox" name="acabat" disabled><%}%></td>-->
					<td><%=vo.getOrdre().getProveidor()%></td>
					<td><%=vo.getOrdre().getData()%></td>
					<td><%=vo.getOrdre().getAlbara()%></td>
					<td><%if ( ((OrdrePk)vo.getOrdre().getKey()).getId()==0) {%><input type="checkbox" name="xecsel<%=+n+";"+num%>" id="xecsel<%=+n+";"+num%>" value=""><%}%></td>
				</tr>
		<%		n++;
			}
		}%>
	</table>
	<div style="float: left; width: auto; height: auto; margin: 15px auto 5px 120px;">
		<input type="button" name="imprimir" value="Imprimir Ordre" onClick='javascript: if(document.formulari.numordre.value!="" && (/^([0-9])*$/.test(document.formulari.numordre.value))) {window.location=("exportarPDF.do?doc="+document.formulari.numordre.value)} else{alert("Introdueix un numero vàlid de ordre de produccio que vols imprimir"); document.formulari.numordre.value=""}'>
		<input type="text" name="numordre" value="" size="10" maxlength="6"></div>
	<div style="float: right; width: auto; height: auto; margin: 15px 120px; 5px 0px;"><input type="submit" name="crearordre" value="Crear Ordre"></div>
	</div>
</form>
<%@ include file="includes/bottom.jsp" %>