package com.capitalbiotech.drugdeafness

import org.codehaus.groovy.runtime.StackTraceUtils

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.GZIPInputStream

import org.apache.commons.io.input.ReversedLinesFileReader

class Utils {
	static SimpleDateFormat dateParser1 = new SimpleDateFormat("yyyy-MM-dd")
	static SimpleDateFormat dateParser2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	
	public static Date parseSimpleDate(String simpleDate) throws ParseException {
		return dateParser1.parse(simpleDate)
	}

	public static Date parseSimpleDateTime(String simpleDateTime) throws ParseException {
		return dateParser2.parse(simpleDateTime)
	}
	
	public static String getCurrentTimeString() throws ParseException {
		def timestamp = System.currentTimeMillis()
		return dateParser2.format(new Date(timestamp))
	}

	public static String getCurrentDateString() throws ParseException {
		def timestamp = System.currentTimeMillis()
		return dateParser1.format(new Date(timestamp))
	}
	
	public static boolean isEmpty(String value) {
		if (value == null || value.equals("")) {
			return true
		}
		return false
	}

	public static boolean hasEmpty(String... values) {
		def result = false
		values?.each { value ->
			if (value == null || value.equals("")) {
				result = true
			}
		}
		return result
	}
	
    public static String getStackTraceString(Throwable throwable, boolean sanitized) {
        StringWriter stringWriter = new StringWriter()
        PrintWriter printWriter = new PrintWriter(stringWriter)
        
        if (sanitized) {
            StackTraceUtils.printSanitizedStackTrace(throwable, printWriter)
        }
        else {
            throwable.printStackTrace(printWriter)
        }
        
        StringBuffer error = stringWriter.getBuffer()
        return error.toString()
    }
    
    public static readLines(file, lineNumber) {
        def fileReader = new FileReader(file)
        def line = null
        def lineIndex = 0
        def stringBuffer = new StringBuffer()
        while ((line = fileReader.readLine()) != null && lineIndex < lineNumber) {
            stringBuffer.append(line + "\n")
            lineIndex ++
        }
        if (fileReader) {
            fileReader.close()
        }
        return stringBuffer.toString()
    }
    
    public static readGZIPLines(file, lineNumber) {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))))
        def line = null
        def lineIndex = 0
        def stringBuffer = new StringBuffer()
        while ((line = fileReader.readLine()) != null && lineIndex < lineNumber) {
            stringBuffer.append(line + "\n")
            lineIndex ++
        }
        if (fileReader) {
            fileReader.close()
        }
        return stringBuffer.toString()
    }
    
    public static readReversedLines(file, lineNumber) {
        def reversedLinesFileReader = new ReversedLinesFileReader(file)
        def line = null
        def lineIndex = 0
        def stringBuffer = new StringBuffer()
        while ((line = reversedLinesFileReader.readLine()) != null && lineIndex < lineNumber) {
            stringBuffer.insert(0, line + "\n")
            lineIndex ++
        }
        if (reversedLinesFileReader) {
            reversedLinesFileReader.close()
        }
        return stringBuffer.toString()
    }
	
	/**
	 * 通过输入的filepath判断是文件还是文件夹，是文件夹则递归读取文件夹下的所有文件,并以map（絶對路徑，截取的文件名）返回
	 */
	public static HashMap readfile(String filepath) throws FileNotFoundException, IOException {
		Map fileMap = null;
		try {
			fileMap = new HashMap<String,String>();
			File file = new File(filepath);
			if (!file.isDirectory()) {
				String fileName = file.getName();
				if(fileName.contains(".") && fileName.contains("_")){
					fileName = fileName.substring(0, fileName.indexOf("."));
					fileName = fileName.substring(0, fileName.lastIndexOf("_"));
				}else if(fileName.contains(".txt")){
					fileName = fileName.substring(0, fileName.indexOf(".txt"));
				}
				fileMap.put(file.getAbsolutePath(),fileName);
			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						String fileName = readfile.getName();
						if(fileName.contains(".") && fileName.contains("_")){
							fileName = fileName.substring(0, fileName.indexOf("."));
							fileName = fileName.substring(0, fileName.lastIndexOf("_"));
						}else if(fileName.contains(".txt")){
							fileName = fileName.substring(0, fileName.indexOf(".txt"));
						}
						fileMap.put(readfile.getAbsolutePath(),fileName);
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return fileMap;
	}
	/**
	 * 生成pdf时使用
	 * @param str
	 * @param i
	 * @return
	 */
	public static String leftPad(String str, int i) {
		int addSpaceNo = i-str.length();
		String space = "";
		for (int k=0; k<addSpaceNo; k++){
			space= " "+space;
		};
		String result =space + str ;
		return result;
	}
	/**
	 * 生成pdf时使用
	 * @param tmp
	 * @return
	 */
	public static String printBlank(int tmp){
		String space="";
		for(int m=0;m<tmp;m++){
			space=space+" ";
		}
		return space;
	}
}