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
		File testJSFile = createTempFileFromName("$$$JS_TestFile");
		copyResourceFileToLocalFile(testJSFile, "test.js");
		File testCSVFile = createTempFileFromName("$$$JS_TestFile");
		copyResourceFileToLocalFile(testCSVFile, "testTabHeaders.csv");
		ImportCSV importer = new ImportCSV(testJSFile, testCSVFile, "\t");
		assertEquals(5, importer.headerLabels.length);
		testCSVFile.delete();
		testJSFile.delete();
	}

	public void testGetHeaders() throws Exception
	{
		File testJSFile = createTempFileFromName("$$$JS_TestFile");
		copyResourceFileToLocalFile(testJSFile, "test.js");
		File testCSVFile = createTempFileFromName("$$$JS_TestFile");
		copyResourceFileToLocalFile(testCSVFile, "test.csv");
		ImportCSV importer = new ImportCSV(testJSFile, testCSVFile, CSV_VERTICAL_BAR_REGEX_DELIMITER);
		String[] headerLabels = importer.headerLabels;
		assertEquals(9, headerLabels.length);
		assertEquals("language", headerLabels[0]);
		assertEquals("first-name", headerLabels[1]);
		assertEquals("last-name", headerLabels[2]);
		assertEquals("guns", headerLabels[8]);
		testCSVFile.delete();
		testJSFile.delete();
	}
	
	public final String CSV_VERTICAL_BAR_REGEX_DELIMITER = "\\|";
}
