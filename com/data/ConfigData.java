package com.data;

import java.util.ArrayList;

import com.type.Instruction;

public class ConfigData
{
	public static ArrayList<Instruction> INSTRUCTIONS_LIST = new ArrayList<>();
	public static int BLOCK_SIZE = 0;
	public static int VIRTUAL_PAGE_COUNT = 0;
	public static int LOWER_RO_PAGE;
	public static int UPPER_RO_PAGE;
	public static boolean INTERACTIVE_MODE = false;

    public static long ANIMATION_DELAY = 0;
}
