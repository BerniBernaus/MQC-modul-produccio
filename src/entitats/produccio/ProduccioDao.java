package entitats.produccio;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.*;

import dao.*;
import entitats.produccio.ProduccioVO;
import entitats.ordre.*;
import entitats.article.*;


public class ProduccioDao implements IDao{
	
	
	public IValueObject findByPrimaryKey(IKey key) throws DaoException {
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT p.pvp, a.*, numero, fecha, almacen, c_prod.articulo, entrada, pmcom, acabado, f_acabado, observa, cast(libre_1 as int) as ordre, libre_2 as data, cast(libre_3 as int) as proveidor, coalesce(libre_4,'') as albara, libre_5 as data_alb FROM c_prod " +
					"INNER JOIN articulo a on a.codigo=articulo LEFT JOIN pvp p ON p.articulo=a.codigo WHERE numero=? and p.tarifa='01'";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ((ProduccioPk)key).getId());
			ArrayList<Double> preus =new ArrayList<Double>();
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				preus.add(rs.getDouble("pvp"));
				sql = "SELECT * FROM d_prod WHERE numero=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, rs.getInt("numero"));
				ResultSet rs2 = ps.executeQuery();
				ArrayList<LiniaP> escandall =new ArrayList<LiniaP>();
				while (rs2.next()) {
					escandall.add(new LiniaP(rs2.getString("articulo"),rs2.getString("nombre"),
							rs2.getDouble("salida"),
							rs2.getDouble("coste"),
							rs2.getString("almacen"),
							rs2.getDate("fecha")));
				}
				return new ProduccioVO(rs.getInt("numero"),
						new OrdreVO(rs.getInt("ordre"), new java.sql.Date(Long.valueOf(rs.getString("data_alb"))), new java.sql.Date(System.currentTimeMillis()), rs.getInt("proveidor"), rs.getString("albara"), null ),
						rs.getDate("fecha"),
						new ArticleVO(rs.getString("articulo"), rs.getString("nombre"), null, rs.getString("familia"),0, true, rs.getDouble("cost_ult1"), preus),
						rs.getDouble("entrada"),
						rs.getDouble("pmcom"),
						rs.getString("Observa"),
						escandall,
						rs.getBoolean("acabado"));
			} else return null;
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	
	public Collection<ProduccioVO> findAll() throws DaoException {
		Collection<ProduccioVO> retorn = new ArrayList<ProduccioVO>();
		Statement ps = null; Connection con = null;
		try {
			con = createConnection();
			ps = con.createStatement();
			String sql = "SELECT a.*, numero, fecha, articulo, entrada, pmcom, observa, acabado, cast(libre_1 as int) as ordre, cast(libre_3 as int) as proveidor, libre_4 as albara, libre_5 as data_alb FROM c_prod " +
					"INNER JOIN articulo a on a.codigo=articulo";
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				java.sql.Date fin = null;
				if (!rs.getString("data_alb").trim().equals("")) fin =new java.sql.Date(Long.valueOf(rs.getString("data_alb")));
				retorn.add(new ProduccioVO(rs.getInt("numero"),
						new OrdreVO(rs.getInt("ordre"), new java.sql.Date(System.currentTimeMillis()), fin, rs.getInt("proveidor"), rs.getString("albara"), null ),
						//rs.getInt("libre_1"), 
						rs.getDate("fecha"),
						new ArticleVO(rs.getString("articulo"), rs.getString("nombre"), null, rs.getString("familia"),0, true, rs.getDouble("cost_ult1"), null),
						rs.getDouble("entrada"),
						rs.getDouble("pmcom"),
						rs.getString("observa"),
						null,
						rs.getBoolean("acabado")));
			}
			return retorn;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	public Collection<ProduccioVO> findAllPendents() throws DaoException {
		Collection<ProduccioVO> retorn = new ArrayList<ProduccioVO>();
		Statement ps = null; Connection con = null;
		try {
			con = createConnection();
			ps = con.createStatement();
			String sql = "SELECT a.*, numero, fecha, articulo, entrada, pmcom, observa, acabado, cast(libre_1 as int) as ordre, cast(libre_3 as int) as proveidor, libre_4 as albara, libre_5 as data_alb FROM c_prod " +
					"INNER JOIN articulo a on a.codigo=articulo WHERE acabado=0";
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				retorn.add(new ProduccioVO(rs.getInt("numero"),
						new OrdreVO(rs.getInt("ordre"), new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(Long.valueOf(rs.getString("data_alb"))), rs.getInt("proveidor"), rs.getString("albara"), null ),
						//rs.getInt("libre_1"), 
						rs.getDate("fecha"),
						new ArticleVO(rs.getString("articulo"), rs.getString("nombre"), null, rs.getString("familia"),0, true, rs.getDouble("cost_ult1"), null),
						rs.getDouble("entrada"),
						rs.getDouble("pmcom"),
						rs.getString("observa"),
						null,
						rs.getBoolean("acabado")));
			}
			return retorn;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	public Collection<ProduccioVO> findByRange(int ini, int fi) throws DaoException {
		Collection<ProduccioVO> retorn = new ArrayList<ProduccioVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT a.*, numero, fecha, articulo, entrada, pmcom, observa, acabado, cast(libre_1 as int) as ordre, cast(libre_3 as int) as proveidor, libre_4 as albara, libre_5 as data_alb FROM c_prod"+
					" inner join articulo a on a.codigo=articulo WHERE numero>=? and numero<=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ini);
			ps.setInt(2, fi);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retorn.add(new ProduccioVO(rs.getInt("numero"),
						new OrdreVO(rs.getInt("ordre"), new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(Long.valueOf(rs.getString("data_alb"))), rs.getInt("proveidor"), rs.getString("albara"), null ),
						//rs.getInt("libre_1"), 
						rs.getDate("fecha"),
						new ArticleVO(rs.getString("articulo"), rs.getString("nombre"), null, rs.getString("familia"),0, true, rs.getDouble("cost_ult1"), null),
						rs.getDouble("entrada"),
						rs.getDouble("pmcom"),
						rs.getString("observa"),
						null,
						rs.getBoolean("acabado")));
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
	public Collection<ProduccioVO> findByOrdre(int ini, int fi) throws DaoException {
		Collection<ProduccioVO> retorn = new ArrayList<ProduccioVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT a.*, numero, fecha, articulo, entrada, pmcom, observa, acabado, cast(libre_1 as int) as ordre, cast(libre_3 as int) as proveidor, libre_4 albara, libre_5 as data_alb FROM c_prod"+
					" INNER JOIN articulo a on a.codigo=articulo WHERE cast(libre_1 as int)>=? and cast(libre_1 as int)<=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ini);
			ps.setInt(2, fi);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retorn.add(new ProduccioVO(rs.getInt("numero"),
						new OrdreVO(rs.getInt("ordre"), new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(Long.valueOf(rs.getString("data_alb"))), rs.getInt("proveidor"), rs.getString("albara"), null ),
						rs.getDate("fecha"),
						new ArticleVO(rs.getString("articulo"), rs.getString("nombre"), null, rs.getString("familia"),0, true, rs.getDouble("cost_ult1"), null),
						rs.getDouble("entrada"),
						rs.getDouble("pmcom"),
						rs.getString("observa"),
						null,
						rs.getBoolean("acabado")));
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
		
	public void add(IValueObject vo) throws DaoException{};
	
	public void update(IValueObject vo) throws DaoException{
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			ProduccioVO prod = (ProduccioVO)vo;
			if (prod.getOrdre()==null) {
				ps = con.prepareStatement("UPDATE c_prod SET libre_1='', libre_2='', libre_3='', libre_4='', libre_5='' WHERE numero=?");
				ps.setInt(1, ((ProduccioPk)prod.getKey()).getId());
				ps.executeUpdate();
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	};
	
	public void delete(IKey key) throws DaoException{};
	
	
	private Connection createConnection() throws Exception {
		try {
			InitialContext ic = new InitialContext();
			Context envCtx = (Context) ic.lookup("java:comp/env");
			DataSource ds = null;
			
			ds = (DataSource)envCtx.lookup("jdbc/BDgestio");
			return ds.getConnection();
			
		} catch (Exception ex) {
			//ex.printStackTrace();
		  	//System.out.println( "No se pudo cargar el driver JDBC." );
			throw ex;
		}
    }

}
