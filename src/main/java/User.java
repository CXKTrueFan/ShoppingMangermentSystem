
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    String username;
    String password;
    String phoneNumber;

    ShoppingCart shoppingCart = new ShoppingCart();
    List<ShoppingCart> shoppingCartHistoryList = new ArrayList<>();

    public User(String username, String password,String phoneNumber) {
        this.username = username;
        this.password = encryptPassword(password);
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(encryptPassword(password));
    }

    public void userRegister(List<User>userList){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入新用户账号：");
        String newUserName = scanner.next();
        System.out.print("请设置您的密码：");
        String userPassword = scanner.next();
        System.out.println("请输入您的电话号码：");
        String userPhoneNumber = scanner.next();
        for (User user : userList){
            if(user.username.equals(newUserName)){
                System.out.println("此用户已存在");
                return;
            }
        }
        userList.add(new User(newUserName,userPassword,userPhoneNumber));
        System.out.println("用户注册成功！");
    }

    public void resetPassword(List<User>userList,String securityCode) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的用户名：");
        String userName = scanner.next();
        System.out.println("请输入您的电话号码：");
        String userPhoneNumber = scanner.next();
        for (User user : userList){
            if(user.username.equals(userName)&&user.phoneNumber.equals(userPhoneNumber)){
                System.out.println("验证码为："+securityCode);
                System.out.print("请输入您接收到的验证码：");
                String userSecurityCode = scanner.next();
                if(securityCode.equals(userSecurityCode)){
                    System.out.print("请输入新的密码：");
                    this.password=encryptPassword(scanner.next());
                    System.out.println("密码重置成功！");
                    return;
                }
                else {
                    System.out.println("验证码错误！");
                    break;
                }
            }
        }
        System.out.println("用户名或电话号码错误！");

    }

    public void changePassword(List<User> userList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String userName = scanner.next();
        System.out.print("请输入当前密码：");
        String currentPassword = scanner.next();
        for (User user : userList) {
            if (user.username.equals(userName) && user.password.equals(encryptPassword(currentPassword))) {
                System.out.print("请输入新密码：");
                this.password = encryptPassword(scanner.nextLine());
                System.out.println("密码修改成功！");
                return;
            }
        }
        System.out.println("用户名或密码错误！");
    }

    public void addProductToCart(List<Product>productList){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要加入购物车的商品名称：");
        String productName = scanner.next();
        System.out.print("请输入要加入的数量：");
        int number = scanner.nextInt();
        for(Product product : productList){
            if(product.name.equals(productName)){
                shoppingCart.item.add(product);
                product.numberToCart = number;
                System.out.println("商品添加购物车成功！");
                return;
            }
        }
        System.out.println("未找到该商品！");
    }

    public void removeProductFromCart(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要移除购物车的商品名称：");
        String productName = scanner.next();
        for (Product product : shoppingCart.item){
            if(product.name.equals(productName)){
                shoppingCart.item.remove(product);
                System.out.println("商品移除成功！");
                return;
            }
        }
        System.out.println("未找到此商品！");
    }

    public void changeProductInCart(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要修改的商品名称：");
        String productName = scanner.next();
        for (Product product : shoppingCart.item){
            if(product.name.equals(productName)){
                System.out.print("请输入您要调整商品数量为：");
                product.numberToCart = scanner.nextInt();
                System.out.println("购物车商品修改成功！");
                return;
            }
        }
        System.out.println("购物车中未找到该商品！");
    }

    public void pay(){
        double payPrice = 0;
        ShoppingCart subCart = new ShoppingCart();
        for(Product product : shoppingCart.item) {
            Product product1 = new Product(product.name,product.price,product.numberToCart);
            subCart.item.add(product1);
            payPrice = payPrice + product.price * product.numberToCart;
        }
             shoppingCartHistoryList.add(subCart);
             System.out.println("请支付"+payPrice+"元");
             System.out.println("支付成功！");
             shoppingCart.item.clear();
    }

    public void viewShoppingHistory(){
        System.out.println("购物车历史：");
        int count = 1;
        for (ShoppingCart shoppingCart : shoppingCartHistoryList) {
            System.out.println("第"+count+"次购买记录：");
            for (Product product : shoppingCart.item) {
                System.out.println("商品名称：" + product.name + "   " + "商品价格：" + product.price + "   " + "购买数量：" + product.numberToCart);
            }
            count++;
        }
    }

    public void viewShoppingCart(){
        System.out.println("购物车里面的商品：");
        for (Product product : shoppingCart.item){
            System.out.println("商品名称："+product.name+"    "+"商品价格："+product.price+"   "+"数量："+product.numberToCart);
        }
    }

    public void logOut(){
         System.out.println("已退出用户界面！");
    }

    public String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
