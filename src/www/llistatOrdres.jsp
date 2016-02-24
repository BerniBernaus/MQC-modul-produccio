<%@page import="java.util.*,bean.*,entitats.produccio.*, entitats.ordre.*"%>
<%@ include file="includes/top.jsp" %>
<H2>LLISTAT DE ORDRES DE PRODUCCIO</H2>

<!--<form name="formulari" method="post" action="generarOrdre.do">-->	
	<table border="1" align="center">
		<br><br>
		<tr>
			<th>Nº Ordre</th>
			<th>Manipulador</th>
			<th>Data Ordre</th>
			<th>Albarà</th>
			<th></th>
		</tr>
		<%
		Collection c = (ArrayList<OrdreVO>)request.getAttribute("coleccio");
		if (c!=null) {
			Iterator i = c.iterator();
			int n = 0; int num;
			while (i.hasNext()) {
				OrdreVO vo = (OrdreVO)i.next();
				int numordre = ((OrdrePk)vo.getKey()).getId();
				if (numordre!=0 && !vo.isAcabat()) {%>
					<tr align="center" style="background-color: #FEC1BA">
				<%}else if (numordre!=0 && vo.isAcabat()) {%>
					<tr align="center" style="background-color: #7CFC00 ">
				<%}else{%>
					<tr align="center">
				<%}%>
					<td><a href="detallarOrdre.do?doc=<%=numordre%>"><%=numordre%></a></td>
					<td><%=vo.getProveidor()%></td>
					<td><%=vo.getData()%></td>
					<td><%=vo.getAlbara()%></td>
					<td>Act</td>
				</tr>
		<%		n++;
			}
		}%>
	</table>
	
	<div style="float: right; width: auto; height: auto; margin: 15px 120px; 5px 0px;">
		<!--<input type="submit" name="crearordre" value="Crear Ordre">-->
	</div>
<!--</form>-->
<%@ include file="includes/bottom.jsp" %>