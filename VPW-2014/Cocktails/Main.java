import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./STDIN.txt"));
		
		int numberOfTests = scanner.nextInt();
		
		processTests(scanner, numberOfTests);
	}

	private static void processTests(Scanner scanner, int numberOfTests) {
		for (int i = 0; i < numberOfTests; i++) {
			// Aantal barmannen
			int aantalBarmannen = scanner.nextInt();
			scanner.nextLine(); // Go to next line
			
			// De cocktails die elke barman kan maken
			String[] barmanRules = new String[aantalBarmannen];
			
			for (int j = 0; j < aantalBarmannen; j++) {
				barmanRules[j] = scanner.nextLine();
			}
			
			// Aantal bestellingen
			int aantalBestellingen = scanner.nextInt();
			scanner.nextLine(); // Go to next line
			
			// Alle bestellingen
			String[] bestellingen = new String[aantalBestellingen];
			
			for (int j = 0; j < aantalBestellingen; j++) {
				bestellingen[j] = scanner.nextLine();
			}
			
			// Process the test case
			_processTestCase(aantalBarmannen, barmanRules, aantalBestellingen, bestellingen);
		}
	}

	private static void _processTestCase(int aantalBarmannen, String[] barmanRules, int aantalBestellingen, String[] bestellingen) {
		// Get the recipe combination count + Max recipes that a barman knows
		int barmanCapacity = _calculateBarmancapacity(aantalBarmannen, barmanRules);
		int maxRecipes = _calculateMaxBarmanRecipes(aantalBarmannen, barmanRules);
		String[] possibleCapacity = new String[barmanCapacity];
		
		// Fill our possible recipe combinations array
		possibleCapacity = _calculateCombinations(aantalBarmannen, barmanRules, maxRecipes, barmanCapacity);	
		
		// Sort it alphabetically
		for (int i = 0; i < possibleCapacity.length; i++) {
			// Sort each word it's letters
			char[] chars = possibleCapacity[i].toCharArray();
			Arrays.sort(chars);
			possibleCapacity[i] = new String(chars);
		}

		// check if the list contains the ordered recipes
		for (int i = 0; i < aantalBestellingen; i++) {
			// If our combination array has the order, then print it as "mogelijk"
			if (Arrays.asList(possibleCapacity).contains(bestellingen[i])) {
				System.out.printf("%s %s\n", bestellingen[i], "mogelijk");
			} else {
				System.out.printf("%s %s\n", bestellingen[i], "onmogelijk");
			}
		}
	}
	
	private static String[] _toStringArray(String s) {
		String[] array = s.split("(?!^)");
		return array;
	}
	

	/**
	 * Calculate the maximum recipe count that a barman knows
	 * @param aantalBarmannen
	 * @param barmanRules
	 * @return
	 */
	private static int _calculateMaxBarmanRecipes(int aantalBarmannen, String[] barmanRules) {
		int capacity = 0;
		
		for (int i = 0; i < aantalBarmannen; i++) {
			if (capacity < barmanRules[i].length()) {
				capacity = barmanRules[i].length();
			}
		}
		
		return capacity;
	}
	
	/**
	 * Calculate all recipes that we can process within 5 minutes
	 * @param aantalBarmannen
	 * @param barmanRules
	 * @return
	 */
	private static int _calculateBarmancapacity(int aantalBarmannen, String[] barmanRules) {
		int capacity = 0;
		
		for (int i = 0; i < aantalBarmannen; i++) {
			capacity += barmanRules[i].length();
		}
		
		return capacity;
	}
	
	/*
	 * CARTESIAN PRODUCT CALCULATION
	 */
	/**
	 * Calculate cartesian product of 2 lists
	 * @param list1
	 * @param list2
	 * @return
	 */
	private static String[] _calculateCartesianProduct(String[] list1, String[] list2) {
		int resultLength = (list1.length) * (list2.length);
		String[] result = new String[resultLength];
		int resultIndex = 0;
		
		for (int i = 0; i < list1.length; i++) {
			for (int j = 0; j < list2.length; j++) {
				if (list1[i] != null && list2[j] != null && !list1[i].equals("") && !list2[j].equals("")) {
					result[resultIndex] = list1[i] + list2[j];
					resultIndex++;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Calculate cartesian product of the different lists
	 * @param aantalBarmannen
	 * @param barmanRules
	 * @param maxRecipes
	 * @param barmanCapacity
	 * @return
	 */
	private static String[] _calculateCombinations(int aantalBarmannen, String[] barmanRules, int maxRecipes, int barmanCapacity) {
		String[] tempResult = new String[barmanCapacity];
		String[] result = new String[barmanCapacity];
		
		for (int i = 0; i < aantalBarmannen; i++) {
			if (i == 0) {
				tempResult = _calculateCartesianProduct(_toStringArray(barmanRules[i]), _toStringArray(barmanRules[i + 1]));
			} else {
				if ((i + 1) < aantalBarmannen) {
					tempResult = _calculateCartesianProduct(tempResult, _toStringArray(barmanRules[i + 1]));
				} else {
					result = tempResult;
				}
			}
		}
		
		return result;
	}
}
