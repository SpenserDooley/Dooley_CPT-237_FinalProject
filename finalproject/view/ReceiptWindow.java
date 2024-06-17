package edu.tridenttech.cpt237.dooley.finalproject.view;

/**
 * ReceiptWindow displays the itemized transactions from the day.
 * 
 * @author Spenser Dooley
 */

import java.util.List;

import edu.tridenttech.cpt237.dooley.finalproject.model.Rental;
import edu.tridenttech.cpt237.dooley.finalproject.model.Store;
import edu.tridenttech.cpt237.dooley.finalproject.model.Transaction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ReceiptWindow {

	private Stage myStage;
	private Store myStore;
	private GridPane gridPane = new GridPane();

	public ReceiptWindow() {
		myStage = new Stage();
		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane);
		myStage.setScene(scene);
		gridPane.setAlignment(Pos.CENTER);
		borderPane.setPrefSize(600, 600);
		myStage.setTitle("Order Confirmation");
		borderPane.setCenter(gridPane);
	}

	public boolean isShowing() {
		return myStage.isShowing();
	}

	public void show(Store store) {
		myStore = store;
		populateGrid(store);
		myStage.show();
	}

	private void populateGrid(Store store) {
		myStore = store;
		List<Transaction> tx = myStore.getTransactionList();
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Consolas", 12));
		textArea.setPadding(new Insets(5));
		textArea.setEditable(false);
		textArea.setPrefSize(550, 550);

		for(Transaction t : tx) {
			textArea.appendText(String.format("Order # %d%n", ((Transaction) t).getId()));
			for(Rental rental: ((Transaction) t).getRentals()) {
				textArea.appendText(String.format("%-35s %15s %10.2f%n", rental.getTitle(), rental.getPlatform(), rental.getPriceEach()));
			}
			textArea.appendText(String.format("%-50s %11.2f%n", "Total", t.getTotalCost()));
			textArea.appendText("\n");
		}

		gridPane.add(textArea, 0, 0);
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
