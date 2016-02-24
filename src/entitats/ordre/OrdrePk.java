package entitats.ordre;

import dao.IKey;

public class OrdrePk implements IKey {

	private int id;
	
	// Constructor.
	public OrdrePk(int id) {this.id = id;}	
	
	
	// M�todes
	public int getId() {return this.id;}
	
	public boolean equals(Object other) {
		return this.id == ((OrdrePk)other).getId();
	}
	
}
