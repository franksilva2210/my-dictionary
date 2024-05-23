package app.util;

import java.util.List;

public interface Dao<T> {
	
	public List<T> consultAll() throws Exception;
	
	public void delete(T ob) throws Exception;
	
	public void save(T ob) throws Exception;
	
	public void update(T ob) throws Exception;
}