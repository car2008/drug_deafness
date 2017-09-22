package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured
import java.text.DecimalFormat

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@Secured(['ROLE_USER'])
class ResultController {
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def utilsService
	def springSecurityService
	def grailsApplication
	
    def index() {
		redirect(action: "listRecord",flash:flash)
	}
	
	def list(){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		if (!params.offset) {
			params.offset = 0
		}
		if (!params.order) {
			params.order = 'desc'
		}
		if (!params.sort) {
			params.sort = 'dateCreated'
		}

		def resultInstanceList = Result.list(params)
		
		def resultInstanceTotal = Result.count()
		def allResultInstanceTotal = resultInstanceTotal
		render view: 'list', model: [resultInstanceList: resultInstanceList,allResultInstanceTotal:allResultInstanceTotal,]
	}
	
	def listRecord(){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		if (!params.offset) {
			params.offset = 0
		}
		if (!params.order) {
			params.order = 'desc'
		}
		if (!params.sort) {
			params.sort = 'dateCreated'
		}

		def recordInstanceList = Record.executeQuery("SELECT record FROM Record record WHERE record.recordCatagrory='CATAGRORY_RESULT' ORDER BY record.${params.sort} ${params.order}",[offset: params.offset,max:params.max])
		
		def recordInstanceTotal = Record.countByRecordCatagrory("CATAGRORY_RESULT")
		def allRecordInstanceTotal = recordInstanceTotal
		render view: 'result', model: [recordInstanceList: recordInstanceList,allRecordInstanceTotal:allRecordInstanceTotal,]
	}
	
	def uploadOne() {
		def startTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		def currentUser = springSecurityService.currentUser
		def resultInstance = new Result(params)
		def informationInstance = Information.findBySampleNum(params.sampleNum)
		if(informationInstance && !Result.findBySampleNum(params.sampleNum)) {
			resultInstance.information = informationInstance
			informationInstance.results.add(resultInstance)
			informationInstance.save(flush: true)
		}else if(informationInstance && Result.findBySampleNum(params.sampleNum)){
			flash.message = "${message(code: 'result.information.found.message', args: [message(code: 'result.label', default: 'resultInstance'), params.sampleNum])}"
			//redirect(action: "index",flash:flash)
			render view:'result'
			return
		}else{
			flash.message = "${message(code: 'result.information.not.found.message', args: [message(code: 'information.label', default: 'informationInstance'), params.sampleNum])}"
			//redirect(action: "index",flash:flash)
			render view:'result'
			return
		}
		if (!resultInstance.hasErrors() && resultInstance.save(flush: true)) {
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: resultInstance.sampleNum+"(单个录入)", successNum: 1, failedNum: 0, startTime:startTime, endTime: endTime)
			record.save(flush: true)

			flash.message = "${message(code: 'default.created.message', args: [message(code: 'result.label', default: 'resultInstance'), ''])}"
			redirect(action: "index", id: resultInstance.id,flash:flash)
		}else {
			flash.error = renderErrors(bean: resultInstance, as: "list")
			redirect(action: "index", id: resultInstance.id, model: [resultInstance: resultInstance],flash:flash)
		}
	}
	
	def uploadBatch() {
		def nameArray=upload()
		def currentUser = springSecurityService.currentUser
		def startTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		int successNum = 0 
		int failedNum = 0
		def str=getStringFromXml(nameArray[1])
		if(str==null || "".equals(str)){
			flash.message = "${message(code: 'result.upload.success.label')}"
			redirect(action: "index")
		}else{
			str=str.replaceFirst("位置","location");
			str=str.replaceFirst("样品编号","sampleNum");
			str=str.replaceFirst("样品类型","sampleBelong");
			str=str.replaceFirst("FAM Ct","famCt");
			str=str.replaceFirst("VIC Ct","vicCt");
			str=str.replaceFirst("NED Ct","nedCt");
			str=str.replaceFirst("检测结果","detectedResult");
			str=str.replaceFirst("备注","comment");
			def propertiesList = readPropFromString(str)
			propertiesList?.each { properties ->
				properties["sampleNum"] = properties["sampleNum"].contains(".0")?properties["sampleNum"].substring(0,properties["sampleNum"].indexOf(".0")):properties["sampleNum"]
				def informationInstance = Information.findBySampleNum(properties["sampleNum"])
				if(Result.findBySampleNum(properties["sampleNum"])){
					failedNum++
				}else if(!Result.findBySampleNum(properties["sampleNum"]) && !informationInstance){
					failedNum++
				}else if(!Result.findBySampleNum(properties["sampleNum"]) && informationInstance){
					def resultInstance = new Result(properties)
					if (resultInstance.hasErrors() || !resultInstance.save(flush: true)) {
						failedNum++
						resultInstance.errors?.each { error ->
							log.error error
						}
					}else{
						resultInstance.information = informationInstance
						informationInstance.results.add(resultInstance)
						informationInstance.save(flush: true)
						successNum++
					}
				}
			}
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: nameArray[0]+"(批量录入)", successNum: successNum, failedNum:failedNum, startTime:startTime, endTime: endTime)
			record.save(flush: true)
			redirect(action: "index")
		}
	}
	
	public static String getStringFromXml(String fileName){
		StringBuffer sb = new StringBuffer();
		boolean isE2007 = false;    //判断是否是excel2007格式
		if(fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName);  //建立输入流
			Workbook wb  = null;
			//根据文件格式(2003或者2007)来初始化
			if(isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
			while (rows.hasNext()) {
				Row row = rows.next();  //获得行数据
				//System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
				Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
				while (cells.hasNext()) {
					Cell cell = cells.next();
					//System.out.println("Cell #" + cell.getColumnIndex());
					switch (cell.getCellType()) {   //根据cell中的类型来输出数据
					case HSSFCell.CELL_TYPE_NUMERIC:
						//System.out.println(cell.getNumericCellValue());
						DecimalFormat df = new DecimalFormat("0");
						sb.append(df.format(cell.getNumericCellValue())+"\t");
						break;
					case HSSFCell.CELL_TYPE_STRING:
						//System.out.println(cell.getStringCellValue());
						sb.append(cell.getStringCellValue().trim()+"\t");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						//System.out.println(cell.getBooleanCellValue());
						sb.append(cell.getBooleanCellValue()+"\t");
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						//System.out.println(cell.getCellFormula());
						sb.append(cell.getCellFormula()+"\t");
						break;
					default:
						//System.out.println("unsuported sell type");
					break;
					}
				}
				sb.append(";;;");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}
	
	def readPropFromString(String fileName) {
		def rawLines = fileName.split(";;;")
		if (rawLines.size() < 2) {
			return
		}
		
		def propertiesList = []
		def keys = rawLines[0].split("\t")
		def values = null
		def line = null
		for (int i = 1; i < rawLines.size(); i++) {
			def properties = [:]
			
			line = rawLines[i]
			if (Utils.isEmpty(line)) {
				continue
			}
			
			values = line.split("\t")
			for (int j = 0; j < values.size(); j++) {
				properties[(keys[j])] = values[j]
			}
			
			propertiesList << properties
		}
		
		return propertiesList
	}
	
	def upload(){                        //文件上传
		def originalFileName
		def fileName
		def filePath
		def f = request.getFile('InputFile')
		String[] nameArray = new String[2]
		if(!f.empty) {
			 def webRootDir = "${grailsApplication.config.project.file.upload.path}"
			 def userDir = new File(webRootDir,"WEB-INF/upload/")
			 if(!userDir.exists()){
				 userDir.mkdirs()
			 }
			 originalFileName=f.originalFilename
			 nameArray[0] = originalFileName
			 if(originalFileName instanceof String){
				 fileName=makeFileName(originalFileName)       //防止重名覆盖
			 }else{
				 println "传入了多个文件名"
			 }
			 filePath=makePath(fileName,userDir.toString())      //防止单个文件夹下文件过多
			 f.transferTo(new File(filePath,fileName))
		}
		nameArray[1] = filePath+"\\"+fileName
		return nameArray
	}
	
	def download(){
		def fileName
		def rootFilePath
		def filepath
		def realName
		
		def projectInstance =  Project.get(params.id)
		fileName = projectInstance.fileName
		
		def webRootDir = "${grailsApplication.config.project.file.upload.path}"
		rootFilePath = new File(webRootDir, "WEB-INF/upload")
		if(!rootFilePath.exists()){
			rootFilePath.mkdirs();
		}
		filepath = findFileSavePathByFileName(fileName,rootFilePath.toString())
		
		realName = fileName.substring(fileName.indexOf("_")+1);
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(realName, "UTF-8"))
		response.contentType = ""
		
		def out = response.outputStream
		def inputStream = new FileInputStream(filepath + "/" + fileName)
		byte[] buffer = new byte[1024]
		int i = -1
		while ((i = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, i)
		}
		out.flush()
		out.close()
		inputStream.close()

	}
	
	private String makeFileName(String filename){ //2.txt
		//为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}
	   /**
	   * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	   * @param filename 文件名，要根据文件名生成存储目录
	   * @param savePath 文件存储路径
	   * @return 新的存储目录
	   */
	private String makePath(String filename,String savePath){
		//得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf; //0--15
		int dir2 = (hashcode&0xf0)>>4; //0-15
		//构造新的保存目录
		String dir = savePath + "/" + dir1 + "/" + dir2 + "/" + dir1 + "/" + dir2; //upload\2\3\2\3
		//File既可以代表文件也可以代表目录
		File file = new File(dir);
		//如果目录不存在
		if(!file.exists()){
		//创建目录
		 file.mkdirs();
		}
		return dir;
	}
	// 删除文件
	public static boolean deleteFile(String filename,String saveRootPath) {
		boolean flag = false;
		if(filename){
			String filePath = findFileSavePathByFileName(filename,saveRootPath)
			File file = new File(filePath + "/" + filename);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
				flag = true;
			}
		}else{
			flag = true;
		}
		
		return flag;
	}
	public static String findFileSavePathByFileName(String filename,String saveRootPath){
	   int hashcode = filename.hashCode();
	   int dir1 = hashcode&0xf; //0--15
	   int dir2 = (hashcode&0xf0)>>4; //0-15
	   String dir = saveRootPath + "/" + dir1 + "/" + dir2 + "/" + dir1 + "/" + dir2; //upload\2\3\2\3
	   File file = new File(dir);
	   if(!file.exists()){
		   //创建目录
		   file.mkdirs();
	   }
	   return dir;
	}


}
