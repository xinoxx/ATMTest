package Server.Service;

import Server.Model.ClientInfo;

public interface ATMService {

    //登录
    int login(String id , String password) throws Exception;
    //查询
    double inquire(String id ) throws Exception;
    //取钱
    double draw( String id , double money )throws  Exception;
    //存钱
    double save(String id , double money) throws Exception ;
    //测试的方法
    String test(String str) ;

}
