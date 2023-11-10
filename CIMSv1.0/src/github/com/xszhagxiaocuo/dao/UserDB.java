package github.com.xszhagxiaocuo.dao;

import github.com.xszhagxiaocuo.entity.User;
import util.DruidUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_user";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return users;
    }

    public static boolean insertUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtils.getConnection();
            String sql = "INSERT INTO t_user (name, password,email) VALUES (?, ?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());

            pstmt.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtils.close(pstmt, conn);
        }
        return true;
    }

    public static boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtils.getConnection();
            String sql = "UPDATE t_user SET name = ?, password = ?, email = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getId());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtils.close(pstmt, conn);
        }
        return true;
    }

    public static boolean deleteUser(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtils.getConnection();
            String sql = "DELETE FROM t_user WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtils.close(pstmt, conn);
        }
        return true;
    }

    public static List<User> findUser(String name) {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_user WHERE name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,name);
            rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return users;
    }

}
