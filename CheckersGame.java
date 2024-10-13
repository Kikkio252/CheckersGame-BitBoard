import java.util.Random;
import java.util.Scanner;

public class CheckersGame {
    private int[] board;
    private static final int PLAYER_PIECE = 1;   // Represents user pieces as bits
    private static final int COMPUTER_PIECE = 2; // Represents computer pieces as bits

    public CheckersGame() {
        board = new int[8]; // 8 rows for the checkers board
        initializeBoard();
    }

    public void initializeBoard() {
        // Set up initial positions for user and computer pieces
        // Rows 0, 1, 2 for computer pieces
        board[0] = 0b01010101; // Row 0: C . C . C . C .
        board[1] = 0b01010101; // Row 1: C . C . C . C .
        board[2] = 0b01010101; // Row 2: C . C . C . C .

        // Rows 3 and 4 are empty
        board[3] = 0b00000000; // Row 3: . . . . . . . .
        board[4] = 0b00000000; // Row 4: . . . . . . . .

        // Rows 5, 6, 7 for user pieces
        board[5] = 0b10101010; // Row 5: U . U . U . U .
        board[6] = 0b10101010; // Row 6: U . U . U . U .
        board[7] = 0b10101010; // Row 7: U . U . U . U .
    }

    public void printBoard() {
        System.out.print("  A B C D E F G H\n");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                // Check if the square is dark or light
                if ((i + j) % 2 == 0) {
                    // Light square
                    System.out.print("  "); // Empty space for light square
                } else {
                    // Dark square
                    if ((board[i] & (1 << (7 - j))) != 0) {
                        System.out.print((i < 3 ? "C " : "U ")); // 'C' for computer, 'U' for user
                    } else {
                        System.out.print(". "); // Empty square for dark
                    }
                }
            }
            System.out.println();
        }
    }

    public boolean isLegalMove(int startX, int startY, int endX, int endY) {
        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 ||
                endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false; // Out of bounds
        }

        // Check if starting square has a piece and the ending square is empty
        if ((board[startX] & (1 << (7 - startY))) == 0 ||
                (board[endX] & (1 << (7 - endY))) != 0) {
            return false; // No piece to move or destination is occupied
        }

        // Normal move
        if (Math.abs(endX - startX) == 1 && Math.abs(endY - startY) == 1) {
            return true; // Regular move
        }

        // Capture move
        if (Math.abs(endX - startX) == 2 && Math.abs(endY - startY) == 2) {
            int midX = (startX + endX) / 2;
            int midY = (startY + endY) / 2;
            // Check if there's an opponent's piece in the middle
            if ((board[midX] & (1 << (7 - midY))) != 0 &&
                    ((board[startX] & (1 << (7 - startY))) != 0)) { // Check if it's the user's piece
                return true; // Valid capture
            }
        }

        return false; // Not a legal move
    }

    public void movePiece(int startX, int startY, int endX, int endY) {
        // Handle capture
        if (Math.abs(endX - startX) == 2 && Math.abs(endY - startY) == 2) {
            int midX = (startX + endX) / 2;
            int midY = (startY + endY) / 2;
            board[midX] &= ~(1 << (7 - midY)); // Capture the opponent's piece
        }

        // Move the piece
        board[endX] |= (1 << (7 - endY)); // Set the destination square
        board[startX] &= ~(1 << (7 - startY)); // Clear the starting square
    }

    public boolean computerMove() {
        Random random = new Random();
        // Check all possible moves for the computer
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((board[i] & (1 << (7 - j))) != 0) { // Computer piece found
                    // Check for normal and capture moves
                    for (int dx = -1; dx <= 1; dx += 2) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int newX = i + dy;
                            int newY = j + dx;
                            if (isLegalMove(i, j, newX, newY)) {
                                movePiece(i, j, newX, newY);
                                System.out.println("Computer moved from (" + (i + 1) + "," + (j + 1) + ") to (" + (newX + 1) + "," + (newY + 1) + ")");
                                return true;
                            }
                            // Check for captures
                            newX = i + 2 * dy;
                            newY = j + 2 * dx;
                            if (isLegalMove(i, j, newX, newY)) {
                                movePiece(i, j, newX, newY);
                                System.out.println("Computer captured from (" + (i + 1) + "," + (j + 1) + ") to (" + (newX + 1) + "," + (newY + 1) + ")");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false; // No move was made
    }

    public boolean checkWinCondition() {
        for (int i = 0; i < 8; i++) {
            if ((board[i] & 0xFF) != 0) { // Check if any computer pieces are present
                return false; // Computer still has pieces
            }
        }
        return true; // No computer pieces left, user wins
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println("Enter your move (e.g., A3 B4): ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            int startX = Integer.parseInt(parts[0].substring(1)) - 1; // Convert to 0-index
            int startY = parts[0].charAt(0) - 'A'; // Convert letter to index
            int endX = Integer.parseInt(parts[1].substring(1)) - 1;
            int endY = parts[1].charAt(0) - 'A';

            if (isLegalMove(startX, startY, endX, endY)) {
                movePiece(startX, startY, endX, endY);
                if (!computerMove()) {
                    System.out.println("Computer has no valid moves left!");
                    break;
                }
            } else {
                System.out.println("Illegal move! Try again.");
            }

            // Check for win condition
            if (checkWinCondition()) {
                printBoard();
                System.out.println("You win!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        CheckersGame game = new CheckersGame();
        game.play();
    }
}



























