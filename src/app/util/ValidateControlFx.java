package app.util;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;

public class ValidateControlFx {
	
	private Node control;
	private Boolean error;
	
	public Node getControl() {
		return control;
	}

	public void setControl(Node control) {
		this.control = control;
	}
	
	public Boolean getError() {
		return error;
	}
	
	public void setError(Boolean error) {
		this.error = error;
	}
	
    public void validateControl() {
        if(control instanceof TextInputControl)
            validateTextInputField();
        else if (control instanceof ComboBox)
            validateComboBox();
        else if(control instanceof CheckBox)
        	validateCheckBox();
        else if(control instanceof DatePicker)
        	validateDatePicker();
    }

    private void validateTextInputField() {
        TextInputControl textField = (TextInputControl) control;
        if (textField.getText() == null || textField.getText().equals(""))
        	error = true;
        else
        	error = false;
    }
    
    private void validateComboBox() {
        ComboBox<?> comboBox = (ComboBox<?>) control;
        if (comboBox.getValue() == null || comboBox.getValue().toString().trim().isEmpty())
        	error = true;
        else
        	error = false;
    }
    
    private void validateCheckBox() {
    	CheckBox checkBox = (CheckBox) control;
    	if(!checkBox.isSelected())
    		error = true;
    	else
    		error = false;
    }
    
    private void validateDatePicker() {
    	DatePicker datePicker = (DatePicker) control;
        if (datePicker.getValue() == null || datePicker.getValue().toString().trim().isEmpty())
        	error = true;
        else
        	error = false;
    }
}