package controller;

import java.io.*;
import java.util.Properties;
import javax.servlet.http.*;
import javax.servlet.*;

public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Properties actions;
	private Properties params;
	
	public void init(ServletConfig conf) throws ServletException {
		String contextPath = conf.getServletContext().getRealPath("/");
		String actionsPath = contextPath+conf.getInitParameter("actions")+".properties";
		String paramsPath = contextPath+conf.getInitParameter("params")+".properties";
		actions = new Properties();
		params = new Properties();
		try {
			FileInputStream fisAccions = new FileInputStream(actionsPath);
			FileInputStream fisParams = new FileInputStream(paramsPath);
			actions.load(fisAccions);
			params.load(fisParams);
		} catch (IOException e) {}
	}
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Cal obtenir que ha demanat exactament l'usuari,
		// mitjançant el fitxer de propietats MVC i la petició HTTP.
		String action = Helper.getAction(request.getServletPath(), params.getProperty("suffix_pattern"));
		// Mirem al fitxer de propietats MVC,
		// si hi ha una classe associada per tractar la petició.
		String actionClass = actions.getProperty(action+".action");
		// Per distingir si la petició provoca una nova petició HTTP
		// o continua amb la mateixa que ha fet l'usuari.
		boolean redireccionat = false;
		if (actionClass != null) { // Si hi ha una interacció amb el model la fem...
			try {
				// Obtenim una instància dinàmicament de la classe
				// que tracta la acció definida en el fitxer de propietats MVC
				Action modelGateway = (Action)(Class.forName("action."+actionClass).newInstance());
				// Executem la lògica de l'acció.
				modelGateway.execute(request, response);
				if (modelGateway.isCustomRedirect()) { // Hi ha una redirecció provocada ...
			        response.sendRedirect(modelGateway.getCustomRedirectURL()+params.getProperty("suffix_pattern"));
			        redireccionat = true;
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		// Si no hi ha redirecció provocada o no hi ha acció associada a la petició
		// continuem enviant la resposta a la vista definida en el fitxer de propietats
		// MVC per la petició que l'usuari ha realitzat.
		if (!redireccionat) {
			// Obtenim el control de la vista.
			String view = actions.getProperty(action+".view");
			// Si no hi ha vista associada enviem a la pàgina d'error per defecte,
			// ja que tota petició que no està redireccionada 
			// ha de generar una sortida HTTP.
			if (view == null) {
				view = params.getProperty("error.controller");
				request.setAttribute("error", new ErrorBean("No s'ha trobat la vista per l'acció: "+action));
			}
			RequestDispatcher disp = request.getRequestDispatcher(view);
			disp.forward(request, response);
		}
	}

}