package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.*;


public class SortirAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		sendRedirect("home");
	}
	
}
