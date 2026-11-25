import java.util.Scanner;

/**
 * Main.java
 * Console menu for the Employee Attendance Tracker.
 */
public class Main {

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
