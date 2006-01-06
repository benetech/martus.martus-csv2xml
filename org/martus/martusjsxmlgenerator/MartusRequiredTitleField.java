/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusRequiredTitleField extends StringField 
{
	public MartusRequiredTitleField()
	{
		super();
	}
	public MartusRequiredTitleField(Object valueToUse)
	{
		super(TITLE_TAG, "", valueToUse);
		requiredFieldTitle = true;
	}
	
	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusRequiredTitleField";
	}
	
	public static final String TITLE_TAG = "title";
}
