package Server.Model;

import java.io.Serializable;

public class RemoteCall implements Serializable {

    private static final long serialVersionUID = 1L ;

    //表示服务类或接口名
    private String className ;
    //表示功能方法名
    private String methodName ;
    //表示方法的参数类型
    private Class<?>[] paramTypes ;
    //表示方法参数值
    private Object[] params ;
    //如果方法正常执行，result是方法的返回值，如果方法抛出异常，result为异常内容
    private Object result ;

    public RemoteCall(){}

    public RemoteCall(String className , String methodName , Class<?>[] paramTypes , Object[] params ){
        this.className = className ;
        this.methodName = methodName ;
        this.paramTypes = paramTypes ;
        this.params = params ;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String toString(){
        return "className=" + className + " methodName=" + methodName ;
    }
}
