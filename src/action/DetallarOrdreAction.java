package action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.*;
import controller.Action;
import controller.ErrorBean;
import dao.DaoException;
import entitats.ordre.*;


public class DetallarOrdreAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			int document = Integer.parseInt(request.getParameter("doc"));
			OrdreDao dao = DaoHelper.getOrdreDao();
			OrdreVO ord = (OrdreVO)dao.findByPrimaryKey(new OrdrePk(document));
			//System.out.println(ord);
			request.setAttribute("ordre", ord);
		} catch (DaoException e) {
			request.setAttribute("error", new ErrorBean("\nProblemes accedint a la base de dades: <br>"+e.getMessage()));			
		}
	}

}
