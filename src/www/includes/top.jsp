<jsp:useBean id="autenticacio" class="bean.AutenticacioBean" scope="session"/>
<%@page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Mòdul de producció - MESQUECORDA</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/presentation/estil.css" type="text/css">
	</head>
	
	<body>
		<div id="contenidor"> 
			<div id="titol">
				<div id="banner" style="width: auto; height: 150px; clear:both; margin: 0 auto; background-image:url(<%=request.getContextPath() %>/presentation/banner_template2.jpg); border-bottom:1px #727272 solid; "></div>
				<div id="marca_usuari" style="float: center; width: 100%; height auto;">
					<%if (autenticacio.isAutenticat()) { %>
						<div style="float: left; width: auto; height: auto;"><span style="display:inline; font-size:14px; color:#FF6600; margin-left: 350px;">USUARI: </span><span style="color:#7C8C98; font-size:13px; font-style:normal;"><%=autenticacio.getId()%></span></div>
						<div style="float: right; width: auto; height: auto;"><span style="text-align: right; display:inline; font-size:14px; color:#FF6600; margin-right: 350px;"><a href="sortir.do">Desconectar</a></span></div>
					<%}%>
		        </div>
	  		</div>
		<div id="cos">
			<div id="principal" align="center">
			<p><jsp:useBean id="error" scope="request" class="controller.ErrorBean" />
			<font color="red"><h2><jsp:getProperty name="error" property="message"/></h2></font></p>