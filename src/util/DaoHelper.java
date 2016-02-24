package util;


import entitats.article.ArticleDao;
import entitats.proveidor.ProveidorDao;
import entitats.produccio.ProduccioDao;
import entitats.ordre.OrdreDao;


public final class DaoHelper {

	private static ArticleDao articleDao;
	private static ProduccioDao produccioDao;
	private static ProveidorDao proveidorDao;
	private static OrdreDao ordreDao;
	
	
	// Mètodes		
	public static ArticleDao getArticleDao() {
		if (articleDao == null) {
			articleDao = new ArticleDao();
		}
		return articleDao;
	}
	
		
	public static ProduccioDao getProduccioDao() {
		if (produccioDao == null) {
			produccioDao = new ProduccioDao();
		}
		return produccioDao;
	}
	
	public static ProveidorDao getProveidorDao() {
		if (proveidorDao == null) {
			proveidorDao = new ProveidorDao();
		}
		return proveidorDao;
	}
	
	public static OrdreDao getOrdreDao() {
		if (ordreDao == null) {
			ordreDao = new OrdreDao();
		}
		return ordreDao;
	}
	
}
