/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusDateOfEventField extends DateRangeField 
{
	public MartusDateOfEventField()
	{
		super();
	}
	public MartusDateOfEventField(Object value, String dateFormat)
	{
		super("eventdate", "", value, dateFormat);
	}
	
	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusDateOfEventField";
	}
}
