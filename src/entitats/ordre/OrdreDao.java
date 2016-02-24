package entitats.ordre;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.*;

import dao.*;
import entitats.produccio.*;
import entitats.article.ArticleVO;
import entitats.ordre.OrdrePk;


public class OrdreDao implements IDao{
	
	public static int contador=1300001;
	
	public IValueObject findByPrimaryKey(IKey key) throws DaoException {
		ArrayList<ProduccioVO> prods = new ArrayList<ProduccioVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT a.*, pmcom, observa, numero, fecha, almacen, articulo, entrada, acabado, f_acabado, cast(libre_1 as int) as ordre, libre_2 as data, cast(libre_3 as int) as proveidor, libre_4 as albara FROM c_prod " +
					"INNER JOIN articulo a on a.codigo=articulo WHERE cast(libre_1 as int)=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ((OrdrePk)key).getId());
			ResultSet rs = ps.executeQuery();
			int prod1=0;
			while (rs.next()) {
				prod1 = rs.getInt("numero");
				prods.add(new ProduccioVO(rs.getInt("numero"),
						new OrdreVO(rs.getInt("ordre"), new java.sql.Date(System.currentTimeMillis()), null,rs.getInt("proveidor"), rs.getString("albara"), null), 
						rs.getDate("fecha"),
						new ArticleVO(rs.getString("articulo"), rs.getString("nombre"), null, rs.getString("familia"),0, true, rs.getDouble("cost_ult1"), null),
						rs.getDouble("entrada"),
						rs.getDouble("pmcom"),
						rs.getString("observa"),
						null, 
						rs.getBoolean("acabado")));
			}
			
			sql = "SELECT cast(libre_1 as int) as ordre, libre_2 as data, cast(libre_3 as int) as proveidor, libre_4 as albara, libre_5 as tancament FROM c_prod " +
					"WHERE numero=? and cast(libre_1 as int)=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, prod1);
			ps.setInt(2, ((OrdrePk)key).getId());
			rs = ps.executeQuery();
			/*System.out.println(sql);
			System.out.println(prod1); System.out.println(((OrdrePk)key).getId());*/
			if (rs.next()) {
				return new OrdreVO(rs.getInt("ordre"),
						//rs.getDate("data"),
						new java.sql.Date(System.currentTimeMillis()),
						rs.getDate("tancament"),
						rs.getInt("proveidor"),
						rs.getString("albara"),prods);
			} else return null;
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	}
	
	public Collection<OrdreVO> findAll() throws DaoException {
		Collection<OrdreVO> retorn = new ArrayList<OrdreVO>();
		Statement ps = null; Connection con = null;
		try {
			con = createConnection();
			ps = con.createStatement();
			String sql = "SELECT distinct cast(libre_1 as int) as ordre, libre_2 as data, cast(libre_3 as int) as proveidor, libre_4 as albara, libre_5 as tancament FROM c_prod " +
					"WHERE libre_1!=''";
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				java.sql.Date fin = null;
				if (!rs.getString("tancament").trim().equals("")) fin =new java.sql.Date(Long.valueOf(rs.getString("tancament")));
				retorn.add(new OrdreVO(rs.getInt("ordre"),
						//rs.getDate("data"),
						new java.sql.Date(System.currentTimeMillis()),
						fin, //rs.getDate("tancament"),
						rs.getInt("proveidor"),
						rs.getString("albara"),
						null));
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
	
	public Collection<OrdreVO> findByRange(int ini, int fi) throws DaoException {
		Collection<OrdreVO> retorn = new ArrayList<OrdreVO>();
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT cast(libre_1 as int) as ordre, libre_2 as data, cast(libre_3 as int) as proveidor, libre_4 as albara FROM c_prod WHERE cast(libre_1 as int)>=? and cast(libre_1 as int)<=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ini);
			ps.setInt(2, fi);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retorn.add(new OrdreVO(rs.getInt("ordre"),
						rs.getDate("data"), null,
						rs.getInt("proveidor"),
						rs.getString("albara"),
						null));
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
	
	public void add(IValueObject vo) throws DaoException{
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT max(cast(libre_1 as int)) as contador FROM c_prod WHERE libre_1!=''";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next() & rs.getInt("contador")!=0) contador = rs.getInt("contador")+1;
			
			OrdreVO ordre = (OrdreVO)vo;
			ArrayList<ProduccioVO> prods = ordre.getDetall();
			sql = "UPDATE c_prod SET libre_1=?, libre_2=?, libre_3=?, libre_4=? WHERE numero=? ";
			for (int i=1; i<prods.size(); i++) sql+=" or numero=?";
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, contador);
			//ps.setDate(2, ordre.getData());
			ps.setLong(2, ordre.getTancament().getTime());
			ps.setInt(3, ordre.getProveidor());
			ps.setString(4, ordre.getAlbara());
			int y=5;
			Iterator<ProduccioVO> it = prods.iterator();
			while (it.hasNext()) {
				ps.setInt(y, ((ProduccioPk)(((ProduccioVO)it.next()).getKey())).getId());
				y++;
			}
			ps.executeUpdate();
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	};
	
	public void close(IValueObject vo) throws DaoException{
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			
			OrdreVO ordre = (OrdreVO)vo;
			ArrayList<ProduccioVO> prods = ordre.getDetall();
			String sql = "UPDATE c_prod SET libre_5=?, libre_3=?, libre_4=? WHERE numero=? ";
			for (int i=1; i<prods.size(); i++) sql+=" or numero=?";
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, ((OrdrePk)ordre.getKey()).getId());
			ps.setDate(2, ordre.getTancament());
			ps.setInt(3, ordre.getProveidor());
			ps.setString(4, ordre.getAlbara());
			int y=4;
			Iterator<ProduccioVO> it = prods.iterator();
			while (it.hasNext()) {
				ps.setInt(y, ((ProduccioPk)(((ProduccioVO)it.next()).getKey())).getId());
				y++;
			}
			ps.executeUpdate();
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	};
	
	public void update(IValueObject vo) throws DaoException{
		
	};
	
	public void delete(IKey key) throws DaoException{
		PreparedStatement ps = null; Connection con = null;
		try {
			con = createConnection();
			String sql = "SELECT numero FROM c_prod WHERE cast(libre_1 as int)=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ((OrdrePk)key).getId());
			ResultSet rs = ps.executeQuery();
			Statement stmt = null;
			while (rs.next()) {
				sql = "UPDATE c_prod SET libre_1='', libre_2='', libre_3='', libre_4='' WHERE numero="+rs.getInt("numero");
				stmt = con.createStatement();
				stmt.addBatch(sql);
			}
			if (stmt!=null) stmt.executeBatch();
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new DaoException(ex.getMessage());
		} finally {
			try {if (ps != null) ps.close();} catch (Exception ex) {}
			try {if (con != null) con.close();} catch (Exception ex) {}
		}
	};
	
	
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
