package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

import bean.AutenticacioBean;

import java.util.*;

import util.*;
import controller.Action;
import controller.ErrorBean;
import dao.DaoException;
import entitats.produccio.*;
import entitats.proveidor.*;
import entitats.ordre.*;


public class BuscarProduccionsAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			AutenticacioBean ab=null; 
			if (session!=null) {
				ab = (AutenticacioBean)session.getAttribute("autenticacio");
				if (ab==null) sendRedirect("sortir");
			}else System.out.println("sessio nula");
			if ( request.getParameter("buscar")!=null ) {
				int p1=0, p2=0, o1=0, o2=0, proveidor=0;
				String aux = request.getParameter("p1"); if (aux!=null) p1= Integer.parseInt(aux);
				aux = request.getParameter("p2"); if (aux!=null) p2= Integer.parseInt(aux);
				aux = request.getParameter("o1"); if (aux!=null) o1= Integer.parseInt(aux);
				aux = request.getParameter("o2"); if (aux!=null) o2= Integer.parseInt(aux);
				aux = request.getParameter("prov"); if (aux!=null) proveidor= Integer.parseInt(aux);
				aux = request.getParameter("opcio");
				
				ProduccioDao daoprod= DaoHelper.getProduccioDao();
				ProveidorDao daoprov = DaoHelper.getProveidorDao();
				//OrdreDao daoord = DaoHelper.getOrdreDao();
				
				Collection<ProduccioVO> list =new ArrayList<ProduccioVO>();
				if (p2!=0) list = daoprod.findByRange(p1, p2);
				else if (o2!=0) list =  daoprod.findByOrdre(o1, o2);
				
				Collection<ProveidorVO> provs = daoprov.findAll();
				request.setAttribute("proveidors", provs);
								
				//request.setAttribute("limitini",1);
				//request.setAttribute("limitfi",0);
				request.setAttribute("coleccio", list);
				request.setAttribute("tots", true);
			}
		}catch (DaoException e) {
			request.setAttribute("error", new ErrorBean("Problemes accedint a la Base de Dades!"));
		}catch (NumberFormatException n) {
			request.setAttribute("error", new ErrorBean("Format de numero incorrecte"));
		}
	}

}
