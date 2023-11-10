package github.com.xszhagxiaocuo.dao;

import github.com.xszhagxiaocuo.entity.Fruit;
import util.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FruitDB {
    private static Map<Integer,Integer> idMap = new HashMap<>();//映射关系id->realId
    private static Map<Integer,Integer> realIdMap = new HashMap<>();//映射关系realId->id
    public static List<Fruit> getFruits() {
        List<Fruit> fruits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_goods";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);

            int id=0;
            while (rs.next()) {
                id++;
                Fruit fruit = new Fruit();
                fruit.setId(id);
                fruit.setRealId(rs.getInt("id"));
                fruit.setName(rs.getString("name"));
                fruit.setPrice(rs.getFloat("price"));
                fruit.setNumber(rs.getInt("number"));
                fruit.setRemark(rs.getString("remark"));

                fruits.add(fruit);
                idMap.put(fruit.getId(),fruit.getRealId());//将代表顺序的id与真实的realId对应起来
                realIdMap.put(fruit.getRealId(),fruit.getId());//将真实的realId与代表顺序的id对应起来
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return fruits;
    }

    public static boolean Add(Fruit fruit){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtils.getConnection();
            String sql = "INSERT INTO t_goods (name, price,number,remark) VALUES (?, ?,?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, fruit.getName());
            pstmt.setFloat(2, fruit.getPrice());
            pstmt.setInt(3, fruit.getNumber());
            pstmt.setString(4, fruit.getRemark());

            pstmt.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtils.close(pstmt, conn);
        }
        return true;
    }

    public static boolean Remove(Integer id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        id=idMap.get(id);

        try {
            conn = DruidUtils.getConnection();
            String sql = "DELETE FROM t_goods WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtils.close(pstmt, conn);
        }
        return true;
    }

    public static boolean Modify(Fruit fruit){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtils.getConnection();
            String sql = "UPDATE t_goods SET name = ?, price = ?, number = ?, remark = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fruit.getName());
            pstmt.setFloat(2, fruit.getPrice());
            pstmt.setInt(3, fruit.getNumber());
            pstmt.setString(4, fruit.getRemark());
            pstmt.setInt(5,fruit.getRealId());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtils.close(pstmt, conn);
        }
        return true;
    }

    public static List<Fruit> SearchById(int id){
        List<Fruit> fruits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_goods WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();

            fruits=fetchRs(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return fruits;
    }

    public static List<Fruit> SearchByName(String name){
        List<Fruit> fruits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_goods WHERE name LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,"%"+name+"%"); //模糊查询
            rs = stmt.executeQuery();

            fruits=fetchRs(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return fruits;
    }

    public static List<Fruit> SearchByPrice(float price){
        List<Fruit> fruits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_goods WHERE price = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1,price);
            rs = stmt.executeQuery();

            fruits=fetchRs(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return fruits;
    }

    public static List<Fruit> SearchByNumber(int number){
        List<Fruit> fruits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_goods WHERE number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,number);
            rs = stmt.executeQuery();

            fruits=fetchRs(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return fruits;
    }

    public static List<Fruit> SearchByRemark(String remark){
        List<Fruit> fruits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //从连接池获取一个连接
            conn = DruidUtils.getConnection();
            String sql = "SELECT * FROM t_goods WHERE remark LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,"%"+remark+"%");
            rs = stmt.executeQuery();

            fruits=fetchRs(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(rs, stmt, conn);
        }

        return fruits;
    }

    public static List<Fruit> Search(String field,String key){
        try {
            switch (field) {
                case "id":
                    System.out.println("当前查询id："+idMap.get(Integer.parseInt(key)));
                    return SearchById(idMap.get(Integer.parseInt(key)));
                case "name":
                    return SearchByName(key);
                case "price":
                    return SearchByPrice(Float.parseFloat(key));
                case "number":
                    return SearchByNumber(Integer.parseInt(key));
                case "remark":
                    return SearchByRemark(key);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static List<Fruit> fetchRs(ResultSet rs) throws SQLException {
        List<Fruit> fruits = new ArrayList<>();
        while (rs.next()) {
            Fruit fruit = new Fruit();
            int id = rs.getInt("id");
            fruit.setId(realIdMap.get(id));
            fruit.setRealId(id);
            fruit.setName(rs.getString("name"));
            fruit.setPrice(rs.getFloat("price"));
            fruit.setNumber(rs.getInt("number"));
            fruit.setRemark(rs.getString("remark"));

            fruits.add(fruit);
        }
        return fruits;
    }

}
