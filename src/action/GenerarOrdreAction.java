package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.sql.Date;

import util.DaoHelper;
import bean.*;
import entitats.produccio.*;
import entitats.ordre.*;
import controller.Action;
import controller.ErrorBean;


public class GenerarOrdreAction extends Action {
	
	public double redondear( double numero, int decimales ) {
	    return Math.round(numero*Math.pow(10,decimales))/Math.pow(10,decimales);
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			AutenticacioBean ab=null; 
			if (session!=null) {
				ab = (AutenticacioBean)session.getAttribute("autenticacio");
				if (ab==null) sendRedirect("sortir");
			}else System.out.println("sessio nula");
			
			ProduccioDao daoprod = DaoHelper.getProduccioDao();
			int proveidor =0;
			String aux = request.getParameter("proveidor");
			if (aux!=null) proveidor=Integer.parseInt(aux);
			
			Enumeration<String> enume = request.getParameterNames();
			ArrayList<ProduccioVO> producs =new ArrayList<ProduccioVO>();
			while (enume.hasMoreElements()) {
				String param = enume.nextElement();
				//System.out.println("Param="+param);
				if (param.startsWith("xecsel", 0)) {
					ProduccioPk prod = null; String n = param.substring(6, param.indexOf(";")+1);
					//System.out.println(n+" -> prod.: "+param.substring(param.indexOf(";")+1));
					int numprod = Integer.parseInt(param.substring(param.indexOf(";")+1));
					prod =new ProduccioPk(numprod);
					producs.add((ProduccioVO)daoprod.findByPrimaryKey(prod));
				}
			}
			if (!producs.isEmpty()) {
				OrdreDao dao = DaoHelper.getOrdreDao();
				dao.add(new OrdreVO(-1,new java.sql.Date(System.currentTimeMillis()), null,proveidor, "", producs));
			}else request.setAttribute("error", new ErrorBean("No s'ha creat cap ordre de producció perque no s'ha seleccinat cap producció"));
			request.setAttribute("coleccio", daoprod.findAll());
			
			//sendRedirect("llistarProduccions");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", new ErrorBean("Problemes al crear la ordre de produccio: <br>"+e.getMessage()));			
		}
	}

}
