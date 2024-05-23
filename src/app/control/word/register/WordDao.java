package app.control.word.register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.util.ConnectionDataBase;
import app.util.Dao;

public class WordDao implements Dao<Word> {

	public Word consult(int id) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
	    ResultSet result = null;
	    Word word = null;
	    
	    String sql = "SELECT * FROM word "
	    		   + "WHERE word_id = ?; ";
	    
	    try { 
        	connection = ConnectionDataBase.getConnection();
        	pstmt = connection.prepareStatement(sql);
        	pstmt.setInt(1, id);
        	
        	result = pstmt.executeQuery();
        	while(result.next()){
        		word = new Word();
        		word.setId(result.getInt("word_id"));
        		word.setDescription(result.getString("word_description"));
        		word.setTranslation(result.getString("word_translation"));
        		word.setClassGramatic(result.getString("word_class_gramatic"));
        		word.setSignification(result.getString("word_signification"));
            }
        } catch (SQLException exc) {
        	exc.printStackTrace();
        	throw exc;
        } finally {
        	try {
        		if(result != null)
        			result.close();
        		if(pstmt != null)
        			pstmt.close();
        		if(connection != null)
        			connection.close();
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	    return word;
	}
	
	public List<Word> consultAll() throws Exception {
		Connection connection = null;
		Statement stmt = null;
	    ResultSet result = null;
	    List<Word> listWord = new ArrayList<Word>();
	    
	    String sql = "SELECT * FROM word ORDER BY word_description ASC; ";
	    
	    try { 
        	connection = ConnectionDataBase.getConnection();
        	stmt = connection.createStatement();
        	result = stmt.executeQuery(sql);
        	while (result.next()) {
            	Word word = new Word(
            			result.getInt("word_id"),
            			result.getString("word_description"),
            			result.getString("word_translation"),
            			result.getString("word_class_gramatic"),
            			result.getString("word_signification"));
            	
            	listWord.add(word);
            }
        } catch (SQLException exc) {
        	exc.printStackTrace();
        	throw exc;
        } finally {
        	try {
        		if(result != null)
        			result.close();
        		if(stmt != null)
        			stmt.close();
        		if(connection != null)
        			connection.close();
        	} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	        
	    return listWord;
	}

	@Override
	public void delete(Word word) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM word "
				   + "WHERE word_id = ?; ";
		
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, word.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(pstmt != null) 
					pstmt.close();
				if(connection != null) 
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void save(Word word) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
					
		String sql = "INSERT INTO word "
				   + "(word_description,"
				   + " word_translation,"
			 	   + " word_class_gramatic,"
				   + " word_signification) "
				   + "VALUES "
				   + "(?,?,?,?);";
				
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql);	
	      	pstmt.setString(1, word.getDescription());
	      	pstmt.setString(2, word.getTranslation());
	      	pstmt.setString(3, word.getClassGramatic());
	      	pstmt.setString(4, word.getSignification());
	      	pstmt.execute();
		} catch(SQLException exc) {
			exc.printStackTrace();
			throw exc;
		} finally {
			try {
				if(pstmt != null) 
					pstmt.close();
				if(connection != null) 
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Integer saveAndReturnKey(Word word) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Integer wordIdInsert = null;
					
		String sql = "INSERT INTO word "
				   + "(word_description,"
				   + " word_translation,"
			 	   + " word_class_gramatic,"
				   + " word_signification) "
				   + "VALUES "
				   + "(?,?,?,?);";
				
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	
	      	pstmt.setString(1, word.getDescription());
	      	pstmt.setString(2, word.getTranslation());
	      	pstmt.setString(3, word.getClassGramatic());
	      	pstmt.setString(4, word.getSignification());
	      	pstmt.execute();
	      	result = pstmt.getGeneratedKeys();
	      	if(result.next()) {
	      		wordIdInsert = result.getInt(1);
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
			throw exc;
		} finally {
			try {
				if(connection != null) 
					connection.close();
				if(pstmt != null) 
					pstmt.close();
				if(result != null) 
					result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wordIdInsert;
	}

	@Override
	public void update(Word word) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
					
		String sql = "UPDATE word "
				   + "SET "
				   + "word_description = ?, "
				   + "word_translation = ?, "
				   + "word_class_gramatic = ?, "
				   + "word_signification = ? "
				   + "WHERE word_id = ?; ";
				
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql);
	      	pstmt.setString(1, word.getDescription());
	      	pstmt.setString(2, word.getTranslation());
	      	pstmt.setString(3, word.getClassGramatic());
	      	pstmt.setString(4, word.getSignification());
	      	pstmt.setInt(5, word.getId());
	      	pstmt.executeUpdate();
		} catch(SQLException exc) {
			exc.printStackTrace();
			throw exc;
		} finally {
			try {
				if(pstmt != null) 
					pstmt.close();
				if(connection != null) 
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}