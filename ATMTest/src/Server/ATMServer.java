package Server;

import Server.Model.RemoteCall;
import Server.Service.ATMServiceImpl;
import Server.Service.XmlReader;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

    public class ATMServer {

    //读Xml文件参数
    public static String method1 = "";
    public static String method2 = "";
    public static String method3 = "" ;
    public static String method4 = "";
    public static String type = "" ;
    public static String aspect = "" ;

    //访问文件路径
    private final static String CLASS_PATH="ATMTest." ;
    //端口号
    private final static int PORT = 8000 ;
    //存放远程对象的缓存
    private Map<String,Object> remoteObjects = new HashMap<>() ;
    //注册服务：把远程对象放到缓存中
    public void register( String className , Object remoteObject ){
        remoteObjects.put( className,remoteObject) ;
    }

    public ATMServer(){}

    //暴露服务，创建给予流的Socket
    public void exportService() throws Exception {

        //建立Socket服务端
        ServerSocket serverSocket = new ServerSocket(PORT) ;
        System.out.println(" 服务器启动...此服务器端口为：" + PORT );

        for(;;) {
            final Socket socket = serverSocket.accept();

            //多线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream ois = null;
                    ObjectOutputStream oos = null ;
                    try {
                        ois = new ObjectInputStream(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        //接收客户发送的Call 对象
                        RemoteCall remotecallobj = (RemoteCall) ois.readObject();

                        System.out.println(remotecallobj);
                        // 调用相关对象的方法
                        System.out.println("calling......");
                        remotecallobj = invoke(remotecallobj);
                        // 向客户发送包含了执行结果的remotecallobj 对象
                        oos.writeObject(remotecallobj);
                        //关闭流
                        ois.close();
                        oos.close();
                        socket.close();
                    }catch(EOFException e) {
                    }
                    catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private RemoteCall invoke(RemoteCall remoteCallobj) {

        String CLASS_PATH = "Server.Service." ;

        Object result = null ;
        try {
            String className = remoteCallobj.getClassName();
            String methodName = remoteCallobj.getMethodName() ;
            Object[] params = remoteCallobj.getParams() ;
            Class<?>[] paramsTypes = remoteCallobj.getParamTypes() ;
            //System.out.println("className="+className + " methodName=" + methodName + " params0=" + params[0] + " paramsTypes0=" + paramsTypes[0]);
            //反射
            Class<?> classType = Class.forName(className) ;
            Method method = classType.getMethod(methodName,paramsTypes) ;

            //从hashmap缓存中取出相关的远程对象Object
            Object remoteObject = remoteObjects.get(className) ;
            if( remoteCallobj == null ){
                throw new Exception(className + "的远程对象不存在！") ;
            }
            else{

                XmlReader.readXml("D:\\Xinoxell\\JAVA\\ATMTest\\src\\aops.xml");
                Class<?> clazz = Class.forName(CLASS_PATH + aspect) ;
                System.out.println("method1:"+method1 + " method2:" + method2 + " method3:" + method3 + " method4:" + method4 + " method5:" + method4 + " method6:" + method4 +
                        " type:" + type + " aspect:" + aspect + " methodName:" + methodName );

                result = method.invoke(remoteObject,params) ;

                //处理after
                if(method2 !=null&& method2.length()>0&&method2.equals(methodName)&&type.equals("after")&&(int)result==0) {
                    System.out.println("进入lock");
                    Method m = clazz.getMethod(method1,String.class) ;
                    Object obj = clazz.newInstance() ;
                    result = m.invoke(obj,params[0]) ;
                }

                if( method4!=null && method4.length()>0&&method4.equals(methodName)&&type.equals("after")){
                    System.out.println("进入log");
                    Method m = clazz.getMethod(method3,String.class,double.class,double.class) ;
                    Object obj = clazz.newInstance() ;
                    result = m.invoke(obj,params[0],params[1],result) ;
                }

                System.out.println("远程调用结束:remoteObject:" + remoteObject.toString() + ",params:" + params.toString() );
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        remoteCallobj.setResult(result);

        return remoteCallobj ;
    }

    public static void main( String args[] ) throws Exception{

        ATMServer atmServer = new ATMServer() ;
        //把事先创建的RemoteServiceImpl对象加入到服务器的缓存中
        //在服务注册中心注册服务
        atmServer.register("Server.Service.ATMService" , new ATMServiceImpl());
        //atmServer.register(CLASS_PATH+"Dao.ClientInfoDao" , new ClientInfoDaoImpl());
        //打开网络接口，接收外部请求，执行服务功能，返回结果
        atmServer.exportService();
    }
}
