package edu.tridenttech.cpt237.dooley.finalproject;

/**
 * MainApplication extends the Application class, sends a file name
 * to a Store object and attempts to open a MainWindow
 * 
 * @author Spenser Dooley
 */

import java.io.FileNotFoundException;

import edu.tridenttech.cpt237.dooley.finalproject.model.Store;
import edu.tridenttech.cpt237.dooley.finalproject.view.MainWindow;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainApplication extends Application{

	private String readFile = "game_inventory.txt";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {

			Store store = new Store(readFile);
			new MainWindow(primaryStage, store).show();

		} catch(FileNotFoundException ex) {

			String alert = String.format("The file name provided could not be found.");
			new Alert(AlertType.ERROR, alert, ButtonType.CLOSE).showAndWait();

		}

	}

}
