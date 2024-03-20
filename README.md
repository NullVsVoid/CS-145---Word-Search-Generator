# Word Search Generator

A Java program that generates custom word search puzzles with solutions.

## Description

This program allows users to create new word search puzzles by inputting a list of words. Words are inserted into the grid in various directions (horizontally, vertically, and diagonally). The program also supports reading words from a file and generating a word search based on those words. Users can view the solution for the generated word search and save the puzzle or its solution to a file.

## Features

- Interactive command-line interface for generating word searches, displaying solutions, and saving puzzles/solutions to files.
- Random word insertion in multiple directions (horizontal, vertical, diagonal).
- Random letter filling for unused cells in the grid.
- File input/output operations for reading word lists and saving word searches/solutions.
- Error handling for invalid commands, file not found, and other edge cases.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your system.

### Running the Program

1. Clone the repository or download the source code.
2. Navigate to the project directory.
3. Compile the Java files: `javac Main.java WordSearchGenerator.java`
4. Run the compiled program: `java Main`

## Usage

1. Follow the on-screen instructions and available commands.
2. Enter 'g' to generate a new word search by providing words one by one (enter 'q' to stop).
3. Enter 'i' to generate a word search from a file (you'll be prompted for the file name).
4. Enter 's' to display the solution for the current word search.
5. Enter 'o' to save the current word search to a file (you'll be prompted for the file name).
6. Enter 'p' to save the solution for the current word search to a file (you'll be prompted for the file name).
7. Enter 'q' to quit the program.

## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
