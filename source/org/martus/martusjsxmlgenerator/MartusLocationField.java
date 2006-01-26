/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusLocationField extends StringField 
{
	public MartusLocationField()
	{
	}
	
	public MartusLocationField(Object valueToUse)
	{
		super("location", "", valueToUse);
	}

	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusLocationField";
	}

}
