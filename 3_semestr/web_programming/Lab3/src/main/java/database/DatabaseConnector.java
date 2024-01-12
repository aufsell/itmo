package database;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Named
@SessionScoped
public class DatabaseConnector implements Serializable {

    private static final String URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "*";
    private static final String PASSWORD = "*";

    public Connection connect() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        // Establish the connection
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
