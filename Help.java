package Steganography;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class Help extends JInternalFrame {
    Steganograph steg;
    public Help(Steganograph stegano) {
        super("Help",
             true,           //resizable
             true,           //closable
             true,           //maximizable
             true);          //iconifiable
       steg=stegano;
        steg.help_live = true;
      //  setFrameIcon(new ImageIcon("src/Steganography/resource/hacker.jpg"));
        setBounds( 20, 25, 500, 450);
        HtmlPane html = new HtmlPane();
        setContentPane(html);
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                steg.help_live = false;
            }
        });
    }
}

class HtmlPane extends JScrollPane implements HyperlinkListener {
    JEditorPane html;

    public HtmlPane() {
        try {
            File f = new File ("src/Steganography/resource/about.html");
            String s = f.getAbsolutePath();
            s = "file:///"+s;
            URL url = new URL(s);
            html = new JEditorPane(s);
            html.setEditable(false);
            html.addHyperlinkListener(this);
            JViewport vp = getViewport();
            vp.add(html);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            linkActivated(e.getURL());
        }
    }
    protected void linkActivated(URL u) {
        Cursor c = html.getCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        html.setCursor(waitCursor);
        SwingUtilities.invokeLater(new PageLoader(u, c));
    }
    class PageLoader implements Runnable {

        PageLoader(URL u, Cursor c) {
            url = u;
            cursor = c;
        }

        public void run() {
            if (url == null) {
                html.setCursor(cursor);
                Container parent = html.getParent();
                parent.repaint();
            } else {
                Document doc = html.getDocument();
                try {
                    html.setPage(url);
                } catch (IOException ioe) {
                    html.setDocument(doc);
                    getToolkit().beep();
                } finally {
                    url = null;
                    SwingUtilities.invokeLater(this);
                }
            }
        }

        URL url;
        Cursor cursor;
    }
}
