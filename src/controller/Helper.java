package controller;

public final class Helper {

	// Separa la acció concreta del sufix que té el Servlet-Mapping.
	public static String getAction(String servletPath, String suffix) {
		return servletPath.substring(0,servletPath.length()-suffix.length());
	}
	
}
