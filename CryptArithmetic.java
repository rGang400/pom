//Crypte Arthmentic

import java.util.*;

class CryptArithmetic {
    private final String word1, word2, result; // Input words
    private final Map<Character, Integer> letterToDigit; // Map of letters to digits
    private final boolean[] usedDigits; // To keep track of which digits are used
    private final List<Character> uniqueLetters; // Unique characters in the input

    // Constructor to initialize values and extract unique characters
    public CryptArithmetic(String word1, String word2, String result) {
        this.word1 = word1;
        this.word2 = word2;
        this.result = result;
        this.letterToDigit = new HashMap<>();
        this.usedDigits = new boolean[10]; // Digits 0-9
        this.uniqueLetters = new ArrayList<>();
        extractUniqueLetters(); // Populate uniqueLetters list
    }

    // Extract unique characters from all three words
    private void extractUniqueLetters() {
        Set<Character> set = new LinkedHashSet<>();
        for (char c : (word1 + word2 + result).toCharArray()) {
            set.add(c); // Avoid duplicates
        }
        uniqueLetters.addAll(set); // Preserve order
    }

    // Entry method to solve the problem using backtracking
    public boolean solve() {
        return backtrack(0); // Start from the first character
    }

    // Backtracking to assign digits to letters
    private boolean backtrack(int index) {
        // Base case: all characters assigned
        if (index == uniqueLetters.size()) return isValid();

        // Try all digits from 0 to 9
        for (int digit = 0; digit <= 9; digit++) {
            if (!usedDigits[digit]) {
                // Assign digit to character
                letterToDigit.put(uniqueLetters.get(index), digit);
                usedDigits[digit] = true;

                // Recur to assign next character
                if (backtrack(index + 1)) return true;

                // Backtrack
                letterToDigit.remove(uniqueLetters.get(index));
                usedDigits[digit] = false;
            }
        }
        return false; // No valid assignment found
    }

    // Validate if current assignment satisfies the equation
    private boolean isValid() {
        // First letter of any number cannot be 0
        if (letterToDigit.get(word1.charAt(0)) == 0 ||
            letterToDigit.get(word2.charAt(0)) == 0 ||
            letterToDigit.get(result.charAt(0)) == 0) {
            return false;
        }

        // Convert words to numeric values
        int num1 = getValue(word1);
        int num2 = getValue(word2);
        int numResult = getValue(result);

        return num1 + num2 == numResult; // Check if equation is satisfied
    }

    // Convert a word to its numeric value using current assignments
    private int getValue(String word) {
        int value = 0;
        for (char c : word.toCharArray()) {
            value = value * 10 + letterToDigit.get(c);
        }
        return value;
    }

    // Print the solution (character -> digit)
    public void printSolution() {
        for (Map.Entry<Character, Integer> entry : letterToDigit.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Main method for user input and solution output
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input first word
        System.out.println("Enter first word:");
        String word1 = scanner.nextLine().toUpperCase();

        // Input second word
        System.out.println("Enter second word:");
        String word2 = scanner.nextLine().toUpperCase();

        // Input result word
        System.out.println("Enter result word:");
        String result = scanner.nextLine().toUpperCase();

        // Solve the puzzle
        CryptArithmetic solver = new CryptArithmetic(word1, word2, result);
        if (solver.solve()) {
            solver.printSolution(); // Print result if found
        } else {
            System.out.println("No solution found."); // If not solvable
        }

        scanner.close();
    }
}



// Input - SEND , MORE , MONEY