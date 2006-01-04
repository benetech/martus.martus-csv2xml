/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.martusjsxmlgenerator;

import java.io.File;

import org.martus.util.TestCaseEnhanced;

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
			assertContains("Row in error = 1", expected.getMessage());
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
		importer.doImport();
		testCSVFile.delete();
		testJSFile.delete();
	}

	public final String CSV_VERTICAL_BAR_REGEX_DELIMITER = "\\|";
}
