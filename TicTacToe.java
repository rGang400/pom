//1) Tic Tac Toe using Non-AI

import java.util.Random;
import java.util.Scanner;
	
public class Main {
private static char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}}; private static char human, computer;
private static final int[][] magicSquare = {
{8, 1, 6},
{3, 5, 7},
{4, 9, 2}
};

public static void main(String[] args) { Scanner scanner = new Scanner(System.in);
System.out.println("Choose your shape (X or O):"); human = scanner.next().toUpperCase().charAt(0); computer = (human == 'X') ? 'O' : 'X';
boolean humanTurn = (human == 'X');

while (true) { printBoard();
if (checkWin(human)) { System.out.println("You win!"); break;
}
if (checkWin(computer)) { System.out.println("Computer wins!"); break;
}
if (isBoardFull()) { System.out.println("It's a tie!"); break;
}
if (humanTurn) {
System.out.println("Enter your move (row and column: 1-3 1-3):"); int row = scanner.nextInt() - 1;
int col = scanner.nextInt() - 1; if (board[row][col] == ' ') {
board[row][col] = human; humanTurn = false;
} else {
 
System.out.println("Invalid move, try again.");
}
} else {
makeComputerMove(); humanTurn = true;
}
}
printBoard(); scanner.close();
}

private static void printBoard() { System.out.println("	");
for (int i = 0; i < 3; i++) { System.out.print("| "); for (int j = 0; j < 3; j++) {
System.out.print(board[i][j] + " | ");
}
System.out.println("\n	");
}
}

private static boolean checkWin(char player) { int sum = 0;
for (int i = 0; i < 3; i++) { sum = 0;
for (int j = 0; j < 3; j++) {
if (board[i][j] == player) { sum += magicSquare[i][j];
}
}
if (sum == 15) return true;
}
for (int i = 0; i < 3; i++) { sum = 0;
for (int j = 0; j < 3; j++) {
if (board[j][i] == player) { sum += magicSquare[j][i];
}
}
if (sum == 15) return true;
}
sum = 0;
for (int i = 0; i < 3; i++) {
if (board[i][i] == player) { sum += magicSquare[i][i];
}
}
if (sum == 15) return true; sum = 0;
for (int i = 0; i < 3; i++) {
if (board[i][2 - i] == player) { sum += magicSquare[i][2 - i];
}
}
return sum == 15;
}
 
private static boolean isBoardFull() { for (int i = 0; i < 3; i++) {
for (int j = 0; j < 3; j++) {
if (board[i][j] == ' ') return false;
}
}
return true;
}

private static void makeComputerMove() { int bestMove = -1;
for (int i = 0; i < 3; i++) { for (int j = 0; j < 3; j++) {
if (board[i][j] == ' ') { board[i][j] = computer;
if (checkWin(computer)) { return;
}
board[i][j] = ' ';
}
}
}

for (int i = 0; i < 3; i++) { for (int j = 0; j < 3; j++) {
if (board[i][j] == ' ') { board[i][j] = human;
if (checkWin(human)) { board[i][j] = computer; return;
}
board[i][j] = ' ';
}
}
}

Random random = new Random(); do {
bestMove = random.nextInt(9) + 1;
} while (!isValidMove(bestMove));

int[] position = getPosition(bestMove); board[position[0]][position[1]] = computer;
}

private static boolean isValidMove(int move) { int[] position = getPosition(move);
return board[position[0]][position[1]] == ' ';
}

private static int[] getPosition(int move) { for (int i = 0; i < 3; i++) {
for (int j = 0; j < 3; j++) {
if (magicSquare[i][j] == move) { return new int[]{i, j};
}
 
}
}
return new int[]{-1, -1};
}
}


// Input & OutPut - First choose X or O, then input your move(In Format of row & Column), then computer will make its move. 

















//2) Tic Tac Toe Using AI



// import java.util.Scanner; 
// import java.util.ArrayList; 
// import java.util.List; 

// public class Main {

//     // 3x3 game board
//     static char[][] board = {
//         {' ', ' ', ' '},
//         {' ', ' ', ' '},
//         {' ', ' ', ' '}
//     };

//     // Magic square for winning condition checking
//     static int[][] magic = {
//         {8, 1, 6},
//         {3, 5, 7},
//         {4, 9, 2}
//     };

//     // Symbols for human and AI
//     static char human, ai;

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         // Choose player symbol
//         System.out.print("Choose your symbol (X or O): "); 
//         human = scanner.next().toUpperCase().charAt(0); 
//         ai = (human == 'X') ? 'O' : 'X';

//         printBoard(); // Show empty board

//         // Game loop
//         while (true) {
//             // Human move
//             humanMove(scanner);
//             if (checkWinner(human)) { // Check if human wins
//                 printBoard(); 
//                 System.out.println("You Win!"); 
//                 break;
//             }
//             if (isBoardFull()) { // Check draw
//                 printBoard(); 
//                 System.out.println("It's a draw!"); 
//                 break;
//             }

//             // AI move
//             aiMove();
//             if (checkWinner(ai)) { // Check if AI wins
//                 printBoard(); 
//                 System.out.println("AI Wins!"); 
//                 break;
//             }
//             if (isBoardFull()) { // Check draw
//                 printBoard(); 
//                 System.out.println("It's a draw!"); 
//                 break;
//             }
//         }

//         scanner.close();
//     }

//     // Let the human make a move
//     static void humanMove(Scanner scanner) { 
//         int row, col;
//         while (true) {
//             System.out.print("Enter row (0-2) and column (0-2): "); 
//             row = scanner.nextInt();
//             col = scanner.nextInt();
            
//             // Check if move is valid
//             if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
//                 board[row][col] = human; 
//                 break;
//             } else {
//                 System.out.println("Invalid move. Try again.");
//             }
//         }
//         printBoard(); // Print board after move
//     }

//     // Let the AI make a move using Minimax
//     static void aiMove() {
//         int bestScore = Integer.MIN_VALUE; 
//         int bestRow = -1, bestCol = -1;

//         // Check all possible moves
//         for (int i = 0; i < 3; i++) { 
//             for (int j = 0; j < 3; j++) {
//                 if (board[i][j] == ' ') {
//                     board[i][j] = ai;
//                     int score = minimax(0, false); // Simulate opponent
//                     board[i][j] = ' ';
//                     if (score > bestScore) { 
//                         bestScore = score; 
//                         bestRow = i; 
//                         bestCol = j;
//                     }
//                 }
//             }
//         }

//         // Make the best move
//         board[bestRow][bestCol] = ai; 
//         printBoard();
//         System.out.println("AI played at: " + bestRow + ", " + bestCol);
//     }

//     // Minimax algorithm: returns the best score possible from current board state
//     static int minimax(int depth, boolean isMaximizing) {
//         if (checkWinner(ai)) return 10 - depth;       // AI wins
//         if (checkWinner(human)) return depth - 10;    // Human wins
//         if (isBoardFull()) return 0;                  // Draw

//         if (isMaximizing) {
//             int bestScore = Integer.MIN_VALUE;
//             for (int i = 0; i < 3; i++) {
//                 for (int j = 0; j < 3; j++) { 
//                     if (board[i][j] == ' ') { 
//                         board[i][j] = ai;
//                         bestScore = Math.max(bestScore, minimax(depth + 1, false));
//                         board[i][j] = ' ';
//                     }
//                 }
//             }
//             return bestScore;
//         } else {
//             int bestScore = Integer.MAX_VALUE;
//             for (int i = 0; i < 3; i++) {
//                 for (int j = 0; j < 3; j++) { 
//                     if (board[i][j] == ' ') {
//                         board[i][j] = human;
//                         bestScore = Math.min(bestScore, minimax(depth + 1, true));
//                         board[i][j] = ' ';
//                     }
//                 }
//             }
//             return bestScore;
//         }
//     }

//     // Check if a player has won using the magic square technique
//     static boolean checkWinner(char player) { 
//         List<Integer> moves = new ArrayList<>();

//         // Map board positions to magic square values
//         for (int i = 0; i < 3; i++) { 
//             for (int j = 0; j < 3; j++) {
//                 if (board[i][j] == player) { 
//                     moves.add(magic[i][j]);
//                 }
//             }
//         }

//         // Check all combinations of 3 moves for a magic sum of 15
//         int n = moves.size(); 
//         if (n < 3) return false;

//         for (int i = 0; i < n - 2; i++) {
//             for (int j = i + 1; j < n - 1; j++) { 
//                 for (int k = j + 1; k < n; k++) {
//                     if (moves.get(i) + moves.get(j) + moves.get(k) == 15) 
//                         return true;
//                 }
//             }
//         }
//         return false;
//     }

//     // Check if the board is full (no more moves)
//     static boolean isBoardFull() { 
//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {
//                 if (board[i][j] == ' ') return false;
//             }
//         }
//         return true;
//     }

//     // Display the game board
//     static void printBoard() { 
//         System.out.println("\t");
//         for (int i = 0; i < 3; i++) { 
//             System.out.print("| ");
//             for (int j = 0; j < 3; j++) {
//                 System.out.print(board[i][j] + " | ");
//             }
//             System.out.println("\n\t");
//         }
//     }
// }


















//Tic-Tac Toe using alpha beta

// import java.util.*;

// public class TicTacToe {
//     // Constants for players and empty cells
//     static final char HUMAN = 'O';
//     static final char AI = 'X';
//     static final char EMPTY = ' ';

//     // Class to store a move (row, column)
//     static class Move {
//         int row, col;
//     }

//     // 3x3 Tic-Tac-Toe board
//     static char[][] board = {
//         { EMPTY, EMPTY, EMPTY },
//         { EMPTY, EMPTY, EMPTY },
//         { EMPTY, EMPTY, EMPTY }
//     };

//     // Prints the current state of the board
//     static void printBoard() {
//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {
//                 System.out.print(board[i][j]);
//                 if (j < 2) System.out.print(" | ");
//             }
//             System.out.println();
//             if (i < 2) System.out.println("---------");
//         }
//     }

//     // Checks if any moves are left on the board
//     static boolean isMovesLeft(char[][] b) {
//         for (char[] row : b)
//             for (char cell : row)
//                 if (cell == EMPTY)
//                     return true;
//         return false;
//     }

//     // Evaluates the board to check if someone has won
//     static int evaluate(char[][] b) {
//         // Check rows
//         for (int row = 0; row < 3; row++) {
//             if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
//                 if (b[row][0] == AI) return +10;
//                 else if (b[row][0] == HUMAN) return -10;
//             }
//         }

//         // Check columns
//         for (int col = 0; col < 3; col++) {
//             if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
//                 if (b[0][col] == AI) return +10;
//                 else if (b[0][col] == HUMAN) return -10;
//             }
//         }

//         // Check diagonals
//         if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
//             if (b[0][0] == AI) return +10;
//             else if (b[0][0] == HUMAN) return -10;
//         }

//         if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
//             if (b[0][2] == AI) return +10;
//             else if (b[0][2] == HUMAN) return -10;
//         }

//         // No winner
//         return 0;
//     }

//     // Minimax algorithm with Alpha-Beta Pruning
//     static int minimax(char[][] b, int depth, boolean isMax, int alpha, int beta) {
//         int score = evaluate(b);

//         // If terminal state is reached (win/loss/draw)
//         if (score == 10 || score == -10) return score;
//         if (!isMovesLeft(b)) return 0;

//         // Maximizer's move (AI)
//         if (isMax) {
//             int best = Integer.MIN_VALUE;

//             for (int i = 0; i < 3; i++) {
//                 for (int j = 0; j < 3; j++) {
//                     if (b[i][j] == EMPTY) {
//                         b[i][j] = AI;
//                         best = Math.max(best, minimax(b, depth + 1, false, alpha, beta));
//                         b[i][j] = EMPTY;
//                         alpha = Math.max(alpha, best);
//                         if (beta <= alpha) return best; // Beta cutoff
//                     }
//                 }
//             }
//             return best;

//         // Minimizer's move (Human)
//         } else {
//             int best = Integer.MAX_VALUE;

//             for (int i = 0; i < 3; i++) {
//                 for (int j = 0; j < 3; j++) {
//                     if (b[i][j] == EMPTY) {
//                         b[i][j] = HUMAN;
//                         best = Math.min(best, minimax(b, depth + 1, true, alpha, beta));
//                         b[i][j] = EMPTY;
//                         beta = Math.min(beta, best);
//                         if (beta <= alpha) return best; // Alpha cutoff
//                     }
//                 }
//             }
//             return best;
//         }
//     }

//     // Finds the best move for the AI using minimax
//     static Move findBestMove(char[][] b) {
//         int bestVal = Integer.MIN_VALUE;
//         Move bestMove = new Move();
//         bestMove.row = -1;
//         bestMove.col = -1;

//         // Try all possible moves and choose the best
//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {
//                 if (b[i][j] == EMPTY) {
//                     b[i][j] = AI;
//                     int moveVal = minimax(b, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
//                     b[i][j] = EMPTY;

//                     if (moveVal > bestVal) {
//                         bestMove.row = i;
//                         bestMove.col = j;
//                         bestVal = moveVal;
//                     }
//                 }
//             }
//         }

//         return bestMove;
//     }

//     // Main game loop
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.println("Tic Tac Toe - You are 'O', AI is 'X'");

//         while (true) {
//             printBoard();

//             // Human move
//             System.out.print("Enter your move (row and column 0-2): ");
//             int row = sc.nextInt();
//             int col = sc.nextInt();
//             if (board[row][col] != EMPTY) {
//                 System.out.println("Invalid move! Try again.");
//                 continue;
//             }
//             board[row][col] = HUMAN;

//             // Check if Human won
//             if (evaluate(board) == -10) {
//                 printBoard();
//                 System.out.println("You win!");
//                 break;
//             }

//             // Check for draw
//             if (!isMovesLeft(board)) {
//                 printBoard();
//                 System.out.println("It's a draw!");
//                 break;
//             }

//             // AI move
//             Move bestMove = findBestMove(board);
//             board[bestMove.row][bestMove.col] = AI;

//             // Check if AI won
//             if (evaluate(board) == 10) {
//                 printBoard();
//                 System.out.println("AI wins!");
//                 break;
//             }

//             // Check for draw
//             if (!isMovesLeft(board)) {
//                 printBoard();
//                 System.out.println("It's a draw!");
//                 break;
//             }
//         }

//         sc.close();
// }
// }
