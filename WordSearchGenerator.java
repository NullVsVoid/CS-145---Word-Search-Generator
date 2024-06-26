//
// Author: Caiden Sanders
// Date: February 6, 2024
// Class: CS145 Assignment 1
// Purpose: This class generates custom word searches with solutions. Users can
//          create new puzzles, view solutions, or exit. It supports word
//          insertion in multiple directions, random letter filling, and I/O
//          operations for word lists and puzzles outputs.
//
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class WordSearchGenerator {
  private Random RANDOM_GENERATOR = new Random();
  private Scanner INPUT_SCANNER = new Scanner(System.in);
  private char[][] wordSearch;
  private char[][] solvedWordSearch;

  /**
   * Prints the available commands to the console. This includes options to
   * generate a new word search, display the solution for the current word
   * search, and quit the program. It prompts the user to enter a command.
   */
  private static void printCommands() {
    System.out.println("'g' - generate a new word search");
    System.out.println("'i' - generate word search from file");
    System.out.println("'s' - get solution to word search");
    System.out.println("'o' - output word search to file");
    System.out.println("'p' - output word search solution to file");
    System.out.println("'q' - quit the program");
    System.out.print("Please enter a command: ");
  }

  /**
   * Runs the Word Search Generator program, handling user input to generate new
   * word searches, solve them, read words from a file, and save word searches or
   * their solutions to files. The method supports commands for generating word
   * searches ('g'), reading words from a file ('i'), displaying the solution
   * ('s'),
   * saving the current word search to a file ('o'), saving the solution to a file
   * ('p'), and quitting the program ('q').
   */
  public void run() {
    System.out.println("Welcome to the Word Search Generator!");
    System.out.println("Here are the allowed commands: ");
    printCommands();
    String command = "";
    while (!command.equals("q")) {
      command = INPUT_SCANNER.nextLine().toLowerCase();
      if (command.length() < 1)
        continue;
      switch (command.charAt(0)) {
        case 'g':
          ArrayList<String> words = gatherWords();
          generateWordSearch(words);
          displayUnsolved();
          break;
        case 'i':
          System.out.print("\nWhat is the file name? ");
          String fileName = INPUT_SCANNER.nextLine();
          try {
            words = readWordsFromFile(fileName);
            generateWordSearch(words);
            displayUnsolved();
          } catch (FileNotFoundException e) {
            System.out.println("\nFile not found."
                 + " Try another command.\n");
          }
          break;
        case 's':
          if (wordSearch == null) {
            System.out.println("\nNo word search to solve."
                 + " Try another command.\n");
          } else {
            displaySolved();
          }
          break;
        case 'o':
        case 'p':
          if (wordSearch == null) {
            System.out.println("\nNo word search to output." 
                + " Try another command.\n");
          } else {
            System.out.print("\nWhat's the file name you would like to use? ");
            fileName = INPUT_SCANNER.nextLine();
            try {
              if (command.equals("o")) {
                writeWordSearchToFile(fileName, wordSearch);
              } else { // command is "p", outputting solvedWordSearch
                writeWordSearchToFile(fileName, solvedWordSearch);
              }
              System.out.println("\nWord search " 
                + (command.equals("o") ? "" : "solution ") 
                + "printed to file.\n");
            } catch (FileNotFoundException e) {
              System.out.println("\nFile could not be created."
                 + " Try another command.\n");
            }
          }
          break;
        case 'q':
          System.out.println("\nQuitting the program. Goodbye!\n");
          break;
        default:
          System.out.println("\nInvalid command. Please try again.\n");
          break;
      }
      if (!command.equals("q"))
        printCommands();
    }
  }

  /**
   * Removes all non-alphabetical characters from a string and converts it to
   * lowercase. This method will be used to preprocess the words for consistency
   * in creating word searches.
   *
   * @param toSanitize The string to be sanitized.
   * @return A sanitized, lowercase version of the input string.
   */
  private String sanitize(String toSanitize) {
    return toSanitize.replaceAll("[^a-zA-Z]", "").toLowerCase();
  }

  /**
   * Collects words inputted by the user until "q" is entered. Each word is
   * sanitized to remove non-alphabetical characters and converted to
   * lowercase before being added to the list.
   *
   * This method prompts the user to input words one by one, adding each to
   * an ArrayList after sanitization. The collection process terminates when
   * the user inputs "q".
   *
   * @return An ArrayList containing the sanitized, lowercase inputs by the user.
   */
  private ArrayList<String> gatherWords() {
    System.out.print("\n");
    System.out.println("Please enter words one by one.");
    System.out.println("Enter 'q' to stop.");
    System.out.print("\n");
    ArrayList<String> words = new ArrayList<String>();
    String word = INPUT_SCANNER.nextLine();
    while (INPUT_SCANNER.hasNextLine()) {
      words.add(sanitize(word));
      word = INPUT_SCANNER.nextLine();
      if (word.equals("q")) {
        System.out.print("\n");
        break;
      }
    }
    return words;
  }

  /**
   * Prints the word search grid to the console, with each character spaced for
   * readability.
   *
   * @param search The word search grid to display, represented as a 2D char
   *               array.
   */
  private static void display(char[][] search) {
    for (int i = 0; i < search.length; i++) {
      for (int j = 0; j < search[i].length; j++) {
        System.out.print(search[i][j] + " ");
      }
      System.out.print("\n");
    }
  }

  /**
   * Prints the unsolved version of the word search, for the user to solve.
   */
  public void displayUnsolved() {
    display(this.wordSearch);
    System.out.print("\n");
  }

  /**
   * Prints the solved version of the word search, for the user to see the
   * spot each word was.
   */
  public void displaySolved() {
    System.out.print("\n");
    display(this.solvedWordSearch);
    System.out.print("\n");
  }

  /**
   * Fills the word search grid with random lowercase letters. This method
   * iteratives over each cell of the provided two-dimensional char array
   * and assigns a random letter (a-z) to it.
   *
   * @param wordSearch The word search grid to be randomized. It is modified
   *                   in place, with each element being replaced by a random
   *                   lowercase letter.
   */
  private void randomizeWordSearch(char[][] wordSearch) {
    for (int i = 0; i < wordSearch.length; i++) {
      for (int j = 0; j < wordSearch[i].length; j++) {
        wordSearch[i][j] = (char) (RANDOM_GENERATOR.nextInt(26) + 'a');
      }
    }
  }

  /**
   * Fills the entire grid with a specified character, 'X' in the current only
   * use case, to mark the cells as initially unused or to reset the grid.
   *
   * @param grid The two-dimensional char array to be filled.
   */
  private static void resetGridWithLetter(char[][] wordSearch, char letter) {
    for (int i = 0; i < wordSearch.length; i++) {
      for (int j = 0; j < wordSearch[i].length; j++) {
        wordSearch[i][j] = letter;
      }
    }
  }

  /**
   * Attempts to insert a word into a specified row of the word search grid.
   * The word can be inserted forwards or backwards on a random decision. If
   * the word cannot be placed due to overlapping with another word that
   * doesn't contain matching characters, the insertion fails.
   *
   * @param wordSearch The word search grid where the word is to be inserted.
   * @param row        The row index where the word should be inserted.
   * @param word       The word to insert into the word search.
   * @return True if the word was successfully inserted, false otherwise.
   */
  private Boolean insertRow(char[][] wordSearch, int row, String word) {
    // Is the word going to be forwards or backwards?
    Boolean forwards = RANDOM_GENERATOR.nextBoolean();
    // Ensure there's enough space for the word to fit in the row.
    if (word.length() > wordSearch[row].length)
      return false;
    // Get a random starting point that will fit the whole word.
    int start = RANDOM_GENERATOR.nextInt(wordSearch[row].length - word.length() +
        1);
    // Check if this is a valid insertion spot.
    for (int i = 0; i < word.length(); i++) {
      char currentChar = solvedWordSearch[row][start + i];
      int index = forwards ? i : word.length() - i - 1;
      if (currentChar != 'X' && currentChar != word.charAt(index)) {
        return false; // Invalid spot due to overlap mismatch.
      }
    }
    // Insert the word into the word search.
    for (int i = 0; i < word.length(); i++) {
      int index = forwards ? i : word.length() - i - 1;
      char charToInsert = word.charAt(index);
      wordSearch[row][start + i] = charToInsert;
      // Mark the spot as filled.
      solvedWordSearch[row][start + i] = charToInsert;
    }
    return true;
  }

  /**
   * Attempts to insert a word into a specified column of the word search grid.
   * The word is inserted vertically, and teh direction (top-to-bottom or
   * bottom-to-top) is random. If the word cannot be placed due to overlapping,
   * the insertion fails.
   *
   * @param wordSearch The word search grid where the word is to be inserted.
   * @param col        The column index where the word should be inserted.
   * @param word       The word to insert into the word search grid.
   * @return True if the word was successfully inserted, false otherwise.
   */
  private Boolean insertColumn(char[][] wordSearch, int col, String word) {
    // Is the word going to be forwards or backwards?
    Boolean forwards = RANDOM_GENERATOR.nextBoolean();
    // Ensure there's enough space for the word to fit in the column.
    if (word.length() > wordSearch.length)
      return false;
    // Get a random starting point that will fit the whole word.
    int start = RANDOM_GENERATOR.nextInt(wordSearch.length - word.length() + 1);
    // Check if this is a valid insertion spot.
    for (int i = 0; i < word.length(); i++) {
      char currentChar = solvedWordSearch[start + i][col];
      int index = forwards ? i : word.length() - i - 1;
      if (currentChar != 'X' && currentChar != word.charAt(index)) {
        return false;
      }
    }
    // Insert the word into the word search.
    for (int i = 0; i < word.length(); i++) {
      int index = forwards ? i : word.length() - i - 1;
      char charToInsert = word.charAt(index);
      wordSearch[start + i][col] = charToInsert;
      // Mark the spot as filled.
      solvedWordSearch[start + i][col] = charToInsert;
    }
    return true;
  }

  /**
   * Attempts to insert a word diagonally into the word search grid. The
   * direction of insertion (top-left to bottom-right or vice versa) is
   * determined randomly. The method checks for conflicts with already placed
   * characters to ensure the word fits without overlapping incorrectly.
   *
   * @param wordSearch The word search grid where the word is to be inserted.
   * @param word       The word to insert into the grid diagonally.
   * @return True if the word was successfully inserted, false otherwise.
   */
  private Boolean insertDiagonal(char[][] wordSearch, String word) {
    // Is the word going to be forwards or backwards?
    Boolean forwards = RANDOM_GENERATOR.nextBoolean();
    // Get a random starting point that will fit the whole word.
    int start_row = RANDOM_GENERATOR.nextInt(wordSearch.length - word.length());
    int start_col = RANDOM_GENERATOR.nextInt(wordSearch[start_row].length -
        word.length());
    // Check if this is a valid insertion spot.
    for (int i = 0; i < word.length(); i++) {
      char currentChar = solvedWordSearch[start_row + i][start_col + i];
      int index = forwards ? i : word.length() - i - 1;
      if (currentChar != 'X' && currentChar != word.charAt(index)) {
        return false;
      }
    }
    // Insert the word into the word search.
    for (int i = 0; i < word.length(); i++) {
      int index = forwards ? i : word.length() - i - 1;
      char charToInsert = word.charAt(index);
      wordSearch[start_row + i][start_col + i] = charToInsert;
      // Mark the spot as filled.
      solvedWordSearch[start_row + i][start_col + i] = charToInsert;
    }
    return true;
  }

  /**
   * Attempts to fillt he word search grid with the provided list of words.
   * Words are inserted using a mixture of row, column, and diagonal placements.
   * The method retries different placements if the initial attempt to insert a
   * word fails, ensuring all words are eventually placed.
   *
   * @param words The list of words to be inserted into the word search grid.
   */
  private void fillWordSearch(ArrayList<String> words) {
    for (String word : words) {
      boolean success = false;
      int attempts = 0; // Limit attempts to prevent infinite loops.
      while (!success && attempts < 100) {
        // 0 for row, 1 for column, and 2 for diagonal.
        int direction = RANDOM_GENERATOR.nextInt(3);
        switch (direction) {
          case 0:
            // Attempt to insert the word in a random row.
            int randomRow = RANDOM_GENERATOR.nextInt(wordSearch.length);
            success = insertRow(wordSearch, randomRow, word);
            break;
          case 1:
            // Attempt to insert the word in a random column.
            int randomCol = RANDOM_GENERATOR.nextInt(wordSearch[0].length);
            success = insertColumn(wordSearch, randomCol, word);
            break;
          case 2:
            // Attempt to insert the word diagonally.
            success = insertDiagonal(wordSearch, word);
            break;
          default:
            // Cautionary, but this should never happen.
            break;
        }
      }
    }
  }

  /**
   * Initializes and fills a word search grid based on a list of words. The grid
   * size is determined by the words inputted, ensuring all words fit. The method
   * populates the grid with random letters, then inserts the words in various
   * directions. The solved grid is prepared for tracking correct placements.
   *
   * @param words the list of words to insert into the word search.
   */
  private void generateWordSearch(ArrayList<String> words) {
    // Generate the size of the word search based on the longest word
    // as well as the number of words.
    int wordSearchSize = 0;
    for (String word : words) {
      if (word.length() > wordSearchSize) {
        wordSearchSize = word.length();
      }
    }
    // Add additional space to the grid size.
    wordSearchSize += 2;
    // Initialize the word search grid with the calculated size.
    this.wordSearch = new char[wordSearchSize][wordSearchSize];
    this.solvedWordSearch = new char[wordSearchSize][wordSearchSize];
    // Fill the word search grid with random letters.
    randomizeWordSearch(this.wordSearch);
    // Fill the solved grid with 'X' to mark unfilled spots.
    resetGridWithLetter(this.solvedWordSearch, 'X');
    fillWordSearch(words);
  }

  //
  // File Input/Output Methods
  //
  /**
   * Reads words from a file, each word on a new line, and stores them
   * in an ArrayList.
   *
   * @param filename The path of the file from which to read words.
   * @return An ArrayList containing the words read from the file.
   * @throws FileNotFoundException if the file with the given filename
   *                               does not exist.
   */
  private static ArrayList<String> readWordsFromFile(String filename)
      throws FileNotFoundException {
    ArrayList<String> words = new ArrayList<String>();
    File file = new File(filename);
    Scanner fileScanner = null;
    try {
      fileScanner = new Scanner(file);
      while (fileScanner.hasNextLine()) {
        String word = fileScanner.nextLine();
        words.add(word);
      }
    } finally {
      if (fileScanner != null) {
        fileScanner.close();
      }
    }
    return words;
  }

  /**
   * Writes a two-dimensional character array (word search) to a file. Each
   * row of the array is written on a new line with characters separated by
   * spaces.
   *
   * @param filename   The path of the file to which the word search will be
   *                   written.
   * @param wordSearch The word search grid to write to the file.
   * @throws FileNotFoundException if the file with the given filename cannot
   *                               be created.
   */
  private static void writeWordSearchToFile(String filename, char[][] wordSearch)
      throws FileNotFoundException {
    PrintStream out = new PrintStream(new File(filename));
    for (int i = 0; i < wordSearch.length; i++) {
      for (int j = 0; j < wordSearch[i].length; j++) {
        out.print(wordSearch[i][j] + " ");
      }
      out.println();
    }
  }
}