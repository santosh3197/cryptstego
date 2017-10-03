package Steganography;
import java.io.*;

public class Decrypt{
    private int val;
    private String message,infile,cpyfile;
    private FileReader fr;
    private FileWriter fw;
    private File file1, file2,file3;


    public boolean decryptFile(byte key, String datafile) {
        cpyfile = datafile + "cpy";
        file1=new File(datafile) ;
        file2=new File(cpyfile) ;
        try {
            fr = new FileReader(file1);
            fw = new FileWriter(file2);
            byte n = key;
            while ((val = fr.read()) != -1) {
                val = (short) (val - n);
                if (val < 0)
                    fw.write(126 + val);
                else
                    fw.write(val);
                n++;
                if (n > 126) n = 0;
            }
        } catch (Exception e) {
            message = "Oops! error in decryption\n" + e.toString();
            return false;
        } finally {
            try {
                fr.close();
                fw.close();
                file3=file1;
                file1.delete() ;
                file2.renameTo(file3);
            } catch (Exception ex) {
                message = "Oops! error in decryption final \n" + ex.toString();
                return false;
            }
        }
        message="Decrypted";
        return true;
    }
//================= This Commented Section is also working, But It should not... =========================
// ================ the decryption process is reverse way... ==========================================
// ================ So, "Galaxy Algo." should first perform GalaxyTwo() and then GalaxyOne() =============
// ============== But here in "Galaxy Algo." order is not important... It can also processed in any order... ============
    
    /*public String decryptMessage(byte key, String msg) {
        this.message = msg;
        char c[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            val = (int) message.charAt(i);
            val = val - 3;
            c[i] = (char) val;
        }
        message = new String(c);
        
        System.out.println("message just decrypted by GalaxyOne= = = "+ message);
        
        String Galaxy=decryptMessageTwo(key, message);
        
        return Galaxy;
       
       
    }
    
    public String decryptMessageTwo(byte key, String msg) {
        int n = key;
        this.message = msg;
        char c[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            val = (int) message.charAt(i);
            val = val - n;
            if (val < 0)
                c[i] = (char) (126 + val);
            else
                c[i] = (char) val;
            n++;
            if (n > 126) n = 0;
        }
        message = new String(c);
        System.out.println("key by GalaxyTwo= = = "+ key);
        System.out.println("message just decrypted by GalaxyTwo= = = "+ message);
        return message;
    }

*/
    
    public String decryptMessageTwo(byte key, String msg) {
        this.message = msg;
        char c[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            val = (int) message.charAt(i);
            val = val - 3;
            c[i] = (char) val;
        }
        message = new String(c);
        
        System.out.println("message just decrypted by GalaxyTwo= = = "+ message);
        
        return message;
       
       
    }
    
    public String decryptMessage(byte key, String msg) {
        int n = key;
        this.message = msg;
        char c[] = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            val = (int) message.charAt(i);
            val = val - n;
            if (val < 0)
                c[i] = (char) (126 + val);
            else
                c[i] = (char) val;
            n++;
            if (n > 126) n = 0;
        }
        message = new String(c);
        System.out.println("key by GalaxyOne= = = "+ key);
        System.out.println("message just decrypted by GalaxyOne= = = "+ message);
        
        String Galaxy=decryptMessageTwo(key, message);
        
        return Galaxy;
        
        
       
    }
    
    
}

