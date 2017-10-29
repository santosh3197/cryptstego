package Steganography;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;

public class Login extends JInternalFrame implements ActionListener{
    private JLabel head,user,password;
    private JTextField userField;
    private JPasswordField passField;
    public JButton ok,cancel;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private boolean valid=false;
    private JSeparator sep1=new JSeparator(),sep2=new JSeparator();
    private Steganograph steg;
    public Reciever reciever;
    private DataInputStream datInStrm = null;

    public boolean isValid(){
        return valid;
    }

    public Login(Steganograph stegano){
        super("Login",
             false,           //resizable
             true,            //closable
             false,           //maximizable
             false);          //iconifiable
        setFrameIcon(new ImageIcon("resource/login.gif"));
        setLocation(10, 10);
        steg = stegano;
        steg.log_live = true ;
        setSize(450,250);
        head = new JLabel("Login");
        user = new JLabel("User name");
        password = new JLabel("Password");
        userField = new JTextField(20);
        passField = new JPasswordField(20);
        ok = new JButton("    Ok    ");
        cancel = new JButton("Cancel");
        ok.addActionListener(this) ;
        cancel.addActionListener(this) ;
        //userField.setFont(new Font("Courier", Font.PLAIN, 14));
        userField.setForeground(Color.BLUE);
        userField.setBorder(BorderFactory.createLineBorder(Color.red));
        userField.setCaretColor(Color.BLUE);

        //passField.setFont(new Font("Courier", Font.PLAIN, 14));
        passField.setForeground(Color.BLUE);
        passField.setEchoChar('-');
        passField.setCaretColor(Color.BLUE);
        passField.setBorder(BorderFactory.createLineBorder(Color.red));

        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();

        JPanel jp = new JPanel();
        jp.setBackground(new Color(218, 201, 233));//
        jp.setBorder(BorderFactory.createRaisedBevelBorder());
        jp.setLayout(gbl);

        //Add heading to the form
        head.setFont(new Font("Times-Roamn", Font.BOLD, 24));
        head.setForeground(Color.RED);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        jp.add(head, gbc);

        //Constraints for Seprator
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
        jp.add(sep1, gbc);

        //Add file label,textfields and browse button
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 50, 10, 10);
        jp.add(user, gbc);

        gbc.insets = new Insets(10, 10, 10, 50);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        jp.add(userField, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 50, 10, 10);
        jp.add(password, gbc);

        gbc.insets = new Insets(10, 10, 10, 50);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        jp.add(passField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 0, 50);
        jp.add(sep2, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(218, 201, 233));
        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 50, 10, 50);
        jp.add(buttonPanel, gbc);

        Container cp = getContentPane();
        cp.setBackground(new Color(218, 201, 233));
        cp.setLayout(gbl);
        gbc.insets = new Insets(5, 10, 5, 10);
        cp.add(jp,gbc) ;
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                if(!valid)
                    steg.fileItem[0].setEnabled(true);
                steg.log_live = false;
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==ok){
				
					valid=check();
            if(valid){
                this.dispose();
                for (int i = 0; i < 5; i++) {
                    steg.winItem[i].setEnabled(true);
                    steg.toolbutton[i].setEnabled(true);
                }
                steg.fileItem[0].setEnabled(false);
                JOptionPane.showInternalMessageDialog(steg.desk,
                              "You have successfully signed in.\nYou can now fully access this application.",
                              "Valid User",
                              JOptionPane.
                              INFORMATION_MESSAGE);
                reciever = new Reciever();
            }
        }
        else if(ae.getSource()==cancel){
            if(!valid)
                steg.fileItem[0].setEnabled(true);
            steg.log_live = false;
            dispose() ;
        }
    }

    public boolean check() 

    {
    	boolean flag = false;
        
    	try
    	{
    		//For database connectivity
    	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	Connection con = DriverManager.getConnection("jdbc:odbc:Login_CryptStego");
    	Statement stmt = con.createStatement();
    	
    	
    	String sql = "SELECT * FROM login"; //Selects login table from database.
		ResultSet rs=stmt.executeQuery(sql);  
    	
		
		
    	String s = userField.getText();
        String s1 = passField.getText();
        //if(s.equals("admin") && s1.equals("admin"))
          //  flag = true;
        //if(!flag)
        while(rs.next())
		{
			String s2=rs.getString("username");
			String s3=rs.getString("password");
		
		if(s2.equals(s)&& s3.equals(s1))
		{
			flag=true;
            
            return flag;
        } 
		}
		
        	JOptionPane.showInternalMessageDialog(steg.desk, "Improper user id or password.\nPlease enter proper details.", "Invalid User", 1);
            userField.setText("");
            passField.setText("");
            userField.requestFocus();
            
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception : " + e);
    	}
    	return flag;
    	}
}
