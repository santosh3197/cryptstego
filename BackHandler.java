package Steganography;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

class BackHandler extends JDialog implements Runnable{
    public Socket socket;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public static Vector sockets = null;
    public String path=null;
    public static int portNumber = 8000, maxClients = 5;
    boolean val = true;
    private String fileName = "";
    Thread thread;

    public BackHandler(Socket s) {
      thread= new Thread(this) ;
        socket = s;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        thread.start();
    }
    public void run () {
        try{
            int flag = 0;
            while (true) {
                Object recieved = in.readObject();
                switch (flag) {
                case 0:
                    if (recieved.equals("file"))
                        flag++;
                    break;
                case 1:
                    fileName = (String) recieved;
                    int option = JOptionPane.showConfirmDialog((Component)null,
                            socket.getInetAddress().getHostName() +
                            " is sending you " + fileName +
                            "!\nDo you want to recieve it?", "Recieve Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (option == JOptionPane.YES_OPTION ) {
                        while (val) {
                            val = inform();
                            if (val == true) {
                                int result = JOptionPane.showConfirmDialog((
                                        Component)null,
                                        "This will cancel the save operation\n" +
                                        "Do you want to ignore thid file",
                                        "Save conformation",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (result == JOptionPane.YES_OPTION) {
                                    path = null ;
                                    break;
                                } else if (result == JOptionPane.NO_OPTION)
                                    val = true;
                            }
                        }
                        if (!val) {
                            out.writeObject(new String("ACCEPT"));
                            out.flush();
                            flag++;
                        }
                    }
                    if(option == JOptionPane.NO_OPTION || val) {
                        out.writeObject(new String("REJECT"));
                        out.flush();
                        flag = 0;
                    }
                    break;
                case 2:
                    byte[] b = (byte[]) recieved;
                    FileOutputStream ff = new FileOutputStream(path + "/" +fileName);
                    ff.write(b);
                    flag = 0;
                    JOptionPane.showMessageDialog(null, "File Recieved!",
                                                  "Confirmation",
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    break;
                }
                thread.yield();
            }
        }
        catch (EOFException eof){}
        catch (SocketException se){System.out.println(se.toString());}
        catch (Exception e) {
            System.out.println("ERROR : " + e.toString());
            JOptionPane.showMessageDialog(null,"Error : \n" + e.toString() ,
                              "Error",
                              JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean ask(){
        Object ob = socket.getInetAddress().getHostName() + " is sending you " + fileName + "!\nDo you want to recieve it?";
        String[] but = new String[2];
        but[0] = "Yes";
        but[1] = "NO";
        System.out.println("Ask err1: ");
        while (true) {
            System.out.println("Ask err2: ");
            int result = JOptionPane.showOptionDialog(
                    (Component)null,			
                    ob,						
                    "Send Confermation",			
                    JOptionPane.DEFAULT_OPTION,			
                    JOptionPane.QUESTION_MESSAGE ,	      
                    null,					
                    but,					
                    but[1]					
                         );
            switch (result) {
            case 0:		// Yes
                return true;
            default:	        //No
                return false;
            }
        }
    }
    public boolean inform() {
        Object[] ob = new Object[2];
        ob[0] = "Select directory for saving the file to be retrived";
        ob[1] = new JTextField(path, 20); //jtf;//
        String[] but = new String[3];
        but[0] = "Browse";
        but[1] = "OK";
        but[2] = "Cancel";
        while (true) {
            int result = JOptionPane.showOptionDialog(
                    (Component)null,			
                    ob,						
                    "Directory Selection",		
                    JOptionPane.DEFAULT_OPTION,		
                    JOptionPane.INFORMATION_MESSAGE,	
                    null,					
                    but,					
                    but[2]						
                         );
            switch (result) {

            case 0:		// Browse
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fc.showDialog((Component)null, "Attach");
                File file = fc.getSelectedFile();
                if (file != null && returnVal == JFileChooser.APPROVE_OPTION)
                    path = file.getAbsolutePath();
                ob[1] = new JTextField(path);
                break;
            case 1:		// OK
                JTextField jtf=new JTextField(20) ;
                jtf=(JTextField)ob[1];
                path=jtf.getText();
                if (path.length()<=0) {
                    JOptionPane.showMessageDialog(null,
                                                  "Please select the path for saving the file" +
                                                  "\nPath field empty !",
                                                  "Empty path field",
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    break;
                }
                else
                    return false;
            default:	//Cancel
                return true;
            }
        }
    }
}
