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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class GridField extends MartusField
{
	public GridField()
	{
		super();
	}
	
	public GridField(String tagToUse, String labelToUse, Object gridDataFileStringToUse, Object keyIdToUse, Object listOfColumns)
	{
		super(tagToUse, labelToUse, null);
		//TODO: we need to set the Value in the super class
		gridDataFileString = gridDataFileStringToUse;
		keyId = keyIdToUse;
	}

	public String getType() 
	{
		return GRID_TYPE;
	}
	
	

	public Object getValue()
	{
		// TODO FIX THIS
		return keyId;
	}

	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "GridField";
	}
	
	public String getFieldSpecSpecificXmlData(Scriptable scriptable) throws Exception
	{
		return "";
	}
	
	public String getMartusValue( Scriptable scriptable ) throws Exception 
	{
		return super.getMartusValue(scriptable);
	}
	

	class GridValueFunction implements Function
	{

		public Object call(Context arg0, Scriptable arg1, Scriptable arg2, Object[] arg3)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public Scriptable construct(Context arg0, Scriptable arg1, Object[] arg2)
		{
			throw new UnsupportedOperationException();
		}

		public String getClassName()
		{
			return "GridValueFunction";
		}

		public Object get(String arg0, Scriptable arg1)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public Object get(int arg0, Scriptable arg1)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public boolean has(String arg0, Scriptable arg1)
		{
			// TODO Auto-generated method stub
			return false;
		}

		public boolean has(int arg0, Scriptable arg1)
		{
			// TODO Auto-generated method stub
			return false;
		}

		public void put(String arg0, Scriptable arg1, Object arg2)
		{
			// TODO Auto-generated method stub
			
		}

		public void put(int arg0, Scriptable arg1, Object arg2)
		{
			// TODO Auto-generated method stub
			
		}

		public void delete(String arg0)
		{
			// TODO Auto-generated method stub
			
		}

		public void delete(int arg0)
		{
			// TODO Auto-generated method stub
			
		}

		public Scriptable getPrototype()
		{
			// TODO Auto-generated method stub
			return null;
		}

		public void setPrototype(Scriptable arg0)
		{
			// TODO Auto-generated method stub
			
		}

		public Scriptable getParentScope()
		{
			// TODO Auto-generated method stub
			return null;
		}

		public void setParentScope(Scriptable arg0)
		{
			// TODO Auto-generated method stub
			
		}

		public Object[] getIds()
		{
			// TODO Auto-generated method stub
			return null;
		}

		public Object getDefaultValue(Class arg0)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasInstance(Scriptable arg0)
		{
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	Object gridDataFileString;
	Object keyId; 
}


