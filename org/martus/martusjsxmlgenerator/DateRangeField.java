/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class DateRangeField extends MartusField 
{
	public DateRangeField()
	{
	}

	public DateRangeField(String tagToUse, String labelToUse, Object valueToUse)
	{
		super(tagToUse, labelToUse, valueToUse);
	}
	
	public String getType() 
	{
		return DATERANGE_TYPE;
	}

	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "DateRangeField";
	}
}
