package action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.*;
import controller.Action;
import controller.ErrorBean;
import dao.DaoException;
import entitats.produccio.*;


public class ActualitzarProduccioAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			int operacio = Integer.parseInt(request.getParameter("op"));
			int document = Integer.parseInt(request.getParameter("doc"));
			ProduccioDao dao = DaoHelper.getProduccioDao();
			switch (operacio) {
			case 1:
				// desassignar ordre de la produccio.
				ProduccioVO aux = (ProduccioVO)dao.findByPrimaryKey(new ProduccioPk(document));
				dao.update(new ProduccioVO(document,null,null,aux.getArticle(),aux.getEntrada(),aux.getCost(),aux.getNota(),null, false));
				break;
				
			default: break;
			}
			request.setAttribute("produccio", dao.findByPrimaryKey(new ProduccioPk(document)));
			
		} catch (DaoException e) {
			request.setAttribute("error", new ErrorBean("\nProblemes accedint a la base de dades (actualitzacio de produccio): <br>"+e.getMessage()));			
		}
	}

}
