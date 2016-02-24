package entitats.article;

import dao.IKey;

public class ArticlePk implements IKey {

	private String id;
	
	// Constructor.
	public ArticlePk(String id) {this.id = id;}	
	
	
	// Mètodes
	public String getId() {return this.id;}
	
	public boolean equals(Object other) {
		return this.id == ((ArticlePk)other).getId();
	}
	
}
