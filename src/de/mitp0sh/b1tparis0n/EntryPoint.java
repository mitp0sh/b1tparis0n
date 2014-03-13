package de.mitp0sh.b1tparis0n;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class EntryPoint
{
	public static final String ORIG       = "/Users/bg/Desktop/report_result/1394618762/template_b336e20c4f1242c2b4512321ddb699c157a5f8072b5cf771bf51309e3da1b344.pdf";
	public static final String MUTA       = "/Users/bg/Desktop/report_result/1394618762/1394619278_0b0cee3a0cc05d7445746451e8cc52920caa446764e9b4c3fedd85b8fd1a7778/tmp_mutant.pdf";
	public static final String RESULT_DIR = "/Users/bg/Desktop/testpdfs";
	
	public static ArrayList<BytePatch> bytePatchList = new ArrayList<BytePatch>();
	
	public static void main(String[] args) throws IOException
	{
		File origFile = new File(ORIG);
		File mutaFile = new File(MUTA);
		
		String extension = "";
		int x = ORIG.lastIndexOf('.');
		if(x > 0)
		{
		    extension = ORIG.substring(x + 1);
		}
		
		byte[] origBytes = getBytesFromFile(origFile);
		byte[] mutaBytes = getBytesFromFile(mutaFile);
		
		if(origBytes.length != mutaBytes.length)
		{
			System.err.println("error - unable to compare files, size not equal!");
			System.exit(-1);
		}
		
		System.out.println("Comparing files now...");
		System.out.println();
		System.out.println("  DiffMap ->");
		System.out.println();;
		
		
		for(int i = 0; i < origBytes.length; i++)
		{
			byte origByte = origBytes[i];
			byte mutaByte = mutaBytes[i];
			
			if(origByte == mutaByte)
			{
				continue;
			}
			
			BytePatch patch = new BytePatch(i, origByte, mutaByte);
			bytePatchList.add(patch);
			System.out.println(patch.toString());
		}
		
		System.out.println();
		System.out.println("Number of BytePatches: " + bytePatchList.size());
		System.out.println();
		
		splitPatchesAndSerialize(RESULT_DIR, extension, bytePatchList, origBytes);
		
		System.out.println();
		System.out.println("Finished!");
		System.out.println();
	}
	
    public static byte[] getBytesFromFile(File file) throws IOException 
    {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int)length];

        int offset  = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0)
        {
            offset += numRead;
        }
        
        is.close();

        if (offset < bytes.length)
        {
            throw new IOException();
        }
        
        return bytes;
    }
    
    public static void splitPatchesAndSerialize(String resultDir, String ext, ArrayList<BytePatch> list, byte[] origBytes) throws IOException
    {
        System.out.println("Create split patch files...");
        System.out.println();
        
        Iterator<BytePatch> iter = list.iterator();
        while(iter.hasNext())
        {        	
        	BytePatch current = iter.next();
        
        	byte[] copyiedBytes = new byte[origBytes.length];
        	System.arraycopy(origBytes, 0, copyiedBytes, 0, origBytes.length);
        	
        	copyiedBytes[current.getOffset()] = current.getMutaByte();
        	
        	String splitPatchPathAndName = resultDir + "/" + current.getOffsString() + "." + ext;
        	System.out.println("Writing file... " + splitPatchPathAndName);
        	
        	BufferedOutputStream bos = null;
        	
        	FileOutputStream fos = new FileOutputStream(new File(splitPatchPathAndName));
        	bos = new BufferedOutputStream(fos);
        	bos.write(copyiedBytes);
        	bos.flush();
        	bos.close();
        	fos.flush();
        	fos.close();
        }
    }
}
