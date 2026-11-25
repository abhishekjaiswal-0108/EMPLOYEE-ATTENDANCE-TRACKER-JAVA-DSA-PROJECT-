import java.util.HashMap;
import java.util.Scanner;

/**
 * Main.java  -  Employee Attendance Tracker (single-file version)
 *
 * Core Java only. Contains 3 parts in ONE file:
 *   1. Employee           -> data model (one attendance record)
 *   2. AttendanceManager  -> ARRAY (manual resize) + HASHMAP + SEARCH logic
 *   3. Main               -> console menu
 *
 * Priority: 1 = High, 2 = Medium, 3 = Low
 * "Complete" means attendance is marked.
 * n = number of records stored.
 */
public class Main {

    // =====================================================================
    // PART 1: Employee (data model)
    // =====================================================================
    static class Employee {
        private int id;
        private String name;
        private String department;
        private int priority;
        private boolean completed;

        Employee(int id, String name, String department, int priority) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.priority = priority;
            this.completed = false;
        }

        int getId() { return id; }
        String getName() { return name; }
        void markCompleted() { this.completed = true; }

        /** Converts priority number to readable text. */
        String getPriorityLabel() {
            if (priority == 1) return "High";
            if (priority == 2) return "Medium";
            return "Low";
        }

        @Override
        public String toString() {
            return String.format("ID: %-4d | %-15s | %-12s | Priority: %-6s | Attendance: %s",
                    id, name, department, getPriorityLabel(),
                    completed ? "Marked" : "Pending");
        }
    }

    // =====================================================================
    // PART 2: AttendanceManager (array + HashMap + searching)
    // =====================================================================
    static class AttendanceManager {

        private Employee[] records = new Employee[2];  // starts small, grows by doubling
        private int size = 0;                          // how many records are used
        private int nextId = 1;
        private HashMap<Integer, Employee> idMap = new HashMap<>();  // id -> Employee

        /** Doubles array capacity by copying manually. Time: O(n), but rare. */
        private void growArray() {
            Employee[] bigger = new Employee[records.length * 2];
            for (int i = 0; i < size; i++) {
                bigger[i] = records[i];
            }
            records = bigger;
        }

        /** ADD. Time: O(1) amortized (array append + HashMap put). */
        Employee add(String name, String department, int priority) {
            if (size == records.length) growArray();
            Employee e = new Employee(nextId++, name, department, priority);
            records[size++] = e;
            idMap.put(e.getId(), e);
            return e;
        }

        /** VIEW all. Time: O(n). */
        void viewAll() {
            if (size == 0) {
                System.out.println("No records found.");
                return;
            }
            for (int i = 0; i < size; i++) {
                System.out.println(records[i]);
            }
        }

        /**
         * DELETE by ID.
         * HashMap check is O(1); finding the index and shifting the array
         * left is O(n). Total: O(n).
         */
        boolean delete(int id) {
            if (!idMap.containsKey(id)) return false;
            int index = -1;
            for (int i = 0; i < size; i++) {
                if (records[i].getId() == id) { index = i; break; }
            }
            for (int i = index; i < size - 1; i++) {
                records[i] = records[i + 1];     // shift left
            }
            records[--size] = null;
            idMap.remove(id);                     // O(1)
            return true;
        }

        /** SEARCH #1: Linear search by name (partial, case-insensitive). Time: O(n). */
        void searchByNameLinear(String keyword) {
            boolean found = false;
            for (int i = 0; i < size; i++) {
                if (records[i].getName().toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println(records[i]);
                    found = true;
                }
            }
            if (!found) System.out.println("No match found.");
        }

        /** SEARCH #2: HashMap lookup by ID. Time: O(1) average. */
        Employee searchById(int id) {
            return idMap.get(id);
        }

        /** COMPLETE (mark attendance) using HashMap. Time: O(1) average. */
        boolean complete(int id) {
            Employee e = idMap.get(id);
            if (e == null) return false;
            e.markCompleted();
            return true;
        }
    }

    // =====================================================================
    // PART 3: Console menu
    // =====================================================================
    private static Scanner sc = new Scanner(System.in);

    /** Reads a whole number safely; returns -1 on invalid input. */
    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static void printMenu() {
        System.out.println("\n===== EMPLOYEE ATTENDANCE TRACKER =====");
        System.out.println("1. Add employee record");
        System.out.println("2. View all records");
        System.out.println("3. Delete record");
        System.out.println("4. Search by name (Linear Search)");
        System.out.println("5. Search by ID (HashMap)");
        System.out.println("6. Mark attendance (complete)");
        System.out.println("0. Exit");
    }

    public static void main(String[] args) {
        AttendanceManager manager = new AttendanceManager();
        int choice;

        do {
            printMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    String name = readText("Name: ");
                    String dept = readText("Department: ");
                    int pr = readInt("Priority (1=High, 2=Medium, 3=Low): ");
                    if (name.isEmpty() || pr < 1 || pr > 3) {
                        System.out.println("Invalid input. Record not added.");
                    } else {
                        Employee e = manager.add(name, dept, pr);
                        System.out.println("Added with ID " + e.getId());
                    }
                    break;
                case 2:
                    manager.viewAll();
                    break;
                case 3:
                    int delId = readInt("Enter ID to delete: ");
                    System.out.println(manager.delete(delId) ? "Deleted." : "ID not found.");
                    break;
                case 4:
                    manager.searchByNameLinear(readText("Enter name/keyword: "));
                    break;
                case 5:
                    Employee found = manager.searchById(readInt("Enter ID: "));
                    System.out.println(found != null ? found : "ID not found.");
                    break;
                case 6:
                    int cId = readInt("Enter ID to mark attendance: ");
                    System.out.println(manager.complete(cId) ? "Attendance marked." : "ID not found.");
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }
}
