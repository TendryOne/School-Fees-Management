package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Result;

public class Database {

    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/school";
    private String username = "root";
    private String pwd = "root";

    public Database() {
        try {
            this.connection = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connexion au Db OK");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
