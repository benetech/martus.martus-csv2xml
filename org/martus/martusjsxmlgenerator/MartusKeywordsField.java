/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusKeywordsField extends StringField 
{
	public MartusKeywordsField()
	{
	}
	
	public MartusKeywordsField(Object valueToUse)
	{
		super("keywords", "", valueToUse);
	}

	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusKeywordsField";
	}
}
