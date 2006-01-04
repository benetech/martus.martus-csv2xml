/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import java.io.File;

import org.martus.util.TestCaseEnhanced;
import org.martus.util.UnicodeReader;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class TestImportCSV extends TestCaseEnhanced 
{
	public TestImportCSV(String name) 
	{
		super(name);
	}

	protected void setUp() throws Exception 
	{
		super.setUp();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}
	
	public void testGetTabbedHeaders() throws Exception
	{
		File testJSFile = createTempFileFromName("$$$MARTUS_JS_TestFile_TabbedHeader");
		copyResourceFileToLocalFile(testJSFile, "test.js");
		File testCSVFile = createTempFileFromName("$$$MARTUS_CSV_TestFile_TabbedHeader");
		copyResourceFileToLocalFile(testCSVFile, "testTabHeaders.csv");
		ImportCSV importer = new ImportCSV(testJSFile, testCSVFile, "\t");
		assertEquals(5, importer.headerLabels.length);
		testCSVFile.delete();
		testJSFile.delete();
	}

	public void testGetHeaders() throws Exception
	{
		File testJSFile = createTempFileFromName("$$$MARTUS_JS_TestFile_GetHeader");
		copyResourceFileToLocalFile(testJSFile, "test.js");
		File testCSVFile = createTempFileFromName("$$$MARTUS_CSV_TestFile_GetHeader");
		copyResourceFileToLocalFile(testCSVFile, "test.csv");
		ImportCSV importer = new ImportCSV(testJSFile, testCSVFile, CSV_VERTICAL_BAR_REGEX_DELIMITER);
		String[] headerLabels = importer.headerLabels;
		assertEquals(9, headerLabels.length);
		assertEquals("language", headerLabels[0]);
		assertEquals("firstname", headerLabels[1]);
		assertEquals("lastname", headerLabels[2]);
		assertEquals("guns", headerLabels[8]);
		testCSVFile.delete();
		testJSFile.delete();
	}
	
	public void testHeaderCountDoesntMatchData() throws Exception
	{
		File testJSFile = createTempFileFromName("$$$MARTUS_JS_TestFile_HeaderCountDoesntMatchData");
		copyResourceFileToLocalFile(testJSFile, "test.js");
		File testInvalidCSVFile = createTempFileFromName("$$$MARTUS_CSV_TestFile_HeaderCountDoesntMatchData");
		copyResourceFileToLocalFile(testInvalidCSVFile, "testInvalidcolumncount.csv");
		ImportCSV importer = new ImportCSV(testJSFile, testInvalidCSVFile, CSV_VERTICAL_BAR_REGEX_DELIMITER);
		try 
		{
			importer.doImport();
			fail("Should have thrown an exception");
		} 
		catch (Exception expected) 
		{
			assertContains("Row Data = en|John| Doe|Bulletin #1|Message 1|212|C.C.|no", expected.getMessage());
		}
		finally
		{
			testInvalidCSVFile.delete();
			testJSFile.delete();
		}
	}

	public void testStringFields() throws Exception
	{
		File testJSFile = createTempFileFromName("$$$MARTUS_JS_TestFile_StringFields");
		copyResourceFileToLocalFile(testJSFile, "test.js");
		File testCSVFile = createTempFileFromName("$$$MARTUS_CSV_TestFile_StringFields");
		copyResourceFileToLocalFile(testCSVFile, "test.csv");
		ImportCSV importer = new ImportCSV(testJSFile, testCSVFile, CSV_VERTICAL_BAR_REGEX_DELIMITER);
		Context cs = Context.enter();
		UnicodeReader readerJSConfigurationFile = new UnicodeReader(testJSFile);
		Script script = cs.compileReader(readerJSConfigurationFile, testCSVFile.getName(), 1, null);
		ScriptableObject scope = cs.initStandardObjects();
		String dataRow = "fr|Jane|Doe|16042001|Bulletin #2|Message 2|234|T.I..|yes";
		Scriptable fieldSpecs = importer.getFieldScriptableSpecs(cs, script, scope, dataRow);
		MartusField field1 = (MartusField)fieldSpecs.get(0, scope);
		assertEquals("Author", field1.getTag());
		assertEquals("", field1.getLabel());
		assertEquals("Jane Doe", field1.getMartusValue(scope));
		MartusField field2 = (MartusField)fieldSpecs.get(1, scope);
		assertEquals("MyTitle", field2.getTag());
		assertEquals("My Title", field2.getLabel());
		assertEquals("Bulletin #2", field2.getMartusValue(scope));
		Context.exit();
		readerJSConfigurationFile.close();
		
		testCSVFile.delete();
		testJSFile.delete();
	}

	public final String CSV_VERTICAL_BAR_REGEX_DELIMITER = "\\|";
}
