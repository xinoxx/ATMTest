package Server.Service;

import Server.Dao.ClientInfoDao;
import Server.Dao.LockDao;
import Server.Model.ClientInfo;

public class ATMServiceImpl implements ATMService {


    //登录
    @Override
    public int login(String id, String password) throws Exception {

        ClientInfoDao clientInfoDao = new ClientInfoDao() ;
        ClientInfo clientInfo = clientInfoDao.findClient(id) ;
        System.out.println("id：" + clientInfo.getId() + " password:" + clientInfo.getPassword() );
        if( clientInfo.getId()==null && clientInfo.getPassword() == null ){
            return 2 ;
        }
        else if( clientInfo.getPassword().equals(password)){
            //登录成功一次  error就应该置0
            LockDao lockDao = new LockDao() ;
            lockDao.modifyError(id,0);
            return 1 ;
        }
        else{
            //密码错误
            return 0;
        }
    }

    //查询
    @Override
    public double inquire(String id) throws Exception {

        ClientInfoDao clientInfoDao = new ClientInfoDao() ;
        ClientInfo clientInfo = clientInfoDao.findClient(id) ;
        return clientInfo.getBalance();
    }

    //取钱
    @Override
    public double draw(String id, double money) throws Exception {

        ClientInfoDao clientInfoDao = new ClientInfoDao() ;
        clientInfoDao.modifyLastBalance(id);
        ClientInfo clientInfo = clientInfoDao.findClient(id) ;
        double balance = clientInfo.getBalance() ;
        if( balance>=money){

            balance -= money ;
            clientInfoDao.modifyBalance(id,balance);
            balance+=money ;
        }
        return balance;
    }

    //存钱
    @Override
    public double save(String id, double money) throws Exception {

        ClientInfoDao clientInfoDao = new ClientInfoDao() ;
        ClientInfo clientInfo = clientInfoDao.findClient(id) ;
        double balance = clientInfo.getBalance() ;
        balance += money ;
        clientInfoDao.modifyBalance(id,balance);
        return balance ;
    }

    @Override
    public String test(String str) {
        return "收到消息：" + str ;
    }

}
