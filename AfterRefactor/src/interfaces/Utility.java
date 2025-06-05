package interfaces;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import povemon.Povemon;
public interface Utility {
	Scanner scan = new Scanner(System.in);
	
	static void printBorder(int length) {
		System.out.print(" ");
		for (int i = 0; i < length; i++) {
			System.out.print("━");
		}
		System.out.println();
	}
	
	
	static void pressEnter() {
	    System.out.println("\n Press ENTER to continue...");
	    scan.nextLine();
	    clearScreen();
	}
	
	static void clearScreen() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
	}
	
	static void showError(String message) {
	    System.out.println(" " + message);
	    pressEnter();
	}
	
	static Povemon findPovemonByName(List<Povemon> list, String name) {
        for (Povemon p : list) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
