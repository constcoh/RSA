package RSA;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.soap.Text;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.awt.*;
import java.io.*;
/**
 * Created by User on 18.01.2016.
 */


class GlobalRandom{
    public static Random rnd=new Random(System.currentTimeMillis());
}

class Message{
    private Date date;
    private String content;
    private boolean isMy;
    public Message(String content,boolean isMy)
    {
        this.date=new Date();
        this.content=content;
        this.isMy=isMy;
    }
    public boolean isMy(){return isMy;}
    public String getContent(){return content;}
    public Date getDate(){return date;}
}

class UserContact
{
    private User user;
    private UserConnection userConnection;
    public UserContact(User user)
    {
        this.user=user;
        userConnection=new UserConnection(UserListener.get(),user);
    }
    public User getUser(){return user;};

    public void open(Socket socket) throws Exception {userConnection.start(socket); }
    public void open() throws Exception {userConnection.start();}
    public void close() throws Exception {userConnection.stop();}
    public void send(String arg) throws Exception  {userConnection.send(arg); }
    public Vector<Message> getHistory(){return  userConnection.getHistory();}
    public boolean isOpen(){return userConnection.isStarted();}
}



class UserContactList{
    protected LinkedList<UserContact> contacts;
    private UserContactList(){contacts=new LinkedList<UserContact>();}
    public void add(UserContact arg){contacts.add(arg);}
    public ListIterator<UserContact> getIterator(){return contacts.listIterator();}
    public UserContact getByIndex(Integer index)
    {
        if(index<0 || index>contacts.size()) return null;
        return contacts.get(index.intValue());
    }
    public UserContact getByLogin(String arg)
    {
        Integer index=-1;
        {
            Iterator<UserContact> iter = contacts.iterator();
            Integer i = 0;
            while (iter.hasNext()) {
                if (iter.next().getUser().getLogin().compareTo(arg) == 0) index = i;
                ++i;
            }
        }
        if(index<0 || index>contacts.size()) return null;
        return contacts.get(index.intValue());
    }
    public String[] getLoginList()
    {
        String[] res=new String[contacts.size()];
        Iterator<UserContact> iter=contacts.iterator();
        Integer i=0;
        while (iter.hasNext())
            res[i++]=iter.next().getUser().getLogin();
        return res;
    }
    public void removeByLogin(String arg)
    {
        Integer index=-1;
        {
            Iterator<UserContact> iter = contacts.iterator();
            Integer i = 0;
            while (iter.hasNext()) {
                if (iter.next().getUser().getLogin().compareTo(arg) == 0) index = i;
                ++i;
            }
        }
        if(index>=0) contacts.remove(index);
    }
    public void removeByIndex(Integer index)
    {
        if(index>=0) contacts.remove(index.intValue());
    }
    public void clear(){contacts.clear();}

    private static UserContactList list=null;
    public static UserContactList get(){if (list==null) list=new UserContactList(); return list;}

}





class NetServer{

}
public class NetConnection {

    public static void ConsoleRun(String[] args)
    {
        MainFrameSwing.get();
        //System.out.println("DIALOG:"+AddContactFrameSwing.Query(null));
    }
    public static void ConsoleRun2(String[] args)
    {
        BigInteger[] M=RSA.getM(100,256,new Random(0));
        BigInteger[] ED=RSA.getED(6,M, new Random(0));
        RSA rsa=new RSA(250,256,M[0].multiply(M[1]),ED[1],ED[0]);
        byte[] src=new byte[250];
        for(int i=0;i<250;++i) src[i]='a';
        byte[] dst=new byte[256];
        byte[] res=new byte[250];
        rsa.shifr(src,dst,250);
        rsa.deshifr(res,dst,250);
        System.out.println("1=");
        for(int i=0;i<250;++i) System.out.print(res[i]); System.out.println();

        System.out.print("OKKK");
    }
}
