package com.type;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PageList extends ArrayList<Page>
{
	public void updateBounds(long block_)
	{
		for (int i = 0; i < size(); ++i)
		{
			Page page = get(i);
            page.setBounds((block_*(i + 1))-1, block_*i);
            set(i, page);
		}
	}
	
	public PageList getAllFromClass(PageClass pgClass)
	{
		PageList temp = new PageList();
		for (Page page : this)
		{
			if (page.getPhysicalPage() != -1 && page.getPageClass() == pgClass)
			{
				temp.add(page);
			}
		}
		return temp;
	}
}