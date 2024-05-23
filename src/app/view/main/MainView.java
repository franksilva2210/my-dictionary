package app.view.main;

import java.io.IOException;

import app.control.main.MainControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainView extends Application {

	private static Stage stage;
	private static Scene scene;
	private static Parent root;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		MainView.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		MainView.scene = scene;
	}

	public static Parent getRoot() {
		return root;
	}

	public static void setRoot(Parent root) {
		MainView.root = root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		buildAndShowScreen(primaryStage);
	}

	public static void buildScreen(Stage primaryStage) {
		buildRoot();
		buildScene();
		buildStage(primaryStage);
	}
	
	public static void showScreen() {
		stage.show();
	}
	
	public static void buildAndShowScreen(Stage primaryStage) {
		buildScreen(primaryStage);
		showScreen();
	}
	
	private static void buildRoot() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(MainView.class.getResource("MainView.fxml"));
		rootFxml.setController(new MainControl());
		
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
	
	private static void buildStage(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Meu Dicionario Tradutor");
		stage.setScene(scene);
		stage.setResizable(false);
	}

}