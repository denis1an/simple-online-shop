package ru.andreev.dracaena.servlet;

import ru.andreev.dracaena.entity.Product;
import ru.andreev.dracaena.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.valueOf(req.getParameter("productId"));
        Optional<Product> optionalProduct = productService.findById(productId);
        if(optionalProduct.isPresent()) {
            req.setAttribute("product", optionalProduct.get());
            req.getRequestDispatcher("product.jsp").forward(req, resp);
        } else {
            //todo error
        }
    }
}
