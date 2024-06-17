package edu.tridenttech.cpt237.dooley.finalproject.view;

/**
 * ConfirmationWindow displays a receipt to the user in a window that
 * contains the games they want to rent, the platform that they're on,
 * the price of each one and the total cost. Users have the option to confirm, 
 * or cancel if they want to add any more items.
 * 
 * @author Spenser Dooley
 */

import java.util.List;
import java.util.Optional;

import edu.tridenttech.cpt237.dooley.finalproject.model.Rental;
import edu.tridenttech.cpt237.dooley.finalproject.model.Store;
import edu.tridenttech.cpt237.dooley.finalproject.model.Transaction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfirmationWindow {

	private Stage myStage;
	private Store myStore;
	protected int txId; 
	private GridPane gridPane = new GridPane();

	public ConfirmationWindow() {
		myStage = new Stage();
		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane);
		myStage.setScene(scene);
		gridPane.setAlignment(Pos.CENTER);
		borderPane.setPrefSize(600, 600);
		myStage.setTitle("Order Confirmation");
		borderPane.setCenter(gridPane);
	}

	public void show(Store store, int id, Stage orderStage) {
		txId = id;
		populateGrid(store, id, orderStage);
		myStage.show();
	}

	private void populateGrid(Store store, int id, Stage orderStage) {
		myStore = store;
		txId = id;
		Optional<Transaction> tx = myStore.findPendingTransaction(id);
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Consolas", 12));
		textArea.setPadding(new Insets(5));
		textArea.setEditable(false);
		textArea.setPrefSize(550, 550);

		ReceiptScreen receipt = new ReceiptScreen();
		List<Rental> rentalList = tx.get().getRentals();

		textArea.appendText(String.format("%-35s %15s %10s%n", "Game", "Platform", "Rental Fee"));

		for(Rental rental: rentalList) {
			textArea.appendText(String.format("%-35s %15s %10.2f%n", rental.getTitle(), rental.getPlatform(), rental.getPriceEach()));
		}

		textArea.appendText(String.format("%-50s %11.2f%n", "Total", tx.get().getTotalCost()));

		gridPane.add(textArea, 0, 0);

		HBox buttonBox = new HBox();
		Button confirm = new Button("Complete Transaction");
		Button cancel = new Button("Cancel");
		confirm.setAlignment(Pos.CENTER);
		cancel.setAlignment(Pos.CENTER);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().add(confirm);
		buttonBox.getChildren().add(cancel);

		confirm.setOnAction(e -> {
			Stage parent = orderStage;
			myStore.placeOrder(id);
			receipt.displayReceipt(tx.get());
			myStage.close();
			parent.close();
		});

		cancel.setOnAction(e -> {
			myStage.close();
		});

		gridPane.add(buttonBox, 0, 1);
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
