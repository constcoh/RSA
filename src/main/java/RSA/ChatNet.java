package RSA;

import com.sun.corba.se.spi.activation.Server;
import com.sun.org.apache.xerces.internal.impl.dv.DatatypeException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import javax.xml.soap.SAAJResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.xml.bind.*;

/**
 * Created by User on 12.02.2016.
 */

class UserShifrator
{
    private static final Integer LEN_SRC_IN_BYTES=125;
    private static final Integer LEN_DST_IN_BYTES=128;
    protected Integer lenSrcinBytes,lenDstinBytes;
    protected RSA rsa;
    public UserShifrator(int lenSrcinBytes,int lenDstinBytes,BigInteger M,BigInteger D, BigInteger E)
    {
        this.lenDstinBytes = lenDstinBytes;
        this.lenSrcinBytes = lenSrcinBytes;
        rsa = new RSA(lenSrcinBytes, lenDstinBytes, M, D, E);
    }
    public UserShifrator(BigInteger M,BigInteger D, BigInteger E)
    {
        this.lenDstinBytes = LEN_DST_IN_BYTES;
        this.lenSrcinBytes = LEN_SRC_IN_BYTES;
        rsa = new RSA(lenSrcinBytes, lenDstinBytes, M, D, E);
    }
    public byte[] shifr(String data) {
        byte[] src = data.getBytes();
        if (src.length % lenSrcinBytes > 0) {
            byte[] buf = src;
            int len = src.length + lenSrcinBytes - src.length % lenSrcinBytes;
            src = new byte[len];
            for (int i = 0; i < buf.length; ++i) src[i] = buf[i];
            src[buf.length]=0;
            for (int i = buf.length+1; i < src.length; ++i) src[i] = (byte)GlobalRandom.rnd.nextInt();
            //System.out.println("ARG:"+new String(src)+": buf.l="+buf.length);
            //System.out.println("src[buf.length]="+src[5]);
        }
        byte[] dst = new byte[src.length / lenSrcinBytes * lenDstinBytes];
        //System.out.println("Before shifr: src:"+new String(src)+" src.length:"+src.length);
        rsa.shifr(src, dst, src.length);
        //System.out.println("src.length="+src.length+"; dst.length="+dst.length+" dst:"+new String(dst));
        return dst;
    }
    public String deshifr(byte[] data){
        if(data.length==0) return new String();
        int cntBlocks=data.length/lenDstinBytes;
        byte[] src=new byte[cntBlocks*lenSrcinBytes];
        rsa.deshifr(data,src,cntBlocks*lenDstinBytes);
        //System.out.println("src.length="+src.length+"; dst.length="+data.length);

        //System.out.println("deshifr src:");
        //for(int i=0;i<src.length;++i) System.out.print((char)src[i]); System.out.println();

        //System.out.println("src[buf.length]="+src[5]);

        {
            int ZeroIndex=-1;
            for (int i = 0; i < src.length; ++i) if (src[i] == 0) {ZeroIndex=i; break;}
            //System.out.println("ZeroIndex="+ZeroIndex);
            if(ZeroIndex>1)
            {
                byte[] buf=new byte[ZeroIndex];
                for(int i=0;i<ZeroIndex;++i) buf[i]=src[i];
                //System.out.println("BUF:"+new String(buf));
                src=buf;
            }
        }
        return new String(src);
    }

}

class User{
    private UserShifrator shifrator;
    private String ip,login;
    private Integer port;
    public String getIp(){return ip;}
    public String getLogin(){return login;}
    public Integer getPort(){return port;}
    public void setIp(String arg){this.ip=arg;}
    public void setLogin(String arg){this.login=arg;}
    public void setPort(Integer arg){this.port=arg;}

    public User(String ip,Integer port, String login,BigInteger M, BigInteger E,BigInteger D)
    {
        this.ip=ip; this.port=port; this.login=login;
        shifrator=new UserShifrator(M,D,E);
    }
    public User(String ip,Integer port, String login,BigInteger M, BigInteger E)
    {
        this.ip=ip; this.port=port; this.login=login;
        shifrator=new UserShifrator(M,BigInteger.ONE,E);
    }
    public User()
    {
        this.ip=null; this.port=null; this.login=null;
        shifrator=null;
    }
    public String shifr(String arg)
    {
        if(shifrator==null)
        {
            System.out.println("shifrator is null");return  arg;
        }
        return DatatypeConverter.printBase64Binary(shifrator.shifr(arg));
    }
    public String deshifr(String arg)
    {
        if(shifrator==null)
        {
            System.out.println("shifrator is null");return  arg;
        }
        return shifrator.deshifr(DatatypeConverter.parseBase64Binary(arg));
    }

    public UserShifrator getShifrator(){return shifrator;}
    public void setShifrator(UserShifrator userShifrator){shifrator=userShifrator;}
}

class UserListener extends User{
    private UserListener(String ip,Integer port, String login,BigInteger M, BigInteger E,BigInteger D)
    {
        super(ip,port,login,M,E,D);
    }
    private UserListener(){super("",10000,"",BigInteger.ZERO,BigInteger.ZERO);}
    private static UserListener singleton=null;
    public static UserListener get(){if(singleton==null) singleton=new UserListener(); return singleton;}
}

class Listener {
    private Listener(){ isStarted=new AtomicBoolean(false);};
    private static Listener listener=null;
    public static Listener get(){if(listener==null) listener=new Listener(); return listener;}

    private AtomicBoolean isStarted;
    public Boolean isStarted(){return isStarted.get();}

    private static final int NET_SERVER_TIMEOUT=1000;
    private AtomicBoolean needStop;//used for stop threadListener
    private Thread threadListener;
    class RunListener implements Runnable{
        private User user;
        public RunListener(User user){this.user=user;}
        public void run()
        {
            try
            {
                ServerSocket serverSocket=new ServerSocket(user.getPort());
                serverSocket.setSoTimeout(NET_SERVER_TIMEOUT);
                while (!needStop.get())
                {
                    try {
                        Socket socket=serverSocket.accept();
                        {
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            out.println(user.getLogin());
                            String clientLogin=in.readLine();
                            System.out.println("addClient Login:"+clientLogin);
                            UserContact userContact=UserContactList.get().getByLogin(clientLogin);
                            if(userContact!=null){
                                userContact.open(socket);
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        MainFrameSwing.get().updateConnection();
                                    }
                                });
                            }
                        }
                        //new UserConnection(UserListener.get(),new User()).start(socket);
                    }catch (SocketTimeoutException error)
                    {
                        //System.out.println("waits");
                    }

                    //operations for threadServer
                }
                serverSocket.close();
            }catch (Exception error) {
                error.printStackTrace();
            }
            finally {
                needStop.set(true);
                isStarted.set(false);
            }
        }
    }
    public void start(User user) throws Exception
    {
        if(isStarted.get()) return;
        needStop=new AtomicBoolean(false);
        threadListener=new Thread(new RunListener(user));
        if(threadListener==null) throw new Exception("ERROR THREAD CREATE");
        threadListener.start();
        isStarted.set(true);
    }
    public void stop()
    {
        if(!isStarted.get()) return;
        needStop.set(true);
        try {
            threadListener.join();
        }catch (InterruptedException error)
        {
            error.printStackTrace();
        }
        isStarted.set(false);
    }
}

class UserConnection
{
    private User user,myInfo;
    private static final int NET_READ_TIMEOUT=1000;
    private static final int NET_WAIT_SEND_TIMEOUT=1000;


    class MessageForSend
    {
        private String message;
        public MessageForSend(){message=new String();}
        public synchronized String get() {return message;}
        public synchronized void set(String arg){message=arg;}
    }
    private MessageForSend messageForSend;
    private AtomicBoolean isSent;//used for send message check

    class MessagesHistory
    {
        private Vector<Message> messagesHistory;
        public MessagesHistory(){messagesHistory=new Vector<Message>();}
        public synchronized void add(Message msg) {messagesHistory.add(msg);}
        public synchronized Vector<Message> getHistory(){
            Vector<Message> res=new Vector<Message>();
            Iterator<Message> iter= messagesHistory.iterator();
            while (iter.hasNext())
                res.add(iter.next());
            return res;
        }
    }
    private MessagesHistory messagesHistory;
    public Vector<Message> getHistory(){return messagesHistory.getHistory();}

    private AtomicBoolean isStarted;//used for correct start and stop
    private AtomicBoolean needStop;//used for stop threadClient
    private Thread threadClient;
    public Boolean isStarted(){return isStarted.get();}

    class RunClient implements Runnable{
        private User user,myInfo;
        private Socket clientSocket;
        public RunClient(User myInfo,User user){ this.myInfo=myInfo; this.user=user; clientSocket=null;}
        public RunClient(User myInfo,User user, Socket socket){ this.myInfo=myInfo; this.user=user; clientSocket=socket;}
        public void run()
        {
            try
            {
                PrintWriter out=null;
                BufferedReader in=null;
                if(clientSocket==null)
                    try {
                        clientSocket = new Socket(user.getIp(), user.getPort());
                        out=new PrintWriter(clientSocket.getOutputStream(), true);
                        in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out.println(myInfo.getLogin());
                        in.readLine();
                    }catch (Exception error)
                    {
                        System.out.println("not valid connection");
                        needStop.set(true);
                        isStarted.set(false);
                        return;
                    }
                clientSocket.setSoTimeout(NET_READ_TIMEOUT);
                out=new PrintWriter(clientSocket.getOutputStream(), true);
                in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.println(user.shifr("HELLO"));
                out=new PrintWriter(clientSocket.getOutputStream(), true);
                in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (!needStop.get())
                {

                    String inputLine;
                    try {
                        if ((inputLine = in.readLine()) != null) {
                            inputLine=myInfo.deshifr(inputLine);
                            System.out.print("input:");
                            System.out.println(inputLine);
                            messagesHistory.add(new Message(inputLine,false));
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    MainFrameSwing.get().updateMessageList();
                                }
                            });
                            if(inputLine.compareTo("CLOSE")==0)
                            {
                                needStop.set(true);
                                isStarted.set(false);
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        MainFrameSwing.get().updateConnection();
                                    }
                                });
                            }
                        }
                    }catch (SocketTimeoutException error) {
                    }
                    if(!isSent.get()) {
                        String msg=messageForSend.get();
                        out.println(user.shifr(msg));
                        messagesHistory.add(new Message(msg,true));
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                MainFrameSwing.get().updateMessageList();;
                            }
                        });
                        isSent.set(true);
                    }
                    //operations for threadClient
                    //System.out.print("wait");
                }
                out.close();
                in.close();
                clientSocket.close();
            }
            catch (SocketException error)
            {
                System.out.println("CLIENT THREAD ERROR:"+error.getMessage());
                error.printStackTrace();
            }
            catch (Exception error) {
                System.out.println("CLIENT THREAD ERROR:"+error.getMessage());
                error.printStackTrace();
            }finally {
                needStop.set(true);
                isStarted.set(false);
            }
            {
                Vector<Message> history=messagesHistory.getHistory();
                Iterator<Message> iter=history.iterator();
                while (iter.hasNext())
                {
                    Message msg=iter.next();
                    if(msg.isMy()){System.out.print("output:"); }else {System.out.print(" input:");}
                    System.out.println(msg.getDate().toString()+": "+msg.getContent());
                }
            }
        }
    }

    private void waitIsStartedIsTrue() throws InterruptedException
    {
        while (!isSent.get()) {
            //System.out.println("WAIT isSent.get()==true");
            Thread.sleep(NET_WAIT_SEND_TIMEOUT);
        }
    }
    public void send(String message) throws InterruptedException,Exception
    {
        if(!isStarted.get()) throw new Exception("ERROR: not opened");
        waitIsStartedIsTrue();
        messageForSend.set(message);
        isSent.set(false);
    }

    public void sendAndWait(String message) throws InterruptedException,Exception
    {
        if(!isStarted.get()) throw new Exception("ERROR: not opened");
        waitIsStartedIsTrue();
        messageForSend.set(message);
        isSent.set(false);
        waitIsStartedIsTrue();
    }

    public void start() throws Exception
    {
        if(isStarted.get()) throw new Exception("ERROR: already is started");

        needStop=new AtomicBoolean(false);
        threadClient=new Thread(new RunClient(myInfo,user));
        if(threadClient==null) throw new Exception("ERROR: thread create");
        threadClient.start();

        isStarted.set(true);
    }
    public void start(Socket socket) throws Exception
    {
        if(isStarted.get()) throw new Exception("ERROR: already is started");

        needStop=new AtomicBoolean(false);
        threadClient=new Thread(new RunClient(myInfo,user,socket));
        if(threadClient==null) throw new Exception("ERROR: thread create");
        threadClient.start();

        isStarted.set(true);
    }

    public UserConnection(User myInfo,User user)
    {
        isStarted=new AtomicBoolean(false);
        this.myInfo=myInfo; this.user=user;
        messagesHistory=new MessagesHistory();

        isSent=new AtomicBoolean(true); messageForSend=new MessageForSend();
    }

    public void stop()
    {
        if(!isStarted.get()) return;
        try {
            this.sendAndWait("CLOSE");
            needStop.set(true);
            threadClient.join();
        }catch (Exception error)
        {
            System.out.println("ERROR:"+error.getMessage());
            error.printStackTrace();
        }
        isStarted.set(false);
    }
}

class ConsoleClient
{
    private void setUser(BufferedReader br) throws IOException
    {
        System.out.print("ip:");UserListener.get().setIp(br.readLine());
        System.out.print("port:");UserListener.get().setPort(Integer.parseInt(br.readLine()));
        System.out.print("login:");UserListener.get().setLogin(br.readLine());
    }
    private void openConnect(BufferedReader br) throws IOException,InterruptedException,Exception
    {
        String ip,login;Integer port;
        System.out.print("ip:");ip=br.readLine();
        System.out.print("port:");port=Integer.parseInt(br.readLine());
        System.out.print("login:");login=br.readLine();
        /////////////////////////VERY BAD BAG -> NEED CORRECT
        User user=new User(ip,port,login,BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO);
        /////////////////////////VERY BAD BAG -> NEED CORRECT
        UserConnection userConnection=new UserConnection(UserListener.get(),user);
        userConnection.start();
        System.out.println("connected");
        while(true)
        {
            System.out.print("oo>");
            String command=br.readLine();
            if(!userConnection.isStarted()) return;
            if(command.compareTo("CLOSE")==0) {userConnection.stop(); break;}
            userConnection.send(command);
        }
    }
    public void go() throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            System.out.print("-->");
            String command=br.readLine();
            if(command.compareTo("SET USER")==0) setUser(br);
            if(command.compareTo("START SERVER")==0)
            {
                Listener.get().start(UserListener.get());
                System.out.println("server started");
            }
            if(command.compareTo("STOP SERVER")==0)
            {
                Listener.get().stop();
                System.out.println("server stoped");
            }
            if(command.compareTo("CONNECT")==0) openConnect(br);
        }
    }
}

public class ChatNet {

    public static void ConsoleRun(String[] args)
    {
        try {
            new ConsoleClient().go();
        }catch (Exception error)
        {
            System.out.println("ERROR:"+error.getMessage());
            error.printStackTrace();
        }
    }
    public static void ConsoleRun2(String[] args)
    {
        User user=new User("127.0.0.1",12345,"userServer",BigInteger.ZERO,BigInteger.ONE);
        try {
            Listener.get().start(user);
            Thread.sleep(10000);
        }
        catch (Exception error)
        {
            System.out.println(error.getMessage());
            error.printStackTrace();
        }
        Listener.get().stop();
        //System.out.println("DIALOG:"+AddContactFrameSwing.Query(null));
    }
}
