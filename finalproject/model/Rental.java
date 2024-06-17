package edu.tridenttech.cpt237.dooley.finalproject.model;

/**
 * Rental objects hold information about a game in the context of a 
 * single transaction. It also calculates the total cost to rent an item.
 * 
 * @author Spenser Dooley
 */

public class Rental {

	private final static Double PRICE_EACH = 2.00;
	private Game game;
	private int numRented;
	private String platform;

	public Rental(Game game, int numRented, String platform) {
		this.game = game;
		this.numRented = numRented;
		this.platform = platform;
	}

	public String getTitle() {
		return game.getName();
	}

	public int getNumRented() {
		return numRented;
	}

	public String getPlatform() {
		return this.platform;
	}

	public double getPriceEach() {
		return PRICE_EACH;
	}

	public double getCost() {
		return PRICE_EACH * numRented;
	}

	//UNUSED FOR NOW
	protected void updateNumRented(int num) {
		if(num <= 0) {
			numRented = num;
		}
	}



}
