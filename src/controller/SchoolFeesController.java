package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
