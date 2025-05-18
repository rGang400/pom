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




























C++ code
    
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

struct Node {
    string name;
    int g; // cost from start to current node
    int h; // heuristic to goal
    int f; // total cost f = g + h
    Node* parent;

    Node(string n, int g_cost, int h_cost, Node* p)
        : name(n), g(g_cost), h(h_cost), f(g_cost + h_cost), parent(p) {}

    // Comparator for priority queue
    bool operator>(const Node& other) const {
        return f > other.f;
    }
};

// Global graph and heuristic maps
unordered_map<string, unordered_map<string, int>> graph;
unordered_map<string, int> heuristic;

vector<string> reconstructPath(Node* goalNode) {
    vector<string> path;
    Node* current = goalNode;
    while (current != nullptr) {
        path.push_back(current->name);
        current = current->parent;
    }
    reverse(path.begin(), path.end());
    return path;
}

vector<string> aStar(string start, string goal) {
    auto cmp = [](Node* a, Node* b) { return *a > *b; };
    priority_queue<Node*, vector<Node*>, decltype(cmp)> openList(cmp);
    set<string> closedSet;

    Node* startNode = new Node(start, 0, heuristic[start], nullptr);
    openList.push(startNode);

    while (!openList.empty()) {
        Node* current = openList.top();
        openList.pop();

        if (current->name == goal) {
            cout << "Total Cost: " << current->g << endl;
            return reconstructPath(current);
        }

        closedSet.insert(current->name);

        for (auto& neighborPair : graph[current->name]) {
            string neighbor = neighborPair.first;
            int cost = neighborPair.second;

            if (closedSet.find(neighbor) != closedSet.end()) continue;

            int gCost = current->g + cost;
            Node* neighborNode = new Node(neighbor, gCost, heuristic[neighbor], current);
            openList.push(neighborNode);
        }
    }

    return {}; // No path found
}

int main() {
    int n, e;
    cout << "Enter number of nodes: ";
    cin >> n;
    cin.ignore();

    cout << "Enter heuristic values (e.g. A 4):" << endl;
    for (int i = 0; i < n; ++i) {
        string line;
        getline(cin, line);
        stringstream ss(line);
        string node;
        int h;
        ss >> node >> h;
        heuristic[node] = h;
    }

    cout << "Enter number of edges: ";
    cin >> e;
    cin.ignore();

    cout << "Enter edges (e.g. A B 3):" << endl;
    for (int i = 0; i < e; ++i) {
        string line;
        getline(cin, line);
        stringstream ss(line);
        string from, to;
        int cost;
        ss >> from >> to >> cost;
        graph[from][to] = cost;
    }

    string start, goal;
    cout << "Enter start node: ";
    cin >> start;
    cout << "Enter goal node: ";
    cin >> goal;

    vector<string> path = aStar(start, goal);
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
