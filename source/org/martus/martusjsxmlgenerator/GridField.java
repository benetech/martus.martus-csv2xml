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

import java.io.File;
import java.io.IOException;
import org.martus.util.UnicodeReader;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

public class GridField extends MartusField
{
	public GridField()
	{
		super();
	}
	
	public GridField(String tagToUse, String labelToUse, String gridDataFileStringToUse, String columnDelimeterToUse, String keyIdToUse, Object listOfColumnsToUse) throws IOException
	{
		super(tagToUse, labelToUse, null);
		keyId = keyIdToUse;
		columnDelimeter = columnDelimeterToUse;
		gridDataFile = new File(gridDataFileStringToUse);
		if(columnDelimeter.equals("|"))
			columnDelimeter = "\\|";
		gridColumns = (NativeArray)listOfColumnsToUse;
	}

	private void readHeader() throws IOException
	{
		header = parseRow();
		keyIdIndex = -1;
		for(int i = 0; i < header.length; ++i)
		{
			if(header[i].equals(keyId))
			{
				keyIdIndex = i;
				break;
			}
		}
	}

	private void fetchNextRow() throws IOException
	{
		currentRow = parseRow();
		currentKeyId = currentRow[keyIdIndex];
	}
	
	private String[] parseRow() throws IOException
	{
		String row = reader.readLine();
		if(row == null)
		{
			reader.close();
			return null;
		}
		return row.split(columnDelimeter);
	}

	public String getType() 
	{
		return GRID_TYPE;
	}
	
	//Actual Name called by the JavaScript
	public String getClassName() 
	{
		return "GridField";
	}
	
	public String getFieldSpecSpecificXmlData(Scriptable scriptable) throws Exception
	{
		StringBuffer gridSpecs = new StringBuffer();
		for(int i = 0; i < gridColumns.getLength(); ++i)
		{
			MartusField field = (MartusField)gridColumns.get(i, gridColumns);
			gridSpecs.append(field.getFieldSpec(scriptable));
		}
		return gridSpecs.toString();
	}
	
	public String getMartusValue( Scriptable scriptable ) throws Exception 
	{
		String bulletinKey = (String)scriptable.get(keyId, scriptable);
		if(bulletinKey == null)
			return "";

		reader = new UnicodeReader(gridDataFile);
		readHeader();
		fetchNextRow();
		StringBuffer gridData = new StringBuffer();
//		while(currentRow != null && bulletinKey.equals(currentKeyId))
		{
			//may need to create new scriptable object
			//if obj / function checking....
		}
		reader.close();
		return "";
	}

	String currentKeyId;
	String[] currentRow;
	String columnDelimeter;
	String keyId;
	int keyIdIndex;
	String[] header;
	File gridDataFile;
	NativeArray gridColumns;
	UnicodeReader reader;
}


