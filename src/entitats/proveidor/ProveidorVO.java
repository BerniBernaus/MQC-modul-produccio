package entitats.proveidor;

import dao.*;


public class ProveidorVO implements IValueObject {

	private String login;				// proveed.codigo
	private String nombre;				// proveed.nombre 
	private String cif;					// proveed.cif
	private String email;		// proveed.email
	private String direccion, poblacion, codpost, provincia;
	
	
	// Contructor
	public ProveidorVO(String login, String nombre, String cif,
			String email, String direccion, String poblacion,
			String codpost, String provincia) {
		super();
		this.login = login;
		this.nombre = nombre;
		this.cif = cif;
		this.email = email;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.codpost = codpost;
		this.provincia = provincia;
	}
	

	// Getter methods
	public String getNombre() {return nombre;}
	public String getCif() {return cif;	}
	public String getEmail() {return email;	}
	public String getDireccion() {return direccion;	}
	public String getPoblacion() {return poblacion;	}
	public String getCodpost() {return codpost;}
	public String getProvincia() {return provincia;}

	public IKey getKey() {
		return new ProveidorPk(this.login);
	}
	
}
