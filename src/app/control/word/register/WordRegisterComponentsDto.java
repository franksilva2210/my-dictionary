package app.control.word.register;

import javafx.scene.control.TextField;

public class WordRegisterComponentsDto {
	
	private TextField txtDescriptionWord;
    private TextField txtTranslationWord;

	public TextField getTxtDescriptionWord() {
		return txtDescriptionWord;
	}

	public void setTxtDescriptionWord(TextField txtDescriptionWord) {
		this.txtDescriptionWord = txtDescriptionWord;
	}

	public TextField getTxtTranslationWord() {
		return txtTranslationWord;
	}

	public void setTxtTranslationWord(TextField txtTranslationWord) {
		this.txtTranslationWord = txtTranslationWord;
	}
    
}
