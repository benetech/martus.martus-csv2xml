/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusRequiredAuthorField extends StringField 
{
	public MartusRequiredAuthorField()
	{
		super();
	}
	
	public MartusRequiredAuthorField(Object valueToUse)
	{
		super(AUTHOR_TAG, "", valueToUse);
		requiredFieldAuthor = true;
	}
	
	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusRequiredAuthorField";
	}
	
	public static final String AUTHOR_TAG = "author";
}
