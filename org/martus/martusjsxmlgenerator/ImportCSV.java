package org.martus.martusjsxmlgenerator;
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

import java.io.File;
import java.io.IOException;

import org.martus.util.UnicodeReader;
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
			System.out.println("	 Egg. if the values are separated by a tab you will need to use \t" );
			System.out.println("	 Egg. if the values are separated by a | you will need to use \\|" );
			System.exit(1);
		}

		ImportCSV importer = new ImportCSV(new File(args[0]), new File(args[1]), args[2]);
		importer.doImport();
	}
	
	public ImportCSV(File javaScriptFile, File csvFile, String csvDelimiterToUse) throws IOException
	{
		bulletinCsvFile = csvFile;
		configurationFile = javaScriptFile;
		csvDelimeter = csvDelimiterToUse;

		initalizeHeaderValues();
		String csvFileName = csvFile.getPath();
		String xmlFileName = csvFileName.substring(0, csvFileName.length()-4);
		martusXmlFile = new File(xmlFileName);
	}

	private void initalizeHeaderValues() throws IOException 
	{
		UnicodeReader csvHeaderReader = new UnicodeReader(bulletinCsvFile);
		String headerInfo = csvHeaderReader.readLine();
		csvHeaderReader.close();
		headerLabels = headerInfo.split(csvDelimeter);
	}
	
	public void doImport() throws Exception
	{
		Context cs = Context.enter();
		UnicodeReader readerJSConfigurationFile = null;
		try
		{
			readerJSConfigurationFile = new UnicodeReader(configurationFile);
			Script script = cs.compileReader(readerJSConfigurationFile, configurationFile.getName(), 1, null);
			ScriptableObject scope = cs.initStandardObjects();
			
			
			scope.put("firstName", scope, "chuck");
			scope.put("lastName", scope, "lap");
			scope.put("ccc", scope, "ch");
			
			ScriptableObject.defineClass(scope, StringField.class);
			script.exec(cs, scope);

			Scriptable fieldSpecs = (Scriptable)scope.get("MartusFieldSpecs", scope);
			for(int i = 0; i < fieldSpecs.getIds().length; i++)
			{
				MartusField fieldSpec = (MartusField)fieldSpecs.get(i, scope);
				System.out.println(fieldSpec.getTag());
				System.out.println(fieldSpec.getLabel());
				System.out.println(fieldSpec.getMartusValue( scope ));
			}
		}
		finally
		{
			Context.exit();
			if(readerJSConfigurationFile != null)
				readerJSConfigurationFile.close();
		}
	}
	
	File configurationFile;
	File martusXmlFile;
	File bulletinCsvFile;
	private String csvDelimeter;
	String[] headerLabels;
}
