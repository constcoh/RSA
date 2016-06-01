package RSA;

import java.io.*;

/**
 * Created by User on 17.01.2016.
 */
class FolderFileConverter
{
    private static void FolderToFile(File inputFile, FileOutputStream outputStream) throws FileNotFoundException, IOException
    {
        if(inputFile.isDirectory())
        {
            String dirName=inputFile.getName();
            System.out.println("FOLDER:"+dirName);
            byte[] dName = dirName.getBytes();
            {
                byte[] buf = new byte[1];
                buf[0] = 'd';
                outputStream.write(buf);
                buf[0] = (byte) dName.length;
                outputStream.write(buf);
            }
            outputStream.write(dName);

            File[] files=inputFile.listFiles();
            for (File file: files)  FolderToFile(file,outputStream);
            System.out.println("END FOLDER:"+dirName);
            {
                byte[] buf=new byte[1];
                buf[0]='e';
                outputStream.write(buf);
            }
        }
        if(inputFile.isFile()) {
            String fileName = inputFile.getName();
            byte[] fName = fileName.getBytes();
            System.out.println(" FILE:" + fileName + " fName.len:" + fName.length);
            {
                byte[] buf = new byte[1];
                buf[0] = 'f';
                outputStream.write(buf);
                buf[0] = (byte) fName.length;
                outputStream.write(buf);
            }
            outputStream.write(fName);
            {
                Long filesize = inputFile.length();
                byte src[]=new byte[4];
                src[0]=(byte)((filesize/(1<< 0))%256);
                src[1]=(byte)((filesize>> 8)%256);
                src[2]=(byte)((filesize>>16)%256);
                src[3]=(byte)((filesize>>24)%256);
                outputStream.write(src);
            }
            {
                FileInputStream inputStream=new FileInputStream(inputFile);
                int blockLen=1024*1024,nRead;
                byte[] buf=new byte[blockLen];
                while((nRead=inputStream.read(buf))!=-1)
                    outputStream.write(buf,0,nRead);
                inputStream.close();
            }
        }
    }
    public static void FolderToFile(String inputFolderName,String outputFileName)
    {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(outputFileName);
            File inputFile=new File(inputFolderName);
            FolderToFile(inputFile,outputStream);
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
    private static void ReadFileFromFile(FileInputStream inputStream,File outputDir) throws FileNotFoundException, IOException
    {
        String filename;
        //
        {
            byte[] buf=new byte[1];
            inputStream.read(buf);
            int len=(int) buf[0];
            byte[] fName=new byte[len];
            inputStream.read(fName);
            filename=new String(fName);

        }
        File file=new File(outputDir, filename);
        file.createNewFile();
        {
            FileOutputStream outputStream=new FileOutputStream(file);
            int filesize=-1;
            {
                byte[] fSize=new byte[4];
                inputStream.read(fSize);
                filesize = (fSize[3]<<24)&0xff000000|
                        (fSize[2]<<16)&0x00ff0000|
                        (fSize[1]<< 8)&0x0000ff00|
                        (fSize[0]<< 0)&0x000000ff;
            }
            System.out.println("FILE NAME:"+filename+" filesize:"+filesize);
            if(filesize>0){
                int blockLen=1024*1024,cntBlock=filesize/blockLen,ostBlock=filesize%blockLen,nRead;
                if(cntBlock>0){
                    byte[] buffer = new byte[blockLen];
                    for (int i = 0; i < cntBlock; ++i)
                        if ((nRead = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, nRead);
                }
                if(ostBlock>0){
                    byte[] buffer=new byte[ostBlock];
                    if ((nRead = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, nRead);
                }

            }
            outputStream.close();
        }
    }
    private static void ReadDirectoryFromFile(FileInputStream inputStream,File outputDir) throws FileNotFoundException, IOException
    {
        String dirname;
        //
        {
            byte[] buf=new byte[1];
            inputStream.read(buf);
            int len=(int) buf[0];
            byte[] dName=new byte[len];
            inputStream.read(dName);
            dirname=new String(dName);
        }
        File directory=new File(outputDir,dirname);
        directory.mkdir();
        byte[] buf=new byte[1];
        inputStream.read(buf);
        while(buf[0]!='e')
        {
            System.out.println("DIR  NAME:"+dirname+" "+(char)buf[0]);
            if(buf[0]=='d')
                ReadDirectoryFromFile(inputStream,directory);
            else if(buf[0]=='f')
                ReadFileFromFile(inputStream,directory);
            inputStream.read(buf);
        }
    }
    private static void FileToFolder(FileInputStream inputStream,File outputDir) throws FileNotFoundException, IOException
    {
        byte[] buf=new byte[1];
        inputStream.read(buf);
        if(buf[0]=='f')
        {
            ReadFileFromFile(inputStream,outputDir);
        }
        if(buf[0]=='d')
        {
            ReadDirectoryFromFile(inputStream,outputDir);
        }
    }
    public  static void FileToFolder(String inputFileName,String outputParentDirectoryName)
    {
        try
        {
            byte[] buffer;
            File outputDir=new File(outputParentDirectoryName);
            if(!outputDir.isDirectory()) outputDir.mkdirs();
            FileInputStream inputStream =new FileInputStream(inputFileName);
            FileToFolder(inputStream,outputDir);
            inputStream.close();
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

