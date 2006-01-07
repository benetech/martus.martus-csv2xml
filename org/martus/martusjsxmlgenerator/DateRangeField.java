/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import org.mozilla.javascript.Scriptable;

public class DateRangeField extends DateField 
{
	public DateRangeField()
	{
	}

	public DateRangeField(String tagToUse, String labelToUse, Object valueToUse, String dateFormatToUse)
	{
		super(tagToUse, labelToUse, valueToUse, dateFormatToUse);
	}
	
	public String getMartusValue( Scriptable scriptable )
	{
		String martusDate = super.getMartusValue(scriptable);
		return "DateRange:"+ martusDate;
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
