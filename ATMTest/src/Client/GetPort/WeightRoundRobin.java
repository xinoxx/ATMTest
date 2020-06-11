package Client.GetPort;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

//是对服务器进行负载均衡
//加权轮询法
public class WeightRoundRobin implements LinkServer {

    private static Integer pos = 0 ;

    public String getPort(){

        //重建一个Map，避免服务器的上下线导致并发问题
        Map<String,Integer> portMap = new HashMap<>() ;
        portMap.putAll(PortMap.portWeightMap);
        //获取端口list
        Set<String> keySet = portMap.keySet() ;
        Iterator<String> iterator = keySet.iterator() ;
        List<String> portList = new ArrayList<>() ;
        while( iterator.hasNext() ){
            String port = iterator.next() ;
            int weight = portMap.get(port) ;
            for( int i = 0 ; i < weight ; i++ ){
                portList.add(port) ;
            }
        }

        String  port= "" ;
        //用来计数无法获得正确端口号的次数
        int times = 0 ;
        //轮询
        synchronized (pos){
            while(true) {
                if (pos >= portList.size()) {
                    pos = 0;
                }
                port = portList.get(pos);
                pos++ ;
                //System.out.println(port);
                if (isPortAvailable(Integer.parseInt(port))) {
                    break ;
                }
                else{
                    times++ ;
                }
                if( times >= portList.size() ){
                    port="65536" ;
                    break ;
                }
            }
        }
        return port ;
    }

    //判断端口是否能正常使用
    public static boolean isPortAvailable(int port){
        Socket socket = null ;
        boolean available = false ;
        try{
            socket = new Socket("localhost",port) ;
            available = true ;
        } catch (IOException e) {
            System.out.println("连接不上此端口:" + port );
        }finally {
            if( socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("关闭服务器连接失败：" + e );
                }
            }
        }
        return available ;
    }
}
