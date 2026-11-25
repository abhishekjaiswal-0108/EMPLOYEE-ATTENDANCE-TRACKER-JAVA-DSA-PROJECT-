import java.util.HashMap;

/**
 * AttendanceManager.java
 * Core logic. Uses:
 *   - a manually resized ARRAY to store records in order
 *   - a HASHMAP (id -> Employee) for fast lookup by ID
 * n = number of records currently stored.
 */
public class AttendanceManager {

    private Employee[] records = new Employee[2];   // starts small, grows by doubling
    private int size = 0;
    private int nextId = 1;
    private HashMap<Integer, Employee> idMap = new HashMap<>();

    /** Doubles array capacity by copying manually. Time: O(n), but rare. */
    private void growArray() {
        Employee[] bigger = new Employee[records.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = records[i];
        }
        records = bigger;
    }

    /** ADD. Time: O(1) amortized (array append + HashMap put). */
    public Employee add(String name, String department, int priority) {
        if (size == records.length) growArray();
        Employee e = new Employee(nextId++, name, department, priority);
        records[size++] = e;
        idMap.put(e.getId(), e);
        return e;
    }

    /** VIEW all. Time: O(n). */
    public void viewAll() {
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
    public boolean delete(int id) {
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
    public void searchByNameLinear(String keyword) {
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
    public Employee searchById(int id) {
        return idMap.get(id);
    }

    /** COMPLETE (mark attendance) using HashMap. Time: O(1) average. */
    public boolean complete(int id) {
        Employee e = idMap.get(id);
        if (e == null) return false;
        e.markCompleted();
        return true;
    }
}
