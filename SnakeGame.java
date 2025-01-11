import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static final char EMPTY = ' ';
    private static final char SNAKE = 'O';
    private static final char FOOD = '*';
    
    private char[][] board;
    private int snakeLength;
    private int[] snakeX;
    private int[] snakeY;
    private int foodX;
    private int foodY;
    private String direction;
    private boolean gameOver;

    public SnakeGame() {
        board = new char[HEIGHT][WIDTH];
        snakeX = new int[WIDTH * HEIGHT];
        snakeY = new int[WIDTH * HEIGHT];
        snakeLength = 1;
        snakeX[0] = HEIGHT / 2;
        snakeY[0] = WIDTH / 2;
        direction = "RIGHT";
        gameOver = false;
        placeFood();
    }

    private void placeFood() {
        Random rand = new Random();
        foodX = rand.nextInt(HEIGHT);
        foodY = rand.nextInt(WIDTH);
        while (isFoodOnSnake()) {
            foodX = rand.nextInt(HEIGHT);
            foodY = rand.nextInt(WIDTH);
        }
    }

    private boolean isFoodOnSnake() {
        for (int i = 0; i < snakeLength; i++) {
            if (snakeX[i] == foodX && snakeY[i] == foodY) {
                return true;
            }
        }
        return false;
    }

    public void changeDirection(String newDirection) {
        if (newDirection.equals("UP") && !direction.equals("DOWN")) {
            direction = "UP";
        } else if (newDirection.equals("DOWN") && !direction.equals("UP")) {
            direction = "DOWN";
        } else if (newDirection.equals("LEFT") && !direction.equals("RIGHT")) {
            direction = "LEFT";
        } else if (newDirection.equals("RIGHT") && !direction.equals("LEFT")) {
            direction = "RIGHT";
        }
    }

    public void move() {
        int newX = snakeX[0];
        int newY = snakeY[0];

        switch (direction) {
            case "UP":
                newX--;
                break;
            case "DOWN":
                newX++;
                break;
            case "LEFT":
                newY--;
                break;
            case "RIGHT":
                newY++;
                break;
        }

        if (newX < 0 || newX >= HEIGHT || newY < 0 || newY >= WIDTH || isSnakeCollision(newX, newY)) {
            gameOver = true;
            return;
        }

        // Move the snake
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        snakeX[0] = newX;
        snakeY[0] = newY;

        if (newX == foodX && newY == foodY) {
            snakeLength++;
            placeFood();
        }
    }

    private boolean isSnakeCollision(int x, int y) {
        for (int i = 0; i < snakeLength; i++) {
            if (snakeX[i] == x && snakeY[i] == y) {
                return true;
            }
        }
        return false;
    }

    public void drawBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = EMPTY;
            }
        }

        for (int i = 0; i < snakeLength; i++) {
            board[snakeX[i]][snakeY[i]] = SNAKE;
        }
        board[foodX][foodY] = FOOD;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("Score: " + (snakeLength - 1));
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SnakeGame game = new SnakeGame();

         while (!game.isGameOver()) {
            game.drawBoard();
            System.out.print("Enter direction (UP, DOWN, LEFT, RIGHT): ");
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "UP":
                case "DOWN":
                case "LEFT":
                case "RIGHT":
                    game.changeDirection(input);
                    break;
                default:
                    System.out.println("Invalid direction! Please enter UP, DOWN, LEFT, or RIGHT.");
                    continue; // Skip the move if the input is invalid
            }

            game.move();
        }

        System.out.println("Game Over! Your final score is: " + (game.snakeLength - 1));
        scanner.close();
    }
}