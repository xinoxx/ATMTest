package Client.GetPort;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

//加入多个端口号以及每个端口号的权值
//IP地址集合，这里只能暂时用来储存端口号了
public class PortMap {

    public static HashMap<String , Integer> portWeightMap = new HashMap<>() ;
    static{

        //遍历取值
        ResourceBundle rb = ResourceBundle.getBundle("port") ;
        Enumeration enumeration = rb.getKeys() ;
        while( enumeration.hasMoreElements()){
            try{
                String port = rb.getString((String)enumeration.nextElement());
                port = new String(port.getBytes("iso-8859-1"),"GBK") ;
                portWeightMap.put(port,2) ;
                //System.out.println(port);
            }catch (UnsupportedEncodingException e ){
                System.out.println("遍历取出端口号错误" + e );
            }
        }
        /* serverWeightMap.put("localhost",2); */
    }
}
