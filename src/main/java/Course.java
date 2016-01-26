import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Course{
  private int mId;
  private int mDepartmentId;
  private int mNumber;
  private String mName;

  public Course(int department_id, int number, String name) {
    mDepartmentId = department_id;
    mNumber = number;
    mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public int getNumber() {
    return mNumber;
  }

  public int getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public boolean equals(Object otherCourse) {
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) &&
        this.getNumber() == newCourse.getNumber() &&
        this.getDepartmentId() == newCourse.getDepartmentId();
    }
  }

  public static List<Course> all() {
    String sql = "SELECT courses.id AS mId, courses.name AS mName, courses.department_id AS mDepartmentId, courses.number AS mNumber FROM courses INNER JOIN departments ON courses.department_id = departments.id ORDER BY abbreviation, courses.number";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }


  public void save() {
    String sql = "INSERT INTO courses(name, department_id, number) VALUES (:name, :departmentid, :number)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .addParameter("departmentid", mDepartmentId)
        .addParameter("number", mNumber)
        .executeUpdate()
        .getKey();
    }
  }

    public static Course find(int id) {
      String sql = "SELECT id AS mId, name AS mName, department_id AS mDepartmentId, number AS mNumber FROM courses WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Course course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteEnrollments = "DELETE FROM enrollments WHERE course_id = :id;";
    con.createQuery(deleteEnrollments)
      .addParameter("id", mId)
      .executeUpdate();
    }

    try(Connection con = DB.sql2o.open()) {
    String deleteCourse = "DELETE FROM courses WHERE id = :id;";
    con.createQuery(deleteCourse)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void update(int newDepartmentId, int newNumber, String newName) {
    mDepartmentId = newDepartmentId;
    mNumber = newNumber;
    mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name = :name, number = :number, department_id = :departmentid WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("number", newNumber)
        .addParameter("departmentid", newDepartmentId)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public List<Student> getAllStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT students.id AS mId, students.name AS mName, students.enrollment_date AS mEnrollmentDate, students.department_id AS mDepartmentId FROM students INNER JOIN enrollments ON students.id = enrollments.student_id WHERE enrollments.course_id = :id";
      List<Student> studentList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Student.class);
      return studentList;
    }
  }

  public List<Student> getAllStudentsWhoPassed() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT students.id AS mId, students.name AS mName, students.enrollment_date AS mEnrollmentDate, students.department_id AS mDepartmentId FROM students INNER JOIN enrollments ON students.id = enrollments.student_id WHERE enrollments.course_id = :id AND enrollments.course_completion = true";
      List<Student> studentList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Student.class);
      return studentList;
    }
  }
}
