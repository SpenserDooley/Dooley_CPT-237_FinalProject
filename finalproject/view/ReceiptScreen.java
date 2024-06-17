package edu.tridenttech.cpt237.dooley.finalproject.view;

import java.util.List;

import edu.tridenttech.cpt237.dooley.finalproject.model.Rental;
import edu.tridenttech.cpt237.dooley.finalproject.model.Transaction;

public class ReceiptScreen {

	public void displayReceipt(Transaction tx) {
		List<Rental> rentalList = tx.getRentals();
		System.out.printf("%-20s%5s%8s%8s%n", "Item", "Cnt", "Price", "Cost");
		for (Rental rental : rentalList) {
			System.out.printf("%-20s%5d%8.2f%8.2f%n",
					rental.getTitle(), rental.getNumRented(), rental.getPriceEach(), rental.getCost());
		}
		System.out.printf("%-33s%8.2f%n", "Total", tx.getTotalCost());
	}
	
}
