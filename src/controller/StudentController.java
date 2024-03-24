package controller;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
            ObservableList<Student> studentList = FXCollections.observableArrayList();

            String sql = "SELECT * FROM student";

            preparedStatement = cnx.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("idStudent");
                String name = result.getString("name");
                String firstName = result.getString("firstname");
                String classe = result.getString("classRank");

                Student studentData = new Student();
                studentData.setIdStudent(id);
                studentData.setName(name);
                studentData.setFirstname(firstName);
                studentData.setClassRank(classe);
                studentList.add(studentData);

            }

            return studentList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void GetPDF() {
        try {

            String sql = "SELECT * FROM student";

            preparedStatement = cnx.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            String pdfFilename = "Liste des etudiants.pdf";
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));
            document.open();

            Font boldFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

            PdfPTable table = new PdfPTable(4);
            table.addCell(new Phrase("Id de l'etudiant", boldFont));
            table.addCell(new Phrase("Nom", boldFont));
            table.addCell(new Phrase("Prenom", boldFont));
            table.addCell(new Phrase("Classe", boldFont));

            int lastId = -1;
            String lastName = "";
            String lastFirstName = "";
            String lastClass = "";

            while (result.next()) {
                int id = result.getInt("idStudent");
                String name = result.getString("name");
                String firstName = result.getString("firstname");
                String classe = result.getString("classRank");

                if (id == lastId && name.equals(lastName) && firstName.equals(lastFirstName)
                        && classe.equals(lastClass)) {

                    continue;
                }

                table.addCell(String.valueOf(id));
                table.addCell(name);
                table.addCell(firstName);
                table.addCell(classe);

                lastId = id;
                lastName = name;
                lastFirstName = firstName;
                lastClass = classe;
            }

            document.add(table);
            document.close();
            System.out.println("PDF created successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
