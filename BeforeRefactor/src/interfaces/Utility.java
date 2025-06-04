package interfaces;

import java.util.Random;
import java.util.Scanner;

public interface Utility {
	Scanner scan = new Scanner(System.in);
	
	static void printBorder(int length) {
		System.out.print(" ");
		for (int i = 0; i < length; i++) {
			System.out.print("━");
		}
		System.out.println();
	}
}
