//
// Author: Caiden Sanders
// Date: February 6, 2024
// Class: CS145 Assignment 1
// Purpose: This is the entry point of the application that generates custom
//          word searches. It initializes the WordSearchGenerator class and
//          starts the user interface for creating puzzles, viewing solutions,
//          or exiting the program. It serves as the main class where the
//          program execution begins.
//
public class Main {
    public static void main(String[] args) {
        WordSearchGenerator myWordSearch = new WordSearchGenerator();
        myWordSearch.run();
    }
}
  