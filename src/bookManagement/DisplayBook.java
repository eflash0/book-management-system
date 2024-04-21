
package bookManagement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class DisplayBook extends JFrame{
    
    DisplayBook(){
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Book Inventory");
        ImageIcon im=new ImageIcon("book.png");
        setIconImage(im.getImage());
//        setLayout(null);
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ISBN","Title","Author","Year","Type"}); //Object[] if we have columns that is not a string
        ArrayList<book> books = retrieveBooks();
        for(book b:books){
            Object[] o= {b.getIsbn(),b.getTitle(),b.getAuthor(),b.getYearpub(),typeString(b.getType())};
            model.addRow(o);
        }
        JTable jt = new JTable(model);
        jt.setEnabled(false);
        jt.setFocusable(false);
        JScrollPane sp=new JScrollPane(jt);
//        add(sp);
        

        JLabel l=new JLabel();
        l.setText("Books Inventory");
        l.setFont(new Font("Arial", Font.PLAIN, 30));
        l.setHorizontalAlignment(SwingConstants.CENTER);

//        getContentPane().setBackground(new Color(42, 116, 156));
        getContentPane().add(l, BorderLayout.NORTH);
//        getContentPane().add(Box.createVerticalStrut(60), BorderLayout.CENTER);

        getContentPane().add(sp, BorderLayout.CENTER);
        
        setVisible(true);
    }
    public static void main(String[] args){
        new DisplayBook();
    }
    
    ArrayList<book> retrieveBooks(){
        DB d = new DB();
        Connection conn;
        ArrayList<book> a=null;
        try{
            conn=d.getConn();
            String sql="select * from books";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            a = new ArrayList<book>();
            while(rs.next()){
                a.add(new book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
            }
        }
        catch(SQLException e){
            System.out.println("error with sql");
        }
        return a;
    }
    
    public String typeString(int id){
        DB d = new DB();
        Connection conn;
        String s=null;
        try{
            conn = d.getConn();
            String sql="select typ from types where id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                s=rs.getString("typ"); //or 1 because m returning only typ from the db
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "error with sql in type query");
            System.out.println("error with sql in type query");
        }
        return s;
    }

}
