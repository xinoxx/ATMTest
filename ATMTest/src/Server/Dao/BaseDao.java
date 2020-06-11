package Server.Dao;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BaseDao {

    private static final String configFile = "dbcpconfig.properties" ;
    public DataSource dataSource = null ;

    public BaseDao(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Properties prop = new Properties();
            InputStream is = BaseDao.class.getClassLoader()
                    .getResourceAsStream("dbcpconfig.properties");
            prop.load(is);
            dataSource = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() throws Exception{
        return dataSource.getConnection();}

    public void closeAll(Connection conn , Statement stmt , ResultSet rs ) throws SQLException {
        if( rs!=null ) rs.close();
        if( stmt!=null ) stmt.close();
        if( conn!=null ) conn.close();
    }

}
