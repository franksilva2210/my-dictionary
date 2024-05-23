package app.control.main;

import java.util.ArrayList;
import java.util.List;

import app.control.dictionary.register.Dictionary;
import app.control.dictionary.register.DictionaryDao;
import app.control.word.register.Word;

public class AbaDictionaryService {
	
	public void addWordInListDictionary(Dictionary dictionary, Word word) {
		if (word.getId().equals(0)) {
			dictionary.getListWords().add(word);
			return;
		}
		
		for(Word wordDictionary : dictionary.getListWords()) {
			if (word.getId().equals(wordDictionary.getId())) {
				dictionary.getListWords().remove(wordDictionary);
				dictionary.getListWords().add(word);
				return;
			}
		}
		
		if (word.getId() > 0) {
			dictionary.getListWords().add(word);
		}
	}
	
	public List<String> getListWordsDescription(List<Word> listWords) {
		List<String> listWordDescription = new ArrayList<String>();
		for(Word word : listWords) {
			listWordDescription.add(word.getDescription());
		}
		return listWordDescription;
	}
	
	public void removeWordInListDictionary(Dictionary dictionary, String wordDescription) {
		Word wordSelected = dictionary.searchWordInList(wordDescription);
		dictionary.getListWords().remove(wordSelected);
	}
	
	public void saveDictionary(Dictionary dictionary) throws Exception {
		DictionaryDao dictionaryDao = new DictionaryDao();
		try {
			dictionaryDao.saveOrUpdateListWords(dictionary);
		} catch (Exception e) {
			throw new Exception("Alguma falha no salvamento do dicionario.");
		}
	}
	
	public Dictionary consultDictionary(int idDictionary) throws Exception {
		Dictionary dictionary = null;
		DictionaryDao dictionaryDao = new DictionaryDao();
		try {
			dictionary = dictionaryDao.consult(idDictionary);
		} catch (Exception e) {
			throw new Exception("Dicionario nao encotrado.");
		}
		return dictionary;
	}
	
}
