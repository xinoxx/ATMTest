package Client;

import Client.GetPort.LinkServer;
import Client.GetPort.WeightRoundRobin;
import Server.Service.ATMService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Scanner;

public class ATMFunction {

    private String clientId ;

    //主页面
    public void menu() throws Exception {
        Scanner in = new Scanner(System.in) ;
        int ok = 0 ;
        int choose = 0 ;

        while( choose != 2 ) {
            System.out.println("       欢迎使用ATM机！\n" +
                    "***************************\n" +
                    "           1.登录\n" +
                    "           2.退出系统\n" +
                    "***************************\n" +
                    "选择功能：");
            choose = in. nextInt() ;
            if (choose == 1) {
                System.out.println("\n\n\n");
                ok = login();
            }
            else
                break ;

            if( ok == 0 ){
                System.out.println("退出系统！");
                break;
            }
        }
        in.close();
    }

    //功能页面
    public int menu2() throws Exception {

        Scanner in = new Scanner(System.in) ;
        int ok = 0 ;
        int choose = 0 ;
        while( choose != 4 ){

            System.out.println("      选择功能\n" +
                    "***************************\n" +
                    "     1.查询\n" +
                    "     2.取钱\n" +
                    "     3.存钱\n" +
                    "     4.退出登录\n" +
                    "选择功能：");
            choose = in.nextInt() ;
            if( choose == 1 ){
                ok = inquire();
            }
            else if( choose == 2 ){
                ok = draw();
            }
            else if( choose == 3 ){
                ok = save();
            }
            if(ok==0)
                break ;
        }
        return ok ;

    }

    //获取服务端口
    private int getPort(){
        int port ;
        InvocationHandler handler = new ProxyHandler(new WeightRoundRobin()) ;
        LinkServer linkServer = (LinkServer) Proxy.newProxyInstance(WeightRoundRobin.class.getClassLoader(),WeightRoundRobin.class.getInterfaces(),handler) ;
        port = Integer.parseInt(linkServer.getPort()) ;
        if( port != 65536 )
        System.out.println("连接端口:" + port);
        return port ;
    }

    private int login() throws Exception {
        Scanner in = new Scanner(System.in) ;
        int ok = 0 ;
        String id , password ;
        System.out.println("           登录\n" +
                "***************************\n" +
                "输入账号：");
        id = in.next();
        this.clientId = id ;
        while( true ) {

            System.out.println("输入密码：");
            password = in.next();
            int port = getPort();
            if( port == 65536 ){
                System.out.println("连接不上服务器！");
                ok = 0 ;
                break ;
            }
            else {

                ok = 1 ;
                ATMService atmService = DynamicProxyFactory.getProxy(ATMService.class, "localhost", port);
                int state = atmService.login(clientId, password);
                if (state == 1) {
                    System.out.println("密码正确！登录成功！");
                    System.out.println("系统连接中...\n\n\n");
                    ok = menu2();
                    break;
                } else if (state == 0) {
                    System.out.println("密码错误！！！重新输入！\n" +
                            "回到登录界面...\n\n\n");
                    System.out.println("           登录\n" +
                            "***************************\n" +
                            "账号：" + id);
                } else if (state == 3) {
                    System.out.println("账户已经自动上锁！无法登录！\n返回主页！\n\n\n");
                    break;
                } else if (state == 2) {
                    System.out.println("查无此账户！返回主页！\n\n\n");
                    break;
                }
            }
        }
        return ok ;
    }

    private int inquire() throws Exception {

        int ok = 0 ;
        Scanner in = new Scanner(System.in) ;
        int port = getPort() ;
        if( port == 65536 ){
            System.out.println("连接不上服务器！");
            ok = 0 ;
        }
        else {
            ok =1 ;
            ATMService atmService = DynamicProxyFactory.getProxy(ATMService.class, "localhost", port);
            //输出
            double balance = atmService.inquire(clientId);
            int re = 0;
            System.out.println("余额：" + balance +
                    "\n输入0回到上一层：");
            while (true) {
                re = in.nextInt();
                if (re == 0) {
                    System.out.println("\n\n\n");
                    break;
                } else System.out.println("输入无效！重新输入：");
            }
        }
        return ok ;
    }

    private int draw() throws Exception{

        int ok = 0 ;
        Scanner in = new Scanner(System.in) ;

        int port = getPort() ;
        if( port == 65536 ){
            System.out.println("连接不上服务器！");
            ok = 0 ;
        }
        else {
            ok = 1 ;
            ATMService atmService = DynamicProxyFactory.getProxy(ATMService.class, "localhost", port);

            System.out.println("输入金额：");
            double money = in.nextByte();
            double balance = atmService.draw(clientId, money);
            if (money <= balance) {
                balance -= money;
                System.out.println("取钱成功！！！\n" +
                        "当前余额为：" +
                        balance +
                        "\n输入0回到上一层：");
                int re = 0;
                while (true) {
                    re = in.nextInt();
                    if (re == 0) {
                        System.out.println("\n\n\n");
                        break;
                    } else System.out.println("输入无效！重新输入：");
                }
            } else {
                System.out.println("余额不足！取钱失败！\n" +
                        "返回上一层...");
            }
        }
        return ok ;
    }

    private int save() throws Exception{

        int ok = 0 ;
        Scanner in = new Scanner(System.in) ;
        System.out.println("输入金额：");
        double money = in.nextByte() ;

        int port = getPort() ;
        if( port == 65536 ){
            System.out.println("连接不上服务器！");
            ok = 0 ;
        }
        else {
            ok= 1 ;
            ATMService atmService = DynamicProxyFactory.getProxy(ATMService.class, "localhost", port);
            double balance = atmService.save(clientId, money);

            System.out.println("当前余额为：" + balance +
                    "\n输入0返回上一层");
            int re = 0;
            while (true) {
                re = in.nextInt();
                if (re == 0) {
                    System.out.println("\n\n\n");
                    break;
                } else System.out.println("输入无效！重新输入：");
            }
        }
        return ok ;
    }
}
