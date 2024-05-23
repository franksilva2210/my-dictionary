package app.control.word.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.util.ModPersistData;
import app.view.word.register.WordRegisterView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class WordRegisterControl implements Initializable {
	
	@FXML private MenuItem menuNewDictionary;
	@FXML private TextField txtDescriptionWord;
    @FXML private TextField txtTranslationWord;
    @FXML private TextField txtClassGramaticWord;
    @FXML private TextArea txtSignification;
    @FXML private Label txtMsg;
    @FXML private Button bttSave;
    @FXML private Button bttCancel;
    @FXML private Pagination pages;
    
    private static ModPersistData modPersist;
    private static Word wordCurrent;
    private WordRegisterComponentsDto componentsFXDto = new WordRegisterComponentsDto();
    private WordRegisterService wordRegisterService = new WordRegisterService();
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		bttSave.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				saveWord();
			}
		});
		
		bttCancel.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				WordRegisterView.getStage().close();
			}
		});
		
		componentsFXDto.setTxtDescriptionWord(txtDescriptionWord);
		componentsFXDto.setTxtTranslationWord(txtTranslationWord);
		
		showDataScreen();
	}
	
	private void saveWord() {
		try {
			wordRegisterService.validateFields(componentsFXDto);
			wordCurrent = getWordExtracted();
			WordRegisterView.getStage().close();
		} catch (Exception e) {
			wordCurrent = null;
			txtMsg.setText(e.getMessage());
		}
	}
	
	protected void clearDataScreen() {
		txtDescriptionWord.clear();
		txtTranslationWord.clear();
		txtClassGramaticWord.clear();
		txtSignification.clear();
		txtMsg.setText("");
	}

	protected void showDataScreen() {
		if (modPersist.equals(ModPersistData.UPDATE) && wordCurrent != null) {
			txtDescriptionWord.setText(wordCurrent.getDescription());
			txtTranslationWord.setText(wordCurrent.getTranslation());
			txtClassGramaticWord.setText(wordCurrent.getClassGramatic());
			txtSignification.setText(wordCurrent.getSignification());
		}
	}
	
	protected Word getWordExtracted() throws Exception {
		Word wordExtracted = new Word();
		try {
			if(modPersist.equals(ModPersistData.UPDATE)) {
				wordExtracted.setId(wordCurrent.getId());
			}
			wordExtracted.setDescription(txtDescriptionWord.getText());
			wordExtracted.setTranslation(txtTranslationWord.getText());
			wordExtracted.setClassGramatic(txtClassGramaticWord.getText());
			wordExtracted.setSignification(txtSignification.getText());
			return wordExtracted;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Falha na extracao dos dados");
		}
	}

	public static Word getWordCurrent() {
		return wordCurrent;
	}

	public static void setWordCurrent(Word word) {
		if (word == null) {
			WordRegisterControl.wordCurrent = null;
		} else {
			WordRegisterControl.wordCurrent = new Word(word);
		}
	}

	public static ModPersistData getModPersist() {
		return modPersist;
	}

	public static void setModPersist(ModPersistData modPersist) {
		WordRegisterControl.modPersist = modPersist;
	}
	
}