package RSA;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.util.*;
import java.io.*;

import com.sun.javafx.scene.layout.region.Margins;
import sun.security.util.Length;

import javax.xml.bind.DatatypeConverter;

class ShifrFile
{
    //get file size in bytes
    private static long getFileSize(String filename)
    {
        File file=new File(filename);
        if(!file.exists() || !file.isFile()){
            return -1;
        }
        return file.length();
    }
    //
    public static void shifrFile(String input,String output,RSA rsa)
    {
        Long filesize=getFileSize(input);
        System.out.print("filesize:"); System.out.println(filesize);
        int lenSrcBlock=rsa.getSrcBlockLength(), lenDstBlock=rsa.getDstBlockLength();
        byte[] src=new byte[lenSrcBlock],dst=new byte[lenDstBlock];
        try
        {
            FileInputStream inputStream =new FileInputStream(input);
            FileOutputStream outputStream = new FileOutputStream(output);
            int total=0,nRead=0;
            //write file size
            {
                //filesize=new Long(255*256);
                src[0]=(byte)((filesize/(1<< 0))%256);
                src[1]=(byte)((filesize>> 8)%256);
                src[2]=(byte)((filesize>>16)%256);
                src[3]=(byte)((filesize>>24)%256);

                Long res=new Long((((((int)src[3])<0?(int)src[3]+256:(int)src[3])*256+(((int)src[2])<0?(int)src[2]+256:(int)src[2]))*256+(((int)src[1])<0?(int)src[1]+256:(int)src[1]))*256+(((int)src[0])<0?(int)src[0]+256:(int)src[0]));
                Random rnd=new Random((new Date()).getTime());
                rnd.nextBytes(dst);
                for(int i=4;i<lenSrcBlock;++i) src[i]=dst[i];

                rsa.shifr(src,dst,lenSrcBlock);
                outputStream.write(dst);
            }
            //write file
            while((nRead=inputStream.read(src))!=-1)
            {
                if(nRead<lenSrcBlock)
                {
                    Random rnd=new Random((new Date()).getTime());
                    rnd.nextBytes(dst);
                    for(int i=nRead;i<lenSrcBlock;++i) src[i]=dst[i];
                }
                //System.out.print(">");
                //System.out.println(new String(src));
                total+=nRead;
                rsa.shifr(src,dst,lenSrcBlock);
                //System.out.println(dst);
                //rsa.deshifr(dst,src,lenDstBlock);
                outputStream.write(dst);
            }
            inputStream.close();
            outputStream.close();
        }
        catch (FileNotFoundException er)
        {
            //er
            System.out.print("ERROR:");System.out.println(er.getMessage());
        }
        catch (IOException er)
        {
            //error
            System.out.print("ERROR:");System.out.println(er.getMessage());
        }
    }

    public static void deshifrFile(String input,String output,RSA rsa)
    {
        Long filesize;
        int lenSrcBlock=rsa.getDstBlockLength(), lenDstBlock=rsa.getSrcBlockLength();
        byte[] src=new byte[lenSrcBlock],dst=new byte[lenDstBlock];
        try
        {
            FileInputStream inputStream =new FileInputStream(input);
            FileOutputStream outputStream = new FileOutputStream(output);
            int total=0,nRead=0;
            //read file length
            {
                inputStream.read(src);
                rsa.deshifr(src,dst,lenSrcBlock);
                Long res=new Long((((((int)dst[3])<0?(int)dst[3]+256:(int)dst[3])*256+(((int)dst[2])<0?(int)dst[2]+256:(int)dst[2]))*256+(((int)dst[1])<0?(int)dst[1]+256:(int)dst[1]))*256+(((int)dst[0])<0?(int)dst[0]+256:(int)dst[0]));
                filesize=res;
            }
            int cntBlocks=filesize.intValue()/(lenDstBlock), finalLen=(filesize).intValue()%(lenDstBlock);
            System.out.print("filesize:"); System.out.println(filesize);
            System.out.print("cntBlocks:"); System.out.println(cntBlocks);
            System.out.print("finalLen:"); System.out.println(finalLen);
            //read file
            while((nRead=inputStream.read(src))!=-1)
            {
                //System.out.print(">");
                //System.out.println(new String(src));
                total+=nRead;
                rsa.deshifr(src,dst,lenSrcBlock);
                //System.out.println(dst);
                if(cntBlocks==0)
                {
                    System.out.print("finalLen:");System.out.println(finalLen);
                    byte[] tmp=new byte[finalLen];
                    for(int i=0;i<finalLen;++i) tmp[i]=dst[i];
                    outputStream.write(tmp);
                }
                else
                    outputStream.write(dst);
                --cntBlocks;
            }
            inputStream.close();
            outputStream.close();
        }
        catch (FileNotFoundException er)
        {
            //er
            System.out.print("ERROR:");System.out.println(er.getMessage());
        }
        catch (IOException er)
        {
            //error
            System.out.print("ERROR:");System.out.println(er.getMessage());
        }
    }

}

class HelloWorldFrame extends Frame
{
	HelloWorldFrame(String s){
		super(s);
	}
	public void paint(Graphics g){
		g.setFont(new Font("Serif",Font.ITALIC|Font.BOLD,30));
		g.drawString("Hello, XXI century World!", 20, 100);
	}
	public static void main2(String[] args){
		Frame f=new HelloWorldFrame("Hello");
		f.setSize(400,150);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev)
			{
				System.exit(0);
			}
		});
	}



}

class Program
{

	public BigInteger[] getM(int len1InBytes,int totalLenInBytes,Random rnd)
	{

		BigInteger[] Res=new BigInteger[2];
		Res[0]=BigInteger.probablePrime(len1InBytes*8, rnd);
		byte[] buffer=Res[0].toByteArray();
		int len2InBytes=totalLenInBytes-len1InBytes;
		BigInteger comparePow=new BigInteger("2").pow(8*totalLenInBytes-1);
		while(true)
		{
			Res[1]=BigInteger.probablePrime(len2InBytes*8, rnd);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
			out.print(Res[0]); out.print(' '); out.println(Res[1]);
			out.print(Res[0].bitLength()); out.print(' '); out.print(Res[1].bitLength()); out.print(' ');out.println(Res[0].multiply(Res[1]).bitLength());
			out.flush();
			if(Res[0].multiply(Res[1]).compareTo(comparePow)>0)
			{
				Res[0]=new BigInteger(buffer);
				return Res;
			}
		}
	}
	public boolean isPrime(int value)
	{
		for(int i=2;i<value;++i)
		{
			if(value%i==0)
			{
				PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
				out.print("i=");out.println(i);
				out.flush();
				return false;
			}
		}
		return true;
	}
	public HashMap<Integer, Integer> parse(BigInteger arg,Integer maxDivisor)
	{
		BigInteger dilimiter=BigInteger.ZERO.add(arg);
		HashMap<Integer,Integer> res=new HashMap<Integer, Integer>();
		Integer i,pow;
		BigInteger I;
		BigInteger[] r;
		//check 2
		if(maxDivisor>=2)
		{
			i=2;pow=0;
			I=BigInteger.valueOf(i);
			r=dilimiter.divideAndRemainder(I);
			while(r[1].compareTo(BigInteger.ZERO)==0)
			{
				++pow;
				dilimiter=r[0];
				r=dilimiter.divideAndRemainder(I);
			}
			if(pow>0) res.put(i, pow);
		}
		//check 5
		if(maxDivisor>=5)
		{
			i=5;pow=0;
			I=BigInteger.valueOf(i);
			r=dilimiter.divideAndRemainder(I);
			while(r[1].compareTo(BigInteger.ZERO)==0)
			{
				++pow;
				dilimiter=r[0];
				r=dilimiter.divideAndRemainder(I);
			}			
			if(pow>0) res.put(i, pow);
		}
		i=3;
		//while()
		return res;		
	}
    public static void main88(String[] args)
    {
        Random rnd=new Random(10002);
        BigInteger[] M=RSA.getM(4, 129, rnd);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        BigInteger E=BigInteger.valueOf(2141);
        BigInteger D=RSA.getD(E,M,rnd);
        out.print("1==");
        out.println(E.multiply(D).mod(M[1].subtract(BigInteger.ONE).multiply(M[0].subtract(BigInteger.ONE))));
        out.flush();
        RSA rsa=new RSA(128,129,M[0].multiply(M[1]),D,E);
        ShifrFile.shifrFile("D:\\input.rar","D:\\output.txt",rsa);
        ShifrFile.deshifrFile("D:\\output.txt","D:\\input2.rar",rsa);
        //ShifrFile.shifrFile("D:\\out.mov","D:\\out.mov.sh",rsa);
        //ShifrFile.deshifrFile("D:\\out.mov.sh","D:\\out2.mov",rsa);
    }
	public static void main(String[] args) {
        Random rnd = new Random(10);
        BigInteger[] M = RSA.getM(50, 127, rnd);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        out.print(BigInteger.ONE.compareTo(BigInteger.ZERO) < 0);
        out.println("M:"+M[0].multiply(M[1]));
        out.println(M[0]);
        out.println(M[1]);
        out.flush();
        BigInteger E = BigInteger.valueOf(2 + 16 + 1);
        BigInteger D = RSA.getD(E, M, rnd);
        out.println("E:");
        out.println(E);
        out.println("D:");
        out.println(D);
        out.print("1==");
        out.println(E.multiply(D).mod(M[1].subtract(BigInteger.ONE).multiply(M[0].subtract(BigInteger.ONE))));
        out.flush();
        {
            UserShifrator userShifrator = new UserShifrator(125, 128, M[0].multiply(M[1]), D, E);
            byte[] data = userShifrator.shifr("hello");
            String dataBase64= DatatypeConverter.printBase64Binary(data);

            System.out.println("dataBase64:"+dataBase64);
            //for(int i=0;i<data.length;++i) System.out.println((int)data[i]); System.out.println();

            System.out.println("deshifr userShifrator:" + userShifrator.deshifr(data)+":");
            System.out.println("deshifr userShifrator:" + userShifrator.deshifr(DatatypeConverter.parseBase64Binary(dataBase64))+":");
        }
        //ShifrFile.shifrFile(new String("D:\\input.txt"),new String("D:\\output.txt"),new RSA(7,8,RSA.getM(4,8,rnd)[0],BigInteger.TEN,BigInteger.TEN));
	}
	public static void main22(String[] args)
	{
		BigInteger M=new BigInteger("3");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		
		Program prog=new Program();
		BigInteger A=BigInteger.valueOf(5797);
		BigInteger B=BigInteger.valueOf(7038);
		//BigInteger res[]=prog.gcdLinear(A,B);
		//out.print(res[0]); out.print(' ');	out.print(res[1]); out.print(' '); out.println(res[2]);
		//out.println(A.multiply(res[1]).add(B.multiply(res[2])).compareTo(res[0])==0);
		out.println(" gcd:"+A.gcd(B).toString());
		out.flush();
	}
}