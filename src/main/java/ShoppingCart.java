import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Product> item = new ArrayList<>();

    public ShoppingCart(){}
    public ShoppingCart(List<Product> items) {
        this.item = items;
    }

}
