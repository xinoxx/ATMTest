package Client;

import java.io.*;
import java.net.Socket;

public class Connector {

    private String host ;
    private int port ;
    private Socket socket ;
    private InputStream is ;
    private ObjectInputStream ois ;
    private OutputStream os ;
    private ObjectOutputStream oos ;

    public Connector(){}

    public Connector(String host , int port )throws Exception{
        this.host = host ;
        this.port = port ;
        connect( host , port ) ;
    }

    //发送对象的方法
    public void send( Object obj ) throws  Exception{
        oos.writeObject(obj);
    }

    //接收对象的方法
    public Object receive() throws Exception{
        return ois.readObject() ;
    }

    //建立远程服务器的连接
    private void connect(String host, int port) throws Exception {

        socket = new Socket(host , port ) ;
        os = socket.getOutputStream() ;
        oos = new ObjectOutputStream(os) ;
        is = socket.getInputStream();
        ois = new ObjectInputStream(is) ;
    }

    //关闭连接
    public void close(){

        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Connector.close:" + e );
        }
    }
}
