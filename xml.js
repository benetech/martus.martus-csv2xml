defineClass("File")
if(arguments.length != 1)
{
	print("Usage: java org.mozilla.javascript.tools.shell.Main xml.js fileToConvert");
	quit();
}

var csvIn = new File(arguments[0]);
var lines = csvIn.readLines();
csvIn.close();

var fileName = csvIn.getFileNameWithoutExtension();
var xmlOut = new File(fileName+".xml");
for(j in lines)
{
	var elements = csvIn.parseLine(lines[j], ",");
	print("Processing bulletin #" +j);
	for(i in elements)
	{
		xmlOut.writeLine(elements[i]);
	}
}
xmlOut.close();

