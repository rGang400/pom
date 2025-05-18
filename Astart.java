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

