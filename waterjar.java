// Water Jar using DFS


import java.util.*;

class Main {

    // Class to represent the state of jars and path taken
    static class State {
        int jar4;       // Amount of water in 4-gallon jar
        int jar3;       // Amount of water in 3-gallon jar
        String path;    // Path of operations taken to reach this state

        State(int jar4, int jar3, String path) {
            this.jar4 = jar4;
            this.jar3 = jar3;
            this.path = path;
        }

        @Override
        public String toString() {
            return "(" + jar4 + ", " + jar3 + ")";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: target volume for the 4-gallon jar
        System.out.print("Enter the target volume for the 4-gallon jar: ");
        int target = scanner.nextInt();

        Stack<State> stack = new Stack<>();        // Stack for DFS
        Set<String> visited = new HashSet<>();     // To track visited states
        List<String> tree = new ArrayList<>();     // To store the state tree

        // Start from (0, 0)
        stack.push(new State(0, 0, ""));
        visited.add("0,0");

        // DFS Loop
        while (!stack.isEmpty()) {
            State current = stack.pop();      // Get current state
            tree.add(current.toString());     // Add to state tree

            // Goal check
            if (current.jar4 == target) {
                System.out.println("Path to solution: " + current.path);
                System.out.println("Generated Tree: " + tree);
                return;
            }

            List<State> leftChildren = new ArrayList<>();
            List<State> rightChildren = new ArrayList<>();

            // LEFT children: operations related to 3-gallon jar
            // Empty 4-gallon jar
            if (current.jar4 > 0)
                leftChildren.add(new State(0, current.jar3, current.path + " -> Empty 4-gallon jar"));

            // Fill 3-gallon jar
            if (current.jar3 < 3)
                leftChildren.add(new State(current.jar4, 3, current.path + " -> Fill 3-gallon jar"));

            // Transfer from 3-gallon to 4-gallon
            if (current.jar3 > 0 && current.jar4 < 4) {
                int transfer = Math.min(current.jar3, 4 - current.jar4);
                leftChildren.add(new State(current.jar4 + transfer, current.jar3 - transfer,
                        current.path + " -> Transfer 3 to 4"));
            }

            // RIGHT children: operations related to 4-gallon jar
            // Empty 3-gallon jar
            if (current.jar3 > 0)
                rightChildren.add(new State(current.jar4, 0, current.path + " -> Empty 3-gallon jar"));

            // Fill 4-gallon jar
            if (current.jar4 < 4)
                rightChildren.add(new State(4, current.jar3, current.path + " -> Fill 4-gallon jar"));

            // Transfer from 4-gallon to 3-gallon
            if (current.jar4 > 0 && current.jar3 < 3) {
                int transfer = Math.min(current.jar4, 3 - current.jar3);
                rightChildren.add(new State(current.jar4 - transfer, current.jar3 + transfer,
                        current.path + " -> Transfer 4 to 3"));
            }

            // Add right children to stack (DFS)
            for (State child : rightChildren) {
                String stateKey = child.jar4 + "," + child.jar3;
                if (!visited.contains(stateKey)) {
                    stack.push(child);
                    visited.add(stateKey);
                }
            }

            // Add left children to stack (DFS)
            for (State child : leftChildren) {
                String stateKey = child.jar4 + "," + child.jar3;
                if (!visited.contains(stateKey)) {
                    stack.push(child);
                    visited.add(stateKey);
                }
            }
        }

        // If no solution is found
        System.out.println("No solution found.");
        System.out.println("Generated Tree: " + tree);
    }
}


//Input - Target Volume for 4 Liter Jar






























// 2) Water Jar using BFS

// import java.util.*;

// class Main {

//     // Represents a state of the jars: amount in 4-gallon and 3-gallon jars, along with the path taken
//     static class State {
//         int jar4;  // Current amount in 4-gallon jar
//         int jar3;  // Current amount in 3-gallon jar
//         String path;  // Operations history to reach this state

//         State(int jar4, int jar3, String path) {
//             this.jar4 = jar4;
//             this.jar3 = jar3;
//             this.path = path;
//         }

//         @Override
//         public String toString() {
//             return "(" + jar4 + ", " + jar3 + ")";
//         }
//     }

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         // Input: Target volume to be measured in the 4-gallon jar
//         System.out.print("Enter the target volume for the 4-gallon jar: ");
//         int target = scanner.nextInt();

//         Queue<State> queue = new LinkedList<>();      // Queue for BFS traversal
//         Set<String> visited = new HashSet<>();        // To avoid visiting same state again
//         List<String> tree = new ArrayList<>();        // To store the generated states (tree nodes)

//         // Start from initial state: both jars are empty
//         queue.add(new State(0, 0, ""));
//         visited.add("0,0");

//         // BFS Loop
//         while (!queue.isEmpty()) {
//             State current = queue.poll();            // Get next state from the queue
//             tree.add(current.toString());            // Add current state to tree

//             // Check if the goal is achieved
//             if (current.jar4 == target) {
//                 System.out.println("Path to solution: " + current.path);
//                 System.out.println("Generated Tree: " + tree);
//                 return;
//             }

//             List<State> children = new ArrayList<>(); // To store next possible states

//             // Possible Operations (children generation)

//             // 1. Empty the 4-gallon jar
//             if (current.jar4 > 0)
//                 children.add(new State(0, current.jar3, current.path + " -> Empty 4-gallon jar"));

//             // 2. Fill the 3-gallon jar
//             if (current.jar3 < 3)
//                 children.add(new State(current.jar4, 3, current.path + " -> Fill 3-gallon jar"));

//             // 3. Transfer water from 3-gallon to 4-gallon jar
//             if (current.jar3 > 0 && current.jar4 < 4) {
//                 int transfer = Math.min(current.jar3, 4 - current.jar4);
//                 children.add(new State(current.jar4 + transfer, current.jar3 - transfer, current.path + " -> Transfer 3 to 4"));
//             }

//             // 4. Empty the 3-gallon jar
//             if (current.jar3 > 0)
//                 children.add(new State(current.jar4, 0, current.path + " -> Empty 3-gallon jar"));

//             // 5. Fill the 4-gallon jar
//             if (current.jar4 < 4)
//                 children.add(new State(4, current.jar3, current.path + " -> Fill 4-gallon jar"));

//             // 6. Transfer water from 4-gallon to 3-gallon jar
//             if (current.jar4 > 0 && current.jar3 < 3) {
//                 int transfer = Math.min(current.jar4, 3 - current.jar3);
//                 children.add(new State(current.jar4 - transfer, current.jar3 + transfer, current.path + " -> Transfer 4 to 3"));
//             }

//             // Add unvisited children to the queue
//             for (State child : children) {
//                 String stateKey = child.jar4 + "," + child.jar3;
//                 if (!visited.contains(stateKey)) {
//                     queue.add(child);
//                     visited.add(stateKey);
//                 }
//             }
//         }

//         // If we reach here, no solution was found
//         System.out.println("No solution found.");
//         System.out.println("Generated Tree: " + tree);
//     }
// }

//Input - Target Volume for 4 Liter Jar