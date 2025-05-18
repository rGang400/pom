// Eight Puzzle

// The Eight Puzzle is a classic sliding tile puzzle consisting of a 3x3 grid. It contains:

// Eight numbered tiles (1 through 8)

// One blank tile (represented by 0)

// The goal is to reach a final configuration (goal state) by sliding the tiles one at a time into the empty space (0), using only legal moves: Up, Down, Left, or Right.

// 🎯 Problem Statement
// Given:

// An initial state of the puzzle

// A goal state of the puzzle

// Find the shortest path (sequence of moves) that transforms the initial state into the goal state using the A* (A-Star) Search Algorithm with Manhattan Distance Heuristic.



import java.util.*;

// Represents a node in the puzzle search tree
class PuzzleNode {  
    int[][] state;       // 2D array to represent puzzle state
    int gCost;           // Cost from start node to this node
    int hCost;           // Heuristic cost to goal
    int fCost;           // Total cost (g + h)
    PuzzleNode parent;   // Reference to parent node for backtracking the solution path

    // Constructor for initializing node
    public PuzzleNode(int[][] state, int gCost, int hCost, PuzzleNode parent) {
        this.state = state;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
        this.parent = parent;
    }
}

class EightPuzzle {
    static final int N = 3;            // Dimension of the puzzle (3x3)
    static int[] goalState;            // Goal state for comparison

    // Possible move directions: Up, Down, Left, Right
    static final int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final String[] directions = {"Up", "Down", "Left", "Right"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input initial puzzle state
        System.out.println("Enter initial state (9 space-separated numbers, 0 represents blank):");
        int[] initialState = new int[9];
        for (int i = 0; i < 9; i++) {
            initialState[i] = scanner.nextInt();
        }

        // Input goal puzzle state
        System.out.println("Enter goal state (9 space-separated numbers, 0 represents blank):");
        goalState = new int[9];
        for (int i = 0; i < 9; i++) {
            goalState[i] = scanner.nextInt();
        }

        // Solve the puzzle using A* search
        EightPuzzle solver = new EightPuzzle();
        solver.solve(initialState);
    }

    // A* Search Algorithm to solve the puzzle
    public void solve(int[] initialState) {
        // Create the start node
        PuzzleNode startNode = new PuzzleNode(to2DArray(initialState), 0, calculateHeuristic(initialState), null);

        // Priority queue based on fCost
        PriorityQueue<PuzzleNode> openList = new PriorityQueue<>(Comparator.comparingInt(node -> node.fCost));

        // Set to store visited states
        Set<String> closedSet = new HashSet<>();

        openList.add(startNode);

        while (!openList.isEmpty()) {
            PuzzleNode currentNode = openList.poll();

            // If goal state reached, print the solution
            if (Arrays.equals(flatten(currentNode.state), goalState)) {
                printSolution(currentNode);
                return;
            }

            // Mark current node as visited
            closedSet.add(Arrays.toString(flatten(currentNode.state)));

            // Try all 4 directions
            for (int i = 0; i < 4; i++) {
                int[] newState = makeMove(currentNode.state, i);

                // Skip already visited states
                if (newState != null && !closedSet.contains(Arrays.toString(newState))) {
                    int gCost = currentNode.gCost + 1;
                    int hCost = calculateHeuristic(newState);
                    PuzzleNode childNode = new PuzzleNode(to2DArray(newState), gCost, hCost, currentNode);
                    openList.add(childNode);
                }
            }
        }
    }

    // Manhattan Distance Heuristic: total distance of each tile from its goal position
    private int calculateHeuristic(int[] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] != 0) { // Skip the blank tile
                int targetX = (state[i] - 1) / N;
                int targetY = (state[i] - 1) % N;
                int currentX = i / N;
                int currentY = i % N;
                heuristic += Math.abs(currentX - targetX) + Math.abs(currentY - targetY);
            }
        }
        return heuristic;
    }

    // Recursively print the path from start to goal
    private void printSolution(PuzzleNode node) {
        if (node == null) return;
        printSolution(node.parent); // Recurse to root
        printState(node.state);
        System.out.println();
    }

    // Print the 3x3 puzzle state
    private void printState(int[][] state) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Convert 2D puzzle state into 1D array
    private int[] flatten(int[][] state) {
        int[] flat = new int[9];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                flat[i * N + j] = state[i][j];
            }
        }
        return flat;
    }

    // For initial and goal states (already in 1D)
    private int[] flatten(int[] state) {
        return state;
    }

    // Convert 1D array into 2D array for internal state representation
    private int[][] to2DArray(int[] state) {
        int[][] array = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array[i][j] = state[i * N + j];
            }
        }
        return array;
    }

    // Generate a new state by moving the blank in the specified direction
    private int[] makeMove(int[][] state, int direction) {
        int[] newState = new int[9];
        System.arraycopy(flatten(state), 0, newState, 0, 9);

        int zeroPos = findZeroPosition(state); // Index of blank (0)
        int x = zeroPos / N;
        int y = zeroPos % N;

        int newX = x + moves[direction][0];
        int newY = y + moves[direction][1];

        // Check if new position is valid
        if (newX >= 0 && newX < N && newY >= 0 && newY < N) {
            int newZeroPos = newX * N + newY;

            // Swap 0 with the adjacent tile
            newState[zeroPos] = newState[newZeroPos];
            newState[newZeroPos] = 0;
            return newState;
        }
        return null; // Invalid move
    }

    // Find the position of blank tile (0) in the state
    private int findZeroPosition(int[][] state) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (state[i][j] == 0) {
                    return i * N + j;
                }
            }
        }
        return -1; // Should never reach here if input is valid
    }
}


//Input are given below

//  2 7 4 8 1 5 3 6 0
// 1 2 3 8 0 4 7 6 5
