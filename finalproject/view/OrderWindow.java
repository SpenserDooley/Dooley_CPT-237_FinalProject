package edu.tridenttech.cpt237.dooley.finalproject.view;

/**
 * OrderWindow presents a list of games and their platforms that the user can rent.
 * 
 * @author Spenser Dooley
 */

import java.util.ArrayList;
import java.util.Optional;

import edu.tridenttech.cpt237.dooley.finalproject.model.Game;
import edu.tridenttech.cpt237.dooley.finalproject.model.Store;
import edu.tridenttech.cpt237.dooley.finalproject.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OrderWindow {

	private Stage myStage;
	private Store myStore;
	private GridPane menuPane = new GridPane();
	private ListView<Game> listView = new ListView<Game>();
	private ListView<Label> stringList = new ListView<>();
	private ArrayList<Game> games;
	private ObservableList<Game> gameList;
	private ArrayList<Label> nameLabels = new ArrayList<>();
	private ArrayList<Label> stockLabels = new ArrayList<>();
	private int id;

	public OrderWindow() {
		myStage = new Stage();
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane);
		Button addGame = new Button("Add Selected Game");
		Button finish = new Button("Checkout");
		Button cancel = new Button("Cancel Transaction");

		myStage.setWidth(450);
		myStage.setTitle("New Transaction");
		myStage.setScene(scene);

		pane.setCenter(menuPane);
		pane.setPadding(new Insets(5));
		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(5));
		pane.setBottom(buttonBox);
		buttonBox.getChildren().add(addGame);
		buttonBox.getChildren().add(finish);
		buttonBox.getChildren().add(cancel);
		buttonBox.setAlignment(Pos.CENTER);

		listView.setPrefSize(400, 400);
		listView.setOrientation(Orientation.VERTICAL);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		menuPane.setAlignment(Pos.CENTER);
		addGame.setAlignment(Pos.CENTER);
		finish.setAlignment(Pos.CENTER);
		cancel.setAlignment(Pos.CENTER);

		addGame.setOnAction(e -> {
			Optional<Transaction> tx = myStore.findPendingTransaction(id);
			if(tx != null) {
				Game selection = listView.getSelectionModel().getSelectedItem();
				if(selection != null) {
					Optional<Game> game = myStore.getGameByTitle(selection.getName(), selection.getPlatform());
					AddRentalWindow rentalWindow = new AddRentalWindow();
					rentalWindow.show(myStore, id, game.get().getName(), game.get().getPlatform());

					System.err.printf("Order ID: %d%n", id);
					System.err.printf("Game Title: %s%n", game.get().getName());
					System.err.printf("Selected Game Title: %s%n", selection.getName());
					System.err.printf("Copies %d%n", game.get().getCopies());
				}
				else {
					Alert alert = new Alert(AlertType.ERROR, "Please select a game from the menu", ButtonType.CLOSE);
					alert.showAndWait();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR, "oops", ButtonType.CLOSE);
				alert.showAndWait();
			}
		});

		finish.setOnAction(e -> {
			Optional<Transaction> tx = myStore.findPendingTransaction(id);

			if(tx.get().getRentals().isEmpty()) {
				System.err.printf("Rentals list was empty. Transaction ID: %s%n", tx.get().getId());
				Alert alert = new Alert(AlertType.ERROR, "Cannot place an empty order.", ButtonType.OK);
				alert.showAndWait();
			}
			else {
				ConfirmationWindow confWin = new ConfirmationWindow();
				confWin.show(myStore, id, myStage);
			}
		});

		cancel.setOnAction(e -> {
			myStore.cancelOrder(id);
			myStage.close();
		});
	}

	public void show(Store store, int id) {
		myStore = store;
		this.id = id;
		populateGrid(store, id);
		myStage.show();
	}

	private void populateGrid(Store store, int id) {
		myStore = store;
		this.id = id;
		games = new ArrayList<>(myStore.getAllGames());
		gameList = FXCollections.observableArrayList(games);
		listView.getItems().setAll(gameList);

		menuPane.getChildren().clear();
		nameLabels.clear();
		stockLabels.clear();
		stringList.getItems().clear();
		for(Game game: games) {
			Label nameLabel = new Label(game.getName());
			Label stockLabel = new Label(String.format("%d", game.getCopies()));
			nameLabels.add(nameLabel);
			stockLabels.add(stockLabel);
			listView.setCellFactory((Callback<ListView<Game>, ListCell<Game>>) new Callback<ListView<Game>, ListCell<Game>>(){
				@Override
				public ListCell<Game> call(ListView<Game> listView) {
					return new TitleCell();
				}
			});
		}

		listView.getItems().sort((g1, g2) -> g1.getName().compareTo(g2.getName()));
		menuPane.add(listView, 0, 0);

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

	static class TitleCell extends ListCell<Game> {
		@Override
		protected void updateItem(Game game, boolean empty) {
			super.updateItem(game, empty);
			if(game != null) {
				setText(game.getName() + " (" + game.getPlatform() + ")");
			}
		}
	}

}
