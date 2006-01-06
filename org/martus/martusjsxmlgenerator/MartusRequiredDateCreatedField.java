/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusRequiredDateCreatedField extends MartusField 
{
	public MartusRequiredDateCreatedField()
	{
		super();
	}
	public MartusRequiredDateCreatedField(Object valueToUse)
	{
		super(ENTRY_DATE_TAG, "", valueToUse);
		requiredFieldEntryDate = true;
	}
	
	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusRequiredDateCreatedField";
	}
	
	public String getType()
	{
		return DATE_TYPE;
	}
	
	public static final String ENTRY_DATE_TAG = "entrydate";

}
