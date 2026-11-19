public class Student {
    private String studentId;
    private String name;
    private String email;

    public Student(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Student)) {
            return false;
        }
        Student s = (Student) other;
        return studentId.equals(s.studentId);
    }

    public int hashCode() {
        return studentId.hashCode();
    }

    public String toString() {
        return name + " (" + studentId + ")";
    }
}
