package Server.Service;

import Server.Dao.ClientInfoDao;
import Server.Dao.LockDao;
import Server.Dao.LogDao;
import Server.Model.ClientInfo;
import Server.Model.Lock;
import Server.Model.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyAspect {

    //锁定账户
    public int lock(String id ) throws Exception {

        LockDao lockDao = new LockDao() ;
        Lock lock = lockDao.findClient(id);
        //System.out.println("id:" + clientInfo.getId() + " password:" + clientInfo.getPassword() );
        if( lock.getError()>=2 ) {
            lockDao.modifyLock(id);
            return 3 ;
        }else {
            int error = lock.getError()+1 ;
            lockDao.modifyError(id,error);
            return 0;
        }
    }
    //日志记录
    public double log(String id , double money , double balance )throws Exception{

        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String day = dateFormat.format(date) ;

        if( balance>=money) {
            System.out.println("写入Log成功！" );
            Log log = new Log(id, day, balance - money, balance);
            LogDao logDao = new LogDao() ;
            logDao.insertLog(log);
        }
        else{
            System.out.println("余额不足，取钱失败，没有写入log");
        }
        return balance ;
    }

}
