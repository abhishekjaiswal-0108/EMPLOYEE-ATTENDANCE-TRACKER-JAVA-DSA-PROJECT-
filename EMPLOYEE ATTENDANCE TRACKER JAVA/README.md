# Employee Attendance Tracker (Core Java, DSA Project)

A console-based attendance tracker built with **Core Java only** (no frameworks, no database, no external libraries).

## DSA Concepts Used
| Concept | Where | Complexity |
|---|---|---|
| Array (manual resize) | `AttendanceManager` | Add O(1) amortized, View O(n) |
| HashMap | ID lookup, mark attendance | O(1) average |
| Linear Search | Search by name | O(n) |
| Delete | Find + shift array | O(n) |

## Project Structure
```
Employee.java           - data model
AttendanceManager.java  - array + HashMap + search logic
Main.java               - console menu
```

## How to Run
```bash
javac *.java
java Main
```

## Sample Output
```
===== EMPLOYEE ATTENDANCE TRACKER =====
1. Add employee record
...
Enter choice: 1
Name: Ravi
Department: IT
Priority (1=High, 2=Medium, 3=Low): 2
Added with ID 1

Enter choice: 2
ID: 1    | Ravi            | IT           | Priority: Medium | Attendance: Pending
```

## Restrictions Followed
No Spring Boot, no database, no external libraries, no built-in `LinkedList`, no `Collections.sort()`.
