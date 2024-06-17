package edu.tridenttech.cpt237.dooley.finalproject.model;

/**
 * Game holds information about each game object loaded in by the
 * Store. One of its constructors uses a switch statement to assign
 * its platform as a String over a char. This made passing information
 * around and displaying text require fewer lines of code overall.
 * 
 * @author Spenser Dooley
 */

public class Game {

	private final static String[] PLATFORMS = {"PS4", "Xbox", "Nintendo"};
	private final String name;
	private int copies;
	private String platform;

	Game(int copies, char platform, String title) {
		switch(platform) {
		case 'P':
			this.platform = PLATFORMS[0];
			break;
		case 'X':
			this.platform = PLATFORMS[1];
			break;
		case 'N':
			this.platform = PLATFORMS[2];
			break;
		}
		this.name = title;
		this.copies = copies;
	}

	Game(Game game) {
		this.platform = game.platform;
		this.name = game.name;
		this.copies = game.copies;
	}

	public String getName() {
		return name;
	}

	public int getCopies() {
		return copies;
	}

	public String getPlatform() {
		return platform;
	}

	public void updateStock() {
		copies--;
	}

}
