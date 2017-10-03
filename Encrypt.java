package Steganography;
import java.io.*;
public class Encrypt{
    private int val;
    private String message,cpyfile;
    private FileReader fr;
    private FileWriter fw;
    private File file1,file2;

    public File encryptFile(byte key, String infile){
        try {
            cpyfile=infile+"cpy";
            file1=new File(infile) ;
            file2=new File(cpyfile) ;
            fr = new FileReader(file1);
            fw=new FileWriter(file2) ;
            byte n = key;
            while((val=fr.read())!=-1){
                val=val+n;
                if ((126 - val) < 0)
                    fw.write(Math.abs(126 - val));
                else
                    fw.write(val);
                n++;
                if (n > 126) n = 0;
            }
        }
        catch(Exception e){
            message="Oops! error in encryption\n" + e.toString() ;
            return null;
        }
        finally{
            try{
                fr.close();
                fw.close();
            }
            catch(Exception ex){
                message="Oops! error in encryption process \n" + ex.toString() ;
                return null;
            }
        }
        message="encypted";
        return file2;
    }

    public String encryptMessage(byte key, String msg) {
        this.message = msg;
        char c[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            val = (int) message.charAt(i);
            val = val + 3;
            c[i] = (char) val;
        }
        message = new String(c);
        System.out.println("message just encrypted by GalaxyOne= = = "+ message);
        
        String Galaxy=encryptMessageTwo(key, message);
        
        return Galaxy;
    }
    
    public String encryptMessageTwo(byte key, String msg) {
        int n = key;
        this.message = msg;
        char c[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            val = (int) message.charAt(i);
            System.out.println("val of charAt("+i+") by GalaxyTwo before Addition = = = "+ val);
            val = val + n;
            System.out.println("val of charAt("+i+") by GalaxyTwo After Addition = = = "+ val+ " value of n="+n);
           
            // Following Code is for writing only Positive Value 
            if ((126 - val) < 0)
                c[i] = (char) (Math.abs(126 - val));
            else
                c[i] = (char) val;
            
            // Now Key increments evrytime by 1 and also Key settlement
            n++;
            if (n > 126) n = 0;   
        }
        message = new String(c);
        System.out.println("key by GalaxyTwo= = = "+ key);
        System.out.println("message just encrypted by GalaxyTwo= = = "+ message);
        return message;
    }
    
    
}
