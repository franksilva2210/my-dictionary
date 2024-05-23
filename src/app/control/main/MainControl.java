package app.control.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import app.control.dictionary.register.Dictionary;
import app.control.dictionary.register.DictionaryRegisterControl;
import app.control.dictionary.search.DictionarySearchControl;
import app.control.message.confirm.MessageConfirmControl;
import app.control.message.info.MessageInfoControl;
import app.control.word.register.Word;
import app.control.word.register.WordRegisterControl;
import app.util.ModPersistData;
import app.view.about.AboutView;
import app.view.dictionary.register.DictionaryRegisterView;
import app.view.dictionary.search.DictionarySearchView;
import app.view.main.MainView;
import app.view.message.confirm.MessageConfirmView;
import app.view.message.info.MessageInfoView;
import app.view.word.register.WordRegisterView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MainControl implements Initializable {
	
	@FXML private MenuItem menuNewDictionary;
	@FXML private MenuItem menuClosedSystem;
	@FXML private MenuItem menuAbout;
	@FXML private Tab tabDictionary;
	
	@FXML private TabPane tabPaneMain;
	@FXML private Button bttRegisterDictionary;
	@FXML private Button bttEditDictionary;
	@FXML private Button bttRemoveDictionary;
	@FXML private Button bttRefresh;
	@FXML private TableView<Dictionary> tableDictionary;
	@FXML private TableColumn<Dictionary, String> columnLang;
	@FXML private TableColumn<Dictionary, String> columnTitle;
	
	@FXML private Tab tabHome;
	@FXML private Button bttSearchDictionary;
	@FXML private Label lblNameDictionary;
	@FXML private TextField txtSearchWords;
	@FXML private Button bttSearchWords;
	@FXML private Button bttAddWord;
	@FXML private Button bttRemoveWord;
	@FXML private Button bttEditWord;
	@FXML private ListView<String> listViewWords;
	@FXML private Label lblTranslation;
	@FXML private TextField txtTranslationWord;
	@FXML private TextField txtClassGramaticWord;
	@FXML private TextArea txtSignificationWord;
	@FXML private Button bttSaveDictionary;
	
	private ObservableList<Dictionary> listDictionary = FXCollections.observableArrayList();
	private static Dictionary dictionaryCurrent;
	private ObservableList<String> listWords = FXCollections.observableArrayList();
	private AbaDictionaryService abaDictionaryService = new AbaDictionaryService();
	private AbaHomeService abaHomeService = new AbaHomeService();
	
	//------------------------------------------------------------
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		menuNewDictionary.setOnAction((ActionEvent) -> {
			addDictionary();
		});
		
		menuClosedSystem.setOnAction((ActionEvent) -> {
			System.exit(0);
		});
		
		menuAbout.setOnAction((ActionEvent) -> {
			AboutView.buildAndShowScreen(MainView.getStage());
		});
		
		bttRegisterDictionary.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				addDictionary();
			}
		});
		
		bttEditDictionary.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				editDictionary();
			}
		});
		
		bttRemoveDictionary.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				removeDictionary();
			}
		});
		
		bttRefresh.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				consultAllDictionary();
			}
		});
		
		tableDictionary.setItems(listDictionary);
		
		tableDictionary.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 2) {
				selectedDictionary();
			}
		});
		
    	columnTitle.setCellValueFactory(new PropertyValueFactory<Dictionary, String>("title"));
    	columnLang.setCellValueFactory(new PropertyValueFactory<Dictionary, String>("language"));
		
    	consultAllDictionary();
    	
    	listViewWords.setItems(listWords);
    	
		listViewWords.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				selectedWordDescription();
			}
		});
		
		bttSearchDictionary.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				searchDictionary();
			}
		});
		
		bttSearchDictionary.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode() == KeyCode.ENTER) {
		        	searchDictionary();
		        }
		    }
		});
		
		bttSearchWords.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				searchWordsInListDictionary();
			}
		});
		
		txtSearchWords.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode() == KeyCode.ENTER) {
		        	searchWordsInListDictionary();
		        }
		    }
		});
		
		txtSearchWords.setOnKeyPressed((tecla) -> {
			if(tecla.getCode() == KeyCode.BACK_SPACE) {
				System.out.println("teste");
				if (txtSearchWords.getText().equals("")) {
					listWords.clear();
					listWords.addAll(abaDictionaryService.getListWordsDescription(
							dictionaryCurrent.getListWords()));
					listViewWords.refresh();
				}
			}
		});
		
		bttAddWord.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				addWordInList();
			}
		});
		
		bttEditWord.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				editWordInList();
			}
		});
		
		bttRemoveWord.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				removeWordInList();
			}
		});
		
		bttSaveDictionary.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				saveDictionary();
			}
		});
		
	}
	
	private void addDictionary() {
		DictionaryRegisterControl.setDictionaryCurrent(null);
		DictionaryRegisterControl.setModPersistence(ModPersistData.INSERT);
		DictionaryRegisterView.buildAndShowScreen(MainView.getStage());
		consultAllDictionary();
	}
	
	private void editDictionary() {
		Dictionary dictionarySelected = tableDictionary.getSelectionModel().getSelectedItem();
		if (dictionarySelected != null) {
			DictionaryRegisterControl.setDictionaryCurrent(dictionarySelected);
			DictionaryRegisterControl.setModPersistence(ModPersistData.UPDATE);
			DictionaryRegisterView.buildAndShowScreen(MainView.getStage());
			consultAllDictionary();
		}
	}
	
	private void removeDictionary() {
		Dictionary dictionarySelected = tableDictionary.getSelectionModel().getSelectedItem();
		if (dictionarySelected != null) {
			MessageConfirmControl.setConfirm(false);
			MessageConfirmControl.setMsgUser("Deseja realmente realizar essa remocao?");
			MessageConfirmView.buildAndShowScreen(MainView.getStage());
			if (MessageConfirmControl.getConfirm()) {
				try {
					abaHomeService.removeDictionary(dictionarySelected);
					consultAllDictionary();
				} catch (Exception e) {
					MessageInfoControl.setMsgUser(e.getMessage());
					MessageInfoView.buildAndShowScreen(MainView.getStage());
				}
			}
		}
	}
	
	private void selectedDictionary() {
		Dictionary dictionarySelected = tableDictionary.getSelectionModel().getSelectedItem();
		if (dictionarySelected != null) {
			dictionaryCurrent = dictionarySelected;
			tabPaneMain.getSelectionModel().select(tabDictionary);
			showDictionaryCurrent();
		}
	}
	
	private void consultAllDictionary() {
		try {
			listDictionary.clear();
			listDictionary.addAll(abaHomeService.consultAllDictionary());
		} catch (Exception e) {
			MessageInfoControl.setMsgUser(e.getMessage());
			MessageInfoView.buildAndShowScreen(MainView.getStage());
		}
	}
	
	private void searchDictionary() {
		DictionarySearchControl.setDictionarySelected(null);
		DictionarySearchView.buildAndshowScreen(MainView.getStage());
		if (DictionarySearchControl.getDictionarySelected() != null) {
			dictionaryCurrent = DictionarySearchControl.getDictionarySelected();
			showDictionaryCurrent();
		}
	}
	
	private void searchWordsInListDictionary() {
		String search = txtSearchWords.getText();
		List<String> listPossibles = new ArrayList<>();
		
		if (!search.equals("")) {
			for(String word :listWords) {
				if(word.toLowerCase().indexOf(search.toLowerCase()) != -1) {
					listPossibles.add(word);
				}
			}
			
			if (listPossibles.size() > 0) {
				listWords.clear();
				listWords.addAll(listPossibles);
				listViewWords.refresh();
			}
			
		} else {
			listWords.clear();
			listWords.addAll(abaDictionaryService.getListWordsDescription(
					dictionaryCurrent.getListWords()));
			listViewWords.refresh();
		}
	}
	
	private void addWordInList() {
		if (dictionaryCurrent != null && dictionaryCurrent.getId() > 0) {
			WordRegisterControl.setModPersist(ModPersistData.INSERT);
			WordRegisterControl.setWordCurrent(null);
			WordRegisterView.buildAndShowScreen(MainView.getStage());
			if (WordRegisterControl.getWordCurrent() != null) {
				Word word = WordRegisterControl.getWordCurrent();
				abaDictionaryService.addWordInListDictionary(dictionaryCurrent, word);
				listWords.clear();
				listWords.addAll(abaDictionaryService.getListWordsDescription(
						dictionaryCurrent.getListWords()));
				listViewWords.refresh();
			}
		} else {
			MessageInfoControl.setMsgUser("Não foi selecionado nenhum dicionario.");
			MessageInfoView.buildAndShowScreen(MainView.getStage());
		}
	}
	
	private void editWordInList() {
		String wordDescription = listViewWords.getSelectionModel().getSelectedItem();
		if (wordDescription != null) {
			Word wordSelected = dictionaryCurrent.searchWordInList(wordDescription);
			WordRegisterControl.setWordCurrent(wordSelected);
			WordRegisterControl.setModPersist(ModPersistData.UPDATE);
			WordRegisterView.buildAndShowScreen(MainView.getStage());
			if (WordRegisterControl.getWordCurrent() != null) {
				Word wordEdit = WordRegisterControl.getWordCurrent();
				if (wordSelected.existUpdate(wordEdit)) {
					abaDictionaryService.addWordInListDictionary(dictionaryCurrent, wordEdit);
					listWords.clear();
					listWords.addAll(abaDictionaryService.getListWordsDescription(
							dictionaryCurrent.getListWords()));
					listViewWords.refresh();
				}	
			}
		}
	}
	
	private void removeWordInList() {
		String wordDescription = listViewWords.getSelectionModel().getSelectedItem();
		if (wordDescription != null) {
			MessageConfirmControl.setConfirm(false);
			MessageConfirmControl.setMsgUser("Deseja realmente confirmar esta operacao?");
			MessageConfirmView.buildAndShowScreen(MainView.getStage());
			if (MessageConfirmControl.getConfirm()) {
				abaDictionaryService.removeWordInListDictionary(dictionaryCurrent, wordDescription);
				listWords.clear();
				listWords.addAll(abaDictionaryService.getListWordsDescription(
						dictionaryCurrent.getListWords()));
				listViewWords.refresh();
			}
		}
	}
	
	private void selectedWordDescription() {
		String wordDescription = listViewWords.getSelectionModel().getSelectedItem();
		if (wordDescription != null) {
			Word wordSelected = dictionaryCurrent.searchWordInList(wordDescription);
			txtTranslationWord.setText(wordSelected.getTranslation());
			txtClassGramaticWord.setText(wordSelected.getClassGramatic());
			txtSignificationWord.setText(wordSelected.getSignification());
		}
	}
	
	private void saveDictionary() {
		try {
			abaDictionaryService.saveDictionary(dictionaryCurrent);
			dictionaryCurrent = abaDictionaryService.consultDictionary(dictionaryCurrent.getId());
			showDictionaryCurrent();
			MessageInfoControl.setMsgUser("Dicionario Atualizado com Sucesso!");
			MessageInfoView.buildAndShowScreen(MainView.getStage());
		} catch (Exception e) {
			MessageInfoControl.setMsgUser(e.getMessage());
			MessageInfoView.buildAndShowScreen(MainView.getStage());
		}
	}
	
	private void showDictionaryCurrent() {
		lblNameDictionary.setText(dictionaryCurrent.getTitle());
		listWords.clear();
		listWords.addAll(abaDictionaryService.getListWordsDescription(
				dictionaryCurrent.getListWords()));
		listViewWords.refresh();
	}

	public static Dictionary getDictionaryCurrent() {
		return dictionaryCurrent;
	}

	public static void setDictionaryCurrent(Dictionary dictionaryCurrent) {
		MainControl.dictionaryCurrent = dictionaryCurrent;
	}
	
}