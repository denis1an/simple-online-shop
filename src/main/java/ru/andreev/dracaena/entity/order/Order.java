package ru.andreev.dracaena.entity.order;

import ru.andreev.dracaena.entity.AbstractEntity;
import ru.andreev.dracaena.entity.Customer;
import ru.andreev.dracaena.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class Order extends AbstractEntity {

    private Customer customer;

    private List<Product> productList;

    private Status status;

    public Order() {
        productList = new ArrayList<>();
    }

    public Order(Customer customer) {
        this.customer = customer;
        productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product){
        productList.add(product);
    }

    public void removeProduct(Product product){
        productList.remove(product);
    }

    public Double getTotalPrice(){
       return productList.stream().mapToDouble(Product::getPrice).sum();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public void setStatus(Status status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", productList=" + productList +
                getUpdatedString() + " / "+ getCreatedToString() +
                '}';
    }
}
