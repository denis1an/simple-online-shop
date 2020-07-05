package ru.andreev.dracaena.servlet;

import ru.andreev.dracaena.entity.Customer;
import ru.andreev.dracaena.entity.order.Order;
import ru.andreev.dracaena.entity.Product;
import ru.andreev.dracaena.service.CustomerService;
import ru.andreev.dracaena.service.OrderService;
import ru.andreev.dracaena.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class ProductsServlet extends HttpServlet {

    private  ProductService productService;
    private  CustomerService customerService;
    private  OrderService orderService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        orderService = new OrderService();
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();

        Optional<Customer> optionalCustomer = customerService.findBySessionId(sessionId);
        if(optionalCustomer.isEmpty()){
            Customer customer = new Customer(sessionId);
            customerService.save(customer);
        }
        req.setAttribute("products", productService.findAllInStock());

        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();

        Long productId = Long.valueOf(req.getParameter("productId"));
        Optional<Product> optionalProduct = productService.findById(productId);
        if (optionalProduct.isPresent()) {
            Optional<Order> optionalOrder = orderService.findBySessionId(sessionId);
            Order order = null;
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
            } else {
                Optional<Customer> optionalCustomer = customerService.findBySessionId(sessionId);
                if (optionalCustomer.isPresent()) {
                    Customer customer = optionalCustomer.get();
                    order = new Order(customer);
                }
            }
            orderService.addProduct(order, optionalProduct.get());
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.sendRedirect(req.getRequestURI());
    }

}
