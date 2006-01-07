/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class DateField extends MartusField 
{
	public DateField()
	{
	}

	public DateField(String tagToUse, String labelToUse, Object valueToUse)
	{
		super(tagToUse, labelToUse, valueToUse);
	}

	public DateField(String tagToUse, String labelToUse, Object valueToUse, String dateFormatToUse)
	{
		this(tagToUse, labelToUse, valueToUse);
		dateFormat = dateFormatToUse;
	}
	
	public void setDateFormatString (String dateFormatToUse)
	{
		dateFormat = dateFormatToUse;
	}
	
	public String getMartusDate(String rawDate)
	{
		try 
		{
			SimpleDateFormat realDateFormat = new SimpleDateFormat(dateFormat);
			Date realDate = realDateFormat.parse(rawDate);
			SimpleDateFormat martusDateFormat = new SimpleDateFormat(MARTUS_DATE_FORMAT);
			return martusDateFormat.format(realDate);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			return "ERROR:Unknown Date:" + rawDate;
		}
	}

	String dateFormat;
	final String MARTUS_DATE_FORMAT = "yyyy-MM-dd";
}
