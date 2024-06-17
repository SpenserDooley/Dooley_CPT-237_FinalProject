package edu.tridenttech.cpt237.dooley.finalproject.view;

/**
 * MainWindow presents the user with the option to start a new order, 
 * display itemized transactions for the day, or quit.
 * 
 * @author Spenser Dooley
 */

import edu.tridenttech.cpt237.dooley.finalproject.model.Store;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainWindow {

	private Stage myStage = new Stage();
	private Store myStore;
	private OrderWindow orderScreen = new OrderWindow();

	public MainWindow(Stage stage, Store store) {
		HBox pane = new HBox();
		Scene scene = new Scene(pane);
		Button newOrder = new Button("New Order");
		Button displayOrders = new Button("Display Orders");
		Button quit = new Button("Quit");

		myStore = store;
		newOrder.setOnAction(new OpenOtherHandler());

		quit.setOnAction(event -> {
			myStage.close();
		});

		displayOrders.setOnAction(event -> {
			myStore.displayTransactions();
			ReceiptWindow display = new ReceiptWindow();
			display.show(myStore);
		});

		myStage = stage;
		myStage.setTitle("Game Emporium");
		myStage.setWidth(350);

		myStage.setScene(scene);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(10));
		pane.getChildren().add(newOrder);
		pane.getChildren().add(displayOrders);
		pane.getChildren().add(quit);
	}

	public void show() {
		myStage.show();
	}

	public class OpenOtherHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent Event) {
			if(!orderScreen.isShowing()) {
				orderScreen.show(myStore, myStore.startTransaction());
			}
			else {
				orderScreen.toFront();
			}
		}
	}

}
