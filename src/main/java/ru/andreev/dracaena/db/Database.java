package ru.andreev.dracaena.db;

import java.sql.*;

public class Database {

    private Connection connection = null;

    public Connection getConnection(){

        try {
            String url = "jdbc:mysql://localhost:3306/shop?serverTimezone=UTC";
            String user = "denis";
            String pass = "denis";

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public void closeStatement(Statement statement){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet){
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
