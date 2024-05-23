package app.view.word.search;

import java.io.IOException;

import app.control.word.search.WordSearchControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class WordSearchView {
	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		WordSearchView.stage = stage;
	}

	public static void buildAndShowScreen(Stage stageOwner) {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(WordSearchView.class.getResource("WordSearchView.fxml"));
		rootFxml.setController(new WordSearchControl());
			
		Parent root = null;
		
		try {
			root = rootFxml.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao carregar arquivo FXML");
		}
			
		Scene scene = new Scene(root);
		
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);
		
		stage = new Stage();
		stage.setTitle("Busca Palavra");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(stageOwner);
		stage.setScene(scene);
		stage.showAndWait();
	}
}
