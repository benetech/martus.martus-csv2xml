/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusRequiredDateCreatedField extends SingleDateField 
{
	public MartusRequiredDateCreatedField()
	{
		super();
	}
	public MartusRequiredDateCreatedField(Object valueToUse)
	{
		super("entrydate", "", valueToUse);
		requiredFieldEntryDate = true;
	}
	
	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusRequiredDateCreatedField";
	}
	
}
