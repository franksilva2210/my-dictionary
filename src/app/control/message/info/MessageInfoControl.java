package app.control.message.info;

import java.net.URL;
import java.util.ResourceBundle;

import app.view.message.info.MessageInfoView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class MessageInfoControl implements Initializable {
	
	@FXML private Button bttOk;
	@FXML private TextArea txtArea;
	
	private static String msgUser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txtArea.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				MessageInfoView.getStage().close();
			}
		});
	}

	public static String getMsgUser() {
		return msgUser;
	}

	public static void setMsgUser(String msgUser) {
		MessageInfoControl.msgUser = msgUser;
	}
	
	

}