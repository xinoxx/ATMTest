package Client;

import Client.GetPort.LinkServer;
import Client.GetPort.WeightRoundRobin;
import Server.Service.ATMService;
import Server.Service.ATMServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ATMClient {

    public static void main(String[] args) throws Exception {

        //获取远程连接端口
        InvocationHandler handler = new ProxyHandler(new WeightRoundRobin()) ;
        LinkServer linkServer = (LinkServer) Proxy.newProxyInstance(WeightRoundRobin.class.getClassLoader(),WeightRoundRobin.class.getInterfaces(),handler) ;
        int port = Integer.parseInt(linkServer.getPort()) ;
        System.out.println("连接端口:" + port);
        if( port == 65536 ){
            System.out.println("连接不上服务器！");
        }else {
            ATMFunction atmFunction = new ATMFunction();
            atmFunction.menu();
        }
    }

}
