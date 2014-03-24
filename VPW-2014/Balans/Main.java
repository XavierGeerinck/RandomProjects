import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./STDIN.txt"));
		
		int numberOfTests = scanner.nextInt();
		
		processTests(scanner, numberOfTests);
	}

	private static void processTests(Scanner scanner, int numberOfTests) {
		for (int i = 0; i < numberOfTests; i++) {
			int numberOfWeights = scanner.nextInt();
			scanner.nextLine();
			String[] weightsString = scanner.nextLine().split(" ");
			int testWeight = scanner.nextInt();

			int[] weights = _stringArrayToIntArray(numberOfWeights, weightsString);
			
			// Process the test case
			_processTestCase(numberOfWeights, weights, testWeight);
		}
	}

	private static void _processTestCase(int numberOfWeights, int[] weights, int testWeight) {
		String result = "NEEN";
		
		for (int i = 0; i < numberOfWeights; i++) {
			for (int j = 0; j < numberOfWeights; j++) {
				if ((weights[i] != weights[j]) && (weights[i] + weights[j]) == testWeight) {
					result = "JA";
					
					// If result found, skip to the next one
					continue;
				}
			}
		}
		
		System.out.printf("%d %s\n", testWeight, result);
	}

	private static int[] _stringArrayToIntArray(int numberOfWeights, String[] weightsString) {
		int[] temp = new int[numberOfWeights];
		
		for (int i = 0; i < numberOfWeights; i++) {
			temp[i] = Integer.parseInt(weightsString[i]);
		}
		
		return temp;
	}
}
