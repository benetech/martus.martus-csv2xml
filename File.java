/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * The contents of this file are subject to the Netscape Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/NPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * The Original Code is Rhino code, released
 * May 6, 1998.
 *
 * The Initial Developer of the Original Code is Netscape
 * Communications Corporation.  Portions created by Netscape are
 * Copyright (C) 1997-2000 Netscape Communications Corporation. All
 * Rights Reserved.
 *
 * Contributor(s):
 * Norris Boyd
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the GNU Public License (the "GPL"), in which case the
 * provisions of the GPL are applicable instead of those above.
 * If you wish to allow use of your version of this file only
 * under the terms of the GPL and not to allow others to use your
 * version of this file under the NPL, indicate your decision by
 * deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL.  If you do not delete
 * the provisions above, a recipient may use your version of this
 * file under either the NPL or the GPL.
 */

/*
 * File.java 
 * 
 * 
 */

import java.io.IOException;
import java.util.Vector;

import org.martus.util.UnicodeReader;
import org.martus.util.UnicodeWriter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class File extends ScriptableObject {

    public File() 
    {
    }

    public static Scriptable jsConstructor(Context cx, Object[] args, Function ctorObj, boolean inNewExpr)
    {
        File result = new File();
        result.name = Context.toString(args[0]);
        result.file = new java.io.File(result.name);        
        return result;
    }

    public String getClassName() 
    {
        return "File";
    }

    public String jsGet_name() 
    {
        return name;
    }

    public Object jsFunction_parseLine(String line, String delimeter)
	{
	    String[] elements = line.split(delimeter);
	    Scriptable scope = ScriptableObject.getTopLevelScope(this);
	    Context cx = Context.getCurrentContext();
	    return cx.newObject(scope, "Array", elements);
	}
    
    public String jsFunction_getFileNameWithoutExtension()
    {
    	String inputFile = file.getAbsolutePath();
    	int length = inputFile.length();
    	String fileWithoutExtension = inputFile.substring(0, length -4);
	    return fileWithoutExtension;
    }

   public Object jsFunction_readLines()
        throws IOException
    {
        Vector v = new Vector();
        String s;
        while ((s = jsFunction_readLine()) != null) {
            v.addElement(s);
        }
        Object[] lines = new Object[v.size()];
        v.copyInto(lines);

        Scriptable scope = ScriptableObject.getTopLevelScope(this);
        Context cx = Context.getCurrentContext();
        return cx.newObject(scope, "Array", lines);
    }
  
    public String jsFunction_readLine() throws IOException 
    {
        return getReader().readLine();
    }

    public String jsFunction_readChar() throws IOException 
    {
        int i = getReader().read();
        if (i == -1)
            return null;
        char[] charArray = { (char) i };
        return new String(charArray);
    }

    public static void jsFunction_write(Context cx, Scriptable thisObj,
                                        Object[] args, Function funObj)
        throws IOException
    {
        write0(thisObj, args, false);
    }

    public static void jsFunction_writeLine(Context cx, Scriptable thisObj,
                                            Object[] args, Function funObj)
        throws IOException
    {
        write0(thisObj, args, true);
    }
    public void jsFunction_close() throws IOException 
    {
        if (reader != null) {
            reader.close();
            reader = null;
        } else if (writer != null) {
            writer.close();
            writer = null;
        }
    }

    public void finalize() 
    {
        try 
        {
            jsFunction_close();
        }
        catch (IOException e) 
        {
        }
    }

    public Object jsFunction_getReader() 
    {
        if (reader == null)
            return null;
        Scriptable parent = ScriptableObject.getTopLevelScope(this);
        return Context.javaToJS(reader, parent);
    }

    public Object jsFunction_getWriter() 
    {
        if (writer == null)
            return null;
        Scriptable parent = ScriptableObject.getTopLevelScope(this);
        return Context.javaToJS(writer, parent);
    }

    private UnicodeReader getReader() throws IOException 
    {
        if (writer != null) 
            throw Context.reportRuntimeError("already writing file \"" + name + "\"");
        if (reader == null)
       		reader = new UnicodeReader(file);
        return reader;
    }

    private static void write0(Scriptable thisObj, Object[] args, boolean eol)
        throws IOException
    {
        File thisFile = checkInstance(thisObj);
        if (thisFile.reader != null)
            throw Context.reportRuntimeError("already writing file \"" + thisFile.name + "\"");
        if (thisFile.writer == null)
       		thisFile.writer = new UnicodeWriter(thisFile.file);

        for (int i=0; i < args.length; i++) 
        {
            String s = Context.toString(args[i]);
            thisFile.writer.write(s, 0, s.length());
        }
        if(eol)
        	thisFile.writer.write('\n');
    }

    private static File checkInstance(Scriptable obj) 
    {
        if (obj == null || !(obj instanceof File)) {
            throw Context.reportRuntimeError("called on incompatible object");
        }
        return (File) obj;
    }
    
    private String name;
    private java.io.File file;
    private UnicodeReader reader;
    private UnicodeWriter writer;
}

