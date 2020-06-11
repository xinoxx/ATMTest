package Client;

import Server.Model.RemoteCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理类
public class DynamicProxyFactory {

    @SuppressWarnings("unchecked")
    public static <G> G getProxy(final Class<G> classType , final String host , final int port ){

        //InvocationHandler匿名类
        InvocationHandler handler = new InvocationHandler() {

            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

                Connector connector = null ;
                try {
                    connector = new Connector(host, port);
                    RemoteCall call = new RemoteCall(classType.getName(), method.getName(), method.getParameterTypes(), objects);
                    connector.send(call);
                    call = (RemoteCall) connector.receive();
                    Object result = call.getResult();
                    return result;
                }finally {
                    if (connector != null)
                        connector.close();
                }
            }
        };
        System.out.println("代理开始执行！");
        return (G) Proxy.newProxyInstance(classType.getClassLoader(),new Class<?>[]{classType},handler) ;
    }
}
