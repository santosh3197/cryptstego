package Steganography;
import java.util.Vector;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Reciever implements Runnable {
    private Socket socket;
    private ServerSocket connect;
    private static Vector sockets = null;
    private static int portNumber = 8000, maxClients = 10;
    private Thread thread;

    public Reciever(){
        System.out.println("Server");
        thread = new Thread(this) ;
        sockets = new Vector();
        try {
            connect = new ServerSocket(portNumber, maxClients);
            thread.start();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void run(){
        try{
            while (true) {
                sockets.addElement(new BackHandler(connect.accept()));
                thread.yield();
            }
        }
        catch(Exception ex){
            System.out.println("run : " + ex.toString()) ;
        }
    }

    public void stopServer(){
        try{
            connect.close() ;
            thread.stop() ;
        }
        catch(Exception ex){
            System.out.println("Error : " + ex.toString()) ;
        }
    }
}