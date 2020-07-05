package ru.andreev.dracaena.service;

import ru.andreev.dracaena.dao.CustomerDao;
import ru.andreev.dracaena.entity.Customer;
import ru.andreev.dracaena.entity.Product;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDao();
    }

    public Optional<Customer> findById(Long id){
        return customerDao.findById(id);
    }

    public Optional<Customer> findBySessionId(String sessionId){
        return customerDao.findBySessionId(sessionId);
    }

    public List<Customer> findAll(){
        return customerDao.findAll();
    }

    public void save(Customer customer){
        customer.toCreate();
        customerDao.save(customer);
    }

    public void update(Customer customer){
        customer.toUpdate();
        customerDao.update(customer);
    }

    public void delete(Long id){
        customerDao.delete(id);
    }
}
