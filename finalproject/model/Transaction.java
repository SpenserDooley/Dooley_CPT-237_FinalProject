package edu.tridenttech.cpt237.dooley.finalproject.model;

/**
 * Transaction holds a list of the rentals on an order, and sets the transaction
 * ID anytime one is started.
 * 
 * @author Spenser Dooley
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transaction {

	private ArrayList<Rental> rentals = new ArrayList<>();
	private static int nextId = 1000;
	private final int id;

	Transaction() {
		id = getNextId();
	}

	private int getNextId() {
		return ++nextId;
	}

	void addGame(Game game, int numRented, String platform) {
		if(numRented > 0 && game != null) {
			rentals.add(new Rental(game, numRented, platform));
		}
	}

	public double getTotalCost() {
		double cost = 0;
		cost += getCostByList(rentals);
		return cost;
	}

	private double getCostByList(ArrayList<Rental> list) {
		double cost = 0;
		for(Rental rental : list) {
			cost += rental.getCost();
		}
		return cost;
	}

	public int getId() {
		return id;
	}

	public List<Rental> getRentals() {
		rentals.sort((r1, r2) -> r1.getTitle().compareTo(r2.getTitle()));
		return Collections.unmodifiableList(rentals);
	}
}
