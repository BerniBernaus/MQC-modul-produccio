package entitats.article;

import dao.*;

import java.util.*;


public class ArticleVO implements IValueObject {

	private String codi;				// articulo.codigo
	private String nom;					// articulo.nombre
	private String nom2;				// articulo.nombre2
	private String familia;				// articulo.familia
	private float iva;					// articulo.tipo_iva
	private boolean internet;			// articulo.internet
	private double cost;				// articulo.ultcoste
		
	private ArrayList<Double> preus;	// pvp.pvp
	private String foto;				// articulo.imagen
	
	
	
	// Constructor
	public ArticleVO() {super();}
	public ArticleVO(String codi, String nom, String nom2, String fam, float iva, boolean internet, double cost, ArrayList<Double> p) {
		super();
		this.codi = codi;
		this.nom = nom;
		this.nom2 = nom2;
		this.familia = fam;
		this.iva = iva;
		this.preus = p;
		this.cost = cost;
	}
	

	// Getters methods
	public String getCodi() {return codi;}
	public String getNom() {return nom;	}
	public String getNom2() {return nom2;}
	public String getFamilia() {return familia;}
	public float getIva() {return iva;}
	public boolean isInternet() {return internet;}
	public double getCost() {return cost;}
	public ArrayList<Double> getPreus() {return preus; }
	
	public IKey getKey() {
		return new ArticlePk(this.codi);
	}
}
