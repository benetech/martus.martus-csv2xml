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
import java.lang.reflect.InvocationTargetException;
import org.martus.util.UnicodeReader;
import org.mozilla.javascript.Context;
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
		localScope = Context.getCurrentContext().initStandardObjects();
		reader = new UnicodeReader(gridDataFile);
		readHeader();
		fetchNextRow();
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
		if(currentRow == null)
			currentKeyId = null;
		else
			currentKeyId = currentRow[keyIdIndex];
	}
	
	private String[] parseRow() throws IOException
	{
		String row;
		do
		{
			row = reader.readLine();
		}while (row != null && row.trim().length() == 0);

		if(row == null)
			return null;
		
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
		gridSpecs.append(getStartTagNewLine(GRID_SPEC_DETAILS));
		for(int i = 0; i < gridColumns.getLength(); ++i)
		{
			MartusField field = (MartusField)gridColumns.get(i, gridColumns);
			String columnType = field.getType();
			verifyColumnTypeAllowedInsideGrid(columnType);
			gridSpecs.append(getColumnTypeStartTag(columnType));			
			gridSpecs.append(getXMLData(TAG, ""));
			gridSpecs.append(getXMLData(LABEL, field.getLabel()));
			gridSpecs.append(field.getFieldSpecSpecificXmlData(scriptable));
			gridSpecs.append(getEndTag(GRID_COLUMN));
		}
		gridSpecs.append(getEndTag(GRID_SPEC_DETAILS));
		return gridSpecs.toString();
	}
	
	private void verifyColumnTypeAllowedInsideGrid(String type) throws Exception
	{
		if (type.equals(MULTILINE_TYPE))
			throw new Exception("Martus Grid Contains Multiline Field.");
		if (type.equals(MESSAGE_TYPE))
			throw new Exception("Martus Grid Contains Message Field.");
		if (type.equals(GRID_TYPE))
			throw new Exception("Martus Grid Contains Another Grid.");
	}
	
	public String getMartusValue( Scriptable scriptable ) throws Exception 
	{
		String bulletinKey = (String)scriptable.get(keyId, scriptable);
		if(bulletinKey == null)
			return "";
		localScope.setParentScope(scriptable);
		StringBuffer gridData = new StringBuffer();
		gridData.append(getStartTagNewLine(GRID_DATA));
		while(currentRow != null && bulletinKey.equals(currentKeyId))
		{
			gridData.append(getStartTagNewLine(GRID_ROW));
			populateGridFields(scriptable);
			for(int i = 0; i < gridColumns.getLength(); ++i)
			{
				MartusField field = ((MartusField)gridColumns.get(i, gridColumns));
				gridData.append(getStartTag(GRID_COLUMN));
				gridData.append(field.getMartusValue(scriptable));
				gridData.append(getEndTag(GRID_COLUMN));
			}
			gridData.append(getEndTag(GRID_ROW));
			fetchNextRow();
		}
		gridData.append(getEndTag(GRID_DATA));
		return gridData.toString();
	}
	
	public void populateGridFields(Scriptable scope) throws Exception, IllegalAccessException, InstantiationException, InvocationTargetException 
	{
		if(currentRow.length != header.length)
		{
			String errorMessage ="Number of Data Fields did not match Header Fields\n" +
					"Expected column count =" + header.length + " but was :" + currentRow.length +"\n" +
					"Row Data = " + currentRow;
			throw new Exception(errorMessage);
		}
		
		for(int i = 0; i < currentRow.length; ++i)
		{
			scope.put(header[i], scope,currentRow[i]);
		}
	}
	
	public String getColumnTypeStartTag(String type)
	{
		return getStartTagNewLine(GRID_COLUMN +" type='"+type+"'");
	}	

	static public final String GRID_SPEC_DETAILS = "GridSpecDetails";
	static public final String GRID_DATA = "GridData";
	static public final String GRID_COLUMN = "Column";
	static public final String GRID_ROW = "Row";
	

	String currentKeyId;
	String[] currentRow;
	String columnDelimeter;
	String keyId;
	int keyIdIndex;
	String[] header;
	File gridDataFile;
	NativeArray gridColumns;
	UnicodeReader reader;
	Scriptable localScope;
}


