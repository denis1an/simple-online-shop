package ru.andreev.dracaena.dao;

import ru.andreev.dracaena.db.Database;
import ru.andreev.dracaena.entity.Customer;
import ru.andreev.dracaena.entity.order.Order;
import ru.andreev.dracaena.entity.Product;
import ru.andreev.dracaena.entity.order.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class OrderDao {

    private final Database database;
    private final Connection connection;

    private final ProductDao productDao;
    private final CustomerDao customerDao;

    public OrderDao() {
        database = new Database();
        connection = database.getConnection();
        productDao = new ProductDao();
        customerDao = new CustomerDao();
    }

    public Optional<Order> findById(Long id){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Optional<Order> optionalOrder = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT productId, c.id, `order`.id, `order`.created, `order`.updated FROM `order` JOIN customer c on `order`.customerSessionId = c.sessionId JOIN product p on `order`.productId = p.id where `order`.id = ?"
            );

            preparedStatement.setLong(1,id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Order order = new Order();
                order.setCustomer(customerDao.findById(resultSet.getLong("c.id")).get());
                order.setId(resultSet.getLong("order.id"));
                order.setCreated(resultSet.getString("order.created"));
                order.setUpdated(resultSet.getString("order.updated"));
                order.addProduct(
                        productDao.findById(resultSet.getLong("productId")).get()
                );

                optionalOrder = Optional.of(order);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
            database.closeResultSet(resultSet);
        }
        return optionalOrder;
    }

    public Optional<Order> findByCustomerSessionId(String sessionId){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Optional<Order> optionalOrder = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT productId, c.id, `order`.id, `order`.created, `order`.updated, `order`.status FROM `order` JOIN customer c on `order`.customerSessionId = c.sessionId JOIN product p on `order`.productId = p.id where customerSessionId = ? AND status = ?");
            preparedStatement.setString(1,sessionId);
            preparedStatement.setString(2, String.valueOf(Status.NOT_CONFIRMED));
            resultSet = preparedStatement.executeQuery();

            Order order = new Order();
            Long customerId = null;

            boolean flag = true;
            while (resultSet.next()){
                if(flag){
                    customerId = resultSet.getLong("c.id");

                    order.setId(resultSet.getLong("order.id"));
                    order.setCreated(resultSet.getString("order.created"));
                    order.setUpdated(resultSet.getString("order.updated"));
                    order.setStatus(resultSet.getString("order.status"));
                    flag = false;
                }
                Product product = productDao.findById(resultSet.getLong("productId")).get();
                product.setOrderId(resultSet.getLong("order.id"));
                order.addProduct(product);
            }

            if(!flag) {
                order.setCustomer(customerDao.findById(customerId).get());
                optionalOrder = Optional.of(order);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(preparedStatement);
            database.closeResultSet(resultSet);
        }
        return optionalOrder;
    }

    public List<Order> findAll(){

        List<Order> orders = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT productId, customerSessionId, c.id, `order`.created, `order`.updated FROM `order` JOIN customer c on `order`.customerSessionId = c.sessionId JOIN product p on `order`.productId = p.id"
            );

            HashMap <Long,Order> orderHashMap = new HashMap<>();
            while (resultSet.next()){
                 Long customerId = resultSet.getLong("c.id");

                if(!orderHashMap.containsKey(customerId)){
                    Customer customer = customerDao.findById(customerId).get();
                    Order order = new Order();

                    order.setCustomer(customer);
                    order.setId(resultSet.getLong("order.id"));
                    order.setCreated(resultSet.getString("order.created"));
                    order.setUpdated(resultSet.getString("order.updated"));

                    orderHashMap.put(customerId, order);
                }

                Long productId = resultSet.getLong("productId");
                Product product = productDao.findById(productId).get();

                Order order = orderHashMap.get(customerId);
                orderHashMap.remove(customerId);
                order.addProduct(product);
                orderHashMap.put(customerId,order);

            }
            orders.addAll(orderHashMap.values());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(statement);
            database.closeResultSet(resultSet);
        }
        return orders;
    }

    public void save(Order order){
        PreparedStatement preparedStatement = null;
        try {
         //   for (int i = 0; i < order.getProductList().size(); i++){
                preparedStatement = connection.prepareStatement("INSERT INTO `order` (productId,customerSessionId, created, updated, status) VALUES (?,?,?,?,?)");

                preparedStatement.setLong(1, order.getProductList().get(order.getProductList().size() -1).getId());
                preparedStatement.setString(2, order.getCustomer().getSessionId());
                preparedStatement.setString(3, order.getCreatedToString());
                preparedStatement.setString(4, order.getUpdatedString());
                preparedStatement.setString(5, order.getStatus());
                preparedStatement.executeUpdate();

      //      }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
        }
    }

    public void update(Order order){
        PreparedStatement preparedStatement = null;
        try { for (int i = 0; i < order.getProductList().size(); i++) {
                preparedStatement = connection.prepareStatement(
                        "UPDATE `order` SET productId = ?, customerSessionId = ?, updated = ?, status = ? WHERE id = ?"
                );

                preparedStatement.setLong(1, order.getProductList().get(i).getId());
                preparedStatement.setString(2, order.getCustomer().getSessionId());
                preparedStatement.setString(3, order.getUpdatedString());
                preparedStatement.setString(4, order.getStatus());
                preparedStatement.setLong(5, order.getProductList().get(i).getOrderId());
                preparedStatement.executeUpdate();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            database.closeStatement(preparedStatement);
        }
    }

    public void delete(Long id){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `order` WHERE id = ?");
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.closeStatement(preparedStatement);
        }

    }
}
