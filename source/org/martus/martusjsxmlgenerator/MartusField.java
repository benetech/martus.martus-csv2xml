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
	
	public String getMartusValue( Scriptable scriptable ) throws Exception
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
	
	public String getFieldSpec(Scriptable scriptable) throws Exception
	{
		StringBuffer xmlFieldSpec = new StringBuffer();
		xmlFieldSpec.append(getFieldTypeStartTag(getType()));
		xmlFieldSpec.append(getXMLData(TAG, getTag()));
		xmlFieldSpec.append(getXMLData(LABEL, getLabel()));
		xmlFieldSpec.append(getFieldSpecSpecificXmlData(scriptable));
		xmlFieldSpec.append(getEndTag(FIELD));
		return xmlFieldSpec.toString();
	}
	
	public String getFieldData(Scriptable scriptable) throws Exception
	{
		StringBuffer xmlFieldData = new StringBuffer();
		xmlFieldData.append(getFieldTagStartTag(getTag()));
		xmlFieldData.append(getXmlFieldValue(scriptable));
		xmlFieldData.append(getEndTagWithExtraNewLine(FIELD));
		return xmlFieldData.toString();
	}

	public String getXmlFieldValue(Scriptable scriptable) throws Exception
	{
		return getXMLData(VALUE, getMartusValue( scriptable ));
	}
	
	public String getFieldSpecSpecificXmlData(Scriptable scriptable)  throws Exception
	{
		return "";
	}
	
	static public String getFieldTagStartTag(String tag)
	{
		return getStartTagNewLine(FIELD +" tag='"+tag+"'");
	}

	static public String getXMLData(String xmlTag, String data)
	{
		StringBuffer xmlData = new StringBuffer(getStartTag(xmlTag));
		xmlData.append(data);
		xmlData.append(getEndTag(xmlTag));
		return xmlData.toString();
	}
	
	static public String getStartTag(String text)
	{
		return ("<" + text + ">");
	}

	static public String getStartTagNewLine(String text)
	{
		return getStartTag(text) + NEW_LINE;
	}

	static public String getEndTag(String text)
	{
		return getStartTagNewLine("/" + text);
	}
	
	static public String getEndTagWithExtraNewLine(String text)
	{
		return getEndTag(text) + NEW_LINE;
	}

	static public String getFieldTypeStartTag(String type)
	{
		return getStartTagNewLine(FIELD +" type='"+type+"'");
	}
	
	static public String getPrivateFieldSpec()
	{
		StringBuffer privateSpec = new StringBuffer(getStartTagNewLine(PRIVATE_FIELD_SPEC));
		privateSpec.append(getFieldTypeStartTag("MULTILINE"));
		privateSpec.append(getXMLData(TAG,"privateinfo"));
		privateSpec.append(getXMLData(LABEL,""));
		privateSpec.append(getEndTag(FIELD));
		privateSpec.append(getEndTagWithExtraNewLine(PRIVATE_FIELD_SPEC));
		return privateSpec.toString();
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
	
	static final String TAG = "Tag";
	static final String LABEL = "Label";
	static final String VALUE = "Value";
	static public final String FIELD = "Field";
	static final String PRIVATE_FIELD_SPEC = "PrivateFieldSpecs";
	static final String NEW_LINE = "\n";

	static public final String LANGUAGE_TYPE = "LANGUAGE";
	static public final String STRING_TYPE = "STRING";
	static public final String MULTILINE_TYPE = "MULTILINE";
	static public final String DATE_TYPE = "DATE";
	static public final String DATERANGE_TYPE = "DATERANGE";
	static public final String DROPDOWN_TYPE = "DROPDOWN";
	static public final String BOOLEAN_TYPE = "BOOLEAN";
	static public final String MESSAGE_TYPE = "MESSAGE";
	static public final String GRID_TYPE = "GRID";
}
