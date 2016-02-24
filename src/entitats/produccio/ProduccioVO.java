package entitats.produccio;


import java.sql.Date;
import java.util.ArrayList;

import dao.*;
import entitats.ordre.*;
import entitats.article.*;


public class ProduccioVO implements IValueObject {

	//Capçalera de la COMANDA.
	private int numero;			// c_pedive.numero
	private OrdreVO ordre;
	private Date data;			// c_pedive.fecha
	private ArticleVO article;
	//private String article;
	private double entrada;
	private double cost;		// c_pedive.importe
	private String nota;		// c_pedive.nota
	private boolean acabat;

	
	//Detall de la produccio.
	private ArrayList<LiniaP> detall;
		
	
	// Constructor
	public ProduccioVO(int num, OrdreVO ord, Date data, ArticleVO art, double ent, double cost, String nota, ArrayList<LiniaP> d, boolean fin) {
		this.numero = num;
		this.ordre = ord;
		this.data = data;
		this.article = art;
		this.entrada = ent;
		this.cost = cost;
		this.nota = nota;
		this.detall = d;
		this.acabat = fin;
	}
	
	
	// Getter methods
	public IKey getKey() {return new ProduccioPk(this.numero);}
	//public String getNumero() {return numero;}
	
	public OrdreVO getOrdre() {return ordre;}
	public String getNota() {return nota;}
	public Date getData() {return data;}
	public ArticleVO getArticle() {return article;};
	public double getEntrada() {return entrada;}
	public double getCost() {return cost;}
	public ArrayList<LiniaP> getDetall() {return detall;}
	public boolean isAcabat() {return acabat;}

}
