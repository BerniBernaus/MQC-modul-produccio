package controller;

public final class Helper {

	// Separa la acci� concreta del sufix que t� el Servlet-Mapping.
	public static String getAction(String servletPath, String suffix) {
		return servletPath.substring(0,servletPath.length()-suffix.length());
	}
	
}
