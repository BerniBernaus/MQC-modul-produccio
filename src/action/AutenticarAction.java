package action;

import javax.servlet.http.*;

import controller.*;
import dao.DaoException;
import bean.*;


public class AutenticarAction extends Action {

	private static final String user="admin";
	private static final String password="1234";
	
	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getParameter("accept") != null) {
				String client= request.getParameter("login");
				String clau = request.getParameter("pass");
				if ( (client != null && !client.trim().equals("")) && (!clau.trim().equals("")) && clau!=null ) {
					client = client.trim().toLowerCase();
					// Lògica d'autenticació.
						/*ClientDao dao = DaoHelper.getClientDao();
						ClientVO vo = (ClientVO)dao.findByPrimaryKey(new ClientPk(client2));
						if (vo != null && vo.isBan()) request.setAttribute("error", new ErrorBean("L'usuari té l'accés prohibit al portal." +
							"\nPossis en contacte amb el seu administrador"));
						else {*/
							if (password.equals(clau.trim()) && user.equals(client)) {
								HttpSession session = request.getSession(true);
								AutenticacioBean ab = (AutenticacioBean)session.getAttribute("autenticacio");
								if (ab == null) {
									ab = new AutenticacioBean();
									session.setAttribute("autenticacio",ab);
								}
								ab.setId(client.toUpperCase());
								request.setAttribute("autentic", true);
							}else request.setAttribute("error", new ErrorBean("\nUsuari o clau incorrecte!"));						
				}else request.setAttribute("error", new ErrorBean("\nCamps incomplets!"));
			}else if (request.getParameter("cancelar") != null) {
				sendRedirect("home");
			}
		} catch (Exception e) {
			request.setAttribute("error", new ErrorBean(e.getMessage()));
		}
	}

}
