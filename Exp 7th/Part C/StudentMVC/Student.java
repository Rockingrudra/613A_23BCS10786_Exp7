package StudentMVC;

public class Student {
    private int studentId;
    private String name;
    private String department;
    private int marks;

    public Student() {}

    public Student(int studentId, String name, String department, int marks) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public Student(String name, String department, int marks) {
        this(0, name, department, marks);
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Dept: %s | Marks: %d",
                              studentId, name, department, marks);
    }
}
