package entitats.ordre;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import dao.*;
import entitats.produccio.ProduccioVO;


public class OrdreVO implements IValueObject {

	//Capçalera de la Ordre de produccio.
	private int numero;			// c_prod.libre_1
	private Date data, fin;			// c_prod.libre_2
	private int proveidor;		// c_prod.libre_3
	private String albara;		// c_prod.libre_4
	//private boolean acabat;

	//Detall de la produccio.
	private ArrayList<ProduccioVO> detall;
		
	
	// Constructor
	public OrdreVO(int num, Date data, Date fin, int prov, String alb, ArrayList<ProduccioVO> d) {
		this.numero = num;
		this.data = data;
		this.fin = fin;
		this.albara = alb;
		this.proveidor = prov;
		this.detall = d;
	}
	
	
	// Getter methods
	public IKey getKey() {return new OrdrePk(this.numero);}
	//public String getNumero() {return numero;}
	
	public Date getData() {return data;}
	public Date getTancament() {return fin;}
	public String getAlbara() {return albara;}
	public int getProveidor() {return proveidor;}
	public boolean isAcabat() {
		if (detall==null || detall.isEmpty()) return false;
		else {
			Iterator<ProduccioVO> it = detall.iterator();
			while(it.hasNext()) {
				if (!((ProduccioVO)it.next()).isAcabat()) return false;
			}
		}
		return true;
	}
	public ArrayList<ProduccioVO> getDetall() {return detall;}
		
	public String toString() {
		return "ORDRE NUM.: "+numero+", data="+data+", tancament="+fin+", albara="+albara+", Proveidor="+proveidor+", detall="+detall;
	}
	

}
