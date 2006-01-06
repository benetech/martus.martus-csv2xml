/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MultilineField extends MartusField 
{

	public MultilineField()
	{
	}

	public MultilineField(String tagToUse, String labelToUse, Object valueToUse)
	{
		super(tagToUse, labelToUse, valueToUse);
	}

	public String getType() 
	{
		return MULTILINE_TYPE;
	}

	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "MultilineField";
	}

}
