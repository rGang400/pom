//1) BFS
import java.util.*;

public class MissionariesCannibalsBFS {

    static int totalM, totalC, boatCapacity;

    static class State implements Comparable<State> {
        int mLeft, cLeft, boat; // missionaries, cannibals on left, boat position (0=left,1=right)
        List<State> path;

        State(int mLeft, int cLeft, int boat, List<State> path) {
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.path = new ArrayList<>(path);
            this.path.add(this);
        }

        boolean isGoal() {
            return mLeft == 0 && cLeft == 0 && boat == 1;
        }

        boolean isValid() {
            int mRight = totalM - mLeft;
            int cRight = totalC - cLeft;

            if (mLeft < 0 || cLeft < 0 || mLeft > totalM || cLeft > totalC) return false;
            if (mRight < 0 || cRight < 0 || mRight > totalM || cRight > totalC) return false;

            if ((mLeft > 0 && mLeft < cLeft) || (mRight > 0 && mRight < cRight)) return false;

            return true;
        }

        @Override
        public int compareTo(State other) {
            if (this.mLeft != other.mLeft) return Integer.compare(this.mLeft, other.mLeft);
            if (this.cLeft != other.cLeft) return Integer.compare(this.cLeft, other.cLeft);
            return Integer.compare(this.boat, other.boat);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return mLeft == s.mLeft && cLeft == s.cLeft && boat == s.boat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mLeft, cLeft, boat);
        }

        void print() {
            System.out.printf("Left: M=%d, C=%d | Right: M=%d, C=%d | Boat: %s\n",
                mLeft, cLeft, totalM - mLeft, totalC - cLeft, boat == 0 ? "Left" : "Right");
        }
    }

    static List<int[]> generateMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int m = 0; m <= boatCapacity; m++) {
            for (int c = 0; c <= boatCapacity; c++) {
                if (m + c >= 1 && m + c <= boatCapacity) {
                    moves.add(new int[]{m, c});
                }
            }
        }
        return moves;
    }

    static void solve() {
        List<int[]> moves = generateMoves();
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        State start = new State(totalM, totalC, 0, new ArrayList<>());
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.isGoal()) {
                System.out.println("\n✅ Solution Path:");
                for (State s : current.path) s.print();
                return;
            }

            for (int[] move : moves) {
                int dm = move[0];
                int dc = move[1];

                int mLeft = current.mLeft;
                int cLeft = current.cLeft;
                int boat = current.boat;

                if (boat == 0) { // boat moves from left to right
                    mLeft -= dm;
                    cLeft -= dc;
                    boat = 1;
                } else { // boat moves from right to left
                    mLeft += dm;
                    cLeft += dc;
                    boat = 0;
                }

                State next = new State(mLeft, cLeft, boat, current.path);

                if (next.isValid() && !visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
        System.out.println("❌ No solution found.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Missionaries and Cannibals Problem - BFS");
        System.out.print("Enter total number of Missionaries: ");
        totalM = sc.nextInt();
        System.out.print("Enter total number of Cannibals: ");
        totalC = sc.nextInt();
        System.out.print("Enter boat capacity: ");
        boatCapacity = sc.nextInt();

        if (totalM < 0 || totalC < 0 || boatCapacity < 1) {
            System.out.println("Invalid input values. Please use positive numbers.");
            return;
        }

        solve();
    }
}











//2) DFS
import java.util.*;

public class MissionariesCannibalsDFS {

    static int totalM, totalC, boatCapacity;

    static class State implements Comparable<State> {
        int mLeft, cLeft, boat;
        List<State> path;

        State(int mLeft, int cLeft, int boat, List<State> path) {
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.path = new ArrayList<>(path);
            this.path.add(this);
        }

        boolean isGoal() {
            return mLeft == 0 && cLeft == 0 && boat == 1;
        }

        boolean isValid() {
            int mRight = totalM - mLeft;
            int cRight = totalC - cLeft;

            if (mLeft < 0 || cLeft < 0 || mLeft > totalM || cLeft > totalC) return false;
            if (mRight < 0 || cRight < 0 || mRight > totalM || cRight > totalC) return false;

            if ((mLeft > 0 && mLeft < cLeft) || (mRight > 0 && mRight < cRight)) return false;

            return true;
        }

        @Override
        public int compareTo(State other) {
            if (this.mLeft != other.mLeft) return Integer.compare(this.mLeft, other.mLeft);
            if (this.cLeft != other.cLeft) return Integer.compare(this.cLeft, other.cLeft);
            return Integer.compare(this.boat, other.boat);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return mLeft == s.mLeft && cLeft == s.cLeft && boat == s.boat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mLeft, cLeft, boat);
        }

        void print() {
            System.out.printf("Left: M=%d, C=%d | Right: M=%d, C=%d | Boat: %s\n",
                mLeft, cLeft, totalM - mLeft, totalC - cLeft, boat == 0 ? "Left" : "Right");
        }
    }

    static List<int[]> generateMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int m = 0; m <= boatCapacity; m++) {
            for (int c = 0; c <= boatCapacity; c++) {
                if (m + c >= 1 && m + c <= boatCapacity) {
                    moves.add(new int[]{m, c});
                }
            }
        }
        return moves;
    }

    static void solveDFSIterative() {
        List<int[]> moves = generateMoves();
        Set<State> visited = new HashSet<>();
        Deque<State> stack = new ArrayDeque<>();

        State start = new State(totalM, totalC, 0, new ArrayList<>());
        stack.push(start);

        while (!stack.isEmpty()) {
            State current = stack.pop();

            if (current.isGoal()) {
                System.out.println("\n✅ Solution Path (Iterative DFS):");
                for (State s : current.path) s.print();
                return;
            }

            if (visited.contains(current))
                continue;

            visited.add(current);

            for (int[] move : moves) {
                int dm = move[0];
                int dc = move[1];

                int mLeft = current.mLeft;
                int cLeft = current.cLeft;
                int boat = current.boat;

                if (boat == 0) {
                    mLeft -= dm;
                    cLeft -= dc;
                    boat = 1;
                } else {
                    mLeft += dm;
                    cLeft += dc;
                    boat = 0;
                }

                State next = new State(mLeft, cLeft, boat, current.path);

                if (next.isValid() && !visited.contains(next)) {
                    stack.push(next);
                }
            }
        }
        System.out.println("❌ No solution found using iterative DFS.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Missionaries and Cannibals Problem - Iterative DFS");
        System.out.print("Enter total number of Missionaries: ");
        totalM = sc.nextInt();
        System.out.print("Enter total number of Cannibals: ");
        totalC = sc.nextInt();
        System.out.print("Enter boat capacity: ");
        boatCapacity = sc.nextInt();

        if (totalM < 0 || totalC < 0 || boatCapacity < 1) {
            System.out.println("Invalid input values. Please use positive numbers.");
            return;
        }

        solveDFSIterative();
    }
}
