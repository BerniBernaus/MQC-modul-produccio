package entitats.produccio;

import java.sql.Date;


public class LiniaP {
	
	//private ArticleVO article;
	private String article;
	private String nom;
	private double sortida;
	private double cost;
	private String magatzem;
	private java.sql.Date data;
	
	
	
	public LiniaP(String art, String nom, double sortida, double cost, String magatzem, Date data) {
		super();
		this.article = art;
		this.nom = nom;
		this.sortida = sortida;
		this.magatzem = magatzem;
		this.data = data;
		this.cost = cost;
	}


	public String getArticle() {return article;}
	public String getNom() {return nom;}
	public double getSortida() {return sortida;}
	public double getCost() {return cost;}
	public String getMagatzem() {return magatzem;}
	public java.sql.Date getData() {return data;}
	

}
