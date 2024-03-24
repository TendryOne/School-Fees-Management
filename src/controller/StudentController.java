package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Student;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentController {
    Student student = new Student();
    Database db = new Database();
    Connection cnx = db.getConnection();
    PreparedStatement preparedStatement;
    ResultSet result;

    public StudentController(Student student) {
        this.student = student;

    }

    public void addStudent(String name, String firstName, String classRank) {
        try {
            String sql = "INSERT INTO student (name,firstname, classRank) VALUES (? , ? , ?)";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, classRank);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Student> GetStudent() {
        try {

            ObservableList studentList = FXCollections.observableArrayList();

            String sql = "SELECT * FROM student";

            preparedStatement = cnx.prepareStatement(sql);

            result = preparedStatement.executeQuery();

            if (result != null) {
                while (result.next()) {
                    Student studentData = new Student();

                    studentData.setIdStudent(result.getInt("idStudent"));
                    studentData.setName(result.getString("name"));
                    studentData.setFirstname(result.getString("firstname"));
                    studentData.setClassRank(result.getString("classRank"));
                    studentList.add(studentData);

                }

                return studentList;

            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void DeleteStudent(Integer idStudent) {
        try {
            String sql = "DELETE FROM student WHERE idStudent = ?";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setInt(1, idStudent);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void UpdateStudent(Integer idStudent, String name, String firstname, String ClassRank) {
        try {
            String sql = "UPDATE student SET name = ? , firstname = ? , classRank = ? WHERE idStudent = ? ";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, ClassRank);
            preparedStatement.setInt(4, idStudent);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
