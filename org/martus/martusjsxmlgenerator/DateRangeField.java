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
		super(tagToUse, labelToUse, valueToUse);
	}
	
	public String getMartusValue( Scriptable scriptable )
	{
		String dateInformation = super.getMartusValue(scriptable);
		String[] dateRangeInfo = dateInformation.split("_");
		if(dateRangeInfo.length !=3)
		{
			System.out.println("DateRange Incorrect must be in the form: beginDate_endDate_dateformat but ="+ dateInformation);
			return "ERROR: Date range incorrect:"+ dateInformation;
		}
		setDateFormatString(dateRangeInfo[2]);
		String martusStartDate = getMartusDate(dateRangeInfo[0]);
		String martusEndDate = getMartusDate(dateRangeInfo[1]);
		
		return "Range:" + martusStartDate + "," + martusEndDate;
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
