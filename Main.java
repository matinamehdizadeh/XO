import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Daneshgah {
    public static void main(String[] args) {
        int endRegestration = 0;
        int endSemester = 0;
        int isMArked = 0;
        int startRegester = 0;
        Scanner scanner = new Scanner(System.in);
        String init;
        int finish = 0;
        while (finish == 0) {
            init = scanner.nextLine();
            String[] split = init.split(" ");
            if (split[0].matches("addCourse") && endRegestration == 0 && endSemester == 0 && startRegester == 0) {

                if (split[1].matches("[0-9]{5}")) {
                    int coursID = Integer.parseInt(split[1]);
                    int exist = -1;
                    for (int i = 0; i < Course.getCourses().size(); i++) {
                        if (Course.getCourses().get(i).getCourseID() == coursID) {
                            exist = i;
                            break;
                        }
                    }
                    if (exist == -1) {
                        Course course = new Course();
                        course.setCourseID(coursID);
                        Course.addNewCourses(course);
                        int unit = Integer.parseInt(split[2]);
                        course.setUnit(unit);
                    }
                }

            } else if (split[0].matches("addStudent") && endRegestration == 0 && endSemester == 0 && startRegester == 0) {

                if (split[1].matches("[0-9]{5}")) {
                    int studentID = Integer.parseInt(split[1]);
                    int exist = -1;
                    for (int i = 0; i < Student.students.size(); i++) {
                        if (Student.students.get(i).getStudentID() == studentID) {
                            exist = i;
                            break;
                        }
                    }
                    if (exist == -1) {
                        Student student = new Student();
                        student.setStudentID(studentID);
                        Student.addNewStudent(student);
                    }
                }

            } else if (split[0].matches("addLecturer")&& endRegestration == 0 && endSemester == 0 && startRegester == 0) {

                if (split[1].matches("[0-9]{5}")) {
                    int lecturerID = Integer.parseInt(split[1]);
                    int exist = -1;
                    for (int i = 0; i < Lecturer.getLecturers().size(); i++) {
                        if (Lecturer.getLecturers().get(i).getLecturerID() == lecturerID) {
                            exist = i;
                            break;
                        }
                    }
                    if (exist == -1) {
                        Lecturer lecturer = new Lecturer();
                        lecturer.setLecturerID(lecturerID);
                        Lecturer.addNewLecturer(lecturer);

                        for (int i = 2; i < split.length; i++) {
                            int courseID = Integer.parseInt(split[i]);
                            if (courseID >= 10000 && courseID <= 99999) {
                                lecturer.addCourse(courseID, lecturerID);
                            }
                        }
                    }
                }

            }else if (init.indexOf("register") > -1 && endRegestration == 0 && endSemester == 0 && startRegester == 1) {

                int studentID = Integer.parseInt(split[0]);
                for (int i = 2; i < split.length; i++) {
                    int courseID = Integer.parseInt(split[i]);
                    Student.regesterCourse(studentID, courseID);
                }
            } else if (init.indexOf("capacitate") >-1 && endRegestration == 0 && endSemester == 0 && startRegester == 1) {

                int lecturerID = Integer.parseInt(split[0]);
                int courseID = Integer.parseInt(split[2]);
                int number = Integer.parseInt(split[3]);
                Lecturer.addCapacity(lecturerID, courseID, number);

            } else if (init.matches("end registration") && endRegestration == 0 && endSemester == 0 && startRegester == 1) {

                endRegestration =1;
                Student temp = new Student();
                temp.deletStudent();
                Course tempCourse = new Course();
                tempCourse.deleteCourse();

            } else if (init.indexOf("mark") > -1 && endRegestration == 1 && endSemester == 0 && startRegester == 1) {
                isMArked = 1;
                if (init.indexOf("-all") > -1) {

                    int lecturerID = Integer.parseInt(split[0]);
                    int courseID = Integer.parseInt(split[2]);
                    double mark = Double.parseDouble(split[3]);
                    Course temp = new Course();
                    if (temp.findCourse(courseID) != null) {


                        Lecturer tempLecturer = new Lecturer();
                        for (int i = 0; i < Lecturer.getLecturers().size(); i++) {
                            tempLecturer = Lecturer.getLecturers().get(i);
                            if (tempLecturer.getLecturerID() == lecturerID) {
                                for (int k = 0; k < tempLecturer.getCourses().size(); k++){
                                    if (tempLecturer.getCourses().get(k).getCourseID() == courseID){
                                        int lenght = temp.findCourse(courseID).getNumberOfRegesteredStudents();
                                        for (int j = 0; j < lenght; j++) {
                                            int studentID = temp.findCourse(courseID).getStudents().get(j).getStudentID();
                                            Lecturer.setMark(tempLecturer, courseID, mark, studentID);
                                        }
                                        break;
                                    }
                                }

                            }
                        }
                    }
                } else {

                    if (split[0].matches("\\d+") && split[2].matches("\\d+")) {
                        int lecturerID = Integer.parseInt(split[0]);
                        int courseID = Integer.parseInt(split[2]);

                        Course temp = new Course();
                        Student tempStudent = new Student();
                        if (temp.findCourse(courseID) != null) {
                            int lenght = temp.findCourse(courseID).getNumberOfRegesteredStudents();
                            int number = 0;
                            for (int i = 1; i <= lenght + number; i++) {
                                int isCheck = 0;
                                if (split[2*i + 1].matches("\\d+")) {
                                    int studentID = Integer.parseInt(split[2 * i + 1]);

                                    if (tempStudent.findStudent(studentID) != null) {
                                        for (int j = 0; j < tempStudent.findStudent(studentID).getStudentCourses().size(); j++) {
                                            if (tempStudent.findStudent(studentID).getStudentCourses().get(j).getCourseID() == courseID) {
                                                isCheck = 1;
                                                break;
                                            }
                                        }
                                    }

                                    double mark = Double.parseDouble(split[2 * i + 2]);
                                    Lecturer tempLecturer = new Lecturer();

                                    for (int k = 0; k < Lecturer.getLecturers().size(); k++) {
                                        tempLecturer = Lecturer.getLecturers().get(k);
                                        if (tempLecturer.getLecturerID() == lecturerID) {
                                            for (int a = 0; a < tempLecturer.getCourses().size(); a++) {
                                                if (tempLecturer.getCourses().get(a).getCourseID() == courseID) {
                                                    Lecturer.setMark(tempLecturer, courseID, mark, studentID);
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

            } else if (split[0].matches("W") && endRegestration == 1 && endSemester == 0 && startRegester == 1) {

                int studentID = Integer.parseInt(split[2]);
                int courseID = Integer.parseInt(split[1]);
                Student.deletCourse(studentID, courseID, isMArked);

            } else if (init.matches("end semester") && endRegestration == 1 && endSemester == 0 && startRegester == 1) {
                endSemester = 1;
            } else if (init.matches("start semester") && endRegestration == 0 && endSemester == 0 && startRegester == 0) {
                startRegester = 1;
            } else if (split[0].matches("showAverage")&& endRegestration == 1 && endSemester == 1 && startRegester == 1) {

                int studentID = Integer.parseInt(split[1]);
                int exist = -1;
                for (int i = 0; i < Student.students.size(); i++) {
                    if (Student.students.get(i).getStudentID() == studentID) {
                        exist = i;
                        break;
                    }
                }
                if (exist != -1) {
                    if ((Student.students.get(exist).getAverage() * 10) % 10 == 0)
                        System.out.println((int) Student.students.get(exist).getAverage());
                    else
                        System.out.println(Student.students.get(exist).getAverage());
                } else {
                    System.out.println("shoma daneshjoo nistid");
                }

            } else if (split[0].matches("showRanks") && endRegestration == 1 && endSemester == 1 && startRegester == 1) {
                if (init.indexOf("-all") > -1) {
                    double[] averages = new double[Student.students.size()];
                    int[] tempStudentID = new int[Student.students.size()];
                    for (int i = 0; i < Student.students.size(); i++) {
                        averages[i] = Student.students.get(i).getAverage();
                        tempStudentID[i] = Student.students.get(i).getStudentID();
                    }
                    for (int j = 0; j < Student.students.size(); j++) {
                        for (int k = 0; k < Student.students.size(); k++) {
                            if (averages[j] > averages[k]) {
                                double sortMark = averages[j];
                                int sortStudentID = tempStudentID[j];
                                averages[j] = averages[k];
                                tempStudentID[j] = tempStudentID[k];
                                averages[k] = sortMark;
                                tempStudentID[k] = sortStudentID;
                            }
                        }
                    }
                    if (averages[0] == averages[1]) {
                        if (tempStudentID[1] > tempStudentID[0]) {
                            System.out.printf("%d ", tempStudentID[0]);
                            if (averages[1] == averages[2]) {
                                if (tempStudentID[2] > tempStudentID[1]) {
                                    System.out.printf("%d ", tempStudentID[1]);
                                    System.out.printf("%d ", tempStudentID[2]);
                                    System.out.printf("\n");

                                } else {
                                    System.out.printf("%d ", tempStudentID[2]);
                                    System.out.printf("%d ", tempStudentID[1]);
                                    System.out.printf("\n");

                                }
                            } else {
                                System.out.printf("%d ", tempStudentID[1]);
                                System.out.printf("%d ", tempStudentID[2]);
                                System.out.printf("\n");

                            }
                        } else {
                            System.out.printf("%d ", tempStudentID[1]);
                            if (averages[0] == averages[2]) {
                                if (tempStudentID[2] > tempStudentID[0]) {
                                    System.out.printf("%d ", tempStudentID[0]);
                                    System.out.printf("%d ", tempStudentID[2]);
                                    System.out.printf("\n");

                                } else {
                                    System.out.printf("%d ", tempStudentID[2]);
                                    System.out.printf("%d ", tempStudentID[0]);
                                    System.out.printf("\n");

                                }
                            } else {
                                System.out.printf("%d ", tempStudentID[0]);
                                System.out.printf("%d ", tempStudentID[2]);
                                System.out.printf("\n");

                            }
                        }
                    } else if (averages[1] == averages[2]) {
                        System.out.printf("%d ", tempStudentID[0]);
                        if (tempStudentID[2] > tempStudentID[1]) {
                            System.out.printf("%d ", tempStudentID[1]);
                            System.out.printf("%d ", tempStudentID[2]);
                            System.out.printf("\n");

                        } else {
                            System.out.printf("%d ", tempStudentID[2]);
                            System.out.printf("%d ", tempStudentID[1]);
                            System.out.printf("\n");

                        }
                    } else {
                        System.out.printf("%d ", tempStudentID[0]);
                        System.out.printf("%d ", tempStudentID[1]);
                        System.out.printf("%d ", tempStudentID[2]);
                        System.out.printf("\n");

                    }
                } else {
                    int courseID = Integer.parseInt(split[1]);
                    Course temp = new Course();
                    if (temp.findCourse(courseID) != null) {
                        temp.findCourse(courseID).showRanks();
                    }
                }
            } else if (split[0].matches("showCourse") && endRegestration == 1 && endSemester == 1 && startRegester == 1) {
                int courseID = Integer.parseInt(split[1]);
                Course temp = new Course();
                if (temp.findCourse(courseID) != null) {
                    temp.findCourse(courseID).showCourses(init);
                } else
                    System.out.println("shoma daneshjoo nistid");
            } else if (init.indexOf("endShow")> -1) {
                finish = 1;
                break;
            }
        }
    }
}


class Lecturer {
    private static ArrayList<Lecturer> lecturers = new ArrayList<>();
    private int lecturerID;
    private ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Lecturer> getLecturers(){
        return lecturers;
    }
    public static void addNewLecturer(Lecturer lecturer){
        lecturers.add(lecturer);
    }
    public void addCourse(int courseID, int lecturerID){
        Lecturer temp = new Lecturer();
        Course tempCourse = new Course();
        for (int i = 0; i < lecturers.size(); i++){
            temp = lecturers.get(i);
            if (temp.lecturerID == lecturerID){
                if(tempCourse.findCourse(courseID)!= null){
                    temp.courses.add(tempCourse.findCourse(courseID));
                    return;
                }
            }
        }

    }
    public void setLecturerID(int lecturerID){
        this.lecturerID = lecturerID;
    }
    public ArrayList<Course> getCourses(){
        return courses;
    }
    public int getLecturerID(){
        return lecturerID;
    }
    public static void addCapacity(int lecturerID, int courseID, int number){
        Lecturer temp = new Lecturer();
        for (int i = 0; i < lecturers.size(); i++){
            temp = lecturers.get(i);
            if (temp.lecturerID == lecturerID){
                for (int j = 0; j < temp.courses.size(); j++){
                    if(temp.courses.get(j).getCourseID() == courseID){
                        temp.courses.get(j).addCapacity(number);
                        return;
                    }
                }
            }
        }
    }
    public static void setMark(Lecturer temp, int courseID, double mark, int studentID){
        Student tempStudent = new Student();
        for (int j = 0; j < temp.courses.size(); j++) {
            if (temp.courses.get(j).getCourseID() == courseID) {
                if(tempStudent.findStudent(studentID) != null){
                    tempStudent.findStudent(studentID).addMark(mark ,temp.courses.get(j).getUnit() );
                    temp.courses.get(j).setMark(studentID, mark);
                    return;
                }
            }
        }
    }
}


class Student {
    private int units = 0;
    private int numberOfWs = 0;
    private int numberOfmarks = 0;
    private double marks = 0;
    public static ArrayList<Student> students = new ArrayList<>();
    private int studentID;
    private ArrayList<Course> studentCourses = new ArrayList<>();

    public static void deletCourse(int studentID, int courseID, int isMArked) {

        Student temp = new Student();
        for (int i = 0; i < students.size(); i++) {
            temp = students.get(i);
            if (temp.studentID == studentID) {
                Course tempCourse = new Course();
                if (tempCourse.findCourse(courseID) != null) {
                    temp.numberOfWs += tempCourse.findCourse(courseID).getUnit();
                    if (temp.numberOfWs < 4) {

                        for (int j = 0; j < temp.studentCourses.size(); j++) {
                            if (temp.studentCourses.get(j).getCourseID() == courseID) {
                                if (isMArked == 1) {
                                    double mark = temp.studentCourses.get(j).getMark(temp.getStudentID());
                                    temp.setMarks(mark, temp.studentCourses.get(j).getUnit());
                                    temp.studentCourses.get(j).setScore(mark, temp.studentID);
                                }
                                temp.studentCourses.get(j).decrementNumberOfRegesteredStudents();
                                temp.studentCourses.get(j).getStudents().remove(temp);
                                temp.studentCourses.remove(temp.studentCourses.get(j));
                                return;
                            }
                        }
                    }
                }
            }
        }

    }

    public void setStudentID(int studentID) {

        this.studentID = studentID;
    }

    public void addMark(double mark, int unit) {
        numberOfmarks += unit;
        mark = mark * unit;
        marks += mark;
    }

    public double getAverage() {
        double average = Math.round(marks / numberOfmarks * 10) / 10.0;
        return average;
    }



    public void setMarks(double marks, int unit) {
        this.marks -= (marks*unit);
        this.numberOfmarks-=unit;

    }

    public static void addNewStudent(Student student) {
        students.add(student);
    }

    public static ArrayList getStudents() {
        return students;
    }

    public static void regesterCourse(int studentID, int courseID) {
        Student temp = new Student();
        Course tempCourse = new Course();
        for (int i = 0; i < students.size(); i++) {
            temp = students.get(i);
            if (studentID == temp.studentID) {
                for (int j = 0; j < temp.studentCourses.size(); j++) {
                    if (temp.studentCourses.get(j).getCourseID() == courseID)
                        return;
                }
                if (tempCourse.findCourse(courseID) != null) {
                    if (tempCourse.findCourse(courseID).getNumberOfRegesteredStudents() <
                            tempCourse.findCourse(courseID).getCapacity()) {
                        temp.addCourse(tempCourse.findCourse(courseID));
                        tempCourse.findCourse(courseID).addStudent(temp);
                        tempCourse.findCourse(courseID).incrementNumberOfRegesteredStudents();
                        return;
                    }
                }
            }
        }
    }

    public void addCourse(Course course) {
        studentCourses.add(course);
        units += course.getUnit();
    }


    public void deletStudent() {
        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).units < 12) {
                for (int j = 0; j < students.get(i).studentCourses.size(); j++) {
                    students.get(i).studentCourses.get(j).decrementNumberOfRegesteredStudents();
                    students.get(i).studentCourses.get(j).getStudents().remove(students.get(i));
                }
                students.remove(students.get(i));
                i = 0;
                for (int j = 0; j < students.size(); j++){
                    if (students.get(j) == students.get(i)){
                    }
                }
            }
        }
    }

    public int getStudentID() {
        return studentID;
    }

    public ArrayList<Course> getStudentCourses() {
        return studentCourses;
    }

    public Student findStudent(int number) {
        Student student = new Student();
        for (int i = 0; i < students.size(); i++) {
            student = students.get(i);
            if (number == student.studentID) {
                return student;
            }
        }
        student = null;
        return student;
    }
}

class Course {
    public Map< Integer, Double> mapMark = new HashMap< Integer, Double>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private  ArrayList<Student> students = new ArrayList<>();
    private int courseID;
    private int unit;
    private int capacity = 15;
    private int numberOfmarks = 0;
    private int numberOfRegisteredStudents = 0;
    private double score = 0;
    public static void addNewCourses(Course course){
        courses.add(course);
    }
    public double getMark(int studentID){
        return mapMark.get(studentID);
    }

    public void setScore(double score , int studentID) {
        mapMark.remove(studentID);
        numberOfmarks--;
        this.score -= score;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public  ArrayList<Student> getStudents(){
        return students;
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public void setMark(int studentID, double mark){
        mapMark.put(studentID, mark);
        score+=mark;
        numberOfmarks++;
    }
    public void getAverage(){
        double average = Math.round(score/numberOfmarks*10)/10.0;
        if ((average *10) % 10 == 0)
            System.out.println((int)average);
        else
            System.out.println(average);
    }
    public void deleteCourse(){
        Course temp;
        for (int i = 0; i < courses.size(); i++) {
            temp = courses.get(i);
            if (temp.numberOfRegisteredStudents < 3) {
                courses.remove(temp);
                i = 0;
            }
        }
    }
    public void showRanks(){
        double[] tempMark = new double[numberOfmarks];
        int[] tempStudentID = new int[numberOfmarks];
        Set< Map.Entry< Integer, Double> > st = mapMark.entrySet();
        int i = 0;
        for (Map.Entry<Integer, Double> me:st) {
            tempMark[i] = me.getValue();
            tempStudentID[i] = me.getKey();
            i++;
        }
        for (int j = 0; j < numberOfmarks; j++) {
            for (int k = 0; k < numberOfmarks; k++) {
                if (tempMark[j] > tempMark[k]) {
                    double sortMark = tempMark[j];
                    int sortStudentID = tempStudentID[j];
                    tempMark[j] = tempMark[k];
                    tempStudentID[j] = tempStudentID[k];
                    tempMark[k] = sortMark;
                    tempStudentID[k] = sortStudentID;
                }
            }
        }

        if (tempMark[0] == tempMark[1]){
            if (tempStudentID[1] > tempStudentID[0]){
                System.out.printf("%d ",tempStudentID[0]);
                if (tempMark[1] == tempMark[2]){
                    if (tempStudentID[2] > tempStudentID[1]) {
                        System.out.printf("%d ",tempStudentID[1]);
                        System.out.printf("%d ",tempStudentID[2]);
                        System.out.printf("\n");
                    }else {
                        System.out.printf("%d ",tempStudentID[2]);
                        System.out.printf("%d ",tempStudentID[1]);
                        System.out.printf("\n");
                    }
                }else {
                    System.out.printf("%d ",tempStudentID[1]);
                    System.out.printf("%d ",tempStudentID[2]);
                    System.out.printf("\n");
                }
            }else {
                System.out.printf("%d ",tempStudentID[1]);
                if (tempMark[0] == tempMark[2]){
                    if (tempStudentID[2] > tempStudentID[0]) {
                        System.out.printf("%d ",tempStudentID[0]);
                        System.out.printf("%d ",tempStudentID[2]);
                        System.out.printf("\n");
                    }else {
                        System.out.printf("%d ",tempStudentID[2]);
                        System.out.printf("%d ",tempStudentID[0]);
                    }
                }else {
                    System.out.printf("%d ",tempStudentID[0]);
                    System.out.printf("%d ",tempStudentID[2]);
                    System.out.printf("\n");
                }
            }
        }else if (tempMark[1] == tempMark[2]){
            System.out.printf("%d ",tempStudentID[0]);
            if (tempStudentID[2] > tempStudentID[1]) {
                System.out.printf("%d ",tempStudentID[1]);
                System.out.printf("%d ",tempStudentID[2]);
                System.out.printf("\n");
            } else {
                System.out.printf("%d ",tempStudentID[2]);
                System.out.printf("%d ",tempStudentID[1]);
                System.out.printf("\n");
            }
        } else {
            System.out.printf("%d ",tempStudentID[0]);
            System.out.printf("%d ",tempStudentID[1]);
            System.out.printf("%d ",tempStudentID[2]);
            System.out.printf("\n");
        }


    }
    public int getNumberOfRegesteredStudents(){
        return numberOfRegisteredStudents;
    }
    public void incrementNumberOfRegesteredStudents(){
        numberOfRegisteredStudents++;

    }
    public void decrementNumberOfRegesteredStudents(){
        numberOfRegisteredStudents--;
    }
    public void setCourseID(int courseID){
        this.courseID = courseID;
    }
    public int getCourseID(){
        return courseID;
    }
    public void addCapacity(int number){
        capacity += number;
    }
    public void setUnit(int unit){
        this.unit = unit;
    }
    public int getUnit(){
        return unit;
    }
    public int getCapacity(){
        return capacity;
    }
    public void showCourses(String string){
        String[] split = string.split(" ");
        int courseShowID = Integer.parseInt(split[1]);
        int setNumberForCourse = -1;
        for (int i = 0; i < courses.size(); i++){
            if (courses.get(i).courseID == courseShowID){
                setNumberForCourse = i;
                break;
            }
        }
        if (setNumberForCourse == -1)
            return;
        if (split[2].matches("students")){
            for (int i = 0; i < students.size(); i++){
                for (int  j = 0; j < students.size(); j++){
                    if (students.get(i).getStudentID() < students.get(j).getStudentID()){
                        Collections.swap(students, i ,j);
                    }
                }
            }
            for (int i = 0; i < students.size(); i++){
                System.out.printf("%d ",students.get(i).getStudentID());
            }
            System.out.printf("\n");
        }else if (split[2].matches("lecturer")){
            for (int i = 0; i < Lecturer.getLecturers().size(); i++){
                for (int j = 0; j < Lecturer.getLecturers().get(i).getCourses().size(); j++){
                    if (Lecturer.getLecturers().get(i).getCourses().get(j).getCourseID() == courseShowID){
                        System.out.println(Lecturer.getLecturers().get(i).getLecturerID());
                        return;
                    }
                }
            }
        }else if (split[2].matches("capacity")){
            System.out.println(courses.get(setNumberForCourse).capacity);
        }else if (split[2].matches("average")){
            courses.get(setNumberForCourse).getAverage();
        }
    }

    public Course findCourse(int number){
        Course course = new Course();
        for (int i = 0; i < courses.size(); i++) {
            course = courses.get(i);
            if (number == course.courseID) {
                return course;
            }
        }
        course = null;
        return course;
    }

}