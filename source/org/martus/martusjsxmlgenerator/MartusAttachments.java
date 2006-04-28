/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2006, Beneficent
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
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

abstract public class MartusAttachments extends MartusField
{
	public MartusAttachments()
	{
	}
	
	public MartusAttachments(Object value, Object attachmentDelimeterToUse, Object attachmentDirectoryToUse, String attachmentSectionToUse)
	{
		super("","",value);
		attachmentSection = attachmentSectionToUse;
		if(Undefined.instance == attachmentDelimeterToUse)
			attachmentDelimeter = DEFAULT_FILE_SEPARATOR;
		else
			attachmentDelimeter = attachmentDelimeterToUse.toString();

		attachmentDirectoryName = null;
		if(Undefined.instance != attachmentDirectoryToUse)
			attachmentDirectoryName = attachmentDirectoryToUse.toString();
	}
	
	public String getType()
	{
		return ATTACHMENT_TYPE;
	}
	
	public boolean isTopSection()
	{
		return attachmentSection.equals(ATTACHMENT_SECTION_TOP);
	}
	
	public String getFieldData(Scriptable scriptable) throws Exception
	{
		String[] attachments = getAttachments(scriptable);
		if(attachments == null)
			return "";
		
		StringBuffer xmlFieldData = new StringBuffer();
		xmlFieldData.append(getStartTagNewLine(getAttachmentListTag()));
		for(int i = 0; i < attachments.length; ++i)
		{
			xmlFieldData.append(getStartTagNewLine(ATTACHMENT_TAG));
			xmlFieldData.append(getStartTag(FILENAME_TAG));
			xmlFieldData.append(attachments[i]);
			xmlFieldData.append(getEndTag(FILENAME_TAG));
			xmlFieldData.append(getEndTag(ATTACHMENT_TAG));
		}
		xmlFieldData.append(getEndTagWithExtraNewLine(getAttachmentListTag()));
		return xmlFieldData.toString();
	}
	
	public String[] getAttachments(Scriptable scriptable) throws Exception
	{
		String listOfAttachmentFileNames = getMartusValue(scriptable);
		if(listOfAttachmentFileNames.length() == 0)
			return null;
		String[] filenames = listOfAttachmentFileNames.split(attachmentDelimeter);
		String[] attachments = new String[filenames.length];
		for(int i = 0; i < filenames.length; ++i)
		{
			if(attachmentDirectoryName != null)
				attachments[i] = new File(attachmentDirectoryName, filenames[i]).getAbsolutePath();
			else
				attachments[i] = filenames[i];
		}
		return attachments;
	}
	
	abstract String getAttachmentListTag();

	private static final String ATTACHMENT_TAG = "Attachment";
	private static final String FILENAME_TAG = "Filename";
	private static final String DEFAULT_FILE_SEPARATOR = ";";
	String attachmentDelimeter;
	String attachmentDirectoryName;
	String attachmentSection;
}