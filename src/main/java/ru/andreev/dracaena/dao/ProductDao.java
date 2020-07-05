package ru.andreev.dracaena.dao;

import ru.andreev.dracaena.db.Database;
import ru.andreev.dracaena.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {

    private final Connection connection;

    private final Database database;

    public ProductDao( ) {
        this.database = new Database();
        connection = database.getConnection();
    }

    public Optional<Product> findById(Long id) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Optional<Product> productOptional = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM product  WHERE ID = ?");
            preparedStatement.setLong(1,id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Product product = fromDatabase(resultSet);
                productOptional = Optional.of(product);
             }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
            database.closeResultSet(resultSet);
        }
        return productOptional;
    }

    public List<Product> findAll() {

        Statement statement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM product");

            while (resultSet.next()){
                Product product = fromDatabase(resultSet);
                productList.add(product);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(statement);
            database.closeResultSet(resultSet);
        }

        return productList;
    }

    public void save(Product product) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =
                    connection.prepareStatement("INSERT INTO product (name, type, price, manufacture, info, description, count, img) values(?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getType());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getManufacturer());
            preparedStatement.setString(5, product.getInfo());
            preparedStatement.setString(6, product.getDescription());
            preparedStatement.setInt(7, product.getCount());
            preparedStatement.setString(8, product.getImg());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
        }
    }

    public void update(Product product) {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement =
                    connection.prepareStatement("UPDATE product SET name = ?, type = ?, price = ?, manufacture = ?, info = ?, description = ?, count = ?, img = ? WHERE ID = ?");

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getType());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getManufacturer());
            preparedStatement.setString(5, product.getInfo());
            preparedStatement.setString(6, product.getDescription());
            preparedStatement.setInt(7, product.getCount());
            preparedStatement.setString(8, product.getImg());
            preparedStatement.setLong(9, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(preparedStatement);
        }
    }

    public void delete(Long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
        }
    }

    private Product fromDatabase(ResultSet resultSet){
        Product product = new Product();
        try {
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setType(resultSet.getString("type"));
            product.setPrice(resultSet.getDouble("price"));
            product.setManufacturer(resultSet.getString("manufacture"));
            product.setInfo(resultSet.getString("info"));
            product.setDescription(resultSet.getString("description"));
            product.setCount(resultSet.getInt("count"));
            product.setImg(resultSet.getString("img"));
            product.setCreated(resultSet.getString("created"));
            product.setUpdated(resultSet.getString("updated"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

}
