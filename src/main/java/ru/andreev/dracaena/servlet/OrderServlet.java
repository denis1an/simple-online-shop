package ru.andreev.dracaena.servlet;

import ru.andreev.dracaena.entity.Customer;
import ru.andreev.dracaena.entity.order.Order;
import ru.andreev.dracaena.entity.order.Status;
import ru.andreev.dracaena.service.CustomerService;
import ru.andreev.dracaena.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends HttpServlet {

    private OrderService orderService;
    private CustomerService customerService;


    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderService.findBySessionId(req.getSession().getId()).orElse(
                new Order(new Customer(req.getSession().getId()))
        );
        if(order != null){
            req.setAttribute("order", order);
            req.getRequestDispatcher("order.jsp").forward(req,resp);
        } else {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("submit");
        if(parameter.equals("Delete")){
            String orderId = req.getParameter("orderId");
            orderService.removeProduct(Long.valueOf(orderId));
        } else if(parameter.equals("checkout")){
            Order order = orderService.findBySessionId(req.getSession().getId()).get();

            Customer customer = order.getCustomer();
            customer.setName(req.getParameter("name"));
            customer.setPhoneNumber(req.getParameter("phoneNumber"));
            customer.setAddress(req.getParameter("address"));

            order.setStatus(Status.CHECKOUT);

            customerService.update(customer);
            orderService.update(order);
        }
        resp.sendRedirect(req.getRequestURI());
    }
}
