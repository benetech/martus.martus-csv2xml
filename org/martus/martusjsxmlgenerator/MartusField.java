/*

 The Martus(tm) free, social justice documentation and
 monitoring software. Copyright (C) 2005, Beneficent
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
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


abstract public class MartusField extends ScriptableObject
{
	
	abstract public String getType();
	
	public MartusField()
	{
	}

	public MartusField(String tagToUse, String labelToUse, Object valueToUse)
	{
		tag = tagToUse;
		label = labelToUse;
		value = valueToUse;
	}
	
	public String getLabel()
	{
		return label;
	}

	public String getTag()
	{
		return tag;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public String jsGet_getLabel()
	{
		return label;
	}
	
	public String jsGet_getTag()
	{
		return tag;
	}

	public Object jsGet_getValue()
	{
		return value;
	}
	
	public String getMartusValue( Scriptable scriptable ) 
	{
		if ( getValue() instanceof String ) 
		{
			return scriptable.get( (String)getValue(), scriptable ).toString();
		}
		
		if ( getValue() instanceof Function ) 
		{

			Function function = (Function)getValue();
			
			return function.call(
						Context.getCurrentContext(),
						scriptable, scriptable,
						null
						).toString();
		}
		throw new RuntimeException( "getMartusValue::Illegal value type" );
	}
	
	static public void clearRequiredFields()
	{
		requiredFieldLanguage = false;
		requiredFieldAuthor = false;
		requiredFieldTitle = false;
		requiredFieldEntryDate = false;
		requiredFieldPrivate = false;
	}
	
	static public void verifyRequiredFields() throws Exception
	{
		StringBuffer missingFieldsErrorMessage = new StringBuffer();
		if(!requiredFieldLanguage)
			missingFieldsErrorMessage.append("MartusRequiredLanguageField missing.  ");
		if(!requiredFieldAuthor)
			missingFieldsErrorMessage.append("MartusRequiredAuthorField missing.  ");
		if(!requiredFieldTitle)
			missingFieldsErrorMessage.append("MartusRequiredTitleField missing.  ");
		if(!requiredFieldEntryDate)
			missingFieldsErrorMessage.append("MartusRequiredDateEntryField missing.  ");
		if(!requiredFieldPrivate)
			missingFieldsErrorMessage.append("MartusRequiredPrivateField missing.  ");
		
		if (missingFieldsErrorMessage.toString().length() > 0)
			throw new Exception(missingFieldsErrorMessage.toString());
	}
	
	String tag;
	String label;
	Object value;
	static boolean requiredFieldLanguage;
	static boolean requiredFieldAuthor;
	static boolean requiredFieldTitle;
	static boolean requiredFieldEntryDate;
	static boolean requiredFieldPrivate;
	
	static public final String LANGUAGE_TYPE = "LANGUAGE";
	static public final String STRING_TYPE = "STRING";
	static public final String MULTILINE_TYPE = "MULTILINE";
	static public final String DATE_TYPE = "DATE";
	static public final String DATERANGE_TYPE = "DATERANGE";
	static public final String DROPDOWN_TYPE = "DROPDOWN";
	static public final String GRID_TYPE = "GRID";
}
