package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Order {
    private String number;
    private Calendar date;
    private double total;
    private List<Product> products;
    private Customer customer;

    public Order(String number, Calendar date, double total, List<Product> products, Customer customer) {
        this.number = number;
        this.date = date;
        this.total = total;
        this.products = products;
        this.customer = customer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "number='" + number + "\n" +
                ", date=" + date + "\n" +
                ", total=" + total + "\n" +
                ", products=" + products + "\n" +
                ", customer=" + customer + "\n" +
                '}';
    }
}
