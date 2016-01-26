import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Department{
  private int mId;
  private String mName;
  private String mAbbreviation;

  public Department(String name, String abbreviation) {
    this.mName = name;
    this.mAbbreviation = abbreviation;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public String getAbbreviation() {
    return mAbbreviation;
  }


  @Override
  public boolean equals(Object otherDepartment) {
    if (!(otherDepartment instanceof Department)) {
      return false;
    } else {
      Department newDepartment = (Department) otherDepartment;
      return this.getName().equals(newDepartment.getName()) &&
        this.getAbbreviation().equals(newDepartment.getAbbreviation());
    }
  }

  public static List<Department> all() {
    String sql = "SELECT id AS mId, name AS mName, abbreviation AS mAbbreviation FROM departments";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Department.class);
    }
  }


  public void save() {
    String sql = "INSERT INTO departments(name, abbreviation) VALUES (:name, :abbreviation)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("abbreviation", this.mAbbreviation)
        .executeUpdate()
        .getKey();
    }
  }

    public static Department find(int id) {
      String sql = "SELECT id AS mId, name AS mName, abbreviation AS mAbbreviation FROM departments WHERE id = :id";
      try(Connection con = DB.sql2o.open()) {
        Department department = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Department.class);
      return department;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteMajorRelationship = "UPDATE students SET department_id = null WHERE department_id = :id;";
    con.createQuery(deleteMajorRelationship)
      .addParameter("id", mId)
      .executeUpdate();
    }

    try(Connection con = DB.sql2o.open()) {
    String deleteCourses = "DELETE FROM courses WHERE department_id = :id;";
    con.createQuery(deleteCourses)
      .addParameter("id", mId)
      .executeUpdate();
    }

    try(Connection con = DB.sql2o.open()) {
    String deleteDepartment = "DELETE FROM departments WHERE id = :id;";
    con.createQuery(deleteDepartment)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void update(String newName, String newAbbreviation) {
    mName = newName;
    mAbbreviation = newAbbreviation;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE departments SET name = :name, abbreviation = :abbreviation WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("abbreviation", newAbbreviation)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public List<Student> getAllStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, enrollment_date AS mEnrollmentDate, department_id AS mDepartment FROM students WHERE department_id = :id";
      List<Student> studentList = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Student.class);
      return studentList;
    }
  }

  public List<Course> getAllCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, department_id AS mDepartmentId, number AS mNumber FROM courses WHERE department_id = :id";
      List<Course> courses = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Course.class);
      return courses;
    }
  }

  public List<Student> getStudentsWhoHaveNotTakenCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT students.id AS mId, students.name AS mName, students.enrollment_date AS mEnrollmentDate, students.department_id AS mDepartmentId FROM students LEFT OUTER JOIN (SELECT * FROM enrollments INNER JOIN courses ON enrollments.course_id = courses.id WHERE courses.department_id = :id) AS alldepartmentenrollments ON students.id = alldepartmentenrollments.student_id WHERE alldepartmentenrollments.name IS null;";

      List<Student> students = con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Student.class);
      return students;
    }
  }
}
