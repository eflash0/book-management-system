/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bookManagement;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class DB {
        private String url="jdbc:oracle:thin:@localhost:1521:xe";
        private String username="";
        private String pass = "";
        private Connection conn;
        
        public DB(){
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(url, username, pass);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        public Connection getConn(){
            return conn;
        }
//        public static void main(String[] args) throws SQLException{
//            DB d = new DB();
//            Connection c = d.getConn();
//            String sql="create table books(id int,title varchar2(20),author varchar2(20),year int,price decimal(10,2));";
//            Statement stmt = c.createStatement();
//            int rslt = stmt.executeUpdate(sql);
//        }
}
