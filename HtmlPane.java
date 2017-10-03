// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Help.java

package Steganography;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class HtmlPane extends JScrollPane
    implements HyperlinkListener
{
    class PageLoader
        implements Runnable
    {

        public void run()
        {
            javax.swing.text.Document document;
            if(url == null)
            {
                html.setCursor(cursor);
                Container container = html.getParent();
                container.repaint();
            }
            document = html.getDocument();
            try {
				html.setPage(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            url = null;
            SwingUtilities.invokeLater(this);
       
            Object ioexception = null;
			Object IOException = ioexception;
            //ioexception;
            html.setDocument(document);
            getToolkit().beep();
            url = null;
            SwingUtilities.invokeLater(this);
            Object exception = null;
			Object Exception = exception;
            //exception;
            url = null;
            SwingUtilities.invokeLater(this);
         
        }

        URL url;
        Cursor cursor;
        final HtmlPane this$0;

        PageLoader(URL url1, Cursor cursor1)
        {
            this$0 = HtmlPane.this;
           // super();
            url = url1;
            cursor = cursor1;
        }
    }


    public HtmlPane()
    {
        try
        {
            File file = new File("resource/index.html");
            String s = file.getAbsolutePath();
            s = (new StringBuilder()).append("file:").append(s).toString();
            URL url = new URL(s);
            html = new JEditorPane(s);
            html.setEditable(false);
            html.addHyperlinkListener(this);
            JViewport jviewport = getViewport();
            jviewport.add(html);
        }
        catch(MalformedURLException malformedurlexception)
        {
            System.out.println((new StringBuilder()).append("Malformed URL: ").append(malformedurlexception).toString());
        }
        catch(IOException ioexception)
        {
            System.out.println((new StringBuilder()).append("IOException: ").append(ioexception).toString());
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent hyperlinkevent)
    {
        if(hyperlinkevent.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED)
            linkActivated(hyperlinkevent.getURL());
    }

    protected void linkActivated(URL url)
    {
        Cursor cursor = html.getCursor();
        Cursor cursor1 = Cursor.getPredefinedCursor(3);
        html.setCursor(cursor1);
        SwingUtilities.invokeLater(new PageLoader(url, cursor));
    }

    JEditorPane html;
}
