/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

public class MartusSummaryField extends MultilineField 
{
	public MartusSummaryField()
	{
	}
	
	public MartusSummaryField(Object valueToUse)
	{
		super("summary", "", valueToUse);
	}

	//Actual Name called by the JavaScript
	public String getClassName()
	{
		return "MartusSummaryField";
	}
}
