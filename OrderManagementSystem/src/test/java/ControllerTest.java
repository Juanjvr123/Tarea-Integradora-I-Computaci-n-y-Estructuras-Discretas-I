import controller.Controller;
import exceptions.*;
import model.Customer;
import model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Order;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller control;

    @BeforeEach
    public void setup() {
        control = new Controller();
    }

    // ----------- Tests for saveProduct -----------

    @Test
    public void testSaveProductValid() throws ExistentProductCodeException, InexistentProductException {
        control.saveProduct("001", "Mcqueen", 16000, "Cars3");
        Product product = control.searchProduct("001");

        assertEquals("Mcqueen", product.getName());
        assertEquals(16000, product.getPrice());
        assertEquals("001", product.getCode());
    }

    @Test
    public void testSaveProductThrowsException() {
        try {
            control.saveProduct("001", "Mcqueen", 16000, "Cars3");
            control.saveProduct("001", "Lightning", 18000, "Cars1");
            fail("Expected ExistentProductCodeException to be thrown");
        } catch (ExistentProductCodeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testSaveProductEdgeCaseEmptyDescription() throws ExistentProductCodeException, InexistentProductException {
        control.saveProduct("002", "Product2", 2000, "");
        Product product = control.searchProduct("002");

        assertEquals("Product2", product.getName());
        assertEquals(2000, product.getPrice());
        assertEquals("002", product.getCode());
        assertEquals("", product.getDescription());
    }

    // ----------- Tests for saveCustomer -----------

    @Test
    public void testSaveCustomer() throws ExistentCustomerIdException, InexistentCustomerException {
        control.saveCustomer("Juan", "Customer01", "Icesi", "juan@gmail.com", "juan1234");
        Customer customer = control.searchCustomer("Customer01");

        assertEquals("Juan", customer.getName());
        assertEquals("Customer01", customer.getId());
    }

    @Test
    public void testSaveCustomerThrowsException() {
        try {
            control.saveCustomer("Juan", "Customer01", "Icesi", "juan@gmail.com", "juan1234");
            control.saveCustomer("Pedro", "Customer01", "Other Address", "pedro@gmail.com", "pedro1234");
            fail("Expected ExistentCustomerIdException to be thrown");
        } catch (ExistentCustomerIdException e) {
            assertTrue(true);
        }
    }

    // ----------- Tests for saveOrder -----------

    @Test
    public void testSaveOrderValid() throws Exception, PriorityFullException {
        control.saveCustomer("Juan", "Customer01", "Icesi", "juan@gmail.com", "password123");
        control.saveProduct("001", "Product1", 1000, "Test Product");
        List<Product> products = new ArrayList<>();
        products.add(control.searchProduct("001"));

        control.saveOrder(products, control.searchCustomer("Customer01"), "Order001");

        Order order = control.searchOrder("Order001");
        assertNotNull(order);
        assertEquals("Order001", order.getNumber());
        assertEquals(1, order.getProducts().size());
    }

    @Test
    public void testSaveOrderThrowsExistentOrderNumberException() throws Exception, PriorityFullException {
        control.saveCustomer("Juan", "Customer01", "Icesi", "juan@gmail.com", "password123");
        control.saveProduct("001", "Product1", 1000, "Test Product");
        List<Product> products = new ArrayList<>();
        products.add(control.searchProduct("001"));

        control.saveOrder(products, control.searchCustomer("Customer01"), "Order001");

        assertThrows(ExistentOrderNumberException.class, () -> {
            control.saveOrder(products, control.searchCustomer("Customer01"), "Order001");
        });
    }

    @Test
    public void testSaveOrderEdgeCaseEmptyProductList() throws Exception {
        control.saveCustomer("Juan", "Customer01", "Icesi", "juan@gmail.com", "password123");
        List<Product> products = new ArrayList<>();

        assertThrows(InexistentProductException.class, () -> {
            control.saveOrder(products, control.searchCustomer("Customer01"), "Order002");
        });
    }

    // ----------- Tests for modifyProduct -----------

    @Test
    public void testModifyProduct() throws ExistentProductCodeException, InexistentProductException {
        control.saveProduct("001", "Mcqueen", 16000, "Cars3");

        control.modifyProduct("002", "Lightning McQueen", 18000, "New Cars", "001");

        Product modifiedProduct = control.searchProduct("002");
        assertEquals("Lightning McQueen", modifiedProduct.getName());
        assertEquals(18000, modifiedProduct.getPrice());
        assertEquals("New Cars", modifiedProduct.getDescription());
        assertEquals("002", modifiedProduct.getCode());

        assertThrows(InexistentProductException.class, () -> control.searchProduct("001"));
    }

    @Test
    public void testModifyProductKeepsOriginalValues() throws ExistentProductCodeException, InexistentProductException {
        control.saveProduct("001", "Mcqueen", 16000, "Cars3");

        control.modifyProduct(null, "Lightning McQueen", 0, "Updated Cars", "001");

        Product modifiedProduct = control.searchProduct("001");
        assertEquals("Lightning McQueen", modifiedProduct.getName());
        assertEquals(16000, modifiedProduct.getPrice());
        assertEquals("Updated Cars", modifiedProduct.getDescription());
        assertEquals("001", modifiedProduct.getCode());
    }

    @Test
    public void testModifyProductThrowsExceptionForNonExistentProduct() {
        assertThrows(InexistentProductException.class, () -> {
            control.modifyProduct("002", "Lightning McQueen", 18000, "New Cars", "001");
        });
    }

    // ----------- Tests for removeOrder -----------

    @Test
    public void testRemoveOrderValid() throws Exception, PriorityFullException {
        control.saveCustomer("Carlos", "Customer02", "Icesi", "carlos@gmail.com", "password");
        control.saveProduct("002", "Product2", 2000, "Test Product");
        List<Product> products = new ArrayList<>();
        products.add(control.searchProduct("002"));

        control.saveOrder(products, control.searchCustomer("Customer02"), "Order002");
        control.removeOrder("Order002");

        assertThrows(InexistentOrderException.class, () -> {
            control.searchOrder("Order002");
        });
    }

    @Test
    public void testRemoveOrderThrowsExceptionForNonExistentOrder() {
        assertThrows(InexistentOrderException.class, () -> {
            control.removeOrder("NonExistentOrder");
        });
    }

    // ----------- Tests for undoLastAction -----------

    @Test
    public void testUndoLastActionForProduct() throws Exception {
        control.saveProduct("001", "Product1", 1000, "Test Product");
        control.undoLastAction();

        assertThrows(InexistentProductException.class, () -> control.searchProduct("001"));
    }

    @Test
    public void testUndoLastActionForCustomer() throws Exception {
        control.saveCustomer("Juan", "01", "Icesi", "juan@gmail.com", "password123");

        Customer savedCustomer = control.searchCustomer("01");
        assertNotNull(savedCustomer, "Customer should exist before undoing the action");

        control.undoLastAction();

        Customer undoneCustomer = control.searchCustomer("01");
        assertNull(undoneCustomer, "Customer should no longer exist after undoing the action");
    }


    @Test
    public void testUndoLastActionThrowsExceptionWhenNoActions() {
        assertThrows(Exception.class, () -> control.undoLastAction());
    }

    // ----------- Tests for dispatchProduct -----------

    @Test
    public void testDispatchProductSuccess() throws Exception, PriorityFullException, NoElementFoundException {
        control.saveProduct("P001", "Smartphone", 800.0, "High-end smartphone");
        control.saveCustomer("Alice", "C001", "123 Street", "alice@example.com", "password");

        List<Product> products = new ArrayList<>();
        products.add(control.searchProduct("P001"));
        control.saveOrder(products, control.searchCustomer("C001"), "O001");

        Product dispatchedProduct = control.dispatchProduct();

        assertEquals("Smartphone", dispatchedProduct.getName());
        assertEquals(800.0, dispatchedProduct.getPrice());
    }

    @Test
    public void testDispatchMultipleProductsForSingleUser() throws Exception, PriorityFullException, NoElementFoundException {
        control.saveProduct("P002", "Laptop", 1500.0, "High-end laptop");
        control.saveProduct("P001", "Smartphone", 800.0, "High-end smartphone");

        control.saveCustomer("Bob", "C002", "456 Road", "bob@example.com", "password");

        List<Product> products = new ArrayList<>();
        products.add(control.searchProduct("P001"));
        products.add(control.searchProduct("P002"));

        control.saveOrder(products, control.searchCustomer("C002"), "O002");

        Product firstDispatched = control.dispatchProduct();
        Product secondDispatched = control.dispatchProduct();

        assertEquals("Laptop", firstDispatched.getName());
        assertEquals("Smartphone", secondDispatched.getName());
    }




    @Test
    public void testRemoveOrder() throws Exception, PriorityFullException {
        control.saveCustomer("Juan", "01", "Icesi", "juan@gmail.com", "password123");
        control.saveProduct("001", "Product1", 1000, "Test Product");

        List<Product> products = new ArrayList<>();
        products.add(control.searchProduct("001"));

        control.saveOrder(products, control.searchCustomer("01"), "Order001");

        Order savedOrder = control.searchOrder("Order001");
        assertNotNull(savedOrder, "Order should exist before being removed");

        control.removeOrder("Order001");

        assertThrows(InexistentOrderException.class, () -> control.searchOrder("Order001"));
    }


}
