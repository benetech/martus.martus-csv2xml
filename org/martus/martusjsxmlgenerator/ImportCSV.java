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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.martus.util.UnicodeReader;
import org.martus.util.UnicodeWriter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
public class ImportCSV
{
	public static void main(String[] args) throws Exception
	{
		if(args.length != 3)
		{
			System.out.println("Usage : java ImportCSV configurationFile.js fileToConvert.csv csvDelimiterRegEx" );
			System.out.println("	 csvDelimiterRegEx = the regular expression java will use to split the csv file into its columns" );
			System.out.println("	 Egg. if the values are separated by a comma you may just use ," );
			System.out.println("	 Egg. if the values are separated by a tab you will need to use \\t" );
			System.out.println("	 Egg. if the values are separated by a | you will need to use \\|" );
			System.exit(1);
		}

		ImportCSV importer = new ImportCSV(new File(args[0]), new File(args[1]), args[2]);
		importer.doImport();
	}
	
	public ImportCSV(File javaScriptFile, File csvFile, String csvDelimiterToUse) throws Exception
	{
		bulletinCsvFile = csvFile;
		configurationFile = javaScriptFile;
		csvDelimeter = csvDelimiterToUse;
		MartusField.clearRequiredFields();

		initalizeHeaderValues();
		String csvFileName = csvFile.getPath();
		String xmlFileName = csvFileName.substring(0, csvFileName.length()-4);
		martusXmlFile = new File(xmlFileName);
	}

	private void initalizeHeaderValues() throws Exception 
	{
		UnicodeReader csvHeaderReader = new UnicodeReader(bulletinCsvFile);
		String headerInfo = csvHeaderReader.readLine();
		csvHeaderReader.close();
		headerLabels = headerInfo.split(csvDelimeter);
		if(headerLabels.length == 1)
		{
			String errorMessage ="Only Found one column, please check your delimeter";
			throw new Exception(errorMessage);
		}
	}
	
	public File getXmlFile()
	{
		return martusXmlFile;
	}
	
	public void doImport() throws Exception
	{
		Context cs = Context.enter();
		UnicodeReader readerJSConfigurationFile = null;
		UnicodeWriter writer = null;
		UnicodeReader csvReader = null;
		try
		{
			readerJSConfigurationFile = new UnicodeReader(configurationFile);
			Script script = cs.compileReader(readerJSConfigurationFile, configurationFile.getName(), 1, null);
			ScriptableObject scope = cs.initStandardObjects();
			
			writer = openMartusXML();
			
			String dataRow = null;
			csvReader = new UnicodeReader(bulletinCsvFile);
			csvReader.readLine(); //skip past header;
			while((dataRow = csvReader.readLine()) != null)
			{
				Scriptable bulletinData = getFieldScriptableSpecsAndBulletinData(cs, script, scope, dataRow);
				writeBulletinFieldSpecs(writer, scope, bulletinData);
				writeBulletinFieldData(writer, scope, bulletinData);
			}
			
			closeMartusXML(writer);
		}
		finally
		{
			Context.exit();
			if(readerJSConfigurationFile != null)
				readerJSConfigurationFile.close();
			if(writer != null)
				writer.close();
			if(csvReader != null)
				csvReader.close();
		}
	}

	private UnicodeWriter openMartusXML() throws IOException
	{
		UnicodeWriter writerMartusXMLBulletinFile = new UnicodeWriter(martusXmlFile);
		writerMartusXMLBulletinFile.write(getStartTagNewLine(MARTUS_BULLETINS));
		return writerMartusXMLBulletinFile;
	}

	private void closeMartusXML(UnicodeWriter writerMartusXMLBulletinFile) throws IOException
	{
		writerMartusXMLBulletinFile.write(getEndTag(MARTUS_BULLETINS));
	}

	public void writeBulletinFieldSpecs(UnicodeWriter writer, ScriptableObject scope, Scriptable fieldSpecs) throws IOException
	{
		writer.write(getStartTagNewLine(MARTUS_BULLETIN));
		writer.write(getStartTagNewLine(PUBLIC_FIELD_SPEC));

		for(int i = 0; i < fieldSpecs.getIds().length; i++)
		{
			MartusField fieldSpec = (MartusField)fieldSpecs.get(i, scope);
			if(fieldSpec.getTag() == "privateinfo")
				continue;//Writen after the Public Field Spec
			writer.write(getFieldTypeStartTag(fieldSpec.getType()));
			writer.write(getXMLData(TAG, fieldSpec.getTag()));
			writer.write(getXMLData(LABEL, fieldSpec.getLabel()));
			writer.write(getEndTag(FIELD));
		}
		writer.write(getEndTagWithExtraNewLine(PUBLIC_FIELD_SPEC));
		writer.write(getPrivateFieldSpec());
	}

	public void writeBulletinFieldData(UnicodeWriter writer, ScriptableObject scope, Scriptable fieldSpecs) throws IOException
	{
		writer.write(getStartTagNewLine(FIELD_VALUES));
		
		for(int i = 0; i < fieldSpecs.getIds().length; i++)
		{
			MartusField fieldSpec = (MartusField)fieldSpecs.get(i, scope);
			writer.write(getFieldTagStartTag(fieldSpec.getTag()));
			writer.write(getXMLData(VALUE, fieldSpec.getMartusValue( scope )));
			writer.write(getEndTagWithExtraNewLine(FIELD));
		}
		writer.write(getEndTag(FIELD_VALUES));
		writer.write(getEndTagWithExtraNewLine(MARTUS_BULLETIN));
	}

	public Scriptable getFieldScriptableSpecsAndBulletinData(Context cs, Script script, ScriptableObject scope, String dataRow) throws Exception, IllegalAccessException, InstantiationException, InvocationTargetException 
	{
		String[] rowContents = dataRow.split(csvDelimeter);
		if(rowContents.length != headerLabels.length)
		{
			String errorMessage ="Number of Data Fields did not match Header Fields\n" +
					"Expected column count =" + headerLabels.length + " but was :" + rowContents.length + NEW_LINE +
					"Row Data = " + dataRow;
			throw new Exception(errorMessage);
		}
		
		for(int i = 0; i < rowContents.length; ++i)
		{
			scope.put(headerLabels[i], scope,rowContents[i]);
		}

		ScriptableObject.defineClass(scope, StringField.class);
		ScriptableObject.defineClass(scope, MultilineField.class);
		ScriptableObject.defineClass(scope, SingleDateField.class);
		ScriptableObject.defineClass(scope, DateRangeField.class);
		ScriptableObject.defineClass(scope, MartusDetailsField.class);
		ScriptableObject.defineClass(scope, MartusSummaryField.class);
		ScriptableObject.defineClass(scope, MartusOrganizationField.class);
		ScriptableObject.defineClass(scope, MartusLocationField.class);
		ScriptableObject.defineClass(scope, MartusKeywordsField.class);
		ScriptableObject.defineClass(scope, MartusDateOfEventField.class);
		ScriptableObject.defineClass(scope, MartusRequiredLanguageField.class);
		ScriptableObject.defineClass(scope, MartusRequiredAuthorField.class);
		ScriptableObject.defineClass(scope, MartusRequiredTitleField.class);
		ScriptableObject.defineClass(scope, MartusRequiredDateCreatedField.class);
		ScriptableObject.defineClass(scope, MartusRequiredPrivateField.class);
		script.exec(cs, scope);
		
		MartusField.verifyRequiredFields();
		Scriptable fieldSpecs = (Scriptable)scope.get("MartusFieldSpecs", scope);
		
		return fieldSpecs;
	}
	
	public String getFieldTypeStartTag(String type)
	{
		return getStartTagNewLine(FIELD +" type='"+type+"'");
	}
	
	public String getFieldTagStartTag(String tag)
	{
		return getStartTagNewLine(FIELD +" tag='"+tag+"'");
	}

	public String getXMLData(String xmlTag, String data)
	{
		StringBuffer xmlData = new StringBuffer(getStartTag(xmlTag));
		xmlData.append(data);
		xmlData.append(getEndTag(xmlTag));
		return xmlData.toString();
	}
	
	public String getStartTag(String text)
	{
		return ("<" + text + ">");
	}

	public String getStartTagNewLine(String text)
	{
		return getStartTag(text) + NEW_LINE;
	}

	public String getEndTag(String text)
	{
		return getStartTagNewLine("/" + text);
	}
	
	public String getEndTagWithExtraNewLine(String text)
	{
		return getEndTag(text) + NEW_LINE;
	}

	public String getPrivateFieldSpec()
	{
		StringBuffer privateSpec = new StringBuffer(getStartTagNewLine(PRIVATE_FIELD_SPEC));
		privateSpec.append(getFieldTypeStartTag("MULTILINE"));
		privateSpec.append(getXMLData(TAG,"privateinfo"));
		privateSpec.append(getXMLData(LABEL,""));
		privateSpec.append(getEndTag(FIELD));
		privateSpec.append(getEndTagWithExtraNewLine(PRIVATE_FIELD_SPEC));
		return privateSpec.toString();
	}
	
	private static final String MARTUS_BULLETINS = "MartusBulletins";
	private static final String MARTUS_BULLETIN = "MartusBulletin";
	private static final String PUBLIC_FIELD_SPEC = "MainFieldSpecs";
	private static final String PRIVATE_FIELD_SPEC = "PrivateFieldSpecs";
	private static final String TAG = "Tag";
	private static final String LABEL = "Label";
	private static final String FIELD = "Field";
	private static final String VALUE = "Value";
	private static final String FIELD_VALUES = "FieldValues";
	private static final String NEW_LINE = "\n";
	
	File configurationFile;
	File martusXmlFile;
	File bulletinCsvFile;
	private String csvDelimeter;
	String[] headerLabels;
}
