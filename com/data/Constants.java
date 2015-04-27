package com.data;

public class Constants
{
	public static final String ALGORITHM_PROPERTY = "algorithm";
	public static final String RESET_PROPERTY = "reset";
	public static final String START_PROPERTY = "start";
	
    public static final int MAX_PAGECOUNT_POWER = 8;
    public static final int MIN_PAGECOUNT_POWER = 5;
    public static final int COLUMN_VIEW = (int) ((MAX_PAGECOUNT_POWER/2.) + 1);
    public static final long MAX_PAGE_COUNT = (long) Math.pow(2, MAX_PAGECOUNT_POWER);
    public static final long MIN_PAGE_COUNT = (long) Math.pow(2, MIN_PAGECOUNT_POWER);

    public static final short ADDRESS_RADIX = 16;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final boolean USE_LOGFILE = true;

    public static final long ANIMATION_DELAY = 10;
    public static final int MAX_PAGESIZE_POWER = 7;
    public static final int MIN_PAGESIZE_POWER = 5;
    public static final long MAX_PAGESIZE = (long) Math.pow(2, MAX_PAGESIZE_POWER);
    public static final long MIN_PAGESIZE = (long) Math.pow(2, MIN_PAGESIZE_POWER);
}
