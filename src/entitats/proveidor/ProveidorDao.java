package entitats.proveidor;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
/*
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;*/

import dao.*;


public class ProveidorDao implements IDao {
	
	public IValueObject findByPrimaryKey(IKey key) throws DaoException {
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			System.out.println(((ProveidorPk)key).getId());
			String sql = "SELECT * FROM proveed WHERE codigo = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ((ProveidorPk)key).getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new ProveidorVO(rs.getString("codigo"),
						rs.getString("nombre"),
						rs.getString("cif"),
						rs.getString("email"),
						rs.getString("direccion"),
						rs.getString("poblacion"),
						rs.getString("codpost"),
						rs.getString("provincia"));
			}else {
				return null;
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	public Collection<ProveidorVO> findAll() throws DaoException {
		Collection<ProveidorVO> retorn = new LinkedList<ProveidorVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql =" SELECT * FROM proveed";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retorn.add(new ProveidorVO(rs.getString("codigo"),
						rs.getString("nombre"),
						rs.getString("cif"),
						rs.getString("email"),
						rs.getString("direccion"),
						rs.getString("poblacion"),
						rs.getString("codpost"),
						rs.getString("provincia")));
			}
			return retorn;
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	
	
	/*public IValueObject findByName(String nom) throws DaoException {
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT * FROM proveed WHERE ltrim(nombre) = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new ProveidorVO(rs.getString("codigo"),
						rs.getString("nombre"),
						rs.getString("cif"),
						rs.getString("email"),
						rs.getString("direccion"),
						rs.getString("poblacion"),
						rs.getString("codpost"),
						rs.getString("provincia"));
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}*/
	
	public void add(IValueObject vo) throws DaoException {	}
	public void update(IValueObject vo) throws DaoException {}; 
	public void delete(IKey key) throws DaoException {}
	
	
	private Connection createConnection() throws Exception {
		try {
			InitialContext ic = new InitialContext();
			Context envCtx = (Context) ic.lookup("java:comp/env");
			DataSource ds = null;
			ds = (DataSource)envCtx.lookup("jdbc/BDgestio");
			return ds.getConnection();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		  	  System.out.println( "No se pudo cargar el driver JDBC." );
			throw ex;
		}
    }
	

}
