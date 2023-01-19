import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;

public class StudentRegister {

    
    private final ArrayList<Student> students;
    private final ArrayList<Course> courses;
    private final ArrayList<Attainment> attainments;

    public StudentRegister() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        attainments = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        Collections.sort(students, (a,b) -> a.getName().compareToIgnoreCase(b.getName()));
        return students;
    }

    public ArrayList<Course> getCourses() {
        Collections.sort(courses, (a,b) -> a.getName().compareTo(b.getName()));
        return courses;
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addCourse(Course course) {
        courses.add(course);
    }
    
    public void addAttainment(Attainment att) {
        attainments.add(att);
    }
    
    public void printStudentAttainments(String studentNumber, String ... order ) {
        boolean checker = false;
        for (var stud : students) {
            if (stud.getStudentNumber().equals(studentNumber)) {
                Student  student = stud;
                checker = true;
                System.out.format("%s (%s):%n",student.getName(), studentNumber);
                break;
            }
        }
        
        if (!checker) {
            System.out.format("Unknown student number: %s%n", studentNumber);
        }
        ArrayList<Attainment> studentAtt = new ArrayList<>();
        for (var att : attainments) {          
            if (att.getStudentNumber().equals(studentNumber)) {
                studentAtt.add(att);
            }
        }
        
        // determining the order for printing
        if (order.length > 0) {
            if (order[0].equals("by name")) {
                Collections.sort(studentAtt, (a,b) -> {
                    String courseNameA = " ";
                    String courseNameB = " ";
                    for (var cour : courses) {
                        if (a.getCourseCode().equals(cour.getCode())){
                            courseNameA = cour.getName();
                        }
                        if (b.getCourseCode().equals(cour.getCode())){
                            courseNameB = cour.getName();
                        }
                    }
                    return courseNameA.compareTo(courseNameB);
                });
            }
            if (order[0].equals("by code")){
                Collections.sort(studentAtt, (a,b) -> a.getCourseCode().compareTo(b.getCourseCode()));
            }
        }
        
               
        for (var att : studentAtt) {
            for (var cour : courses) {
                if (att.getCourseCode().equals(cour.getCode())) {
                    System.out.format("  %s %s: %d%n", att.getCourseCode(), cour.getName(), att.getGrade());
                }
            }
            
        }
        
        
    }
    
    
    public static void main(String[] args) {
        /*
        StudentRegister register = new StudentRegister();
        Student stud1 = new Student("jaakko","235352");
        Student stud2 = new Student("aapo","235352");
        
        register.addStudent(stud1);
        register.addStudent(stud2);
        
        register.printStudentAttainments("235352", " ");
        
        for (var stud : register.getStudents()) {
            System.out.println(stud.getName());
        }
        */
    }

    
}
