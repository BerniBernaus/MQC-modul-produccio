package action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import bean.AutenticacioBean;
import util.*;
import controller.Action;
import controller.ErrorBean;
import dao.DaoException;
//import entitats.ordre.*;
import entitats.article.*;


public class CrearOrdreAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			AutenticacioBean ab=null; 
			if (session!=null) {
				ab = (AutenticacioBean)session.getAttribute("autenticacio");
				if (ab==null) sendRedirect("sortir");
			}else System.out.println("sessio nula");
			
			String alm = request.getParameter("almacen");
			String aux = request.getParameter("data");
			ArticleDao daoart = DaoHelper.getArticleDao();
			
			if (request.getParameter("Generar")!=null) {
				
				//OrdreDao dao = DaoHelper.getOrdreDao();
				System.out.println(ab.getOrdre());
				sendRedirect("llistarOrdres");
			}else if (request.getParameter("Afegir")!=null) {
				String art = request.getParameter("article");
				int uni = Integer.parseInt(request.getParameter("unitats").trim());
				
				/*ArrayList<DocLinia> arr = ab.getOrdre();
				System.out.println(arr);
				arr.add(new DocLinia(art, uni));*/
				
				ab.getOrdre().add(new DocLinia(art, uni));
				
			}
			ArrayList<String> marcas =new ArrayList<String>();
			aux = request.getParameter("01"); if (aux!=null) marcas.add(aux);
			aux = request.getParameter("02"); if (aux!=null) marcas.add(aux);
			aux = request.getParameter("03"); if (aux!=null) marcas.add(aux);
			aux = request.getParameter("04"); if (aux!=null) marcas.add(aux);
			aux = request.getParameter("05"); if (aux!=null) marcas.add(aux);
			
			request.setAttribute("articles", daoart.findAllOfBrand(marcas));
			request.setAttribute("data", aux);
			request.setAttribute("magatzem", alm);
		} catch (DaoException e) {
			e.printStackTrace();
			request.setAttribute("error", new ErrorBean("\nProblemes accedint a la base de dades: <br>"+e.getMessage()));			
		}
	}

}
