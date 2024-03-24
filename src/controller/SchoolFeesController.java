package controller;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Model.SchoolFees;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolFeesController {
    SchoolFees schoolFeesModel = new SchoolFees();
    Database db = new Database();
    Connection cnx = db.getConnection();
    PreparedStatement preparedStatement;
    ResultSet result;

    public SchoolFeesController(SchoolFees schoolFees) {
        this.schoolFeesModel = schoolFees;
    }

    public ObservableList<SchoolFees> GetSchoolFeesByStudentId(Integer idStudent) {
        try {
            String sql = "SELECT * FROM school_fees WHERE idStudent = ?";

            ObservableList<SchoolFees> SchoolFeesList = FXCollections.observableArrayList();

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setInt(1, idStudent);

            result = preparedStatement.executeQuery();

            if (result != null) {
                while (result.next()) {
                    SchoolFees schoolFees = new SchoolFees();

                    schoolFees.setIdSchoolFees(result.getInt("idSchoolFees"));
                    schoolFees.setAmount(result.getInt("amount"));
                    schoolFees.setPaymentDate(result.getDate("paymentDate"));

                    SchoolFeesList.add(schoolFees);

                }

                return SchoolFeesList;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void RegisterSchoolFees(int amount, Date paymentDate, int idStudent) {
        try {
            String sql = "INSERT INTO school_fees (paymentDate , amount , idStudent) VALUES (? , ? , ?)";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setDate(1, paymentDate);

            preparedStatement.setInt(2, amount);

            preparedStatement.setInt(3, idStudent);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteSchoolFees(int idSchoolFees) {
        try {
            String sql = "DELETE FROM school_fees WHERE IdSchoolFees = ?";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setInt(1, idSchoolFees);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void UpdateSchoolFees(int amount, int idSchoolFees) {
        try {
            String sql = "UPDATE school_fees SET amount = ? WHERE idSchoolFees = ?";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, idSchoolFees);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GetPDF(int idStudent, String pdfName) {
        try {

            String sql = "SELECT * FROM student WHERE idStudent = ?";

            preparedStatement = cnx.prepareStatement(sql);

            preparedStatement.setInt(1, idStudent);

            String pdfFilename = pdfName + ".pdf";
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));
            document.open();
            Font boldFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                int id = result.getInt("idStudent");
                String firstname = result.getString("firstname");
                String classRank = result.getString("classRank");
                Paragraph PId = new Paragraph();
                PId.add(new Phrase("Id de l'etudiant : ", boldFont));
                PId.add(new Phrase(String.valueOf(id)));

                Paragraph PName = new Paragraph();
                PName.add(new Phrase("Nom : ", boldFont));
                PName.add(new Phrase(name));

                Paragraph PFirstname = new Paragraph();
                PFirstname.add(new Phrase("Prenom : ", boldFont));
                PFirstname.add(new Phrase(firstname));

                Paragraph PclassRank = new Paragraph();
                PclassRank.add(new Phrase("Classe : ", boldFont));
                PclassRank.add(new Phrase(classRank));

                // Ajouter chaque paragraphe au document
                document.add(PId);
                document.add(PName);
                document.add(PFirstname);
                document.add(PclassRank);
            }

            String sql2 = "SELECT * FROM school_fees WHERE idStudent = ?";

            preparedStatement = cnx.prepareStatement(sql2);

            preparedStatement.setInt(1, idStudent);

            result = preparedStatement.executeQuery();
            PdfPTable table = new PdfPTable(3);
            table.addCell(new Phrase("Numero de facture", boldFont));
            table.addCell(new Phrase("Date de paiement", boldFont));
            table.addCell(new Phrase("Montant", boldFont));

            while (result.next()) {
                Date paymentDate = result.getDate("paymentDate");
                int amount = result.getInt("amount");
                int idSchoolFees = result.getInt("idSchoolFees");

                table.addCell(String.valueOf(idSchoolFees));
                table.addCell(paymentDate.toString());
                table.addCell(String.valueOf(amount));

            }

            document.add(table);
            document.close();
            System.out.println("PDF created successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
