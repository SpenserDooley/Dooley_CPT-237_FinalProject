package edu.tridenttech.cpt237.dooley.finalproject.model;

/**
 * Store loads data from a file into arrays that contain games, transactions,
 * and information about both. Store handles validation and searching as other
 * classes in the program pull from it to grab information constantly.
 * 
 * @author Spenser Dooley
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Store {

	private ArrayList<Game> games = new ArrayList<>();
	private static final char[] platforms = {'P', 'N', 'X'};
	private ArrayList<Transaction> completedTransactions = new ArrayList<>();
	private ArrayList<Transaction> pendingTransactions = new ArrayList<>();

	public Store(String configPath) throws FileNotFoundException {
		Scanner input = new Scanner(new File(configPath));
		int lineNum = 1;
		while(input.hasNext()) {
			try {
				String line = input.nextLine();
				String[] fields = line.split(",");
				int copies = Integer.parseInt(fields[0]);
				char platform = fields[1].charAt(0);
				String title = fields[2];
				Game game;
				if(platform == platforms[0] || platform == platforms[1] || platform == platforms[2]) {
					game = new Game(copies, platform, title);
					games.add(game);
				} else {
					System.err.printf("Unknown format: %s%n", platform);
				}
			} catch(Exception e) {
				System.err.printf("Error on line %d: %s%n", lineNum, e.getMessage());
			}
			lineNum++;
		}
		input.close();
	}

	public List<Transaction> getTransactionList(){
		return Collections.unmodifiableList(completedTransactions);
	}

	public void addLineItem(int orderId, String gameTitle, int numRented, String platform) {
		Optional<Transaction> transaction = findPendingTransaction(orderId);
		System.err.printf("Transaction ID, isPresent: %d %s%n", orderId, transaction.isPresent());
		try {
			if(transaction.isPresent()) {
				Optional<Game> game = getGameByTitle(gameTitle, platform);
				System.err.printf("Game info: %s %s%n", game.get().getName(), game.get().getPlatform());
				if(game.isPresent() && game.get().getCopies() >= 1) {
					transaction.get().addGame(game.get(), numRented, game.get().getPlatform());
				}
				else {
					System.err.printf("The game is not present OR the number of copies is less than one. %s %d %c%n", game.isPresent(), orderId, platform);
				}
			}
			else {
				System.err.printf("The transaction ID couldn't be found (in pending transactions) %s%n", transaction.isPresent());
			}

		}catch(Exception ex) {
			System.err.printf("An error occurred when trying to locate the pending transaction %s%n", ex.getMessage());
		}

	}

	private Optional<Game> findGameByName(String gameTitle, String platform){
		Optional<Game> foundGame = Optional.empty();
		for(Game game : games) {
			if(gameTitle.equalsIgnoreCase(game.getName()) && platform.equalsIgnoreCase(game.getPlatform())) {
				foundGame = Optional.of(game);
				break;
			}
		}
		return foundGame;
	}

	public char[] getPlatforms(){
		return platforms;
	}

	public List<Game> getAllGames(){
		return Collections.unmodifiableList(games);
	}

	public List<Game> getGamesByPlatform(char platform){
		List<Game> games = new ArrayList<>();

		for(Game game : games) {
			if(platform == game.getPlatform().charAt(0)) {
				games.add(game);
				//System.err.printf("Platform, platform charAt(0), passed char platform. %s %c %c%n", game.getPlatform(), game.getPlatform().charAt(0), platform);
			}
		}
		return games;
	}

	public Optional<Game> getGameByTitle(String gameTitle, String platform) {
		return findGameByName(gameTitle, platform);
	}

	public int startTransaction() {
		Transaction transaction = new Transaction();
		pendingTransactions.add(transaction);
		return transaction.getId();
	}

	public void placeOrder(int id) {
		Optional<Transaction> transaction = findPendingTransaction(id);
		if(transaction.isPresent()) {
			completedTransactions.add(transaction.get());
		}
	}

	public void cancelOrder(int id) {
		Optional<Transaction> transaction = findPendingTransaction(id);
		if(transaction.isPresent()) {
			pendingTransactions.remove(transaction.get());
		}
	}

	private Optional<Transaction> findTransactionById(ArrayList<Transaction> list, int id) {
		return list.stream()
				.filter(e -> e.getId() == id)
				.findAny();
	}

	public Optional<Transaction> findPendingTransaction(int id){
		return findTransactionById(pendingTransactions, id);
	}

	public Optional<Transaction> findCompletedTransaction(int id){
		return findTransactionById(completedTransactions, id);
	}

	public void displayTransactions() {
		for(Transaction tx : completedTransactions) {
			System.out.printf("Order # %d%n", tx.getId());
			for(Rental rental : tx.getRentals()) {
				System.out.println(rental.getTitle() + rental.getPlatform() + rental.getNumRented() + rental.getCost());
			}
		}
	}

}
