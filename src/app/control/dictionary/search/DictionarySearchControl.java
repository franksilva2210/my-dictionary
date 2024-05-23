package app.control.dictionary.search;

import java.net.URL;
import java.util.ResourceBundle;

import app.control.dictionary.register.Dictionary;
import app.control.dictionary.register.DictionaryDao;
import app.control.message.info.MessageInfoControl;
import app.view.dictionary.search.DictionarySearchView;
import app.view.message.info.MessageInfoView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class DictionarySearchControl implements Initializable {
	
	@FXML private Button bttSearch;
	@FXML private TableView<Dictionary> tableDictionary;
	@FXML private TableColumn<Dictionary, Integer> columnCod;
	@FXML private TableColumn<Dictionary, String> columnTitle;
	@FXML private TableColumn<Dictionary, String> columnLang;
	@FXML private Button bttSelection;
	
	private static Dictionary dictionarySelected;
	private ObservableList<Dictionary> listDictionary = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableDictionary.setItems(listDictionary);
		
		tableDictionary.setOnMouseClicked((MouseEvent mouse) -> {
    		if(mouse.getClickCount() == 2) {
    			selection();
    		}
    	});
		
		columnCod.setCellValueFactory(new PropertyValueFactory<Dictionary, Integer>("id"));
    	columnTitle.setCellValueFactory(new PropertyValueFactory<Dictionary, String>("title"));
    	columnLang.setCellValueFactory(new PropertyValueFactory<Dictionary, String>("language"));
    	
    	bttSearch.setOnMouseClicked((MouseEvent mouse) -> {
    		if(mouse.getClickCount() == 1) {
    			consultAllDictionary();
    		}
    	});
    	
    	bttSelection.setOnMouseClicked((MouseEvent mouse) -> {
    		if(mouse.getClickCount() == 1) {
    			selection();
    		}
    	});
    	
    	consultAllDictionary();
	}

	protected void consultAllDictionary() {
		DictionaryDao dictionaryDao = new DictionaryDao();
		try {
			listDictionary.clear();
			listDictionary.addAll(dictionaryDao.consultAll());
			tableDictionary.refresh();
		} catch (Exception e) {
			MessageInfoControl.setMsgUser("Falha na consulta de todos os dicionarios.");
			MessageInfoView.buildAndShowScreen(DictionarySearchView.getStage());
		}
	}
	
	protected void selection() {
		dictionarySelected = tableDictionary.getSelectionModel().getSelectedItem();
		if(dictionarySelected != null) { 
			DictionarySearchView.getStage().close();
		}
	}

	public static Dictionary getDictionarySelected() {
		return dictionarySelected;
	}

	public static void setDictionarySelected(Dictionary dictionarySelected) {
		DictionarySearchControl.dictionarySelected = dictionarySelected;
	}
	
}