package Sudoku;

import java.util.Random;
public class SudokuGenerator {

    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static Random random = new Random();

    // Function to check if a number can be placed in a given position
    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % SUBGRID_SIZE, startCol = col - col % SUBGRID_SIZE;
        for (int r = 0; r < SUBGRID_SIZE; r++) {
            for (int d = 0; d < SUBGRID_SIZE; d++) {
                if (grid[r + startRow][d + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function to fill the grid with random numbers
    private static boolean fillGrid(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;

                            if (fillGrid(grid)) {
                                return true;
                            }

                            grid[row][col] = 0; // Backtrack
                        }
                    }
                    return false; // Trigger backtracking
                }
            }
        }
        return true; // Grid filled
    }

    // Function to remove numbers from the grid to create a puzzle
    private static void removeNumbers(int[][] grid, int k) {
        while (k != 0) {
            int i = random.nextInt(SIZE);
            int j = random.nextInt(SIZE);
            if (grid[i][j] != 0) {
                grid[i][j] = 0; // Remove the number
                k--;
            }
        }
    }

    // Function to generate a random Sudoku puzzle
    public static int[][] generateSudoku(int k) {
        int[][] grid = new int[SIZE][SIZE];
        fillGrid(grid);
        removeNumbers(grid, k);
        return grid;
    }

    public static void main(String[] args) {
        int k = 20; // Number of cells to remove
        int[][] sudokuPuzzle = generateSudoku(k);

        System.out.println("Generated Sudoku Puzzle:");
        for (int r = 0; r < SIZE; r++) {
            for (int d = 0; d < SIZE; d++) {
                System.out.print(sudokuPuzzle[r][d] + " ");
            }
            System.out.print("\n");
        }
    }
}
