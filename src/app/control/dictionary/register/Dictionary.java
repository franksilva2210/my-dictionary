package app.control.dictionary.register;

import java.util.ArrayList;
import java.util.List;

import app.control.word.register.Word;

public class Dictionary {
	
	private Integer id;
	private String title;
	private String language;
	private List<Word> listWords;
	
	public Dictionary() {
		this.id = 0;
		this.title = new String();
		this.language = new String();
		this.listWords = new ArrayList<>();
	}
	
	public Dictionary(int id, String title, String language) {
		this.id = id;
		this.title = title;
		this.language = language;
		this.listWords = new ArrayList<>();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Word> getListWords() {
		return listWords;
	}

	public void setListWords(List<Word> listWords) {
		this.listWords = listWords;
	}

	public void clear() {
		this.id = 0;
		this.title = "";
		this.language = "";
		this.listWords.clear();
	}
	
	public void showDataConsole() {
		System.out.println("-------------------------");
		System.out.println("Cod Dicionario: " + id);
		System.out.println("Titulo: " + title);
		System.out.println("Lingua: " + language);
		System.out.println();
		System.out.println("Lista de Palavras:");
		for(int i = 0; i < listWords.size(); i++) {
			System.out.println("Descricao: " + listWords.get(i).getDescription());
		}
		System.out.println("-------------------------");
	}
	
	public void removeWordInList(Word word) {
		for(int i = 0; i < listWords.size(); i++) {
			if(word.getId() == listWords.get(i).getId()) {
				listWords.remove(i);
			}
		}
	}
	
	public Word searchWordInList(String wordDescription) {
		for(int i = 0; i < listWords.size(); i++) {
			if(listWords.get(i).getDescription().equals(wordDescription)) {
				return listWords.get(i);
			}
		}
		return null;
	}
}