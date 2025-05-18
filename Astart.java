// 🧩 Problem Statement: A* Search Pathfinding Simulator
// 💡 Objective:
// Design and implement a Java program that uses the A* (A-Star) Search Algorithm to find the shortest path between two nodes in a user-defined graph, using heuristic values and cost-based edges.

// 📋 Description:
// In many real-world applications like GPS navigation, game AI, and robotics, finding the shortest or most optimal path between two points is crucial. This program simulates such a scenario by taking input from the user to build a graph and then applying the A* algorithm to find the shortest path from a start node to a goal node.

// 📥 Inputs:
// Total number of nodes in the graph.

// Heuristic values for each node (an estimated cost from that node to the goal).

// Number of edges and the cost for each directed edge (e.g., A to B with cost 3).

// Start node.

// Goal node.

// 📤 Output:
// The optimal path from start to goal using A* search.

// The total cost of the path.

// 🛠 Functional Requirements:
// Accept graph and heuristic data from the user.

// Use a priority queue to implement the A* search efficiently.

// Avoid revisiting nodes already explored (closed set).

// Trace the path from goal back to start using parent references.



java.util.*;

class Node implements Comparable<Node> {
    String name;
    int g; // Cost from start to current node
    int h; // Heuristic cost to goal
    int f; // f = g + h
    Node parent;

    Node(String name, int g, int h, Node parent) {
        this.name = name;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}

public class Main {
    static Map<String, Map<String, Integer>> graph = new HashMap<>();
    static Map<String, Integer> heuristic = new HashMap<>();

    public static List<String> aStar(String start, String goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        openList.add(new Node(start, 0, heuristic.get(start), null));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.name.equals(goal)) {
                // Build path
                List<String> path = new ArrayList<>();
                Node temp = current;
                while (temp != null) {
                    path.add(temp.name);
                    temp = temp.parent;
                }
                Collections.reverse(path);
                System.out.println("Total Cost: " + current.g);
                return path;
            }

            closedSet.add(current.name);

            Map<String, Integer> neighbors = graph.getOrDefault(current.name, new HashMap<>());
            for (String neighbor : neighbors.keySet()) {
                if (closedSet.contains(neighbor)) continue;

                int gCost = current.g + neighbors.get(neighbor);
                Node neighborNode = new Node(neighbor, gCost, heuristic.getOrDefault(neighbor, 0), current);

                openList.add(neighborNode);
            }
        }

        return null; // No path found
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter heuristic values (e.g. A 4):");
        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().split(" ");
            heuristic.put(parts[0], Integer.parseInt(parts[1]));
        }

        System.out.print("Enter number of edges: ");
        int e = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter edges (e.g. A B 3):");
        for (int i = 0; i < e; i++) {
            String[] parts = scanner.nextLine().split(" ");
            String from = parts[0];
            String to = parts[1];
            int cost = Integer.parseInt(parts[2]);

            graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, cost);
        }

        System.out.print("Enter start node: ");
        String start = scanner.nextLine();

        System.out.print("Enter goal node: ");
        String goal = scanner.nextLine();

        List<String> path = aStar(start, goal);
        if (path != null) {
            System.out.println("Path found: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found.");
        }

        scanner.close();
    }
}



// Input - Enter number of nodes: 5
// Enter heuristic values (e.g. A 4):
// A 4
// B 2
// C 6
// D 1
// G 0

// Enter number of edges: 6
// Enter edges (e.g. A B 3):
// A B 1
// A C 4
// B D 1
// C D 2
// D G 1
// C G 5

// Enter start node: A
// Enter goal node: G




























//C++ code
#include <iostream>
#include <unordered_map>
#include <vector>
#include <queue>
#include <set>
#include <string>
#include <sstream>
#include <limits>
#include <algorithm>

using namespace std;

// Structure to represent each node in the graph for A* search
struct Node {
    string name;  // Node name (e.g., "A")
    int g;        // Cost from start node to current node
    int h;        // Heuristic estimated cost from current node to goal
    int f;        // Total cost = g + h
    Node* parent; // Pointer to parent node to reconstruct the path later

    // Constructor to initialize node values
    Node(string n, int g_cost, int h_cost, Node* p)
        : name(n), g(g_cost), h(h_cost), f(g_cost + h_cost), parent(p) {}

    // Comparator to compare nodes by their f value for priority queue
    bool operator>(const Node& other) const {
        return f > other.f;
    }
};

// Global variables to hold graph edges and heuristic values
unordered_map<string, unordered_map<string, int>> graph; // graph[from][to] = cost
unordered_map<string, int> heuristic; // heuristic[node] = estimated cost to goal

// Function to reconstruct the path from goal to start using parent pointers
vector<string> reconstructPath(Node* goalNode) {
    vector<string> path;
    Node* current = goalNode;
    // Follow parent pointers back to start node
    while (current != nullptr) {
        path.push_back(current->name);
        current = current->parent;
    }
    // Reverse path to get correct order from start to goal
    reverse(path.begin(), path.end());
    return path;
}

// A* Search Algorithm to find shortest path from start to goal
vector<string> aStar(string start, string goal) {
    // Custom comparator for priority queue based on f value
    auto cmp = [](Node* a, Node* b) { return *a > *b; };

    // Priority queue to hold nodes to explore, sorted by lowest f cost
    priority_queue<Node*, vector<Node*>, decltype(cmp)> openList(cmp);

    // Set to keep track of nodes already visited (closed set)
    set<string> closedSet;

    // Create start node with g=0 and heuristic h for start node
    Node* startNode = new Node(start, 0, heuristic[start], nullptr);
    openList.push(startNode);

    while (!openList.empty()) {
        Node* current = openList.top(); // Node with lowest f cost
        openList.pop();

        // Check if current node is the goal
        if (current->name == goal) {
            cout << "Total Cost: " << current->g << endl;
            return reconstructPath(current);
        }

        // Mark current node as visited
        closedSet.insert(current->name);

        // Explore neighbors of current node
        for (auto& neighborPair : graph[current->name]) {
            string neighbor = neighborPair.first;
            int cost = neighborPair.second;

            // Skip neighbor if already visited
            if (closedSet.find(neighbor) != closedSet.end()) continue;

            // Calculate cost to neighbor through current node
            int gCost = current->g + cost;

            // Create a new node for neighbor with updated costs and parent pointer
            Node* neighborNode = new Node(neighbor, gCost, heuristic[neighbor], current);

            // Add neighbor node to the open list for further exploration
            openList.push(neighborNode);
        }
    }

    // If open list is empty and goal not found, return empty path
    return {};
}

int main() {
    int n, e;

    cout << "Enter number of nodes: ";
    cin >> n;
    cin.ignore(); // Ignore newline after number input

    cout << "Enter heuristic values (e.g. A 4):" << endl;
    // Read heuristic values for each node
    for (int i = 0; i < n; ++i) {
        string line;
        getline(cin, line);
        stringstream ss(line);
        string node;
        int h;
        ss >> node >> h;
        heuristic[node] = h; // Store heuristic value
    }

    cout << "Enter number of edges: ";
    cin >> e;
    cin.ignore();

    cout << "Enter edges (e.g. A B 3):" << endl;
    // Read edges and their costs and store in graph
    for (int i = 0; i < e; ++i) {
        string line;
        getline(cin, line);
        stringstream ss(line);
        string from, to;
        int cost;
        ss >> from >> to >> cost;
        graph[from][to] = cost; // Directed edge from 'from' to 'to' with cost
    }

    string start, goal;
    cout << "Enter start node: ";
    cin >> start;
    cout << "Enter goal node: ";
    cin >> goal;

    // Run A* algorithm to find path from start to goal
    vector<string> path = aStar(start, goal);

    // Output the found path or no path message
    if (!path.empty()) {
        cout << "Path found: ";
        for (size_t i = 0; i < path.size(); ++i) {
            cout << path[i];
            if (i < path.size() - 1) cout << " -> ";
        }
        cout << endl;
    } else {
        cout << "No path found." << endl;
    }

    return 0;
}
// Input - Enter number of nodes: 5
// Enter heuristic values (e.g. A 4):
// A 4
// B 2
// C 6
// D 1
// G 0

// Enter number of edges: 6
// Enter edges (e.g. A B 3):
// A B 1
// A C 4
// B D 1
// C D 2
// D G 1
// C G 5

// Enter start node: A
// Enter goal node: G
