package entitats.produccio;

import dao.IKey;

public class ProduccioPk implements IKey {

	private int id;
	
	// Constructor.
	public ProduccioPk(int id) {this.id = id;}	
	
	
	// M�todes
	public int getId() {return this.id;}
	
	public boolean equals(Object other) {
		return this.id == ((ProduccioPk)other).getId();
	}
	
}
