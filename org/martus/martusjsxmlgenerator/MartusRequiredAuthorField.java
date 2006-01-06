/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusRequiredAuthorField extends MartusField 
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
	
	public String getType()
	{
		return STRING_TYPE;
	}
	
	public String getClassName()
	{
		return "MartusRequiredAuthorField";
	}
	
	public static final String AUTHOR_TAG = "author";

}
