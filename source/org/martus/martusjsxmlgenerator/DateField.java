/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2005-2006, Beneficent
Technology, Inc. (Benetech).

Martus is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later
version with the additions and exceptions described in the
accompanying Martus license file entitled "license.txt".

It is distributed WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, including warranties of fitness of purpose or
merchantability.  See the accompanying Martus License and
GPL license for more details on the required license terms
for this software.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.

*/
package org.martus.martusjsxmlgenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mozilla.javascript.Undefined;

abstract public class DateField extends MartusField 
{
	public DateField()
	{
	}

	public DateField(String tagToUse, String labelToUse, Object valueToUse, Object dateFormatToUse, boolean isBottomSectionFieldToUse)
	{
		super(tagToUse, labelToUse, valueToUse, isBottomSectionFieldToUse);
		dateFormat = dateFormatToUse;
	}
	
	public String getMartusDate(String rawDate)
	{
		try 
		{
			if(Undefined.instance == dateFormat)
				dateFormat = MARTUS_DATE_FORMAT;
			
			SimpleDateFormat realDateFormat = new SimpleDateFormat(dateFormat.toString());
			Date realDate = realDateFormat.parse(rawDate);
			SimpleDateFormat martusDateFormat = new SimpleDateFormat(MARTUS_DATE_FORMAT);
			return martusDateFormat.format(realDate);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			return "ERROR:Unknown Date:" + rawDate;
		}
	}

	Object dateFormat;
	final String MARTUS_DATE_FORMAT = "yyyy-MM-dd";
}
