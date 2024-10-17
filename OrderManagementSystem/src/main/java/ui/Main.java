package ui;

import controller.Controller;
import exceptions.*;
import model.Customer;
import model.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private Controller controller;
    private Scanner reader;

    public static void main(String[] args) throws Exception {
        Main exe = new Main();
        exe.menu();
    }

    public Main() {
        controller = new Controller();
        reader = new Scanner(System.in);
    }

    public void menu() throws Exception {

        System.out.println("-------------------------------------");
        System.out.println("Welcomer to order management software");
        System.out.println("-------------------------------------");
        System.out.println("[1] Operator menu");
        System.out.println("-------------------------------------");
        System.out.println("[2] User menu");
        System.out.println("-------------------------------------");
        System.out.println("[3] Close software");
        System.out.println("-------------------------------------");
        int option = reader.nextInt();
        boolean flag = true;
        do {
            switch (option) {
                case 1:
                    operatorMenu();
                    break;
                case 2:
                    customerLogInMenu();
                    break;
                case 3:
                    System.out.println("Closing");
                    flag = false;
                    break;
                default:
                    System.out.println("Choose a valid option");
                    break;
            }
        } while (flag);
    }

    public void operatorMenu() throws Exception {

        System.out.println("-------------------------------------");
        System.out.println("[1] Create product");
        System.out.println("-------------------------------------");
        System.out.println("[2] Delete product");
        System.out.println("-------------------------------------");
        System.out.println("[3] Modify product information");
        System.out.println("-------------------------------------");
        System.out.println("[4] Dispatch order");
        System.out.println("-------------------------------------");
        System.out.println("[5] Undo action");
        System.out.println("-------------------------------------");
        System.out.println("[6] List Orders");
        System.out.println("------------------------------------");
        System.out.println("[7] Go back to main menu");
        System.out.println("-------------------------------------");
        int option = reader.nextInt();
        boolean flag = false;
        do {
            switch (option) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    modifyProduct();
                    break;
                case 4:
                    dispatchOrder();
                    break;
                case 5:
                    undoLastAction();

                    break;
                case 6:
                    listOrders();
                    break;
                case 7:
                    menu();
                    ;
                default:
                    System.out.println("Choose a valid option");
                    flag = true;
                    break;
            }
        } while (flag);
    }

    public void createProduct() throws Exception {

        reader.nextLine();
        System.out.println("-------------------------------------");
        System.out.println("Type the product code");
        String code = reader.nextLine();
        System.out.println("-------------------------------------");
        System.out.println("Type the product name");
        String name = reader.nextLine();
        System.out.println("-------------------------------------");
        System.out.println("Type the product description");
        String description = reader.nextLine();
        System.out.println("-------------------------------------");
        System.out.println("Type the product price");
        double price = reader.nextDouble();
        System.out.println("-------------------------------------");

        try {
            controller.saveProduct(code, name, price, description);
            System.out.println("Product created and registered with this code:" + code);
        } catch (ExistentProductCodeException e) {
            System.out.println("The code " + e + " Is already associated to a product");
        }
        operatorMenu();
    }

    public void deleteProduct() throws Exception {
        reader.nextLine();
        System.out.println(controller.listOrders());
        System.out.println("Type the product code:");
        System.out.println("-------------------------------------");
        String code = reader.nextLine();

        try {
            controller.removeProduct(code);
            System.out.println("Product successfully deleted");
        } catch (InexistentProductException e) {
            System.out.println("The product code " + e.getMessage() + " didn't exist");
        }

        operatorMenu();
    }

    public void modifyProduct() throws Exception {
        reader.nextLine();
        System.out.println(controller.listProducts());
        System.out.println("-------------------------------------");
        System.out.println("Type the product code:");
        String code = reader.nextLine();
        if (!controller.productExists(code)) {
            System.out.println("Product does not exist. Please try again.");
            return;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("Type the new product code (leave empty to keep current):");
        String newCode = reader.nextLine();
        if (newCode.isEmpty()) {
            newCode = null;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("Type the product new name (leave empty to keep current):");
        String newName = reader.nextLine();
        if (newName.isEmpty()) {
            newName = null;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("Type the product new description (leave empty to keep current):");
        String newDescription = reader.nextLine();
        if (newDescription.isEmpty()) {
            newDescription = null;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("Type the product new price (enter 0 to keep current):");
        double newPrice = reader.nextDouble();
        reader.nextLine();
        if (newPrice == 0) {
            newPrice = -1;
        }

        try {
            controller.modifyProduct(newCode, newName, newPrice, newDescription, code);
            System.out.println("Product modified successfully.");
        } catch (InexistentProductException e) {
            System.out.println("Error modifying the product: " + e.getMessage());
        }
        operatorMenu();
    }


    public void dispatchOrder() throws Exception {
        try {
            Product product = controller.dispatchProduct();
            System.out.println("Product dispatched: " + product.getName() + " with code: " + product.getCode());
        } catch (NoElementFoundException e) {
            System.out.println("No products to dispatch.");
        }
        operatorMenu();
    }



    private void customerLogInMenu() throws Exception {
        reader.nextLine();
        System.out.println("------------------------------");
        System.out.println("[1] Sign Up (New Customer)");
        System.out.println("------------------------------");
        System.out.println("[2] Log In ");
        System.out.println("------------------------------");
        System.out.println("[3] Go back to main menu");
        System.out.println("------------------------------");

        int option = reader.nextInt();
        boolean flag = false;
        do {
            switch (option) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    menu();
                    break;
                default:
                    System.out.println("Choose a valid option");
                    flag = true;
                    break;
            }
        } while (flag);
    }

    public void createCustomer() throws Exception {
        reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type the customer id");
        String id = reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type the customer name");
        String name = reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type the customer address");
        String address = reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type the customer email");
        String email = reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type the customer password");
        String password = reader.nextLine();
        System.out.println("----------------------------------");
        try {
            controller.saveCustomer(id, name, address, email, password);
            System.out.println("Successful customer creation, going back to log menu");
            customerLogInMenu();

        } catch (ExistentCustomerIdException e) {
            System.out.println("The customer id " + e + "Already exists");
            customerLogInMenu();
        }
    }


    public void logIn() throws Exception {
        reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type you id");
        String id = reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type you password");
        String password = reader.nextLine();
        System.out.println("----------------------------------");
        try {
            controller.validateCustomerPassword(id, password);
            System.out.println("Successful log in");
            customerMenu(controller.searchCustomer(id));

        } catch (InexistentCustomerException | InvalidPasswordException | PriorityFullException e) {
            System.out.println("Inexistent user id or invalid password");
            customerLogInMenu();
        }
    }


    public void customerMenu(Customer customer) throws Exception, PriorityFullException {

        System.out.println("----------------------------------");
        System.out.println("[1] Create order");
        System.out.println("----------------------------------");
        System.out.println("[2] Undo action");
        System.out.println("----------------------------------");
        System.out.println("[3] Log Out");
        System.out.println("----------------------------------");
        int option = reader.nextInt();
        reader.nextLine();
        boolean flag = false;

        do {
            switch (option) {
                case 1:
                    createOrder(customer);
                    break;
                case 2:
                    undoLastAction();

                    break;
                case 3:
                    customerLogInMenu();
                    break;
                default:
                    System.out.println("Choose a valid option");
                    flag = true;
                    break;
            }
        } while (flag);
    }

    public void createOrder(Customer customer) throws Exception, PriorityFullException {
        reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("Type the order number");
        String number = reader.nextLine();
        System.out.println("----------------------------------");
        System.out.println("How much products do you want to order");
        int numberOfProducts = reader.nextInt();
        reader.nextLine();
        ArrayList<Product> products = new ArrayList<>();

        for (int i = 0; i < numberOfProducts; i++) {
            System.out.println(controller.listProducts());
            System.out.println("Type the product code");
            String code = reader.nextLine();
            try {
                products.add(controller.searchProduct(code));
            } catch (InexistentProductException e) {
                System.out.println("The product with the code " + e + "didnt exist");
                customerMenu(customer);
            }
        }

        controller.saveOrder(products, customer, number);
        System.out.println("----------------------------------");
        System.out.println("Order successfully placed");
        System.out.println("----------------------------------");
        System.out.println("Going back to customer menu");
        System.out.println("----------------------------------");
        customerMenu(customer);
    }

    public void undoLastAction() {
        try {
            controller.undoLastAction();
            System.out.println("Last action undone successfully.");
        } catch (Exception e) {
            System.out.println("No actions to undo.");
        }
    }

    public void listOrders() throws Exception {
        System.out.println("-------------------------------------");
        System.out.println("Listing all orders");
        System.out.println("-------------------------------------");
        System.out.println(controller.listOrders());
        operatorMenu();
    }

}