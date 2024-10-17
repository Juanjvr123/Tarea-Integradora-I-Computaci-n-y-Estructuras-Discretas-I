package model;

public class Product implements Comparable<Product> {

    private String name;
    private double price;
    private String description;
    private String code;

    public Product(String code, String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.code = code;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(Product otherProduct) {
        return Double.compare(this.price, otherProduct.getPrice());
    }

    @Override
    public String toString(){
        String string = "";
        string += "The product code is: " + code + "\n";
        string += "The product name is: " + name + "\n";
        string += "The product price is: " + price + "\n";
        string += "The product description is: " + description + "\n";

        return string;
    }






}
