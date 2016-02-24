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
				<%if (autenticacio.isAutenticat()) {%>
					<H2>PANELL DE CONTROL</h2><br>
					<p>
						<div align="center"><a href="crearOrdre.do">GENERACIÓ DE ORDRES DE PRODUCCIÓ</a></div>
						<BR>
						<div align="center"><a href="llistarProduccions.do">LLISTAT DE PRODUCCIONS</a></div><br>
						<div align="center"><a href="llistarOrdres.do">LLISTAT DE ORDRES DE PRODUCCIO</a></div>
						
					</p>
				<%}else {%>
					<div class="crlogin" style="width: 549px;height: auto; margin-top: 50px;margin-left: 150px;margin-right: auto;margin-bottom: 100px;float: left;background-color: #999999;border: 1px solid #cccccc;clear: both;">
						<div id="clogintitol" style="width: 549px;height: auto;	text-align: center;	float: left;margin: 0px;padding-bottom: 2px;">
							<h1 style="font-size: 17px;	font-weight:bold;	height: 16px;	width: auto; margin-top: 4px; color: #ffffff;">
								Benvinguts al mòdul de producció de MESQUECORDA
							</h1>
						</div>
						<div id="clogincos">
							<div id="cloginfoto">
								<img src="<%=request.getContextPath()%>/presentation/icon_candau.gif">
							</div>
							<div id="cloginform">
								<h1>Escribe usuario y password para entrar</h1>
								<form method="post" action="autenticar.do">		
								<div id="fields">
									<label>Usuario</label>
	              					<input name="login" type="text" id="login" size="20" maxlength="8">
								  	<br>
									<label>Password</label>
									<input name="pass" type="password" id="pass" size="20">
								</div>
								<div id="enviar" align="left">								
	              					<input name="accept" type="submit" class="button" value="Entrar" size="10"> 
	              				</div>
	              				</form>
							</div>
						</div>
						
							<jsp:useBean id="error" scope="request" class="controller.ErrorBean" />
							<font color="red"><h2><jsp:getProperty name="error" property="message"/></h2>
						
						
					</div><!-- tanca div crlogin-->
				<%}%>
			</div> <!-- Tanco div principal -->
		</div> <!-- Tanco div cos -->
	</div> <!-- Tanco div contenidor -->
</body>
</html>
				