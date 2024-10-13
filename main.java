public static void main(String[] args) {
    CheckersGame game = new CheckersGame();
    game.play();
}
//import java.util.Scanner;
//
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        CheckersGame game = new CheckersGame();
//
//        while (true) {
//            game.printBoard();
//
//            // User Move
//            System.out.println("Enter your move (e.g., A3 B4) or 'exit' to quit:");
//            String input = scanner.nextLine();
//            if (input.equalsIgnoreCase("exit")) break;
//
//            String[] parts = input.split(" ");
//            if (parts.length == 2) {
//                int startRow = Character.getNumericValue(parts[0].charAt(1)) - 1; // Convert to 0-index
//                int startCol = parts[0].charAt(0) - 'A'; // Convert letter to column index
//                int endRow = Character.getNumericValue(parts[1].charAt(1)) - 1;
//                int endCol = parts[1].charAt(0) - 'A';
//
//                if (game.isLegalMove(startRow, startCol, endRow, endCol)) {
//                    game.movePiece(startRow, startCol, endRow, endCol);
//                    game.printBoard();
//                    game.computerMove(); // Computer makes a random move
//                } else {
//                    System.out.println("Illegal move. Try again.");
//                }
//            }
//        }
//        scanner.close();
//    }








