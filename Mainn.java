package com.Studd;
import java.util.List;
import java.util.Scanner;

//by RohanIndalkar...
public class Mainn {
    private static final String DATA_FILE = "students.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager(DATA_FILE);

        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student");
            System.out.println("5. Delete student");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    addStudentFlow(sc, manager);
                    break;
                case "2":
                    List<Student> list = manager.getAllStudents();
                    if (list.isEmpty()) System.out.println("No students found.");
                    else list.forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("Enter ID: ");
                    int idSearch = Integer.parseInt(sc.nextLine().trim());
                    Student found = manager.findById(idSearch);
                    System.out.println(found == null ? "Not found." : found);
                    break;
                case "4":
                    System.out.print("Enter ID to update: ");
                    int idUpd = Integer.parseInt(sc.nextLine().trim());
                    Student sUpd = manager.findById(idUpd);
                    if (sUpd == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.println("Leave blank to keep existing value.");
                    System.out.print("New name (" + sUpd.getName() + "): ");
                    String nn = sc.nextLine().trim(); nn = nn.isEmpty() ? null : nn;
                    System.out.print("New age (" + sUpd.getAge() + "): ");
                    String na = sc.nextLine().trim();
                    Integer newAge = na.isEmpty() ? null : Integer.parseInt(na);
                    System.out.print("New course (" + sUpd.getCourse() + "): ");
                    String nc = sc.nextLine().trim(); nc = nc.isEmpty() ? null : nc;
                    boolean updated = manager.updateStudent(idUpd, nn, newAge, nc);
                    System.out.println(updated ? "Updated." : "Update failed.");
                    break;
                case "5":
                    System.out.print("Enter ID to delete: ");
                    int idDel = Integer.parseInt(sc.nextLine().trim());
                    boolean deleted = manager.deleteStudent(idDel);
                    System.out.println(deleted ? "Deleted." : "Delete failed.");
                    break;
                case "6":
                    System.out.println("Exiting. Bye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudentFlow(Scanner sc, StudentManager manager) {
        try {
            System.out.print("ID (integer): ");
            int id = Integer.parseInt(sc.nextLine().trim());
            if (manager.findById(id) != null) {
                System.out.println("ID already exists. Use update instead.");
                return;
            }
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Course: ");
            String course = sc.nextLine().trim();

            Student s = new Student(id, name, age, course);
            if (manager.addStudent(s)) System.out.println("Student added.");
            else System.out.println("Failed to add student.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers where required.");
        }
    }
}
