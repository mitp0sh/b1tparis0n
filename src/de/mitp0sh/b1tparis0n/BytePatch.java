package de.mitp0sh.b1tparis0n;

public class BytePatch
{
	private int           offset = 0;
	private byte            orig = 0;
	private byte            muta = 0;
	private String origBinString = "";
	private String mutaBinString = "";
	private String origHexString = "";
	private String mutaHexString = "";
	private String    offsString = "";

	public BytePatch(int offset, byte orig, byte muta) 
	{
		super();
	
		this.offset = offset;
		this.orig   = orig;
		this.muta   = muta;
		
		String origS = Integer.toBinaryString(orig & 0xff);
		String mutaS = Integer.toBinaryString(muta & 0xff);
		
		while(origS.length() != 8) origS = "0" + origS ;
		while(mutaS.length() != 8) mutaS = "0" + mutaS ;
		
		setOrigBinString(origS);
		setMutaBinString(mutaS);
		
		origS = Integer.toHexString(orig & 0xff);
		mutaS = Integer.toHexString(muta & 0xff);
		
		while(origS.length() != 2) origS = "0" + origS ;
		while(mutaS.length() != 2) mutaS = "0" + mutaS ;
		
		setOrigHexString(origS);
		setMutaHexString(mutaS);
	
		int  offsLen = Integer.toString(Integer.MAX_VALUE).length();
		String offsS = String.valueOf(offset);
		
		while(offsS.length() != offsLen) offsS = "0" + offsS ;
		
		setOffsString(offsS);
	}
	
	public byte getOrigByte()
	{
		return orig;
	}
	
	public void setOrigByte(byte orig) 
	{
		this.orig = orig;
	}
	
	public byte getMutaByte()
	{
		return muta;
	}
	
	public void setMutaByte(byte muta)
	{
		this.muta = muta;
	}
	
	public String getOrigBinString() 
	{
		return origBinString;
	}

	public void setOrigBinString(String origBinString) 
	{
		this.origBinString = origBinString;
	}

	public String getMutaBinString() 
	{		
		return mutaBinString;
	}

	public void setMutaHexString(String mutaHexString) 
	{
		this.mutaHexString = mutaHexString;
	}
	
	public String getOrigHexString() 
	{
		return origHexString;
	}

	public void setOrigHexString(String origHexString) 
	{
		this.origHexString = origHexString;
	}

	public String getMutaHexString() 
	{		
		return mutaHexString;
	}

	public void setMutaBinString(String mutaBinString) 
	{
		this.mutaBinString = mutaBinString;
	}
	
	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}
	
	public String getOffsString() 
	{
		return offsString;
	}

	public void setOffsString(String offsString) 
	{
		this.offsString = offsString;
	}
	
	@Override
	public String toString() 
	{
		String representation = "";
		
		representation += "Offset: " + getOffsString();
		representation += ", origByte = 0x" + getOrigHexString() + " ( " + getOrigBinString() + " )";
		representation += ", muteByte = 0x" + getMutaHexString() + " ( " + getMutaBinString() + " )";
		
		return representation;
	}
}
