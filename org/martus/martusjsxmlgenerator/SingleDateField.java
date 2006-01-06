/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import org.mozilla.javascript.Scriptable;

public class SingleDateField extends MartusField 
{
	public SingleDateField()
	{
	}

	public SingleDateField(String tagToUse, String labelToUse, Object valueToUse)
	{
		super(tagToUse, labelToUse, valueToUse);
	}
	
	public String getType() 
	{
		return DATE_TYPE;
	}
	
	public String getMartusValue( Scriptable scriptable )
	{
		String rawDate = super.getMartusValue(scriptable);
//		Date realDate;
//		try 
//		{
//			SimpleDateFormat realDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//		realDate = realDateFormat.parse(rawDate);
			return "Simple:" + rawDate;
//		} catch (ParseException e) 
//		{
	//		e.printStackTrace();
		//	return rawDate;
		//}
		
	}


	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "SingleDateField";
	}
}
