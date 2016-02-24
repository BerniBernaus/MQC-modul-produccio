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
		// mitjan�ant el fitxer de propietats MVC i la petici� HTTP.
		String action = Helper.getAction(request.getServletPath(), params.getProperty("suffix_pattern"));
		// Mirem al fitxer de propietats MVC,
		// si hi ha una classe associada per tractar la petici�.
		String actionClass = actions.getProperty(action+".action");
		// Per distingir si la petici� provoca una nova petici� HTTP
		// o continua amb la mateixa que ha fet l'usuari.
		boolean redireccionat = false;
		if (actionClass != null) { // Si hi ha una interacci� amb el model la fem...
			try {
				// Obtenim una inst�ncia din�micament de la classe
				// que tracta la acci� definida en el fitxer de propietats MVC
				Action modelGateway = (Action)(Class.forName("action."+actionClass).newInstance());
				// Executem la l�gica de l'acci�.
				modelGateway.execute(request, response);
				if (modelGateway.isCustomRedirect()) { // Hi ha una redirecci� provocada ...
			        response.sendRedirect(modelGateway.getCustomRedirectURL()+params.getProperty("suffix_pattern"));
			        redireccionat = true;
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		// Si no hi ha redirecci� provocada o no hi ha acci� associada a la petici�
		// continuem enviant la resposta a la vista definida en el fitxer de propietats
		// MVC per la petici� que l'usuari ha realitzat.
		if (!redireccionat) {
			// Obtenim el control de la vista.
			String view = actions.getProperty(action+".view");
			// Si no hi ha vista associada enviem a la p�gina d'error per defecte,
			// ja que tota petici� que no est� redireccionada 
			// ha de generar una sortida HTTP.
			if (view == null) {
				view = params.getProperty("error.controller");
				request.setAttribute("error", new ErrorBean("No s'ha trobat la vista per l'acci�: "+action));
			}
			RequestDispatcher disp = request.getRequestDispatcher(view);
			disp.forward(request, response);
		}
	}

}