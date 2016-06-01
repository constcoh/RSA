package RSA;


import java.io.*;
import java.net.*;
import java.util.Enumeration;


/**
 * Hello world!
 *
 */
public class App
{
    //NET
    public static void main(String[] args)
    {
        //ChatNet.ConsoleRun(args);
        NetConnection.ConsoleRun(args);
        //Program.main(args);
    }
    public static  void  main11(String[] args)
    {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostAddress());
                }
            }
        }

        catch (SocketException error)
        {
            System.out.println("ERROR:" + error.getMessage());

        }

    }
    public static void main22(String[] args)
    {
        FolderFileConverter.FolderToFile("D:\\tmp","D:\\output.fl");
        FolderFileConverter.FileToFolder("D:\\output.fl","D:\\out");
    }
    public static void main88( String[] args )
    {

        byte rno[] ={-1,0,0,0};

        int lk= (rno[3]<<24)&0xff000000|
                (rno[2]<<16)&0x00ff0000|
                (rno[1]<< 8)&0x0000ff00|
                (rno[0]<< 0)&0x000000ff;
        byte[] src=rno;
        Long res=new Long((((((int)src[3])<0?(int)src[3]+256:(int)src[3])*256+(((int)src[2])<0?(int)src[2]+256:(int)src[2]))*256+(((int)src[1])<0?(int)src[1]+256:(int)src[1]))*256+(((int)src[0])<0?(int)src[0]+256:(int)src[0]));
        System.out.println(rno[0]);
        System.out.println(rno[1]);
        System.out.println(rno[2]);
        System.out.println(rno[3]);
        boolean v=true;
        for(int i=-128;i<=127;++i)
        {
            rno[0]=(byte) i;
            res=new Long((((((int)src[3])<0?(int)src[3]+256:(int)src[3])*256+(((int)src[2])<0?(int)src[2]+256:(int)src[2]))*256+(((int)src[1])<0?(int)src[1]+256:(int)src[1]))*256+(((int)src[0])<0?(int)src[0]+256:(int)src[0]));
            lk= (rno[3]<<24)&0xff000000|
                    (rno[2]<<16)&0x00ff0000|
                    (rno[1]<< 8)&0x0000ff00|
                    (rno[0]<< 0)&0x000000ff;
            if(res==lk) {}else v=false;
            System.out.print(res); System.out.print(' ');
        }
        System.out.println();
        System.out.println(lk);
        System.out.println(res);
        System.out.println(v);
        //Program.main(args);
    }
}
