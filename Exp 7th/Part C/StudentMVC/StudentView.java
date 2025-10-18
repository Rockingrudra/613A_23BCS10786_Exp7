package StudentMVC;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = null;

        try {
            controller = new StudentController();
            System.out.println("Connected to database.");
        } catch (Exception e) {
            System.err.println("Failed to connect to DB:");
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            System.out.println("\n===== Student Management =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student By ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Department: ");
                    String dept = sc.nextLine().trim();
                    System.out.print("Marks: ");
                    int marks = Integer.parseInt(sc.nextLine().trim());
                    boolean ok = controller.addStudent(new Student(name, dept, marks));
                    System.out.println(ok ? "Student added." : "Failed to add student.");
                }
                case 2 -> {
                    List<Student> students = controller.getAllStudents();
                    if (students.isEmpty()) System.out.println("No students found.");
                    else students.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    int id = Integer.parseInt(sc.nextLine().trim());
                    Student s = controller.getStudentById(id);
                    System.out.println(s == null ? "Not found." : s);
                }
                case 4 -> {
                    System.out.print("Enter Student ID to update: ");
                    int id = Integer.parseInt(sc.nextLine().trim());
                    Student existing = controller.getStudentById(id);
                    if (existing == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.print("New name (leave blank to keep '" + existing.getName() + "'): ");
                    String newName = sc.nextLine().trim();
                    if (!newName.isEmpty()) existing.setName(newName);

                    System.out.print("New department (leave blank to keep '" + existing.getDepartment() + "'): ");
                    String newDept = sc.nextLine().trim();
                    if (!newDept.isEmpty()) existing.setDepartment(newDept);

                    System.out.print("New marks (leave blank to keep '" + existing.getMarks() + "'): ");
                    String marksLine = sc.nextLine().trim();
                    if (!marksLine.isEmpty()) existing.setMarks(Integer.parseInt(marksLine));

                    boolean updated = controller.updateStudent(existing);
                    System.out.println(updated ? "Updated successfully." : "Update failed.");
                }
                case 5 -> {
                    System.out.print("Enter Student ID to delete: ");
                    int id = Integer.parseInt(sc.nextLine().trim());
                    boolean deleted = controller.deleteStudent(id);
                    System.out.println(deleted ? "Deleted." : "Delete failed / not found.");
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    controller.close();
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("Choose 1-6.");
            }
        }
    }
}
