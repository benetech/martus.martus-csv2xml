/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mozilla.javascript.Undefined;

abstract public class DateField extends MartusField 
{
	public DateField()
	{
	}

	public DateField(String tagToUse, String labelToUse, Object valueToUse, Object dateFormatToUse)
	{
		super(tagToUse, labelToUse, valueToUse);
		dateFormat = dateFormatToUse;
	}
	
	public String getMartusDate(String rawDate)
	{
		try 
		{
			if(Undefined.instance == dateFormat)
				dateFormat = MARTUS_DATE_FORMAT;
			
			SimpleDateFormat realDateFormat = new SimpleDateFormat(dateFormat.toString());
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

	Object dateFormat;
	final String MARTUS_DATE_FORMAT = "yyyy-MM-dd";
}
