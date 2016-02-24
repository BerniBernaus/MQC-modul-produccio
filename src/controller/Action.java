package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {

	private boolean _customRedirect;
	private String _customRedirectURL;
	
	protected void sendRedirect(String URL) {
		_customRedirect = true;
		_customRedirectURL = URL;
	}
	
	public boolean isCustomRedirect() {return _customRedirect;}
	
	public String getCustomRedirectURL() {return _customRedirectURL;}
	
	public abstract void execute(HttpServletRequest request, HttpServletResponse response);
	
}
