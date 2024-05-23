package app.view.dictionary.search;

import java.io.IOException;

import app.control.dictionary.search.DictionarySearchControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class DictionarySearchView {
	
	private static Stage stage;
	private static Scene scene;
	private static Parent root;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		DictionarySearchView.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		DictionarySearchView.scene = scene;
	}

	public static Parent getRoot() {
		return root;
	}

	public static void setRoot(Parent root) {
		DictionarySearchView.root = root;
	}

	public static void buildAndshowScreen(Stage stageOwner) {
		buildRoot();
		buildScene();
		buildAndShowStage(stageOwner);
	}

	private static void buildRoot() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(DictionarySearchView.class.getResource("DictionarySearchView.fxml"));
		rootFxml.setController(new DictionarySearchControl());
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
	
	private static void buildAndShowStage(Stage stageOwner) {
		stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Buscar Dicionarios");
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(stageOwner);
		stage.showAndWait();
	}
}