/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import org.mozilla.javascript.Scriptable;

public class DateRangeField extends DateField 
{
	private static final String DATE_RANGE_DELIMETER = "_";

	public DateRangeField()
	{
	}

	public DateRangeField(String tagToUse, String labelToUse, Object valueToUse, Object dateFormatToUse)
	{
		super(tagToUse, labelToUse, valueToUse, dateFormatToUse);
	}
	
//	public DateRangeField(String tagToUse, String labelToUse, String startDateToUse, String endDateToUse, String dateFormatToUse)
	//{
		//super(tagToUse, labelToUse, startDateToUse+endDateToUse, dateFormatToUse);
	//}

	
	
	public String getMartusValue( Scriptable scriptable ) throws Exception
	{
		String dateInformation = super.getMartusValue(scriptable);
		String[] dateRangeInfo = dateInformation.split(DATE_RANGE_DELIMETER);
		if(dateRangeInfo.length !=2)
		{
			System.out.println("DateRange Incorrect must be in the form: beginDate_endDate but ="+ dateInformation);
			return "ERROR: Date range incorrect:"+ dateInformation;
		}
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
