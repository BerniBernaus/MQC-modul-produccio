package dao;

import java.util.Collection;

public interface IDao {
	
	public IValueObject findByPrimaryKey(IKey key) throws DaoException;
	public Collection findAll() throws DaoException;
	public void add(IValueObject vo) throws DaoException;
	public void update(IValueObject vo) throws DaoException;
	public void delete(IKey key) throws DaoException;
	
}
