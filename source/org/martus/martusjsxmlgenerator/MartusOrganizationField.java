/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusOrganizationField extends StringField 
{
	public MartusOrganizationField()
	{
	}
	
	public MartusOrganizationField(Object valueToUse)
	{
		super("organization", "", valueToUse);
	}

	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusOrganizationField";
	}
}
