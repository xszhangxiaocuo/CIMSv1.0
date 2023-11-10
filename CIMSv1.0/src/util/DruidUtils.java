package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import util.PropertiesUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DruidUtils {
    private static DataSource ds=null;
    private static Connection conn = null;

    static{
        try {
            System.out.println("start read");
            //读取数据库连接配置文件
            PropertiesUtils.loadFile("github/com/xszhagxiaocuo/config/druid.properties");

            ds = DruidDataSourceFactory.createDataSource(PropertiesUtils.property);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws Exception{
        conn = null;
        try {
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(conn==null){
            System.out.println("conn is null!");
        }
        return conn;
    }

    public static void close(Statement stmt, Connection conn){
        if (stmt!=null){
            try {
                stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn!=null){
            try {
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            conn = null;
        }
    }

    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if (rs!=null){
            try {
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            rs = null;
        }
        close(stmt,conn);
    }
}
