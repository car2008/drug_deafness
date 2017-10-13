package com.capitalbiotech.drugdeafness

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@Secured(['ROLE_USER'])
class InformationController {
	def utilsService
	def springSecurityService
	
    def index() {
		redirect(action: "listRecord")
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
		
		def informationInstanceList
		def informationInstanceTotal
		def projectInstanceTotalList
		if(params.num||params.name||params.hasResult){
			def beginSearchDate=params.beginSearchDate
			def endSearchDate=params.endSearchDate
			def num = params.num
			def name = params.name
			def hasResult = params.hasResult
			def q6 = params.q6
			def stringBuf=new StringBuffer()
			def paramMap1=[:]
			
			stringBuf.append("SELECT DISTINCT information FROM Information information ")
			if(q6){
				stringBuf.append("LEFT JOIN information.district district ")
			}
			if((beginSearchDate && endSearchDate)||(num||name||hasResult||q6)){
				stringBuf.append("WHERE ")
			}
			if(beginSearchDate && endSearchDate){
				stringBuf.append("information.dateCreated BETWEEN '"+beginSearchDate+"' AND '"+endSearchDate+"' ")
			}
			stringBuf.append(num?"AND sampleNum like '%"+num+"%' ":"")
			stringBuf.append(name?"AND patientName like '%"+name+"%' ":"")
			stringBuf.append(hasResult=="true"?"AND hasResult=true ":"")
			stringBuf.append(hasResult=="false"?"AND hasResult=false ":"")
			if(q6){
				def district=District.findByCode(q6)
				stringBuf.append("AND district = :district ")
				paramMap1.put("district", district)
			}
			
			def s1=stringBuf.toString()
			if(s1.contains("WHERE AND")){
				s1=s1.replaceFirst("AND","")
			}
			if(s1.endsWith("WHERE ")){
				s1=s1.replaceFirst("WHERE","")
			}
			stringBuf.append("ORDER BY information.${params.sort} ${params.order}")
			def s2=stringBuf.toString()
			if(s2.contains("WHERE AND")){
				s2=s2.replaceFirst("AND","")
			}
			
			if(paramMap1){
				projectInstanceTotalList = Result.executeQuery(s1,paramMap1)
				paramMap1.put("offset",params.offset)
				paramMap1.put("max",params.max)
				informationInstanceList = Result.executeQuery(s2,paramMap1)
			}else{
				projectInstanceTotalList = Result.executeQuery(s1)
				paramMap1.put("offset",params.offset)
				paramMap1.put("max",params.max)
				informationInstanceList = Result.executeQuery(s2,paramMap1)
			}
			informationInstanceTotal = projectInstanceTotalList.size()
		}else{
			informationInstanceList = Information.list(params)
			informationInstanceTotal = Information.count()
		}
		
		//render view: 'list', model: [informationInstanceList: informationInstanceList,allInformationInstanceTotal:allInformationInstanceTotal,]
		if(params.json=="json"){
			render ([
					"total":informationInstanceTotal,
					"rows":
							informationInstanceList.collect {  informationInstance ->
								["id":informationInstance.id,
								"sampleNum":informationInstance.sampleNum,
								"patientName":informationInstance.patientName,
								"gender":informationInstance.gender,
								"age":informationInstance.age,
								"hospital":informationInstance.hospital,
								"patientNum":informationInstance.patientNum,
								"wardBed":informationInstance.wardBed,
								"inspectionDepartment":informationInstance.inspectionDepartment,
								"inspectionDoctor":informationInstance.inspectionDoctor,
								"inspectionSample":informationInstance.inspectionSample,
								"inspectionTime":informationInstance.inspectionTime,
								"phoneNum":informationInstance.phoneNum,
								"remark":informationInstance.remark,
								"hasResult":informationInstance.hasResult==true?"有":"无"
								]
							}
				] as JSON)
		}else{
			return
		}
	}
	
	def listRecord(){
		if(!params.showRecords){
			params.showRecords="showRecords"
		}
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

		def recordInstanceList = Record.executeQuery("SELECT record FROM Record record WHERE record.recordCatagrory='CATAGRORY_INFORMATION' ORDER BY record.${params.sort} ${params.order}",[offset: params.offset,max:params.max])
		
		def recordInstanceTotal = Record.countByRecordCatagrory("CATAGRORY_INFORMATION")
		def allRecordInstanceTotal = recordInstanceTotal
		//render view: 'index', model: [recordInstanceList: recordInstanceList,allRecordInstanceTotal:allRecordInstanceTotal,]
		if(params.json=="json"){
			render ([
					"total":allRecordInstanceTotal,
					"rows":
							recordInstanceList.collect {  resultInstance ->
								["id":resultInstance.id,
								"name":resultInstance.uploadUser.name,
								"recordName":resultInstance.recordName,
								"successNum":resultInstance.successNum,
								"failedNum":resultInstance.failedNum,
								"successedSample":resultInstance.successedSample,
								"failedSample":resultInstance.failedSample,
								"startTime":resultInstance.startTime.format("yyyy-MM-dd HH:mm:ss"),
								"recordLog":resultInstance.recordLog
								]
							}
				] as JSON)
		}else{
			render view: 'index'
		}
	}
	
	def uploadOne() {
		def startTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		def currentUser = springSecurityService.currentUser
		def informationInstance = new Information(params)
		informationInstance.district = currentUser.district
		if(Information.findBySampleNum(params.sampleNum)) {
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_INFORMATION", recordName: informationInstance.sampleNum+"(单个录入)", successNum: 0, failedNum: 1, startTime:startTime, endTime: endTime,recordLog: "已有该编号："+params.sampleNum)
			record.save(flush: true)
			flash.message = "${message(code: 'result.information.found.message', args: [message(code: 'information.label', default: 'informationInstance'), params.sampleNum])}"
			redirect(action: "listRecord")
		}else{ 
			if(!informationInstance.hasErrors() && informationInstance.save(flush: true)) {
				def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
				def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_INFORMATION", recordName: informationInstance.sampleNum+"(单个录入)", successNum: 1, failedNum: 0, startTime:startTime, endTime: endTime,recordLog: "上传成功的编号："+params.sampleNum)
				record.save(flush: true)
	
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'Information.label', default: 'informationInstance'), ''])}"
				redirect(action: "listRecord", id: informationInstance.id, params: [showRecords: "showNewRecords"])
			}else {
				flash.error = renderErrors(bean: informationInstance, as: "list")
				redirect(action: "listRecord", id: informationInstance.id, model: [informationInstance: informationInstance])
			}
		}
	}
	
	def uploadBatch() {
		StringBuffer failedsb=new StringBuffer()
		StringBuffer sucessedsb=new StringBuffer()
		def nameArray=upload()
		def currentUser = springSecurityService.currentUser
		def startTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		int successNum = 0 
		int failedNum = 0
		def str=getStringFromXml(nameArray[1])
		str=str.replaceFirst("姓名","patientName");
		str=str.replaceFirst("样本编号","sampleNum");
		str=str.replaceFirst("性别","gender");
		str=str.replaceFirst("年龄","age");
		str=str.replaceFirst("医院\\/单位","hospital");
		str=str.replaceFirst("门诊号\\/住院号","patientNum");
		str=str.replaceFirst("病房\\/床位","wardBed");
		str=str.replaceFirst("送检科室","inspectionDepartment");
		str=str.replaceFirst("送检医生","inspectionDoctor");
		str=str.replaceFirst("送检样本","inspectionSample");
		str=str.replaceFirst("送检时间","inspectionTime");
		str=str.replaceFirst("联系电话","phoneNum");
		str=str.replaceFirst("备注","remark");
		def propertiesList = readPropFromString(str)
		propertiesList?.each { properties ->
			properties["sampleNum"] = properties["sampleNum"].contains(".0")?properties["sampleNum"].substring(0,properties["sampleNum"].indexOf(".0")):properties["sampleNum"]
			properties["patientNum"] = properties["patientNum"].contains(".0")?properties["patientNum"].substring(0,properties["patientNum"].indexOf(".0")):properties["patientNum"]
			properties["wardBed"] = properties["wardBed"].contains(".0")?properties["wardBed"].substring(0,properties["wardBed"].indexOf(".0")):properties["wardBed"]
			properties["phoneNum"] = properties["phoneNum"].contains(".0")?properties["phoneNum"].substring(0,properties["phoneNum"].indexOf(".0")):properties["phoneNum"]
			properties["age"] = properties["age"].contains(".0")?properties["age"].substring(0,properties["age"].indexOf(".0")):properties["age"]
			//println Integer.parseInt(s)
			if(Information.findBySampleNum(properties["sampleNum"])){
				failedNum++
				failedsb.append(","+properties["sampleNum"])
			}else{
				def informationInstance = new Information(properties)
				informationInstance.district = currentUser.district
				if (informationInstance.hasErrors() || !informationInstance.save(flush: true)) {
					failedNum++
					failedsb.append(","+properties["sampleNum"])
					informationInstance.errors?.each { error ->
						log.error error
					}
				}else{
					successNum++
					sucessedsb.append(","+properties["sampleNum"])
				}
			}
		}
		def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_INFORMATION", recordName: nameArray[0]+"(批量录入)", successNum: successNum, failedNum:failedNum, startTime:startTime, endTime: endTime,successedSample:sucessedsb.toString().replaceFirst("\\,", ""),failedSample:failedsb.toString().replaceFirst("\\,", ""),recordLog:"".equals(failedsb.toString())?"":"数据库已存在并上传失败的编号："+failedsb.toString().replaceFirst("\\,", ""))
		record.save(flush: true)
		render failedsb.toString().replaceFirst("\\,", "")+"###"+sucessedsb.toString().replaceFirst("\\,", "")
		//redirect(action: "listRecord",params: [showRecords: "showNewRecords"])
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
						DecimalFormat df = new DecimalFormat("0");                 //数字格式，防止长数字成为科学计数法形式，或者int变为double形式
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
		//def f = request.getFile("InputFile")
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
