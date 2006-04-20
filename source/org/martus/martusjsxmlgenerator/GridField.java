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
		gridDataFileName = gridDataFileStringToUse;
		if(columnDelimeter.equals("|"))
			columnDelimeter = "\\|";
		gridColumns = (NativeArray)listOfColumnsToUse;
		localScope = Context.getCurrentContext().initStandardObjects();
		File gridDataFile = new File(gridDataFileStringToUse);
		reader = new UnicodeReader(gridDataFile);
		readHeader();
		fetchNextRow();
	}
	
	public void cleanup()
	{
		try
		{
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
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
		do
		{
			currentRowNotSplit = reader.readLine();
		}while (currentRowNotSplit != null && currentRowNotSplit.trim().length() == 0);

		if(currentRowNotSplit == null)
			return null;
		
		return currentRowNotSplit.split(columnDelimeter);
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
			verifyColumnTypeAllowedInsideGrid(field);
			gridSpecs.append(getColumnTypeStartTag(field.getType()));			
			gridSpecs.append(getXMLData(TAG, ""));
			gridSpecs.append(getXMLData(LABEL, field.getLabel()));
			gridSpecs.append(field.getFieldSpecSpecificXmlData(scriptable));
			gridSpecs.append(getEndTag(GRID_COLUMN));
		}
		gridSpecs.append(getEndTag(GRID_SPEC_DETAILS));
		return gridSpecs.toString();
	}
	
	private void verifyColumnTypeAllowedInsideGrid(MartusField field) throws Exception
	{
		String type = field.getType();
		if (type.equals(MULTILINE_TYPE))
			throw new Exception("Martus Grid Contains Multiline Field.");
		if (type.equals(MESSAGE_TYPE))
			throw new Exception("Martus Grid Contains Message Field.");
		if (type.equals(GRID_TYPE))
		{
			field.cleanup();
			throw new Exception("Martus Grid Contains Another Grid.");
		}
		if (field.isMartusDefaultField())
			throw new Exception("Martus Grid Contains a Martus Default Field.");
			
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
			String errorMessage ="Number of Grid Data Fields did not match Header Fields\n" +
					"Expected column count =" + header.length + " but was " + currentRow.length +"\n" +
					"Grid File name ="+ gridDataFileName +"\n" +
					"Row Data = "+ currentRowNotSplit;
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
	String currentRowNotSplit;
	String[] currentRow;
	String columnDelimeter;
	String gridDataFileName;
	String keyId;
	int keyIdIndex;
	String[] header;
	NativeArray gridColumns;
	UnicodeReader reader;
	Scriptable localScope;
}


