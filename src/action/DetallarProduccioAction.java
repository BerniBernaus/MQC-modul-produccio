package action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.*;
import controller.Action;
import controller.ErrorBean;
import dao.DaoException;
import entitats.produccio.*;


public class DetallarProduccioAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			int document = Integer.parseInt(request.getParameter("doc"));
			ProduccioDao dao = DaoHelper.getProduccioDao();
			/*Collection<Linia> c = null;
			c = (ArrayList<Linia>) ((ProduccioVO)dao.findByPrimaryKeyFull(new ProduccioPk(document.trim()))).getDetall();*/
			ProduccioVO prod = (ProduccioVO)dao.findByPrimaryKey(new ProduccioPk(document));
			request.setAttribute("produccio", prod);
		} catch (DaoException e) {
			request.setAttribute("error", new ErrorBean("\nProblemes accedint a la base de dades: <br>"+e.getMessage()));			
		}
	}

}
