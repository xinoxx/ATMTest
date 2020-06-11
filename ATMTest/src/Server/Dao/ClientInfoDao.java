package Server.Dao;

import Server.Model.ClientInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class ClientInfoDao extends BaseDao {

    //修改余额
    public void modifyBalance(String id , double balance) throws Exception {

        Connection conn = getConnection() ;
        String sql = "update clientInfo set balance = ? where id = ?" ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setDouble(1,balance);
        pstmt.setString(2,id );
        pstmt.executeUpdate() ;
        closeAll(conn,pstmt,null);
    }

    //修改账户上一次余额
    public void modifyLastBalance(String id)throws Exception{

        System.out.println("修改上次余额呗");
        Connection conn = getConnection() ;
        String sql = "select * from clientInfo where id = ? " ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setString(1,id);
        closeAll(conn,pstmt,null);
    }

    //查询某个客户信息
    public ClientInfo findClient(String id) throws Exception {

        Connection conn = getConnection() ;
        String sql = "select * from clientInfo where id = ?" ;
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery() ;
        ClientInfo clientInfo = new ClientInfo();
        if( rs.next() ){
            clientInfo = findInfo(rs) ;
        }
        closeAll(conn,pstmt,rs);
        return clientInfo ;
    }

    //查询信息
    public ClientInfo findInfo(ResultSet resultSet) throws Exception{

        ClientInfo clientInfo = new ClientInfo() ;
        clientInfo.setId(resultSet.getString("id"));
        clientInfo.setPassword(resultSet.getString("password"));
        clientInfo.setBalance(resultSet.getDouble("balance"));
        return clientInfo ;
    }
}
