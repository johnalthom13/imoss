package com.data;

public class Constants
{
    public static final int PAGE_POWER = 7;
    public static final int COLUMN_VIEW = (int) ((PAGE_POWER/2.) + 1);
    public static final int MAX_PAGE_COUNT = (int) Math.pow(2, PAGE_POWER);
    public static final int MIN_PAGE_COUNT = 2;

    public static final short ADDRESS_RADIX = 16;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final boolean USE_LOGFILE = true;

    public static final long ANIMATION_DELAY = 100;
    public static final long MAX_PAGESIZE = (long) Math.pow(2, 26);
    public static final long MIN_PAGESIZE = 64;
}
