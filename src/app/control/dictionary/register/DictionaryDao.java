package app.control.dictionary.register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.control.word.register.Word;
import app.util.ConnectionDataBase;
import app.util.Dao;

public class DictionaryDao implements Dao<Dictionary> {
	
	public Dictionary consult(int id) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
        ResultSet result = null;
        Dictionary dictionary = null;
        
        String sql = "SELECT * FROM dictionary WHERE dictionary_id = ?; ";
        
    	try {
    		connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeQuery();
			if (result.next()) {
				dictionary = new Dictionary();
        		dictionary.setId(result.getInt("dictionary_id"));
        		dictionary.setTitle(result.getString("dictionary_title"));
        		dictionary.setLanguage(result.getString("dictionary_language"));
        		dictionary.setListWords(consultListWordsOfDictionary(result.getInt("dictionary_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
    	
		return dictionary;
	}
	
	@Override
	public List<Dictionary> consultAll() throws Exception {
		Connection connection = null;
		Statement stmt = null;
        ResultSet result = null;
        List<Dictionary> listDictionary = new ArrayList<Dictionary>();
        
        String sql = "SELECT * FROM dictionary;";
        
        try { 
        	connection = ConnectionDataBase.getConnection();
        	stmt = connection.createStatement();
        	result = stmt.executeQuery(sql);
        	while(result.next()) {
        		Dictionary dictionary = new Dictionary();
        		dictionary.setId(result.getInt("dictionary_id"));
        		dictionary.setTitle(result.getString("dictionary_title"));
        		dictionary.setLanguage(result.getString("dictionary_language"));
        		dictionary.setListWords(consultListWordsOfDictionary(result.getInt("dictionary_id")));
            	listDictionary.add(dictionary);
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
        
        return listDictionary;
	}

	@Override
	public void delete(Dictionary dictionary) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		String query1 = "DELETE FROM "
				  	  + "dictionary_word "
				  	  + "WHERE dictionary_id = ?; ";
		
		String query2 = "DELETE FROM dictionary "
				      + "WHERE dictionary_id = ?; ";
		
		try {
			connection = ConnectionDataBase.getConnection();
			connection.setAutoCommit(false);
			
			List<Word> listWordDictionary = consultListWordsOfDictionary(dictionary.getId());
			if (listWordDictionary.size() > 0) {
				pstmt = connection.prepareStatement(query1);
				pstmt.setInt(1, dictionary.getId());
				pstmt.executeUpdate();
			}
			
			pstmt = connection.prepareStatement(query2);
			pstmt.setInt(1, dictionary.getId());
			pstmt.execute();
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
			throw e;
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
	}

	@Override
	public void save(Dictionary dictionary) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		String sql = "INSERT INTO dictionary("
				   + "dictionary_title, "
				   + "dictionary_language) "
				   + "VALUES(?, ?) ";
		
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, dictionary.getTitle());
			pstmt.setString(2, dictionary.getLanguage());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
	}

	@Override
	public void update(Dictionary dictionary) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		String sql = "UPDATE dictionary "
				   + "SET "
				   + "dictionary_title = ?, "
				   + "dictionary_language = ?"
				   + "WHERE dictionary_id = ?;";
		
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, dictionary.getTitle());
			pstmt.setString(2, dictionary.getLanguage());
			pstmt.setInt(3, dictionary.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
	}
	
	public void saveOrUpdateListWords(Dictionary dictionary) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		String query1 = "UPDATE word "
				   	  + "SET "
				   	  + "word_description = ?, "
				   	  + "word_translation = ?, "
				   	  + "word_class_gramatic = ?, "
				   	  + "word_signification = ? "
				   	  + "WHERE word_id = ?; ";
		
		String query2 = "INSERT INTO word "
				   	  + "(word_description,"
				   	  + " word_translation,"
				   	  + " word_class_gramatic,"
				   	  + " word_signification) "
				   	  + "VALUES "
				   	  + "(?,?,?,?);";
		
		String query3 = "DELETE FROM "
					  + "dictionary_word "
					  + "WHERE dictionary_id = ?; ";
		
		String query4 = "INSERT INTO dictionary_word("
					  + "dictionary_id, "
					  + "word_id) "
					  + "VALUES(?,?); ";
		
		try {
			connection = ConnectionDataBase.getConnection();
			connection.setAutoCommit(false);
			
			for(int i = 0; i < dictionary.getListWords().size(); i++) {
				if (dictionary.getListWords().get(i).getId() > 0) {
					pstmt = connection.prepareStatement(query1);
			      	pstmt.setString(1, dictionary.getListWords().get(i).getDescription());
			      	pstmt.setString(2, dictionary.getListWords().get(i).getTranslation());
			      	pstmt.setString(3, dictionary.getListWords().get(i).getClassGramatic());
			      	pstmt.setString(4, dictionary.getListWords().get(i).getSignification());
			      	pstmt.setInt(5, dictionary.getListWords().get(i).getId());
			      	pstmt.executeUpdate();
				}
			}
			
			for(int i = 0; i < dictionary.getListWords().size(); i++) {
				if (dictionary.getListWords().get(i).getId() <= 0) {
					pstmt = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, dictionary.getListWords().get(i).getDescription());
					pstmt.setString(2, dictionary.getListWords().get(i).getTranslation());
					pstmt.setString(3, dictionary.getListWords().get(i).getClassGramatic());
					pstmt.setString(4, dictionary.getListWords().get(i).getSignification());
					pstmt.execute();
			      	result = pstmt.getGeneratedKeys();
			      	if(result.next()) {
			      		int wordIdInsert = result.getInt(1);
			      		dictionary.getListWords().get(i).setId(wordIdInsert);
					}
				}
			}
			
			List<Word> listWord = consultListWordsOfDictionary(dictionary.getId());
			if (listWord.size() > 0) {
				pstmt = connection.prepareStatement(query3);
				pstmt.setInt(1, dictionary.getId());
				pstmt.executeUpdate();
			}
			
			pstmt = connection.prepareStatement(query4);
			for(int i = 0; i < dictionary.getListWords().size(); i++) {
				pstmt.setInt(1, dictionary.getId());
				pstmt.setInt(2, dictionary.getListWords().get(i).getId());
				pstmt.executeUpdate();
			}

			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
			throw e;
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
	}
	
	private List<Word> consultListWordsOfDictionary(int idDictionary) throws Exception {
		Connection connection = null;
		ResultSet result = null;
		PreparedStatement pstmt = null;
		List<Word> listWords = new ArrayList<>();
		
		String query = "SELECT "
					 + "	dictionary_word.word_id,"
					 + "    word.word_description,"
					 + "    word.word_translation,"
					 + "    word.word_class_gramatic,"
					 + "    word.word_signification "
					 + "FROM "
					 + "	dictionary_word "
					 + "LEFT JOIN word "
					 + "ON dictionary_word.word_id = word.word_id "
					 + "WHERE "
					 + "	dictionary_id = ? "
					 + "ORDER BY word.word_description ASC; ";
		
		try {
			connection = ConnectionDataBase.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, idDictionary);
			
			result = pstmt.executeQuery();
			while(result.next()) {
				Word word = new Word();
				word.setId(result.getInt("word_id"));
				word.setDescription(result.getString("word_description"));
				word.setTranslation(result.getString("word_translation"));
				word.setClassGramatic(result.getString("word_class_gramatic"));
				word.setSignification(result.getString("word_signification"));
				listWords.add(word);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
				if (pstmt != null)
					pstmt.close();
				if (result != null)
					result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listWords;
	}

}