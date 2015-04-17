package com;

import java.util.Random;

public class Common
{
    public static long generateRandomLong (long limit)
    {
        long i = -1;
        Random generator = new Random();
        while (i > limit || i < 0)
        {
            int intOne = generator.nextInt();
            int intTwo = generator.nextInt();
            i = (long) intOne + intTwo;
        }
        return i;
    }

    static public byte stringToByte (String s)
    {
        int i = 0;
        byte b = 0;
        try
        {
            i = Integer.parseInt(s.trim());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        b = (byte) i;
        return b;
    }

    static public int stringToInt (String s)
    {
        int i = 0;
        try
        {
            i = Integer.parseInt(s.trim());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }

        return i;
    }

    static public long stringToLong (String s)
    {
        long i = 0;
        try
        {
            i = Long.parseLong(s.trim());
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        return i;
    }
}

