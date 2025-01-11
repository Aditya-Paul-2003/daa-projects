package Sudoku;

public class SudokuSolverWithGenerator {

    private static final int SIZE = 9;

    // Function to print the Sudoku grid
    public static void printGrid(int[][] grid) {
        for (int r = 0; r < SIZE; r++) {
            for (int d = 0; d < SIZE; d++) {
                System.out.print(grid[r][d] + " ");
            }
            System.out.print("\n");
        }
    }

    // Function to check if a number can be placed in a given position
    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int d = 0; d < 3; d++) {
                if (grid[r + startRow][d + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Backtracking function to solve the Sudoku
    private static boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;

                            if (solveSudoku(grid)) {
                                return true;
                            }

                            grid[row][col] = 0; // Backtrack
                        }
                    }
                    return false; // Trigger backtracking
                }
            }
        }
        return true; // Solved
    }

    public static void main(String[] args) {
        int k = 20; // Number of cells to remove
        int[][] sudokuPuzzle = SudokuGenerator.generateSudoku(k);

        System.out.println("Generated Sudoku Puzzle:");
        printGrid(sudokuPuzzle);

        if (solveSudoku(sudokuPuzzle)) {
            System.out.println("Solved Sudoku Puzzle:");
            printGrid(sudokuPuzzle);
        } else {
            System.out.println("No solution exists");
        }
    }
}
