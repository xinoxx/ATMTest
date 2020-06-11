package Client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    Object obj ;

    public ProxyHandler(Object o ){
        this.obj = o ;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        Object result = method.invoke(obj,objects) ;

        return result ;
    }
}
