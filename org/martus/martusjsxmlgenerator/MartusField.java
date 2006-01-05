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
	
	String tag;
	String label;
	Object value;
	
}
