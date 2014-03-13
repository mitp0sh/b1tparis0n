package de.mitp0sh.b1tparis0n;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class EntryPoint
{
	public static String ORIG       = null;
	public static String MUTA       = null;
	public static String RESULT_DIR = null;
	
	public static ArrayList<BytePatch> bytePatchList = new ArrayList<BytePatch>();
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("[b1p] - B1tpARiS0n v1.0 - mitp0sh @ 2014");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println();
		System.out.flush();
		
		if(args.length != 3)
		{
			System.out.println("  usage: <b1tparis0n> \"original_file\" \"mutated_file\" \"result_directory\"");
			System.out.println();
			System.out.flush();
			System.err.println("  [-] - missing commandline parameters!");
			System.err.flush();
			System.exit(-1);
		}
		
		ORIG       = args[0];
		MUTA       = args[1];
		RESULT_DIR = args[2];
		
		File origFile = new File(ORIG);
		try 
		{
			if(!origFile.exists() || !origFile.exists())
			{
				throw new FileNotFoundException();
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println("  [-] - original file parameter is NOT a file!");
			System.err.flush();
			System.exit(-1);
		}
		
		
		File mutaFile = new File(MUTA);
		try 
		{
			if(!mutaFile.exists() || !mutaFile.exists())
			{
				throw new FileNotFoundException();
			}
		}
		catch(FileNotFoundException e1)
		{
			System.err.println("  [-] - mutated file parameter is NOT a file!");
			System.err.flush();
			System.exit(-1);
		}
		
		File dir = new File(RESULT_DIR);
		try 
		{
			if(!dir.exists() || !dir.isDirectory())
			{
				throw new FileNotFoundException();
			}
		}
		catch(FileNotFoundException e1)
		{
			System.err.println("  [-] - result directory parameter is NOT a directory!");
			System.err.flush();
			System.exit(-1);
		}
		
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
			System.err.println("  [-] - unable to compare files, size not equal!");
			System.err.flush();
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
