package app.control.word.search;

import java.net.URL;
import java.util.ResourceBundle;

import app.control.word.register.Word;
import app.control.word.register.WordDao;
import app.view.word.search.WordSearchView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class WordSearchControl implements Initializable {
	
	@FXML private ChoiceBox<String> choiceOptionsFilters;
    @FXML private TextField txtSearchWord;
    @FXML private Button bttSearchWord;
    @FXML private TableView<Word> tableWords;
    @FXML private TableColumn<Word, Integer> colunCod;
    @FXML private TableColumn<Word, String> colunDescription;
    @FXML private TableColumn<Word, String> colunTranslation;
    @FXML private Button bttSelection;

    private static Word wordSelected;
    private ObservableList<Word> listWords = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		bttSearchWord.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				search();
			}
		});
		
		bttSelection.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				selection();
			}
		});
		
		tableWords.setItems(listWords);
		
		tableWords.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 2) {
				selection();
			}
		});
		
		tableWords.setOnKeyPressed((KeyEvent key) -> {
			if (key.getCode().equals(KeyCode.ENTER)) {
				selection();
			}
		});
		
		colunCod.setCellValueFactory(new PropertyValueFactory<Word, Integer>("id"));
		colunDescription.setCellValueFactory(new PropertyValueFactory<Word, String>("description"));
		colunTranslation.setCellValueFactory(new PropertyValueFactory<Word, String>("translation"));

	}
	
	protected void search() {
		WordDao wordDao = new WordDao();
		try {
			listWords.clear();
			listWords.addAll(wordDao.consultAll());
			tableWords.refresh();
		} catch (Exception e) {
			//adicionar mensagem ao usuario em caso de erro na consulta
		}
	}

	protected void selection() {
		wordSelected = tableWords.getSelectionModel().getSelectedItem();
		if(wordSelected != null) {
			WordSearchView.getStage().close();
		}
	}
	
	public static Word getWordSelected() {
		return wordSelected;
	}

	public static void setWordSelected(Word word) {
		WordSearchControl.wordSelected = word;
	}

}