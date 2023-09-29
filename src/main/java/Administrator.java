import java.util.List;
import java.util.Scanner;

public class Administrator {
     String name;
     String password;


    public Administrator(String username, String password) {
        this.name = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        return this.name.equals(username) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.print("您已成功修改密码！");
    }

    public void listUsers(List<User>userList){
        System.out.println("用户列表：");
        for(User user : userList){
            System.out.println("用户名："+user.getUsername());
        }
    }

    public void searchUsers(List<User>userList){
        Scanner scanner = new Scanner(System.in);
        boolean isFind = false;
        System.out.println("请输入你要查找的用户：");
        String userName = scanner.next();
        for(User user : userList){
            if(user.username.equals(userName)){
                System.out.println("查找成功！");
                System.out.println("账号："+user.username+"密码："+user.password);
                isFind = true;
            }
        }
        if (!isFind) {
            System.out.println("未找到该用户！");
        }
    }

    public void deleteUsers(List<User>userList){
        Scanner scanner = new Scanner(System.in);
        boolean isFind = false;
        System.out.println("请输入要删除的用户名：");
        String userName = scanner.next();
        for (User user : userList){
            if(user.username.equals(userName)){
                userList.remove(user);
                System.out.println("该用户已删除！");
                isFind = true;
                return;
            }
        }
        if(!isFind) {
            System.out.println("未找到该用户！");
        }
    }

    public void listProducts(List<Product>productList){
        System.out.println("商品列表：");
        for(Product product : productList){
            System.out.println("编号"+product.id+"名称："+product.name+"价格："+product.price+"数量："+product.number+"产地："+product.address+"保质期至："+product.pastData);
        }

    }

    public void addProducts(List<Product>productList){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要添加的商品信息：");
        System.out.print("请输入商品编号：");
        String productId = scanner.next();
        System.out.print("请输入商品名称：");
        String productName = scanner.next();
        System.out.print("请输入商品价格：");
        double productPrice = scanner.nextDouble();
        System.out.print("请输入商品产地：");
        String productAddress = scanner.next();
        System.out.print("请输入商品数量： ");
        int productNumber = scanner.nextInt();
        System.out.print("请输入保质期(年/月/日）： ");
        String productPastData = scanner.next();
        productList.add(new Product(productId,productName,productPrice,productAddress,productNumber,productPastData));
        System.out.println("商品添加成功！");
    }


    public void changeProducts(List<Product>productList){
        Scanner scanner = new Scanner(System.in);
        boolean isChange = true;
        System.out.print("请输入要修改的商品名称：");
        String productName = scanner.next();
        for(Product product : productList){
            if(product.name.equals(productName)){
                System.out.println("该商品的信息如下：");
                System.out.println("1.编号："+product.id);
                System.out.println("2. 名称："+product.name);
                System.out.println("3. 价格："+product.price);
                System.out.println("4. 数量："+product.number);
                System.out.println("5. 产地："+product.address);
                System.out.println("6. 保质期至："+product.pastData);
                System.out.println("0. 退出修改");
                while (isChange) {
                    System.out.println("请输入您要修改的信息对应的序号：");
                    int productOption = scanner.nextInt();
                    switch (productOption){
                        case 1:
                            System.out.print("请输入新的编号：");
                            String newId = scanner.next();
                            product.id = newId;
                            break;

                        case 2:
                            System.out.print("请输入新的名称：");
                            String newName = scanner.next();
                            product.name = newName;
                            break;

                        case 3:
                            System.out.println("请输入新的价格：");
                            double newPrice = scanner.nextDouble();
                            product.price = newPrice;
                            break;

                        case 4:
                            System.out.println("请输入新的数量：");
                            int newNumber = scanner.nextInt();
                            product.number = newNumber;
                            break;

                        case 5:
                            System.out.println("请输入新的产地：");
                            String newAddress = scanner.next();
                            product.address = newAddress;
                            break;

                        case 6:
                            System.out.println("请输入新的保质期：");
                            String newPastData = scanner.next();
                            product.pastData = newPastData;
                            break;

                        case 0:
                            isChange = false;
                            System.out.println("已退出修改");
                            break;
                    }
                }
            }
        }
        if (isChange){
            System.out.println("未找到此商品！");
        }
    }

    public void deleteProduct(List<Product>productList){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要删除的商品名称：");
        String productName = scanner.next();
        for(Product product : productList){
            if(product.name.equals(productName)){
                productList.remove(product);
                System.out.println("商品删除成功！");
                return;
            }
        }
        System.out.println("未找到该商品！");

    }

    public void logout() {
        System.out.println("管理员退出登录！");
    }
}
