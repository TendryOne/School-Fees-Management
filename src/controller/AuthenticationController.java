package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Student;
import Model.User;
import database.Database;

public class AuthenticationController {

    Database db = new Database();
    Connection cnx = db.getConnection();
    PreparedStatement preparedStatement;
    ResultSet result;

    public User GetAdminUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ? ;";
        try {
            preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, username);

            result = preparedStatement.executeQuery();
            if (result.next()) {

                User User = new User();
                User.setId(result.getInt("id_users"));
                User.setusername(result.getString("username"));
                User.setpassword(result.getString("password"));

                return User;
            } else {
                return null;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
