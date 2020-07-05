package ru.andreev.dracaena.service;

import ru.andreev.dracaena.dao.ProductDao;
import ru.andreev.dracaena.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService{
    private final ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }


    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public List<Product> findAllInStock(){
        return productDao.findAll()
                .stream().filter(product -> product.getCount() > 0)
                .collect(Collectors.toList());
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(Long id) {
        productDao.delete(id);
    }


}
