/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusDetailsField extends MultilineField 
{
	public MartusDetailsField()
	{
	}
	
	public MartusDetailsField(Object publicValueToUse)
	{
		super(PUBLICINFO, "", publicValueToUse);
	}

	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusDetailsField";
	}

	public static final String PUBLICINFO = "publicinfo";
}
