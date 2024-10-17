package controller;

import exceptions.*;
import model.Product;
import model.Order;
import model.Customer;
import structures.HashTable;
import structures.PriorityQueue;
import structures.Stack;
import structures.Queue;


import java.util.*;


public class Controller {
    private HashTable<String,Product> productsH = new HashTable<>();
    private HashTable<String,Customer> customersH = new HashTable<>();
    private HashTable<String, Queue<Order>> ordersH = new HashTable<>();
    private HashTable<String, Order> ordersByNumberH = new HashTable<>();
    private Stack<Object> actionStack = new Stack<>();
    private Stack<Integer> typeActionStack = new Stack<>();
    private Queue<Customer> customerQueue = new Queue<>();
    private PriorityQueue<Product> productPriorityQueue = new PriorityQueue<>();


    /*
    --------------------------------------------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------------------
     --------------------------------------------------------------------------------------------------------------------------------
     */

    public void saveProduct(String code, String name, double price, String description) throws ExistentProductCodeException {
        if(productsH.search(code) == null){
            Product newProduct = new Product(code,name, price, description);
            productsH.insert(code, newProduct);
            actionStack.push(newProduct);
            typeActionStack.push(1);

        }else {
            throw new ExistentProductCodeException(code);
        }
    }

    public void saveCustomer(String name, String id, String address, String email, String password) throws ExistentCustomerIdException {
        if(customersH.search(id) == null){
            Customer newCustomer = new Customer(name, id, address, email, password);
            customersH.insert(id, newCustomer);
            actionStack.push(newCustomer);
            typeActionStack.push(2);
        }else if(customersH.search(id) != null){
            throw new ExistentCustomerIdException(id);
        }
    }

    public void saveOrder(List<Product> products, Customer customer, String number) throws ExistentOrderNumberException, InexistentCustomerException, PriorityFullException, InexistentProductException {
        if(customersH.search(customer.getId()) == null){
            throw new InexistentCustomerException(customer.getId());
        }
        if (ordersByNumberH.search(number) != null) {
            throw new ExistentOrderNumberException(number);
        }

        if (products == null || products.isEmpty()) {
            throw new InexistentProductException("There's no products");
        }

        Queue<Order> userQueue = ordersH.search(customer.getId());
        if(userQueue == null){
            userQueue = new Queue<>();
            ordersH.insert(customer.getId(), userQueue);
            customerQueue.enqueue(customer);
        }

        Order newOrder = new Order(number, Calendar.getInstance(), getProductsPrice(products), products, customer);
        userQueue.enqueue(newOrder);
        ordersByNumberH.insert(number, newOrder);
        actionStack.push(newOrder);
        typeActionStack.push(3);

        for(Product product : products){
            productPriorityQueue.insert(product);
        }
        //FALTA QUE SE GUARDE EN LA COLA DE PRIORIDAD
    }


    public void removeProduct(String code) throws InexistentProductException {
        Product product = productsH.search(code);
        if (product != null) {
            productsH.delete(code);
            actionStack.push(product);
            typeActionStack.push(4);
        } else {
            throw new InexistentProductException(code);
        }
    }

    public double getProductsPrice(List<Product> products) {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice();
        }
        return total;
    }



    public void removeOrder(String number) throws InexistentOrderException {
        Order order = ordersByNumberH.search(number);
        if (order == null) {
            throw new InexistentOrderException(number);
        }

        Queue<Order> userQueue = ordersH.search(order.getCustomer().getId());
        if (userQueue != null) {
            userQueue.dequeue();
        }

        ordersByNumberH.delete(number);

        actionStack.push(order);
        typeActionStack.push(5);

    }



    public void modifyProduct(String newCode, String newName, double newPrice, String newDescription, String code) throws InexistentProductException {
        Product newProduct = productsH.search(code);
        if (newProduct == null) {
            throw new InexistentProductException(code);
        }
        if (newCode != null && !newCode.equals(newProduct.getCode())) {
            productsH.delete(code);
            newProduct.setCode(newCode);
            productsH.insert(newCode, newProduct);
        }

        if (newName != null && !newName.isEmpty()) {
            newProduct.setName(newName);
        }

        if (newPrice != newProduct.getPrice() && newPrice > 0) {
            newProduct.setPrice(newPrice);
        }

        if (newDescription != null && !newDescription.isEmpty()) {
            newProduct.setDescription(newDescription);
        }
        if (newCode == null || newCode.equals(code)) {
            productsH.set(code, newProduct);
        }
    }


    /*
   --------------------------------------------------------------------------------------------------------------------------------
   Undo Last Action and Dispatch products
   --------------------------------------------------------------------------------------------------------------------------------
   */
    public void undoLastAction() throws Exception {
        if (actionStack.isEmpty() || typeActionStack.isEmpty()) {
            throw new Exception("No actions to undo.");
        }

        Object lastActionObject = actionStack.pop();
        int lastActionType = typeActionStack.pop();

        switch (lastActionType) {
            case 1:
                Product product = (Product) lastActionObject;
                productsH.delete(product.getCode());
                break;

            case 2:
                Customer customer = (Customer) lastActionObject;
                if (customersH.search(customer.getId()) != null) {
                    customersH.delete(customer.getId());
                } else {
                    throw new InexistentCustomerException(customer.getId());
                }
                break;

            case 3:
                Order order = (Order) lastActionObject;
                Queue<Order> userQueue = ordersH.search(order.getCustomer().getId());
                if (userQueue != null) {
                    userQueue.dequeue();
                    ordersByNumberH.delete(order.getNumber());
                }
                break;

            case 4:
                Product removedProduct = (Product) lastActionObject;
                productsH.insert(removedProduct.getCode(), removedProduct);
                break;

            case 5:
                Order removedOrder = (Order) lastActionObject;
                Queue<Order> userOrderQueue = ordersH.search(removedOrder.getCustomer().getId());
                if (userOrderQueue == null) {
                    userOrderQueue = new Queue<>();
                    ordersH.insert(removedOrder.getCustomer().getId(), userOrderQueue);
                }
                userOrderQueue.enqueue(removedOrder);
                ordersByNumberH.insert(removedOrder.getNumber(), removedOrder);
                break;

            default:
                throw new Exception("Unknown action type: " + lastActionType);
        }
    }

    public Product dispatchProduct() throws NoElementFoundException {
        while (!customerQueue.isEmpty()) {
            Customer currentCustomer = customerQueue.front();
            Queue<Order> orderQueue = ordersH.search(currentCustomer.getId());

            if (orderQueue == null || orderQueue.isEmpty()) {
                customerQueue.dequeue();
                continue;
            }

            Order currentOrder = orderQueue.front();

            if (currentOrder.getProducts().isEmpty()) {
                orderQueue.dequeue();
                continue;
            }

            Product productToDispatch = productPriorityQueue.extractMaximum();
            currentOrder.getProducts().remove(productToDispatch);

            if (currentOrder.getProducts().isEmpty()) {
                orderQueue.dequeue();
            }

            if (orderQueue.isEmpty()) {
                customerQueue.dequeue();
            }

            return productToDispatch;
        }

        throw new NoElementFoundException("No orders to dispatch.");
    }






     /*
    --------------------------------------------------------------------------------------------------------------------------------
    Methods for Searching and Lists
    --------------------------------------------------------------------------------------------------------------------------------
    */



    public String listProducts(){
        String productsString = "";
        ArrayList<Product> products = (ArrayList<Product>) productsH.getAll();
        for(int i = 0; i < products.size(); i++){
            Product actual = products.get(i);
            productsString += actual.toString() + "\n";
        }
        return productsString;
    }

    public String listOrders() {
        String ordersString = "";

        ArrayList<Queue<Order>> ordersQueues = (ArrayList<Queue<Order>>) ordersH.getAll();

        for (Queue<Order> userQueue : ordersQueues) {
            Queue<Order> tempQueue = new Queue<>();

            while (!userQueue.isEmpty()) {
                Order order = userQueue.dequeue();
                ordersString += order.toString() + "\n";
                tempQueue.enqueue(order);
            }

            while (!tempQueue.isEmpty()) {
                userQueue.enqueue(tempQueue.dequeue());
            }
        }

        return ordersString;
    }



    public Product searchProduct(String code) throws InexistentProductException {
        if(productsH.search(code) == null){
            throw new InexistentProductException(code);
        }else{
            return productsH.search(code);
        }
    }

    public boolean productExists(String code) {
        return productsH.search(code) != null;
    }

    public boolean validateCustomerPassword(String id, String password) {
        Customer founded = customersH.search(id);

        if (founded == null) {
            return false;
        }
        return founded.getPassword().equals(password);
    }

    public Customer searchCustomer(String key){
        return customersH.search(key);
    }

    public Order searchOrder(String number) throws InexistentOrderException {
        Order order = ordersByNumberH.search(number);
        if (order == null) {
            throw new InexistentOrderException(number);
        }
        return order;
    }

}