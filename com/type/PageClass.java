package com.type;

public enum PageClass
{
	CLASS_0, // Not referenced, not modified
	CLASS_1, // Not referenced, modified
	CLASS_2, // Referenced, not modified
	CLASS_3; // Referenced, modified

	public static PageClass compute(boolean isReferenced, boolean isModified)
	{
		if (!isReferenced && !isModified) return CLASS_0;
		if (!isReferenced && isModified) return CLASS_1;
		if (isReferenced && !isModified) return CLASS_2;
		if (isReferenced && isModified) return CLASS_3;
		return null;
	}
}
