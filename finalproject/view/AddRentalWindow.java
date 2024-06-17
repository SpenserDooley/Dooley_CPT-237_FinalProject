package edu.tridenttech.cpt237.dooley.finalproject.view;

/**
 * AddRentalWindow allows the user to confirm or cancel anytime they add
 * a game to a transaction. Once a game's inventory reaches zero, the user
 * is provided with an error message and is unable to add any more to their order.
 * 
 * @author Spenser Dooley
 */

import java.util.Optional;
import edu.tridenttech.cpt237.dooley.finalproject.model.Game;
import edu.tridenttech.cpt237.dooley.finalproject.model.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AddRentalWindow {

	private final static Integer RENTAL_MAX = 1;
	private Stage myStage;
	private Store myStore;
	protected int orderId; 
	private Optional<Game> games;
	private GridPane gridPane = new GridPane();

	public AddRentalWindow() {
		myStage = new Stage();
		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane);
		myStage.setWidth(400);
		myStage.setTitle("Add Game?");
		myStage.setScene(scene);

		borderPane.setCenter(gridPane);
		borderPane.setPadding(new Insets(5));

		gridPane.setMinWidth(300);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(5));

		borderPane.setCenter(gridPane);
	}

	public void show(Store store, int id, String gameTitle, String platform) {
		myStore = store;
		this.orderId = id;
		populateGrid(store, id, gameTitle, platform);
		myStage.show();
	}

	private void populateGrid(Store store, int id, String title, String platform) {
		myStore = store;
		this.orderId = id;
		games = myStore.getGameByTitle(title, platform);

		Text prompt = new Text("You are about to add:");
		Text gameTitle = new Text(games.get().getName() + " for the " + games.get().getPlatform());
		Text rentalCost = new Text(" ($2.00/night)");

		prompt.setTextAlignment(TextAlignment.CENTER);
		gameTitle.setTextAlignment(TextAlignment.CENTER);
		rentalCost.setTextAlignment(TextAlignment.CENTER);
		gridPane.add(prompt, 0, 0);
		gridPane.add(gameTitle, 0, 1);
		gridPane.add(rentalCost, 1, 1);

		Button confirm = new Button("OK");
		Button cancel = new Button("Cancel");
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(5));
		buttonBox.getChildren().add(confirm);
		buttonBox.getChildren().add(cancel);
		gridPane.add(buttonBox, 0, 3);

		confirm.setOnAction(e -> {
			try {
				System.err.printf("Order ID: %d%n", orderId);
				System.err.printf("Game Title: %s%n", games.get().getName());
				System.err.printf("Platform: %s%n", games.get().getPlatform());
				System.err.printf("Platform charAt(0): %c%n", games.get().getPlatform().charAt(0));
				System.err.printf("Copies: %d%n", games.get().getCopies());

				if(games.get().getCopies() >= 1) {
					myStore.addLineItem(orderId, games.get().getName(), RENTAL_MAX, games.get().getPlatform()); 
					myStage.close();
					games.get().updateStock();
				}
				else {
					Alert alert = new Alert(AlertType.ERROR, "This item appears to be out of stock", ButtonType.OK);
					alert.showAndWait();
				}
			}catch(Exception ex) {
				Alert alert = new Alert(AlertType.ERROR, "This item appears to be out of stock", ButtonType.OK);
				alert.showAndWait();
			}
		});

		cancel.setOnAction(e -> {
			myStage.close();
		});
	}

	public boolean isShowing() {
		return myStage.isShowing();
	}

	public void toFront() {
		if(myStage.isIconified()) {
			myStage.setIconified(false);
		}
		else {
			myStage.toFront();
		}
	}
}
