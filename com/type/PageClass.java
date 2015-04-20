package com.type;

public enum PageClass
{
	CLASS_0, // Not referenced, not modified
	CLASS_1, // Not referenced, modified
	CLASS_2, // Referenced, not modified
	CLASS_3; // Referenced, modified

	public static PageClass compute(byte r, byte m)
	{
		if (r == 0 && m == 0) return CLASS_0;
		if (r == 0 && m == 1) return CLASS_1;
		if (r == 1 && m == 0) return CLASS_2;
		if (r == 1 && m == 1) return CLASS_3;
		return null;
	}
}
