package RSA;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;


class RSA
{
    private int lenSrcBlock;
    private int lenDstBlock;
	private int lenM;
    public int getSrcBlockLength(){return lenSrcBlock;}
    public int getDstBlockLength(){return lenDstBlock;}
	private BigInteger M,D,E;
	public RSA(int lenSrcinBytes,int lenDstinBytes, BigInteger M, BigInteger D, BigInteger E)
	{
        this.lenSrcBlock=lenSrcinBytes;
        this.lenDstBlock=lenDstinBytes;
		this.M=new BigInteger(M.toByteArray());
		this.D=this.E=null;
		if(D!=null)
			this.D=new BigInteger(D.toByteArray());
		if(E!=null)
			this.E=new BigInteger(E.toByteArray());
	}
	//BigInteger --> array[offset..offset+cntBytes-1]
	private void BigIntegerToByteArray(BigInteger src,byte[] dst,int offset,int cntBytes)
	{
		byte[] arg=src.toByteArray();
		int finIndex=offset+cntBytes-1;
		for(int i=arg.length-1;i>=0;--i)
		{
			dst[finIndex--]=arg[i];
			//if(finIndex==-1) return;/////////////////////old string
			if(finIndex==offset-1) return;/////////////////////corrected 130216
		}
		//while(finIndex>=0) dst[finIndex--]=0;/////////////old string
		while(finIndex>=offset) dst[finIndex--]=0;/////////////corrected 130216
	}
	//array[offset..offset+cntBytes-1] --> BigInteger
	private BigInteger ByteArrayToBigInteger(byte[] src,int offset,int cntBytes)
	{
		byte[] res=new byte[cntBytes+1];
		res[0]=0;
		for(int i=1;i<=cntBytes;++i)
		{
			res[i]=src[i+offset-1];
		}
		return new BigInteger(res);
	}
	//dst=(src^Pow)%M, lenBlock - длина блока в байтах, cntBlock - количество блоков
	private void convert(byte[] src,int lenSrcBlock,byte[] dst,int lenDstBlock,int cntBlock,BigInteger Pow)
	{
        //System.out.println("Check:");
        //System.out.print("  M:");System.out.println(M);
        //System.out.print("  D:");System.out.println(D);
        //System.out.print("  E:");System.out.println(E);
		//System.out.println("cntBlocks:"+cntBlock+" src:"+new String(src));
		for(int i=0;i<cntBlock;++i)
		{
			BigInteger tmp=ByteArrayToBigInteger(src, i*lenSrcBlock, lenSrcBlock);
            //System.out.print(" IN:");
            //System.out.println(tmp);
            //tmp.modPow(arg0, arg1)
            //System.out.print("IN mod M:\n    ");
            //System.out.println(tmp.mod(M));
			tmp=tmp.modPow(Pow, M);
            //System.out.print("OUT:");
            //System.out.println(tmp);
			BigIntegerToByteArray(tmp, dst, i*lenDstBlock, lenDstBlock);
		}
		//System.out.println("dst:");
		//for(int i=0;i<dst.length;++i) System.out.println((int)dst[i]); System.out.println();
	}
	//функция шифрования блока кратного длине ключа
	public void shifr(byte[] src,byte[] dst,int cntBytes)
	{
		convert(src,lenSrcBlock, dst, lenDstBlock, cntBytes/lenSrcBlock, E);
	}
	//функция расшифрования блока кратного длине ключа
	public void deshifr(byte[] src,byte[] dst, int cntBytes)
	{
		convert(src,lenDstBlock, dst,lenSrcBlock, cntBytes/lenDstBlock, D);
	}
	//получение простого числа длиной cntBytes
	public BigInteger getPrime(int cntBytes,Random rnd)
	{
		return BigInteger.probablePrime(cntBytes*8, rnd);
	}
	//0 - НОД A и B, 1,2 - коэффициенты линецного разложения
	public static BigInteger[] gcdLinear(BigInteger A, BigInteger B)
	{
		BigInteger[] res=new BigInteger[3];//res[0]=A*res[1]+B*res[2]=gcd
		//getting q_i
		Stack<BigInteger> Q=new Stack<BigInteger>();
		BigInteger[] r=B.divideAndRemainder(A);
		BigInteger forDiv=A,divisor=r[1];
		int Qlen=0;
		BigInteger gcd=BigInteger.valueOf(0).add(A);
		while(r[1].compareTo(BigInteger.valueOf(0))!=0)
		{
			gcd=r[1];
			Q.push(r[0]); ++Qlen;
			r=forDiv.divideAndRemainder(divisor);
			forDiv=divisor;divisor=r[1];
		}
		res[0]=gcd;
		//checking len(Q)==0
		if(Qlen==0)//B=A*q1
		{
			res[1]=r[0].subtract(BigInteger.ONE).negate();
			res[2]=BigInteger.ONE;
			return res;
		}
		//checking len(Q)==1
		if(Qlen==1)//B=A*q1+r1, A=r1*q2,nod=r1
		{
			res[1]=Q.peek().negate();
			res[2]=BigInteger.ONE;
			return res;
		}
		//Getting Kj(q1,..,qj) 
		{
			BigInteger prev=BigInteger.ONE;
			Iterator<BigInteger> i=Q.iterator();
			BigInteger tek=i.next();
			while(i.hasNext())
			{
				BigInteger next=prev.add(tek.multiply(i.next()));
				prev=tek;
				tek=next;
			}
			res[1]=tek;
			if(Qlen%2==1) res[1]=res[1].negate();
		}
		//getting Kj-1(q2,...,qj)
		{
			BigInteger prev=BigInteger.ONE;
			Iterator<BigInteger> i=Q.iterator();
			i.next();
			BigInteger tek=i.next();
			while(i.hasNext())
			{
				BigInteger next=prev.add(tek.multiply(i.next()));
				prev=tek;
				tek=next;
			}
			res[2]=tek;
			if((Qlen-1)%2==1) res[2]=res[2].negate();
		}
		return res;
	} 

	//генерирование M=p1*p2, по длине p1 и M в байтах
	public static BigInteger[] getM(int len1InBytes,int totalLenInBytes,Random rnd)
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
			//out.print(Res[0]); out.print(' '); out.println(Res[1]);
			//out.print(Res[0].bitLength()); out.print(' '); out.print(Res[1].bitLength()); out.print(' ');out.println(Res[0].multiply(Res[1]).bitLength());
			//out.flush();
			if(Res[0].multiply(Res[1]).compareTo(comparePow)>0)
			{
				Res[0]=new BigInteger(buffer);
				return Res;
			}
		}
	}

    private static BigInteger nextRandomBigInteger(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }

    public static  BigInteger[] getED(int lenEInBytes,BigInteger[] M,Random rnd)
    {
        BigInteger E;
        BigInteger lim =BigInteger.valueOf(2).pow(lenEInBytes*8).subtract(BigInteger.ONE);
        E=nextRandomBigInteger(lim);
        BigInteger phiM=M[0].subtract(BigInteger.ONE).multiply(M[1].subtract(BigInteger.ONE));
        //D*E=1(mod phi M)
        BigInteger[] gcd=gcdLinear(E,phiM);
        while (gcd[0].compareTo(BigInteger.ONE)!=0)
        {
            E=nextRandomBigInteger(lim);
            gcd=gcdLinear(E,phiM);
        }
        BigInteger D=gcd[1];
        while (D.compareTo(BigInteger.ZERO)<0) D=D.add(phiM);
        while (D.compareTo(phiM)>0) D=D.subtract(phiM);
        BigInteger[] res=new BigInteger[2];
        res[0]=E;
        res[1]=D;
        return res;
    }

    //get D by given E, return -1 if E is immpossible
    public static  BigInteger getD(BigInteger E,BigInteger[] M,Random rnd)
    {
        BigInteger phiM=M[0].subtract(BigInteger.ONE).multiply(M[1].subtract(BigInteger.ONE));
        //D*E=1(mod phi M)
        BigInteger[] gcd=gcdLinear(E,phiM);
        if (gcd[0].compareTo(BigInteger.ONE)!=0) return BigInteger.valueOf(-1);

        BigInteger D=gcd[1];
        while (D.compareTo(BigInteger.ZERO)<0) D=D.add(phiM);
        while (D.compareTo(phiM)>0) D=D.subtract(phiM);
        return D;
    }
}