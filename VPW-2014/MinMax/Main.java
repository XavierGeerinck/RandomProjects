import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.System;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throw FileNotFoundException {
        Scanner scanner = new Scanner(new File("./STDIN.txt"));
        int numberOfLists = scanner.nextInt();

        // Get amount of lists
        for (int i = 0; i < numberOfLists; i++) {
            // Get amount of numbers in that list
            int amountOfNumbersInList = scanner.nextInt();
            ArrayList<Integer> numbers = new ArrayList<Integer>();

            // Add numbers to array
            for (int j = 0; j < amountOfNumbersInList; j++) {
                numbers.add(scanner.nextInt());
            }

            // Sort, from smaller to higher
            Collections.sort(numbers, new Comparator<Integer>() {
                @Override
                public int compare(Integer number1, Integer number2) {
                    return number1.compareTo(number2);
                }
            });

            // Print
            System.out.print("" + (i + 1)); // Guidance number
            System.out.print(" " + numbers.get(0));  // Print Min
            System.out.print(" " + numbers.get(numbers.size() - 1));  // Print Max

            System.out.printf("\n");
        }
    }
}