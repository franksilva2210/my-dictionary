package app.control.main;

import java.util.ArrayList;
import java.util.List;

import app.control.dictionary.register.Dictionary;
import app.control.dictionary.register.DictionaryDao;

public class AbaHomeService {
	
	public List<Dictionary> consultAllDictionary() throws Exception {
		List<Dictionary> listDictionary = new ArrayList<>();
		try {
			DictionaryDao dictionaryDao = new DictionaryDao();
			listDictionary = dictionaryDao.consultAll();
		} catch (Exception e) {
			throw new Exception("Falha na consulta dos dicionarios cadastrados.");
		}
		return listDictionary;
	}
	
	public void removeDictionary(Dictionary dictionary) throws Exception {
		DictionaryDao dictionaryDao = new DictionaryDao();
		
		try {
			dictionaryDao.delete(dictionary);
		} catch (Exception e) {
			throw new Exception("Falha na remocao do registro.");
		}
	}
}
