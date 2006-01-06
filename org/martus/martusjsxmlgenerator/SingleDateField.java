/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

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

	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "SingleDateField";
	}
}
