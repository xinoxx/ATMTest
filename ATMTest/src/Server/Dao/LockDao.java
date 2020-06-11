package Server.Dao;

import Server.Model.Lock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LockDao extends BaseDao {

    //修改账户锁
    public void modifyLock(String id) throws Exception {

        System.out.println("锁账户呗");
        Connection conn = getConnection() ;
        String sql = "update lock set lock = ? where id = ? " ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setInt(1,1);
        pstmt.setString(2,id);
        pstmt.executeUpdate() ;
        closeAll(conn,pstmt,null);
    }

    //修改账户错误次数
    public void modifyError(String id , int error )throws Exception{

        System.out.println("修改账户呗 error:" + error );
        Connection conn = getConnection() ;
        String sql = "update lock set error = ? where id = ? " ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setInt(1,error);
        pstmt.setString(2,id);
        pstmt.executeUpdate() ;
        closeAll(conn,pstmt,null);
    }

    //查询某个客户信息
    public Lock findClient(String id) throws Exception {

        Connection conn = getConnection() ;
        String sql = "select * from lock where id = ?" ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery() ;
        Lock lock = new Lock( ) ;
        if( rs.next() ){
            lock = findInfo(rs) ;
        }
        closeAll(conn,pstmt,rs);
        return lock ;
    }

    //查询信息
    public Lock findInfo(ResultSet resultSet) throws Exception{

        Lock lock = new Lock();
        lock.setId(resultSet.getString("id"));
        lock.setPassword(resultSet.getString("password"));
        lock.setError(resultSet.getInt("error"));
        lock.setLock(resultSet.getInt("lock"));
        return lock ;
    }
}
