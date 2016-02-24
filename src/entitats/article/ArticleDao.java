package entitats.article;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.*;

import dao.*;


public class ArticleDao implements IDao{
	
	
	public IValueObject findByPrimaryKey(IKey key) throws DaoException {
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT a.codigo, a.nombre, a.nombre2, a.familia, a.tipo_iva, a.internet, a.ultcoste, r.proveedor FROM articulo a " +
			"INNER JOIN referpro r ON a.codigo=r.articulo WHERE a.codigo=? and r.predet=1";
			ps = con.prepareStatement(sql);
			ps.setString(1, (((ArticlePk)key).getId()));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ArrayList<Double> preus =new ArrayList<Double>();
				sql = "SELECT tarifa, pvp, pvpiva FROM PVP WHERE articulo=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, (((ArticlePk)key).getId()));
				ResultSet rs2 = ps.executeQuery();
				while (rs2.next()) {
					preus.add(rs2.getDouble("pvp"));
				}
				return new ArticleVO(rs.getString("codigo"),rs.getString("nombre"), rs.getString("nombre"),rs.getString("familia"),
						rs.getFloat("tipo_iva"),true,rs.getDouble("cost_ult1"),preus);
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}

	public Collection<ArticleVO> findAll() throws DaoException {
		LinkedList<ArticleVO> retorn = new LinkedList<ArticleVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT a.codigo, a.nombre, a.nombre2, a.familia, a.tipo_iva, a.cost_ult1, r.proveedor FROM articulo a " +
			"LEFT JOIN referpro r ON a.codigo=r.articulo WHERE r.predet=1"; //and (a.marca='01' or a.marca='02' or a.marca='03' or a.marca='04' or a.marca='05')";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArrayList<Double> preus =new ArrayList<Double>();
				sql = "SELECT tarifa, pvp, pvpiva FROM PVP WHERE articulo=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rs.getString("codigo"));
				ResultSet rs2 = ps.executeQuery();
				while (rs2.next()) {
					preus.add(rs2.getDouble("pvp"));
				}
				retorn.add(new ArticleVO(rs.getString("codigo"),rs.getString("nombre"),rs.getString("familia"),rs.getString("nombre2"),
						rs.getFloat("tipo_iva"),true,rs.getDouble("cost_ult1"),preus));
			}
			return retorn;
		} catch (Exception ex) {
			ex.printStackTrace();throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	
	public Collection<ArticleVO> findAllOfBrand(ArrayList<String> marcas) throws DaoException {
		LinkedList<ArticleVO> retorn = new LinkedList<ArticleVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT a.codigo, a.nombre, a.nombre2, a.familia, a.tipo_iva, a.cost_ult1, r.proveedor FROM articulo a " +
			"LEFT JOIN referpro r ON a.codigo=r.articulo WHERE r.predet=1";
			//and (a.marca='01' or a.marca='02' or a.marca='03' or a.marca='04' or a.marca='05')";
			if (!marcas.isEmpty()) {
				sql+=" and (1=1 ";
				for (int i=0; i<marcas.size();i++) sql+="OR a.marca=? ";
				sql+=")";
			}
			ps = con.prepareStatement(sql);
			for (int i=0; i<marcas.size();i++) ps.setString(i, marcas.get(i));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArrayList<Double> preus =new ArrayList<Double>();
				sql = "SELECT tarifa, pvp, pvpiva FROM PVP WHERE articulo=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rs.getString("codigo"));
				ResultSet rs2 = ps.executeQuery();
				while (rs2.next()) {
					preus.add(rs2.getDouble("pvp"));
				}
				retorn.add(new ArticleVO(rs.getString("codigo"),rs.getString("nombre"),rs.getString("familia"),rs.getString("nombre2"),
						rs.getFloat("tipo_iva"),true,rs.getDouble("cost_ult1"),preus));
			}
			return retorn;
		} catch (Exception ex) {
			ex.printStackTrace();throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	
	public void add(IValueObject vo) throws DaoException{};
	public void update(IValueObject vo) throws DaoException{};
	public void delete(IKey key) throws DaoException{};
	
	
	private Connection createConnection() throws Exception {
		try {
			InitialContext ic = new InitialContext();
			Context envCtx = (Context) ic.lookup("java:comp/env");
			DataSource ds = null;
			ds = (DataSource)envCtx.lookup("jdbc/BDgestio");
			return ds.getConnection();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		  	  System.out.println("No se pudo cargar el driver JDBC.");
			throw ex;
		}
    }

}
