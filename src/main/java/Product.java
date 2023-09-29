public class Product {
    String id;
    String name;
    double price;
    String address;
    int number;
    String pastData;
    int numberToCart;

    public Product (String id, String name , double price , String address , int number , String pastData){
        this.id = id;
        this.name = name;
        this.price = price;
        this.address = address;
        this.number = number;
        this.pastData = pastData;
    }

    public Product(String name,double price, int numberToCart){
        this.name = name;
        this.price = price;
        this.numberToCart = numberToCart;
    }


}
