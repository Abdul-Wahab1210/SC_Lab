package Task2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class PermutationGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Get user input for the string
            System.out.print("Enter a string: ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.println("The input string is empty.");
                return;
            }

            // Get user choice for including duplicates
            System.out.print("Include duplicate permutations? (yes/no): ");
            String includeDuplicates = scanner.nextLine();

            List<String> permutations;

            // Choose method based on user input
            if (includeDuplicates.equalsIgnoreCase("yes")) {
                permutations = generatePermutations(input);
            } else {
                permutations = generateUniquePermutations(input);
            }

            // Print the generated permutations
            System.out.println("Generated Permutations:");
            for (String perm : permutations) {
                System.out.println(perm);
            }

            // Compare with iterative approach (optional)
            System.out.println("\nIterative Permutations:");
            List<String> iterativePermutations = generatePermutationsIteratively(input);
            for (String perm : iterativePermutations) {
                System.out.println(perm);
            }

        } finally {
            scanner.close();
        }
    }

    /**
     * Recursive function to generate all permutations of a string.
     * 
     * @param str The input string.
     * @return A list of all permutations.
     */
    public static List<String> generatePermutations(String str) {
        List<String> results = new ArrayList<>();
        generatePermutationsHelper(str.toCharArray(), 0, results);
        return results;
    }

    private static void generatePermutationsHelper(char[] chars, int index, List<String> results) {
        if (index == chars.length - 1) {
            results.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            generatePermutationsHelper(chars, index + 1, results);
            swap(chars, index, i); // backtrack
        }
    }

    /**
     * Generates unique permutations of a string to avoid duplicates.
     * 
     * @param str The input string.
     * @return A list of unique permutations.
     */
    public static List<String> generateUniquePermutations(String str) {
        HashSet<String> results = new HashSet<>();
        generateUniquePermutationsHelper(str.toCharArray(), 0, results);
        return new ArrayList<>(results);
    }

    private static void generateUniquePermutationsHelper(char[] chars, int index, HashSet<String> results) {
        if (index == chars.length - 1) {
            results.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            generateUniquePermutationsHelper(chars, index + 1, results);
            swap(chars, index, i); // backtrack
        }
    }

    /**
     * Iterative method to generate all permutations of a string.
     * 
     * @param str The input string.
     * @return A list of permutations.
     */
    public static List<String> generatePermutationsIteratively(String str) {
        List<String> permutations = new ArrayList<>();
        permutations.add(String.valueOf(str.charAt(0)));

        for (int i = 1; i < str.length(); i++) {
            List<String> currentList = new ArrayList<>();
            for (String perm : permutations) {
                for (int j = 0; j <= perm.length(); j++) {
                    currentList.add(perm.substring(0, j) + str.charAt(i) + perm.substring(j));
                }
            }
            permutations = currentList;
        }

        return permutations;
    }

    /**
     * Utility function to swap characters in an array.
     */
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /**
     * Analyzes the time complexity of the recursive algorithm.
     * Time complexity: O(n * n!) because we generate all permutations.
     * 
     * @param strLength Length of the input string.
     */
    public static void analyzeTimeComplexity(int strLength) {
        System.out.println("For a string of length " + strLength + ":");
        System.out.println("Recursive solution time complexity: O(" + strLength + " * " + strLength + "!)");
        System.out.println("Iterative solution may have slightly better performance but is still factorial.");
    }
}
