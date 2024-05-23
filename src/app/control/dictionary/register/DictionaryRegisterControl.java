package app.control.dictionary.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.control.dictionary.search.DictionarySearchControl;
import app.control.message.confirm.MessageConfirmControl;
import app.util.ModPersistData;
import app.view.dictionary.register.DictionaryRegisterView;
import app.view.dictionary.search.DictionarySearchView;
import app.view.message.confirm.MessageConfirmView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DictionaryRegisterControl implements Initializable {
	
	@FXML private Button bttNewDictionary;
    @FXML private Button bttSearchDictionary;
    @FXML private Button bttRemoveDictionary;
	@FXML private TextField txtTitle;
	@FXML private TextField txtLanguage;
    @FXML private Label txtMsg;
    @FXML private Button bttSave;
    @FXML private Button bttCancel;
    
	private static Dictionary dictionaryCurrent = new Dictionary();
	private static ModPersistData modPersistence = ModPersistData.INSERT;
	private DictionaryRegisterService dictionaryRegisterService = new DictionaryRegisterService();
	private DictionaryRegisterComponentsFxDto componentsFxDto = new DictionaryRegisterComponentsFxDto();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		bttNewDictionary.setOnMouseClicked((MouseEvent clique) -> {
			if(clique.getClickCount() == 1) {
				newDictionary();
			}
		});
		
		bttSearchDictionary.setOnMouseClicked((MouseEvent clique) -> {
			if(clique.getClickCount() == 1) {
				searchDictionary();
			}
		});
		
		bttRemoveDictionary.setOnMouseClicked((MouseEvent clique) -> {
			if(clique.getClickCount() == 1) {
				removeDictionary();
			}
		});
		
		bttSave.setOnMouseClicked((MouseEvent clique) -> {
			if(clique.getClickCount() == 1) {
				saveDictionary();
			}
		});
		
		bttCancel.setOnMouseClicked((MouseEvent clique) -> {
			if(clique.getClickCount() == 1) {
				DictionaryRegisterView.getStage().close();
			}
		});
		
		componentsFxDto.setTxtLanguage(txtLanguage);
		componentsFxDto.setTxtTitle(txtTitle);
		
		showDataScreen();
	}
	
	private void searchDictionary() {
		DictionarySearchControl.setDictionarySelected(null);
		DictionarySearchView.buildAndshowScreen(DictionaryRegisterView.getStage());
		if (DictionarySearchControl.getDictionarySelected() != null) {
			dictionaryCurrent = DictionarySearchControl.getDictionarySelected();
			modPersistence = ModPersistData.UPDATE;
			showDataScreen();
		}
	}
	
	private void saveDictionary() {
		try {
			dictionaryRegisterService.validateFields(componentsFxDto);
			extractFields();
			dictionaryRegisterService.executePersistence(modPersistence, dictionaryCurrent);
			newDictionary();
		} catch (Exception e) {
			txtMsg.setText(e.getMessage());
		}
	}

	private void removeDictionary() {
		if (modPersistence == ModPersistData.UPDATE) {
			try {
				MessageConfirmControl.setConfirm(false);
				MessageConfirmControl.setMsgUser("Deseja realmente realizar essa remocao?");
				MessageConfirmView.buildAndShowScreen(DictionaryRegisterView.getStage());
				if (MessageConfirmControl.getConfirm()) {
					modPersistence = ModPersistData.DELETE;
					dictionaryRegisterService.executePersistence(modPersistence, dictionaryCurrent);
					newDictionary();
				}
			} catch (Exception e) {
				txtMsg.setText(e.getMessage());
			}
		}
	}

	private void newDictionary() {
		modPersistence = ModPersistData.INSERT;
		dictionaryCurrent = new Dictionary();
		clearDataScreen();
	}

	protected void extractFields() {
		dictionaryCurrent.setTitle(txtTitle.getText());
		dictionaryCurrent.setLanguage(txtLanguage.getText());
	}

	protected void clearDataScreen() {
		txtTitle.clear();
		txtLanguage.clear();
		txtMsg.setText("");
	}

	protected void showDataScreen() {
		txtTitle.setText(dictionaryCurrent.getTitle());
		txtLanguage.setText(dictionaryCurrent.getLanguage());
		txtMsg.setText("");
	}

	public static Dictionary getDictionaryCurrent() {
		return dictionaryCurrent;
	}

	public static void setDictionaryCurrent(Dictionary dictionaryCurrent) {
		if (dictionaryCurrent != null) {
			DictionaryRegisterControl.dictionaryCurrent = dictionaryCurrent;
		} else {
			DictionaryRegisterControl.dictionaryCurrent = new Dictionary();
		}
	}

	public static ModPersistData getModPersistence() {
		return modPersistence;
	}

	public static void setModPersistence(ModPersistData modPersistence) {
		DictionaryRegisterControl.modPersistence = modPersistence;
	}
	
}