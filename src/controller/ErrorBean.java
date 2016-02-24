package controller;

public class ErrorBean {

	private String msg;
	
	public ErrorBean() {
		this.msg = "";
	}
	
	public ErrorBean(String message) {
		this.msg = message;
	}
	
	public String getMessage() {return this.msg;}
	
}
