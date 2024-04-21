/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookManagement;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class AddBook extends JFrame implements ActionListener{
    JTextField[] tf = new JTextField[3];
    JLabel[] tl = new JLabel[5];
    String[] ts={"isbn","title","author","year","type"};
    int[] ty = {100,150,200,250,300};
    JButton b1,b2;
    JLabel msg;
    JComboBox cb,cb2;
    ArrayList<String> a = new ArrayList<String>();
    String typ[] = retrieveTypes().toArray(new String[0]);
    public AddBook(){
        setSize(420,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        msg=new JLabel();
        
        b1=new JButton("add");        
        b2=new JButton("reset");
        
        b1.setBounds(120,350,80,30);        
        b2.setBounds(210,350,80,30);

        
        for(int i=0;i<tl.length;i++){
            tl[i]=new JLabel(ts[i]);
            tl[i].setBounds(65, ty[i], 75, 25);
            add(tl[i]);
        }
        for(int i=0;i<tf.length;i++){
            tf[i]=new JTextField();
            tf[i].setBounds(150, ty[i], 160, 25);
            tf[i].setOpaque(true);// to make the background of the textfield transparent
            tf[i].setBorder(null);
            add(tf[i]);
        }
        
        b1.addActionListener(this);        
        b2.addActionListener(this);
        getContentPane().setBackground(new Color(105, 138, 138));
        
        msg.setBounds(150, 40, 140, 25);
        
        cb=new JComboBox(typ);
        cb.setBounds(150, 300, 160, 25);
        
        for(int i=1980;i<=Calendar.getInstance().get(Calendar.YEAR);i++){
            a.add(i+"");
        }
        cb2=new JComboBox(a.toArray());
        cb2.setBounds(150,250, 70, 25);
        //to center the items in JComboBox
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        cb2.setRenderer(listRenderer);
        
        
        add(b1);
        add(b2);
        add(msg);
        add(cb2);
        add(cb);
        
        setResizable(false);
        
        setVisible(true);

    }
    public static void main(String[] args){
        new AddBook();
    }
    
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            for(int i=0;i<tf.length;i++){
                if(tf[i].getText().equals("")){
                    JOptionPane.showMessageDialog(null, "you must fill all the fields");
                        return;
                }           
            }    
                    DB d = new DB();
                    Connection conn = d.getConn();
                    int y;
                    String ys;
                    try{
                        ys=(String) cb2.getItemAt(cb2.getSelectedIndex());
                        y=Integer.parseInt(ys);
                    }
                    catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "year must be an integer");
                        return;
                    }
                    String type = ""+cb.getItemAt(cb.getSelectedIndex());
                    int typ=returnIdType(type);
                    
                    book bk=new book(tf[0].getText(),tf[1].getText(),tf[2].getText(),y,typ);
                    String sql="insert into books values("+"'"+bk.isbn+"'"+","+"'"+bk.title+"'"+","+"'"+bk.author
                            +"'"+","+bk.yearpub+","+bk.type+")";
                    try{
                        Statement stmt = conn.createStatement();
                        int rslt = stmt.executeUpdate(sql);
                        if(rslt>0){
                            msg.setText("added with success");
                            msg.setForeground(Color.green);
                        }
                        else{
                            msg.setText("an error occurs");
                            msg.setForeground(Color.red);
                        }
                        
                    }
                    catch(SQLException sqlex){
                        JOptionPane.showMessageDialog(null, "error with sql");
                        System.out.print(sqlex);                      
                        return;
                    }
                
            
        }
        else if(e.getSource()==b2){
            for(int i=0;i<tf.length;i++){
                tf[i].setText("");
            }
        }
    }
    public ArrayList<String> retrieveTypes(){
        ArrayList<String> a = new ArrayList<String>();
        try{
            DB db = new DB();
            Connection cn = db.getConn();
            String sql="select * from types";
            PreparedStatement ps=cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                a.add(rs.getString(2));
            }
        }
        catch(SQLException sqe){
            System.out.println(sqe);
            return null;
        }
        return a;
    }
    public int returnIdType(String type){
        int r=0;
        Connection conn;
        try{
            DB db=new DB();
            conn=db.getConn();
            String sql="select id from types where typ = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                r = rs.getInt(1);
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
        return r;
    }
}
