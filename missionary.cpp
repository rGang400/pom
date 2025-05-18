//1) Missonaries using BFS
// 🧠 Problem Statement: Missionaries and Cannibals
// You are given:

// A group of missionaries and cannibals on one side of a river (the left bank).

// A boat that can carry a limited number of people (defined by a given boat capacity).

// 🛶 Objective:
// Safely transport all the missionaries and cannibals from the left bank to the right bank using the boat, following certain rules.

// ✅ Rules and Constraints:
// Boat Capacity: The boat can carry at least 1 person and up to the specified maximum number of people.

// Only Two Types: Only missionaries and cannibals are allowed in the boat.

// One Side at a Time: The boat must always carry people from one bank to the other. It cannot cross empty.

// Missionary Safety Rule:

// On either bank, if cannibals outnumber missionaries, the missionaries will get eaten.

// This is allowed only if there are no missionaries on that side.

// 🎯 Goal:
// Move all missionaries and cannibals to the right bank safely — without violating the safety rule — using the boat.


#include <iostream>
#include <queue>
#include <vector>
#include <set>
#include <tuple>

using namespace std;

// Global variables for user-defined total missionaries, cannibals, and boat capacity
int totalM, totalC, boatCapacity;

// Structure to represent a state of the problem
struct State {
    int mLeft, cLeft, boat; // missionaries and cannibals on the left bank, boat position (0=left, 1=right)
    vector<State> path;     // to track the path (solution steps)

    // Constructor to initialize a state and track its path
    State(int m, int c, int b, vector<State> p = {}) : mLeft(m), cLeft(c), boat(b), path(p) {
        path.push_back(*this); // add current state to the path
    }

    // Check if the goal state is reached
    bool isGoal() const {
        return mLeft == 0 && cLeft == 0 && boat == 1;
    }

    // Validate state (missionaries are never outnumbered and values are within bounds)
    bool isValid() const {
        int mRight = totalM - mLeft;
        int cRight = totalC - cLeft;

        // Check for out-of-bounds or negative values
        if (mLeft < 0 || cLeft < 0 || mLeft > totalM || cLeft > totalC) return false;
        if (mRight < 0 || cRight < 0 || mRight > totalM || cRight > totalC) return false;

        // Missionaries must not be outnumbered by cannibals on either side (unless missionaries are 0)
        if ((mLeft > 0 && mLeft < cLeft) || (mRight > 0 && mRight < cRight)) return false;

        return true;
    }

    // Custom comparison operator for using State in set
    bool operator<(const State &other) const {
        return tie(mLeft, cLeft, boat) < tie(other.mLeft, other.cLeft, other.boat);
    }

    // Function to print the current state
    void print() const {
        cout << "Left: M=" << mLeft << ", C=" << cLeft
             << " | Right: M=" << totalM - mLeft << ", C=" << totalC - cLeft
             << " | Boat: " << (boat == 0 ? "Left" : "Right") << "\n";
    }
};

// Generate all valid combinations of missionaries and cannibals that can fit in the boat
vector<pair<int, int>> generateMoves() {
    vector<pair<int, int>> moves;
    for (int m = 0; m <= boatCapacity; ++m) {
        for (int c = 0; c <= boatCapacity; ++c) {
            if (m + c >= 1 && m + c <= boatCapacity) { // must carry at least one and not exceed capacity
                moves.emplace_back(m, c); // (m missionaries, c cannibals)
            }
        }
    }
    return moves;
}

// BFS to find the shortest solution path
void solve() {
    vector<pair<int, int>> moves = generateMoves(); // possible boat moves
    queue<State> q;                                  // BFS queue
    set<tuple<int, int, int>> visited;               // track visited states to avoid cycles

    State start(totalM, totalC, 0); // start from left side
    q.push(start);
    visited.insert({totalM, totalC, 0});

    while (!q.empty()) {
        State current = q.front();
        q.pop();

        // If goal is reached, print the solution path
        if (current.isGoal()) {
            cout << "\n✅ Solution Path:\n";
            for (const State &s : current.path)
                s.print();
            return;
        }

        // Try all possible valid boat moves
        for (auto move : moves) {
            int dm = move.first; // number of missionaries to move
            int dc = move.second; // number of cannibals to move

            State next = current;

            // Move boat from left to right
            if (current.boat == 0) {
                next.mLeft -= dm;
                next.cLeft -= dc;
                next.boat = 1;
            } 
            // Move boat from right to left
            else {
                next.mLeft += dm;
                next.cLeft += dc;
                next.boat = 0;
            }

            // Add to queue if valid and not already visited
            if (next.isValid() && visited.find({next.mLeft, next.cLeft, next.boat}) == visited.end()) {
                visited.insert({next.mLeft, next.cLeft, next.boat});
                next.path = current.path;
                next.path.push_back(next);
                q.push(next);
            }
        }
    }

    cout << "❌ No solution found.\n";
}

// Main function to take input and start the solver
int main() {
    cout << "Missionaries and Cannibals Problem - BFS\n";
    
    // User input
    cout << "Enter total number of Missionaries: ";
    cin >> totalM;
    cout << "Enter total number of Cannibals: ";
    cin >> totalC;
    cout << "Enter boat capacity: ";
    cin >> boatCapacity;

    // Check for valid input
    if (totalM < 0 || totalC < 0 || boatCapacity < 1) {
        cout << "Invalid input values. Please use positive numbers.\n";
        return 1;
    }

    solve(); // Start solving the problem
 return 0;
}
















//2) Missonaries using dfs
#include <iostream>
#include <vector>
#include <set>
#include <tuple>
#include <stack>

using namespace std;

// Global variables to store the total number of Missionaries, Cannibals, and Boat Capacity
int totalM, totalC, boatCapacity;

// Structure to represent the state of the game
struct State {
    int mLeft, cLeft, boat; // Missionaries, Cannibals on the left bank, and boat position
    vector<State> path;     // Path to track the sequence of steps

    // Constructor to initialize a state and its path
    State(int m, int c, int b, vector<State> p = {}) : mLeft(m), cLeft(c), boat(b), path(p) {
        path.push_back(*this); // Add the current state to the path
    }

    // Check if the goal state is reached (All are on the right bank and boat is also on the right)
    bool isGoal() const {
        return mLeft == 0 && cLeft == 0 && boat == 1;
    }

    // Validate the state:
    // 1. No negatives or exceeding the original number of missionaries/cannibals.
    // 2. Missionaries are never outnumbered by cannibals on either side.
    bool isValid() const {
        int mRight = totalM - mLeft;
        int cRight = totalC - cLeft;

        // Check if values are out of range
        if (mLeft < 0 || cLeft < 0 || mLeft > totalM || cLeft > totalC) return false;
        if (mRight < 0 || cRight < 0 || mRight > totalM || cRight > totalC) return false;

        // Missionaries must not be outnumbered by cannibals on either side
        if ((mLeft > 0 && mLeft < cLeft) || (mRight > 0 && mRight < cRight)) return false;

        return true;
    }

    // Operator overload to use State in a set (compares based on missionaries, cannibals, and boat position)
    bool operator<(const State &other) const {
        return tie(mLeft, cLeft, boat) < tie(other.mLeft, other.cLeft, other.boat);
    }

    // Display the current state
    void print() const {
        cout << "Left: M=" << mLeft << ", C=" << cLeft
             << " | Right: M=" << totalM - mLeft << ", C=" << totalC - cLeft
             << " | Boat: " << (boat == 0 ? "Left" : "Right") << "\n";
    }
};

// Function to generate all valid combinations of missionaries and cannibals that can fit in the boat
vector<pair<int, int>> generateMoves() {
    vector<pair<int, int>> moves;

    // Loop through all possible combinations of missionaries and cannibals
    for (int m = 0; m <= boatCapacity; ++m) {
        for (int c = 0; c <= boatCapacity; ++c) {
            // The boat must carry at least one and not exceed its capacity
            if (m + c >= 1 && m + c <= boatCapacity) {
                moves.emplace_back(m, c); // Add the combination to the list of possible moves
            }
        }
    }
    return moves;
}

// Function to solve the problem using **Iterative DFS**
void solveDFSIterative() {
    // Generate all valid moves for the boat
    vector<pair<int, int>> moves = generateMoves();
    set<tuple<int, int, int>> visited;  // Set to keep track of visited states
    stack<State> st;                    // Stack for DFS (LIFO)

    // Initialize the start state (all missionaries and cannibals on the left, boat also on the left)
    State start(totalM, totalC, 0);
    st.push(start); // Push the initial state onto the stack

    // DFS Loop
    while (!st.empty()) {
        State current = st.top(); // Take the top of the stack (last inserted state)
        st.pop();                 // Remove it from the stack

        // If the goal is reached, print the solution path and return
        if (current.isGoal()) {
            cout << "\n✅ Solution Path (Iterative DFS):\n";
            for (const State &s : current.path)
                s.print();
            return;
        }

        // If the state is already visited, skip further processing
        if (visited.find({current.mLeft, current.cLeft, current.boat}) != visited.end())
            continue;

        // Mark the current state as visited
        visited.insert({current.mLeft, current.cLeft, current.boat});

        // Loop through all valid moves and generate the next states
        for (auto move : moves) {
            int dm = move.first;  // Number of missionaries to move
            int dc = move.second; // Number of cannibals to move

            State next = current; // Start with the current state

            // If the boat is on the left bank, move to the right
            if (current.boat == 0) {
                next.mLeft -= dm;
                next.cLeft -= dc;
                next.boat = 1;
            } 
            // If the boat is on the right bank, move to the left
            else {
                next.mLeft += dm;
                next.cLeft += dc;
                next.boat = 0;
            }

            // If the next state is valid and not already visited, add it to the stack
            if (next.isValid() && visited.find({next.mLeft, next.cLeft, next.boat}) == visited.end()) {
                next.path = current.path;  // Carry forward the path
                next.path.push_back(next); // Add the new state to the path
                st.push(next);             // Push the next state onto the stack
            }
        }
    }

    // If the stack is empty and no solution was found, print this message
    cout << "❌ No solution found using iterative DFS.\n";
}

int main() {
    // Input for number of missionaries, cannibals, and boat capacity
    cout << "Missionaries and Cannibals Problem - Iterative DFS\n";
    cout << "Enter total number of Missionaries: ";
    cin >> totalM;
    cout << "Enter total number of Cannibals: ";
    cin >> totalC;
    cout << "Enter boat capacity: ";
    cin >> boatCapacity;

    // Validate the input values
    if (totalM < 0 || totalC < 0 || boatCapacity < 1) {
        cout << "Invalid input values. Please use positive numbers.\n";
        return 1;
    }

    // Call the DFS solver
    solveDFSIterative();
    return 0;
}
