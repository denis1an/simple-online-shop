package ru.andreev.dracaena.service;

import ru.andreev.dracaena.dao.OrderDao;
import ru.andreev.dracaena.entity.order.Order;
import ru.andreev.dracaena.entity.Product;
import ru.andreev.dracaena.entity.order.Status;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private final OrderDao orderDao;
    private final ProductService productService;

    public OrderService() {
        orderDao = new OrderDao();
        productService = new ProductService();
    }

    public void addProduct(Order order, Product product){
        order.addProduct(product);
        product.setCount(product.getCount() - 1);

        productService.update(product);
        save(order);
    }

    public void removeProduct(Long orderId){

        Order order = orderDao.findById(orderId).get();
        Product product = order.getProductList().get(0);
        order.removeProduct(product);
        product.setCount(product.getCount() + 1);

        productService.update(product);
        delete(orderId);

        System.out.println("hee");
    }

    public Optional<Order> findBySessionId(String sessionId) {
        return orderDao.findByCustomerSessionId(sessionId);
    }

    public List<Order> findAll(){
        return orderDao.findAll();
    }

    private void save(Order order){
        order.setStatus(Status.NOT_CONFIRMED);
        order.toCreate();
        orderDao.save(order);
    }

    public void update(Order order){
        order.toUpdate();
        orderDao.update(order);
    }

    public void delete(Long id){
        orderDao.delete(id);
    }
}
