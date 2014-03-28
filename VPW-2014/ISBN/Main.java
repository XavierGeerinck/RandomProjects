import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./STDIN.txt"));
		
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		
		processTests(scanner, numberOfTests);
	}

	private static void processTests(Scanner scanner, int numberOfTests) {
		for (int i = 0; i < numberOfTests; i++) {
			String isbn = scanner.nextLine();
			_processTestCase(i + 1, isbn);
		}
	}

	private static void _processTestCase(int testNumber, String isbn) {
		long result = 0;
		ArrayList<String> found = new ArrayList<String>();
		
		// Go through string
		for (int i = 0; i <= isbn.length(); i++) {
			// Try every number
			for (int j = 0; j < 10; j++) {
				String part1 = isbn.substring(0, i);
				String part2 = isbn.substring(i, isbn.length());
				String newIsbn = part1 + j + part2;
				
//	
				if (found.contains(newIsbn)) {
					//j++;
					result -= Long.parseLong(newIsbn);
				}
				
				if (_isIsbnCorrect(newIsbn)) {
					//System.out.println("Correct: " + newIsbn);
					result += Long.parseLong(newIsbn);
					found.add(newIsbn);
					continue;
				}
			}
		}
		
		System.out.println("" + testNumber + " " + result);
	}
	
	private static boolean _isIsbnCorrect(String isbn) {
		int result = 0;
		int[] numbers = new int[isbn.length()];
		
		for (int j = 0; j < numbers.length; j++) {
			// Put isbn in different array
			int number = Integer.parseInt(isbn.substring(j, j + 1));

			if (((j + 1) % 2) == 0) {
				number *= 3;
			}
			result += number;
		}
		
		if ((result % 10) == 0) {
			return true;
		}
		
		return false;
	}
}
