package company;

import java.sql.*;

public class DBManager {

    private Connection connection;
    private final String url;
    private final String user;
    private final String password;

    DBManager (String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        createDatabase();
    }

    private void createDatabase() {
        this.connect();
        String createDB = "CREATE DATABASE IF NOT EXISTS diary";

        try (Statement statement = this.connection.createStatement()) {

            DatabaseMetaData exist = this.connection.getMetaData();
            ResultSet checkIfExist = exist.getCatalogs();
            boolean hasDb = true;
            while (checkIfExist.next()) {
                String dbName = checkIfExist.getString(1);
                if(dbName.equals("diary")) {
                    hasDb = false;
                    break;
                }
            }

            if(hasDb) {
                System.out.println("Creating database...");
                statement.executeUpdate(createDB);
                System.out.println("Database is created successfully!");
            } else {
                System.out.println("Database with this name has already exist!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMetaData() {
        try {
            DatabaseMetaData dbMetData = this.connection.getMetaData();
            String productName = dbMetData.getDatabaseProductName();
            System.out.println("DataBase: " + productName);
            String productVersion = dbMetData.getDatabaseProductVersion();
            System.out.println("Version: " + productVersion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection () {
        return this.connection;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error loading driver: " + e);
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
