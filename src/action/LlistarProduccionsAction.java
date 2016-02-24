package action;


import java.util.Collection;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.*;
import controller.Action;
import controller.ErrorBean;
import dao.DaoException;
import entitats.produccio.*;
import bean.AutenticacioBean;


public class LlistarProduccionsAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			AutenticacioBean ab=null; 
			if (session!=null) {
				ab = (AutenticacioBean)session.getAttribute("autenticacio");
				if (ab==null) sendRedirect("sortir");
			}else System.out.println("sessio nula");
			
			ProduccioDao dao = DaoHelper.getProduccioDao();
			Collection<ProduccioVO> c = null;
			c = (ArrayList<ProduccioVO>) dao.findAll();
			
			request.setAttribute("coleccio", c);
		} catch (DaoException e) {
			request.setAttribute("error", new ErrorBean("\nProblemes accedint a la base de dades: <br>"+e.getMessage()));			
		}
	}

}
