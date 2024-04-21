/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookManagement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class SignUp extends JFrame implements ActionListener{
    String[] ts ={"username","password","confirm password"};
    int[] ty={100,150,200};
    JLabel label[] = new JLabel[3];
    JTextField jf[] = new JTextField[3];
    JLabel l3;
    JButton b1,b2;
    public SignUp(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(450,500);
        for(int i=0;i<ts.length;i++){
            label[i]=new JLabel(ts[i]);
            label[i].setBounds(45,ty[i],120,25);
            add(label[i]);
        }
        
        l3 = new JLabel();
        l3.setBounds(140,45,120,45);
        l3.setForeground(Color.green);
        
        for(int i=0;i<ts.length;i++){
            jf[i]=new JTextField();
            jf[i].setBounds(200,ty[i],120,25);
            add(jf[i]);
        }
        b1=new JButton("signup");        
        b2=new JButton("reset");
        b1.setBounds(120,250,80,30);
        b2.setBounds(220,250,80,30);
        add(b1);
        add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b1.setFocusable(false);        
        b2.setFocusable(false);

        
        add(l3);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            for(int i=0;i<jf.length;i++){
                if(jf[i].getText().equals("")){
                    JOptionPane.showMessageDialog(null, "you must fill all the field");
                    return;
                }
            }
            DB d= new DB();
            Connection conn=d.getConn();
            if(!jf[1].getText().equals(jf[2].getText())){
                JOptionPane.showMessageDialog(null, "password must be equal to the confirm password");
                return;
            }        
            String sql="insert into Users values (user_seq.nextval,"+"'"+jf[0].getText()+"'"+","+"'"+jf[1].getText()+"'"+")";
            try{
//            Statement stmt=conn.createStatement();
//            int rslt = stmt.executeUpdate(sql);
              int r=preparedStmt(conn);
            
            if(r>0)
                l3.setText("added with succes");
            }
            catch(SQLException ex){
                System.out.println(ex);
                l3.setForeground(Color.red);
                l3.setText("an error occurs");
            }
        }
    }
    
    public int preparedStmt(Connection conn) throws SQLException{
        int r=0;
        String sql = "insert into users values(user_seq.nextval,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,jf[0].getText());
        ps.setString(2,jf[1].getText());
        r=ps.executeUpdate();
        return r;
    }
    
    public static void main(String[] args){
        new SignUp();
    }
}
