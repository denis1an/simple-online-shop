package ru.andreev.dracaena.dao;

import ru.andreev.dracaena.db.Database;
import ru.andreev.dracaena.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao {

    private final Database database;

    private final Connection connection;

    public CustomerDao() {
        this.database = new Database();
        this.connection = database.getConnection();
    }

    public Optional<Customer> findById(Long id){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Optional<Customer> optionalCustomer = Optional.empty();

        try {
            preparedStatement = connection.prepareStatement("select * from customer where id = ?");
            preparedStatement.setLong(1,id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Customer customer = fromDatabase(resultSet);
                optionalCustomer = Optional.of(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(preparedStatement);
            database.closeResultSet(resultSet);
        }

        return optionalCustomer;
    }

    public Optional<Customer> findBySessionId(String sessionId){

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Optional<Customer> optionalCustomer = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM customer WHERE sessionId = ?"
            );
            preparedStatement.setString(1,sessionId);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
               Customer customer = fromDatabase(resultSet);
               optionalCustomer = Optional.of(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
            database.closeResultSet(resultSet);
        }

        return optionalCustomer;
    }

    public List<Customer> findAll(){
        Statement statement = null;
        ResultSet resultSet = null;

        List<Customer> customers = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from customer");

            while (resultSet.next()){
                Customer customer = fromDatabase(resultSet);
                customers.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(statement);
            database.closeResultSet(resultSet);
        }
        return customers;
    }

    public void save(Customer customer){

        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO customer (sessionId, name, phoneNumber, address, created, updated) values (?,?,?,?,?,?)"
            );
            preparedStatement.setString(1, customer.getSessionId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getCreatedToString());
            preparedStatement.setString(6, customer.getUpdatedString());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(preparedStatement);
        }
    }

    public void update(Customer customer){

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE customer SET sessionId = ?, name = ?, phoneNumber = ?, address = ?, created = ?, updated = ? WHERE id = ?");

            preparedStatement.setString(1, customer.getSessionId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getCreatedToString());
            preparedStatement.setString(6, customer.getUpdatedString());
            preparedStatement.setLong(7, customer.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
        }
    }

    public void delete (Long id){
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
        }
    }

    private Customer fromDatabase(ResultSet resultSet){
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getLong("id"));
            customer.setSessionId(resultSet.getString("sessionId"));
            customer.setName(resultSet.getString("name"));
            customer.setPhoneNumber(resultSet.getString("phoneNumber"));
            customer.setAddress(resultSet.getString("address"));
            customer.setCreated(resultSet.getString("created"));
            customer.setUpdated(resultSet.getString("updated"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }
}
