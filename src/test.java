
import bookManagement.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class test {
    
        public static void main(String[] args){
            int r=0;
            String type="Classics";
        Connection conn;
        try{
            DB db=new DB();
            conn=db.getConn();
            String sql="select id from types where typ = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                r = rs.getInt(1);
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
           
        }
        System.out.print(r);
        }
}
