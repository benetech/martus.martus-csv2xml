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
import org.martus.util.UnicodeReader;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
public class ImportCSV
{
	public static void main(String[] args) throws Exception
	{
		if(args.length != 2)
		{
			System.out.println("Usage : java ImportCSV configurationFile.js fileToConvert.csv");
			System.exit(1);
		}

		ImportCSV importer = new ImportCSV(args[0], args[1]);
		importer.doImport();
	}
	
	public ImportCSV(String javaScriptFileName, String csvFileName)
	{
		bulletinCsvFile = new File(csvFileName);
		configurationFile = new File(javaScriptFileName);
	//	String xmlFileName = csvFileName.substring(0, csvFileName.length()-4);
		//martusXmlFile = new File(xmlFileName);
	}
	
	
	
	
	public void doImport() throws Exception
	{
		Context cs = Context.enter();

		try
		{
			UnicodeReader reader = new UnicodeReader(configurationFile);
			
			Script script = cs.compileReader(reader, configurationFile.getName(), 1, null);
			ScriptableObject scope = cs.initStandardObjects();
			ScriptableObject.defineClass(scope, StringField.class);
			

			script.exec(cs, scope);
			Scriptable array = (Scriptable)scope.get("MartusFieldSpecs", scope);
			for(int i = 0; i < array.getIds().length; i++)
			{
				MartusField o = (MartusField)array.get(i, scope);
				System.out.println(o.getTag());
				System.out.println(o.getLabel());
				scope.put("firstName", scope, "chuck");
				scope.put("lastName", scope, "lap");
				scope.put("ccc", scope, "ch");
				System.out.println(o.getMartusValue( scope ));
				
			}
			
		}
		finally
		{
			Context.exit();
		}
	}
	
	
	
	File configurationFile;
	File martusXmlFile;
	File bulletinCsvFile;
}
