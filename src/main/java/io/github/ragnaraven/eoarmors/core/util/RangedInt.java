package io.github.ragnaraven.eoarmors.core.util;

/**
 * Created by Ragnaraven on 10/5/2017 at 2:42 PM.
 */

//TODO: Mutable version should be made.
public class RangedInt
{
	public enum EMODES
	{
		NONE,
		INIT,
		ALWAYS
	}
	
	public final int min;
	public final int max;
	public final int defaultVal;
	private int value;
	
	public EMODES EMODE;
	
	/**Constructor exists because if youre just going to set min and max to the Integer defined constants, why are using this.**/
	public RangedInt (int defaultVal)                                  {this(defaultVal, EMODES.NONE);}
	public RangedInt (int defaultVal, EMODES EMODE)                   {this (0, Integer.MAX_VALUE, defaultVal, defaultVal, EMODE);}
	
	public RangedInt (int max, int defaultVal)                         {this(max, defaultVal, EMODES.NONE);}
	public RangedInt (int max, int defaultVal, EMODES EMODE)          {this (0, Integer.MAX_VALUE, defaultVal, defaultVal, EMODE);}
	
	public RangedInt (int min, int max, int defaultVal)                {this (min, max, defaultVal, EMODES.NONE);}
	public RangedInt (int min, int max, int defaultVal, EMODES EMODE) {this(min, max, defaultVal, defaultVal, EMODE);}
	
	public RangedInt (int min, int max, int defaultVal, int value)     {this(min, max, defaultVal, value, EMODES.NONE);}
	public RangedInt (int min, int max, int defaultVal, int value, EMODES EMODE)
	{
		this.min = min;
		this.max = max;
		this.defaultVal = defaultVal;
		
		this.EMODE = EMODE;
		
		if(EMODE != EMODES.NONE)
			setE(value);
		else
			set(value);
	}
	
	public int get()
	{
		return value;
	}
	
	public void set (int value)
	{
		if(EMODE == EMODES.ALWAYS)
		{
			setE(value);
			return;
		}
		
		this.value = value < min ? min : value > max ? max : value;
	}
	
	public void setE (int value)
	{
		if(value < min)
			throw new RangedIntException.RangedIntMinException(value, this);
		else if(value > max)
			throw new RangedIntException.RangedIntMaxException(value, this);
		else
			this.value = value;
	}
	
	public abstract static class RangedIntException extends RuntimeException
	{
		public static class RangedIntMaxException extends RangedIntException
		{
			public RangedIntMaxException (int failedValue, RangedInt rangedInt)
			{
				System.out.printf("Ranged Int value can not be greater than %d but the given value was %d.\n", rangedInt.max, failedValue);
			}
		}
		
		public static class RangedIntMinException extends RangedIntException
		{
			public RangedIntMinException (int failedValue, RangedInt rangedInt)
			{
				System.out.printf("Ranged Int value can not be lass than %d but the given value was %d.\n", rangedInt.min, failedValue);
			}
		}
	}
}
