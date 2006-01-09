/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import org.mozilla.javascript.Scriptable;

public class SingleDateField extends DateField 
{
	public SingleDateField()
	{
	}

	public SingleDateField(String tagToUse, String labelToUse, Object valueToUse, Object dateFormatToUse)
	{
		super(tagToUse, labelToUse, valueToUse, dateFormatToUse);
	}
	
	public String getType() 
	{
		return DATE_TYPE;
	}
	
	public String getMartusValue( Scriptable scriptable )
	{
		String rawDate = super.getMartusValue(scriptable);
		String martusDate = getMartusDate(rawDate);
		return "Simple:"+ martusDate;
	}

	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "SingleDateField";
	}
}
