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

import org.martus.common.fieldspec.DropDownFieldSpec;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

public class DropDownField extends MartusField
{
	public DropDownField()
	{
		super();
	}
	public DropDownField(String tagToUse, String labelToUse, Object valueToUse, Object dropdownListToUse)
	{
		super(tagToUse, labelToUse, valueToUse);
		dropdownList = (NativeArray)dropdownListToUse;
	}

	public String getType() 
	{
		return DROPDOWN_TYPE;
	}

	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "DropDownField";
	}
	
	public String getFieldSpecSpecificXmlData(Scriptable scriptable) throws Exception
	{
		return(getXMLData(DropDownFieldSpec.DROPDOWN_SPEC_CHOICES_TAG, getChoicesXml()));
	}
	
	private String getChoicesXml()
	{
		StringBuffer choices = new StringBuffer();
		for(int i = 0; i < dropdownList.getLength(); ++i)
		{
			choices.append(getXMLData(DropDownFieldSpec.DROPDOWN_SPEC_CHOICE_TAG, (String)dropdownList.get(i, dropdownList)));
		}
		return choices.toString();
	}
	
	public String getMartusValue( Scriptable scriptable ) throws Exception
	{
		String dropdownValue = super.getMartusValue(scriptable);
		for(int i = 0; i < dropdownList.getLength(); ++i)
		{
			if(dropdownValue.equals(dropdownList.get(i, dropdownList)))
				return dropdownValue;
		}
		throw new Exception("Dropdown value not in list :" + dropdownValue);
	}

	
	NativeArray dropdownList;
}
