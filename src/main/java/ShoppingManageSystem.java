import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

public class ShoppingManageSystem {
    Administrator administrator;
    User user;
    List<User> userList;
    List<Product> productList;
    Scanner scanner;
    boolean isAdminLogIn;
    boolean isUserLogIn;
    boolean noExit;

    public ShoppingManageSystem() {
        administrator = new Administrator("WuTao", "123456");
        userList = new ArrayList<>();
        userList.add(new User("Mike", "666666", "054728"));
        userList.add(new User("Tom", "333666", "952783"));
        productList = new ArrayList<>();
        productList.add(new Product("001", "可乐", 3.00, "上海", 1000, "2023/7/12"));
        productList.add(new Product("023", "面包", 4.50, "福建", 200, "2023/8/13"));
        productList.add(new Product("433", "牛奶", 3.30, "大理", 450, "2023/8/9"));
        scanner = new Scanner(System.in);
        isAdminLogIn = false;
        isUserLogIn = false;
        noExit = true;
        user = new User("", "", "");
    }

    public void run() {
        loadDataFromFile();
        while (noExit) {
            showMenu();
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    administratorSign();
                    break;

                case 2:
                    user.userRegister(userList);
                    break;

                case 3:
                    userSign();
                    break;

                case 4:
                    user.resetPassword(userList, "1111");
                    break;

                case 0:
                    noExit = false;
                    System.out.println("已退出登录系统！");
                    break;
            }

            while (isAdminLogIn) {
                showAdminMenu();
                int adOption = scanner.nextInt();

                switch (adOption) {
                    case 1:
                        System.out.print("请输入新密码：");
                        String newPassword = scanner.next();
                        administrator.changePassword(newPassword);
                        break;

                    case 2:
                        administrator.listUsers(userList);
                        break;

                    case 3:
                        administrator.deleteUsers(userList);
                        break;

                    case 4:
                        administrator.searchUsers(userList);
                        break;

                    case 5:
                        administrator.listProducts(productList);
                        break;

                    case 6:
                        administrator.addProducts(productList);
                        break;

                    case 7:
                        administrator.changeProducts(productList);
                        break;

                    case 8:
                        administrator.deleteProduct(productList);
                        break;

                    case 0:
                        isAdminLogIn = false;
                        administrator.logout();
                        break;
                }
            }

            while (isUserLogIn) {
                showUserMenu();
                int userOption = scanner.nextInt();

                switch (userOption) {
                    case 1:
                        user.changePassword(userList);
                        break;

                    case 2:
                        user.addProductToCart(productList);
                        break;

                    case 3:
                        user.viewShoppingCart();
                        break;

                    case 4:
                        user.removeProductFromCart();
                        break;

                    case 5:
                        user.changeProductInCart();
                        break;

                    case 6:
                        user.pay();
                        break;

                    case 7:
                        user.viewShoppingHistory();
                        break;

                    case 0:
                        isUserLogIn = false;
                        user.logOut();
                        break;
                }
            }
        }
        saveDataToFile();
    }

    public void showMenu() {
        System.out.println("**********************");
        System.out.println("欢迎来到购物管理系统！");
        System.out.println("1.管理员登录");
        System.out.println("2.用户注册");
        System.out.println("3.用户登录");
        System.out.println("4.用户重置密码");
        System.out.println("0.退出");
        System.out.println("**********************");
        System.out.print("请输入选项：");
    }

    public void showAdminMenu() {
        System.out.println("******************");
        System.out.println("****管理员界面******");
        System.out.println("1.修改密码");
        System.out.println("2.用户列表");
        System.out.println("3.删除用户信息");
        System.out.println("4.查询用户信息");
        System.out.println("5.商品列表");
        System.out.println("6.添加商品");
        System.out.println("7.修改商品信息");
        System.out.println("8.删除商品");
        System.out.println("0.退出");
        System.out.print("请输入选项：");
    }

    public void showUserMenu() {
        System.out.println("*******************");
        System.out.println("******用户界面******");
        System.out.println("1.修改密码");
        System.out.println("2.添加商品到购物车");
        System.out.println("3.查看购物车（待支付商品）");
        System.out.println("4.将商品从购物车移除");
        System.out.println("5.修改购物车中的商品");
        System.out.println("6.结账");
        System.out.println("7.查看购物车历史");
        System.out.println("0.退出登录");
        System.out.print("请输入您要执行的功能对应的序号：");
    }

    public void administratorSign() {
        if (isAdminLogIn) {
            System.out.println("管理员已登录！");
        } else {
            System.out.print("请输入账号：");
            String adminName = scanner.next();
            System.out.print("请输入密码：");
            String adminPassword = scanner.next();

            if (administrator.login(adminName, adminPassword)) {
                System.out.println("管理员登录成功！");
                isAdminLogIn = true;
            } else {
                System.out.println("账号或密码错误！");
            }
        }
    }


    public void userSign() {
        if (isUserLogIn) {
            System.out.println("用户已登录！");
        } else {
            System.out.print("请输入账号：");
            String userName = scanner.next();
            System.out.print("请输入密码：");
            String userPassword = scanner.next();

            for (User users : userList) {
                user = users;
                if (!user.login(userName, userPassword)) {
                    System.out.println("用户登录成功！");
                    isUserLogIn = true;
                    break;
                } else {
                    System.out.println("账号或密码错误！");
                }
            }
        }
    }

    public void saveDataToFile() {
        saveUserList(userList, "users.txt");
        saveProductList(productList, "products.txt");
        saveShoppingCartHistory(user.shoppingCartHistoryList, "shopping_history.txt");
    }

    public void loadDataFromFile() {
        userList = loadUserList("users.txt");
        productList = loadProductList("products.txt");
        user.shoppingCartHistoryList = loadShoppingCartHistory("shopping_history.txt");
    }

    public void saveUserList(List<User> userList, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (User user : userList) {
                writer.write(user.getUsername() + "," + user.password + "," + user.phoneNumber + "\n");
            }
            System.out.println("用户信息保存成功！");
        } catch (IOException e) {
            System.out.println("保存用户信息失败：" + e.getMessage());
        }
    }

    public void saveProductList(List<Product> productList, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Product product : productList) {
                writer.write(product.id+ "," + product.name + "," + product.price + "," + product.address + "," + product.number + "," + product.pastData + "\n");
            }
            System.out.println("商品信息保存成功！");
        } catch (IOException e) {
            System.out.println("保存商品信息失败：" + e.getMessage());
        }
    }

    public void saveShoppingCartHistory(List<ShoppingCart> shoppingCartHistoryList, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (ShoppingCart shoppingCart : shoppingCartHistoryList) {
                for (Product product : shoppingCart.item) {
                    writer.write(product.name + "," + product.price + "," + product.numberToCart + "\n");
                }
                writer.write("=====\n");
            }
            System.out.println("购物历史信息保存成功！");
        } catch (IOException e) {
            System.out.println("保存购物历史信息失败：" + e.getMessage());
        }
    }

    public List<User> loadUserList(String filename) {
        List<User> userList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                String phoneNumber = parts[2];
                userList.add(new User(username, password, phoneNumber));
            }
            System.out.println("用户信息加载成功！");
        } catch (FileNotFoundException e) {
            System.out.println("加载用户信息失败：" + e.getMessage());
        }
        return userList;
    }

    public List<Product> loadProductList(String filename) {
        List<Product> productList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String address = parts[3];
                    int number = Integer.parseInt(parts[4]);
                    String pastData = parts[5];
                    productList.add(new Product(id, name, price, address, number, pastData));
                }
            }
            System.out.println("商品信息加载成功！");
        } catch (FileNotFoundException e) {
            System.out.println("加载商品信息失败：" + e.getMessage());
        }
        return productList;
    }

    public List<ShoppingCart> loadShoppingCartHistory(String filename) {
        List<ShoppingCart> shoppingCartHistoryList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            List<Product> shoppingCartItems = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equals("=====")) {
                    shoppingCartHistoryList.add(new ShoppingCart(shoppingCartItems));
                    shoppingCartItems = new ArrayList<>();
                } else {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int numberToCart = Integer.parseInt(parts[2]);
                    shoppingCartItems.add(new Product("", name, price, "", numberToCart, ""));
                }
            }
            System.out.println("购物历史信息加载成功！");
        } catch (FileNotFoundException e) {
            System.out.println("加载购物历史信息失败：" + e.getMessage());
        }
        return shoppingCartHistoryList;
    }

}