package app.control.dictionary.register;

import javafx.scene.control.TextField;

public class DictionaryRegisterComponentsFxDto {
	
	private TextField txtTitle;
	private TextField txtLanguage;

	public TextField getTxtTitle() {
		return txtTitle;
	}

	public void setTxtTitle(TextField txtTitle) {
		this.txtTitle = txtTitle;
	}

	public TextField getTxtLanguage() {
		return txtLanguage;
	}

	public void setTxtLanguage(TextField txtLanguage) {
		this.txtLanguage = txtLanguage;
	}
	
}