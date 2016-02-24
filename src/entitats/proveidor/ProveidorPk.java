package entitats.proveidor;

import dao.IKey;

public class ProveidorPk implements IKey {

	private String id;
	
	// Constructor.
	public ProveidorPk(String id) {this.id = id;}	
	
	// Mètodes
	public String getId() {return this.id;}	
	public boolean equals(Object other) {
		return this.id.equals(((ProveidorPk)other).getId());
	}
	
}
