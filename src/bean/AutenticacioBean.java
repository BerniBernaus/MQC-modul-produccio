package bean;

import util.DocLinia;
import java.util.*;

public class AutenticacioBean {

	private String id;
	private ArrayList<DocLinia> ordre;
	//private boolean isAdmin;
	
	// Constructor
	public AutenticacioBean() {}
	
	// Mètodes
	public String getId() {return this.id;}
	public void setId(String id) {this.id=id;}
	public ArrayList<DocLinia> getOrdre() {return this.ordre;}
	
	public boolean isAutenticat() {return (this.id != null);}
	
	/*public void setIsAdmin(boolean bool) {this.isAdmin = bool;}
	public boolean isAdmin() {return this.isAdmin;}*/
	
}
