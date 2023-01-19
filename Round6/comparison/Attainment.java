
import java.util.Comparator;


public class Attainment implements Comparable<Attainment> {
    
    private String courseCode;
    private String studentNumber;
    private int grade;

    public Attainment(String courseCode, String studentNumber, int grade) {
        this.courseCode = courseCode;
        this.studentNumber = studentNumber;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public int getGrade() {
        return grade;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %d%n",courseCode, studentNumber, grade);
    }
    
    
    public static final Comparator<Attainment> CODE_STUDENT_CMP = new Comparator<>() {
        @Override
        public int compare(Attainment o1, Attainment o2) {
            if (o1.courseCode.compareTo(o2.courseCode) == 0) {
                return o1.studentNumber.compareTo(o2.studentNumber);
            } else {
                return o1.courseCode.compareTo(o2.courseCode);
            }
        }
    };
    public static final Comparator<Attainment> CODE_GRADE_CMP = new Comparator<>() {
        @Override
        public int compare(Attainment o1, Attainment o2) {
            if (o1.courseCode.compareTo(o2.courseCode) == 0) {
                if (o1.grade == o2.grade) {
                    return 0;
                } else if (o1.grade > o2.grade) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return o1.courseCode.compareTo(o2.courseCode);
            }
        }
    };

    @Override
    public int compareTo(Attainment o) {
        if (this.studentNumber.compareTo(o.studentNumber) == 0) {
            return this.courseCode.compareTo(o.courseCode);
        } else {
            return this.studentNumber.compareTo(o.studentNumber);
        }
        
    }   
}
