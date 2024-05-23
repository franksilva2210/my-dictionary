package app.view.message.confirm;

import java.io.IOException;

import app.control.message.confirm.MessageConfirmControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MessageConfirmView {
	
	private static Stage stage;
	private static Scene scene;
	private static Parent root;
	
	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		MessageConfirmView.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		MessageConfirmView.scene = scene;
	}

	public static Parent getRoot() {
		return root;
	}

	public static void setRoot(Parent root) {
		MessageConfirmView.root = root;
	}

	public static void buildScreen(Stage stageOwner) {
		buildRoot();
		buildScene();
		buildStage(stageOwner);
	}
	
	public static void showScreen() {
		stage.showAndWait();
	}
	
	public static void buildAndShowScreen(Stage stageOwner) {
		buildScreen(stageOwner);
		showScreen();
	}

	private static void buildRoot() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(MessageConfirmView.class.getResource("MessageConfirmView.fxml"));
		rootFxml.setController(new MessageConfirmControl());
			
		try {
			root = rootFxml.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private static void buildScene() {
		scene = new Scene(root);
		
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);
	}
		
	private static void buildStage(Stage stageOwner) {
		stage = new Stage();
		stage.setTitle("Mensagem");
		stage.setScene(scene);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(stageOwner);
		stage.setResizable(false);
	}
}
