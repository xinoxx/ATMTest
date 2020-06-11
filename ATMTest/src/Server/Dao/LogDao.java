package Server.Dao;

import Server.Model.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogDao extends BaseDao {

    public void insertLog(Log log) throws Exception {
        Connection conn = getConnection() ;
        String sql = "insert into logger values(?,?,?,?)" ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setString(1,log.getId());
        pstmt.setString(2,log.getDate());
        pstmt.setDouble(3,log.getBalance());
        pstmt.setDouble(4,log.getLastBalance());
        pstmt.executeUpdate() ;
        closeAll(conn,pstmt,null);
        System.out.println("写入log:" + "取钱账户" + log.getId() + " 取钱日期：" + log.getDate() + " 取钱后金额：" + log.getBalance() + " 取钱前金额：" + log.getLastBalance() );
    }
}
