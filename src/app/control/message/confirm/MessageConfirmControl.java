package app.control.message.confirm;

import java.net.URL;
import java.util.ResourceBundle;

import app.view.message.confirm.MessageConfirmView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MessageConfirmControl implements Initializable {
	
	@FXML private Button bttCancel;
	@FXML private Button bttOk;
	@FXML private Text msgText;
	
	private static Boolean confirm;
	private static String msgUser;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		msgText.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				confirm = true;
				MessageConfirmView.getStage().close();
			}
		});
		
		bttCancel.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				confirm = false;
				MessageConfirmView.getStage().close();
			}
		});
		
	}

	public static Boolean getConfirm() {
		return confirm;
	}

	public static void setConfirm(Boolean confirm) {
		MessageConfirmControl.confirm = confirm;
	}

	public static String getMsgUser() {
		return msgUser;
	}

	public static void setMsgUser(String msgUser) {
		MessageConfirmControl.msgUser = msgUser;
	}

}
