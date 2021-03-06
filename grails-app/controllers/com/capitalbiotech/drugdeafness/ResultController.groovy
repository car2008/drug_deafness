package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

import java.text.DecimalFormat
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import grails.converters.JSON

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
		if(!params.district){
			def currentUser = (User)springSecurityService.currentUser
			if(!currentUser.getAuthorities().contains(Role.findByAuthority("ROLE_ADMIN")))params.district=currentUser.district.code
		}
		
		def districtInstanceList = District.list()
		def resultInstanceList
		def resultInstanceTotal
		def projectInstanceTotalList
		if(params.num||params.name||params.positive=="true"||params.negative=="true"||params.abnormal=="true"||params.district){
			def beginSearchDate=params.beginSearchDate
			def endSearchDate=params.endSearchDate
			def num = params.num
			def name = params.name
			def positive = params.positive
			def negative = params.negative
			def abnormal = params.abnormal
			def district = params.district
			def stringBuf=new StringBuffer()
			def paramMap1=[:]
			
			stringBuf.append("SELECT DISTINCT result FROM Result result ")
			if(district){
				stringBuf.append("LEFT JOIN result.information.district district ")
			}
			if((beginSearchDate && endSearchDate)||(num||name||positive=="true"||negative=="true"||abnormal=="true"||district)){
				stringBuf.append("WHERE ")
			}
			if(beginSearchDate && endSearchDate){
				stringBuf.append("result.dateCreated BETWEEN '"+beginSearchDate+"' AND '"+endSearchDate+"' ")
			}
			stringBuf.append(num?"AND result.sampleNum like '%"+num+"%' ":"")
			stringBuf.append(name?"AND result.information.patientName like '%"+name+"%' ":"")
			stringBuf.append(positive=="true"?"OR result.detectedResult like '%突变型%' ":"")
			stringBuf.append(negative=="true"?"OR result.detectedResult like '%野生型%' ":"")
			stringBuf.append(abnormal=="true"?"OR result.detectedResult like '%重新检测%' ":"")
			if(district){
				def districtInstance=District.findByCode(district)
				stringBuf.append("AND district = :district ")
				paramMap1.put("district", districtInstance)
			}
			
			def s1=stringBuf.toString()
			if(s1.contains("OR result.detectedResult")){
				s1=s1.replaceFirst("OR","AND")
			}
			if(s1.contains("WHERE AND")){
				s1=s1.replaceFirst("AND","")
			}
			if(s1.endsWith("WHERE ")){
				s1=s1.replaceFirst("WHERE","")
			}
			stringBuf.append("ORDER BY result.${params.sort} ${params.order}")
			def s2=stringBuf.toString()
			if(s2.contains("OR result.detectedResult")){
				s2=s2.replaceFirst("OR","AND")
			}
			if(s2.contains("WHERE AND")){
				s2=s2.replaceFirst("AND","")
			}
			
			if(paramMap1){
				projectInstanceTotalList = Result.executeQuery(s1,paramMap1)
				paramMap1.put("offset",params.offset)
				paramMap1.put("max",params.max)
				resultInstanceList = Result.executeQuery(s2,paramMap1)
			}else{
				projectInstanceTotalList = Result.executeQuery(s1)
				paramMap1.put("offset",params.offset)
				paramMap1.put("max",params.max)
				resultInstanceList = Result.executeQuery(s2,paramMap1)
			}
			resultInstanceTotal = projectInstanceTotalList.size()
				
		}else{
			resultInstanceList = Result.list(params)
			resultInstanceTotal = Result.count()
		}
		//render view: 'list', model: [resultInstanceList: resultInstanceList,allResultInstanceTotal:allResultInstanceTotal,]
		if(params.json=="json"){
			render ([
					"total":resultInstanceTotal,
					"rows":
							resultInstanceList.collect {  resultInstance ->
								["id":resultInstance.id,
								"sampleNum":resultInstance.information?.sampleNum,
								"patientName":resultInstance.information?.patientName,
								"gender":resultInstance.information?.gender,
								"age":resultInstance.information?.age,
								"famCt":resultInstance.famCt,
								"vicCt":resultInstance.vicCt,
								"nedCt":resultInstance.nedCt,
								"detectedResult":resultInstance.detectedResult,
								"resulttitle":resultInstance.resulttitle,
								"checker":resultInstance.checker,
								"assessor":resultInstance.assessor,
								"pdfcomment":resultInstance.pdfcomment,
								"remark":resultInstance.information?.remark,
								"district":resultInstance.information?.district.title
								]
							}
				] as JSON)
		}else{
			return [districtInstanceList:districtInstanceList]
		}

	}
	
	def findLastedResult(){
		def resultInstance = Result.executeQuery("SELECT result from Result result where isnull(result.resulttitle) is false AND isnull(result.pdfcomment) is false AND isnull(result.checker) is false AND isnull(result.assessor) is false ORDER BY id DESC")[0]
		if(resultInstance){
			render ([
				"resulttitle":resultInstance.resulttitle,
				"checker":resultInstance.checker,
				"assessor":resultInstance.assessor,
				"pdfcomment":resultInstance.pdfcomment,
				] as JSON)
		}else{
			render null
		}
		
	}
	
	def generatePdf(){
		if(!params.hospital){
			
		}
		if(!params.checker){
		
		}
		if(!params.assessor){
		
		}
		if(!params.pdfcomment){
			params.pdfcomment = "* 本报告仅对本次送检样品负责，如有疑问，请与您的医生联系。"
		}
		def outPathList = []
		def idStr  = params.id.toString()//params.jsonData.toString()
		idStr=idStr.replace("[","")
		idStr=idStr.replace("]","")
		idStr=idStr.replace('"',"")
		def resultInstanceList = Result.executeQuery("SELECT record FROM Result record WHERE record.id in ("+idStr+")")
		def outDir = "${grailsApplication.config.project.file.pdf.path}".toString()
		resultInstanceList.each{ resultInstance ->
			HashMap map = new LinkedHashMap<String,String>();
			map.put("医院", resultInstance.resulttitle?resultInstance.resulttitle:params.hospital.toString());
			map.put("姓名",resultInstance.information.patientName);
			map.put("性别",resultInstance.information.gender);
			map.put("年龄",resultInstance.information.age);
			map.put("样本编号",resultInstance.information.sampleNum);
			map.put("门诊号/住院号",resultInstance.information.patientNum);
			map.put("病房/床位",resultInstance.information.wardBed);
			map.put("送检科室",resultInstance.information.inspectionDepartment);
			map.put("送检医生",resultInstance.information.inspectionDoctor);
			map.put("送检样本",resultInstance.information.inspectionSample);
			map.put("送检时间",resultInstance.information.inspectionTime);
			map.put("备注",resultInstance.information.remark==null?"":resultInstance.information.remark);
			map.put("famCt",resultInstance.famCt);
			map.put("vicCt",resultInstance.vicCt);
			map.put("nedCt",resultInstance.nedCt);
			map.put("famCtResult",resultInstance.famCt?.contains("--")?"阴性（-）":"阳性（+）");
			map.put("vicCtResult",resultInstance.vicCt?.contains("--")?"阴性（-）":"阳性（+）");
			map.put("nedCtResult",resultInstance.nedCt?.contains("--")?"阴性（-）":"阳性（+）");
			map.put("detectedResult",resultInstance.detectedResult);
			map.put("resultcomment",resultInstance.comment==null?"":resultInstance.comment);
			if(resultInstance.detectedResult.contains("检测异常"))map.put("resultpictureUrl","web-app\\images\\detected_pic\\0001-检测异常.bmp");
			if(resultInstance.detectedResult.contains("1555"))map.put("resultpictureUrl","web-app\\images\\detected_pic\\0002-1555突变.bmp");
			if(resultInstance.detectedResult.contains("野生型"))map.put("resultpictureUrl","web-app\\images\\detected_pic\\0003-野生型.bmp");
			if(resultInstance.detectedResult.contains("1494"))map.put("resultpictureUrl","web-app\\images\\detected_pic\\0004-1494突变.bmp");
			if(resultInstance.detectedResult.contains("NED质控异常"))map.put("resultpictureUrl","web-app\\images\\detected_pic\\0005-质控异常.bmp");
			map.put("checker",resultInstance.checker?resultInstance.checker:params.checker.toString());
			map.put("assessor",resultInstance.assessor?resultInstance.assessor:params.assessor.toString());
			map.put("dateCreated","2017-9-1");
			map.put("pdfcomment",resultInstance.pdfcomment?resultInstance.pdfcomment:params.pdfcomment.toString());
			map.put("outDir", outDir+"/"+resultInstance.sampleNum+".pdf");
			outPathList.add(PDFUtil.createPDF(map));
			if(!resultInstance.resulttitle)resultInstance.resulttitle=params.hospital.toString();
			if(!resultInstance.checker)resultInstance.checker=params.checker.toString();
			if(!resultInstance.assessor)resultInstance.assessor=params.assessor.toString();
			if(!resultInstance.pdfcomment)resultInstance.pdfcomment=params.pdfcomment.toString();
			resultInstance.save(flush: true);
		}
		def zipName = new Date().format("yyyy-MM-dd-HH-mm-ss")+"-"+UUID.randomUUID()
		if(PDFUtil.fileToZip(outPathList,outDir,zipName)){
			download(outDir,zipName+".zip")
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

		def recordInstanceList = Record.executeQuery("SELECT record FROM Record record WHERE record.recordCatagrory='CATAGRORY_RESULT' ORDER BY record.${params.sort} ${params.order}",[offset: params.offset,max:params.max])
		
		def recordInstanceTotal = Record.countByRecordCatagrory("CATAGRORY_RESULT")
		def allRecordInstanceTotal = recordInstanceTotal
		//render view: 'result', model: [recordInstanceList: recordInstanceList,allRecordInstanceTotal:allRecordInstanceTotal,]
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
			render view: 'result'
		}
	}
	
	def uploadOne() {
		def startTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		def currentUser = springSecurityService.currentUser
		if(!params.famCt){
			params.famCt="--"
		}
		if(!params.vicCt){
			params.vicCt="--"
		}
		if(!params.nedCt){
			params.nedCt="--"
		}
		if(!params.detectedResult){
			params.detectedResult="--"
		}
		def resultInstance = new Result(params)
		def informationInstance = Information.findBySampleNum(params.sampleNum)
		if(informationInstance && !Result.findBySampleNum(params.sampleNum)) {
			resultInstance.information = informationInstance
			informationInstance.result = resultInstance
			informationInstance.hasResult=true
			informationInstance.save(flush: true)
			if (!resultInstance.hasErrors() && resultInstance.save(flush: true)) {
				def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
				def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: resultInstance.sampleNum+"(单个录入)", successNum: 1, failedNum: 0, startTime:startTime, endTime: endTime,recordLog: "该编号："+params.sampleNum+"保存成功")
				record.save(flush: true)
	
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'result.label', default: 'resultInstance'), ''])}"
				redirect(action: "listRecord", id: resultInstance.id,flash:flash, params: [showRecords: "showNewRecords"])
			}else {
				flash.error = renderErrors(bean: resultInstance, as: "list")
				redirect(action: "listRecord", id: resultInstance.id, model: [resultInstance: resultInstance],flash:flash)
			}
		}else if(informationInstance && Result.findBySampleNum(params.sampleNum)){
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: resultInstance.sampleNum+"(单个录入)", successNum: 0, failedNum: 1, startTime:startTime, endTime: endTime,recordLog: "已有该编号："+params.sampleNum)
			record.save(flush: true)
			render view:'result'
			return
		}else{
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: resultInstance.sampleNum+"(单个录入)", successNum: 0, failedNum: 1, startTime:startTime, endTime: endTime,recordLog: "未找到该编号"+params.sampleNum+"的相关信息")
			record.save(flush: true)
			render view:'result'
			return
		}
	}
	
	def uploadBatch() {
		StringBuffer failedsb=new StringBuffer()
		StringBuffer sucessedsb=new StringBuffer()
		StringBuffer resultExistedsb=new StringBuffer()
		StringBuffer informationNotExistedsb=new StringBuffer()
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
				properties["sampleNum"] = properties["sampleNum"].toString().contains(".0")?properties["sampleNum"].toString().substring(0,properties["sampleNum"].toString().indexOf(".0")):properties["sampleNum"]
				def informationInstance = Information.findBySampleNum(properties["sampleNum"])
				if(Result.findBySampleNum(properties["sampleNum"])){
					failedNum++
					failedsb.append(","+properties["sampleNum"])
					resultExistedsb.append(","+properties["sampleNum"])
				}else if(!Result.findBySampleNum(properties["sampleNum"]) && !informationInstance){
					failedNum++
					failedsb.append(","+properties["sampleNum"])
					informationNotExistedsb.append(","+properties["sampleNum"])
				}else if(!Result.findBySampleNum(properties["sampleNum"]) && informationInstance){
					def resultInstance = new Result(properties)
					if (resultInstance.hasErrors() || !resultInstance.save(flush: true)) {
						failedNum++
						failedsb.append(","+properties["sampleNum"])
						resultInstance.errors?.each { error ->
							log.error error
						}
					}else{
						resultInstance.information = informationInstance
						informationInstance.result=resultInstance
						informationInstance.hasResult=true
						informationInstance.save(flush: true)
						successNum++
						sucessedsb.append(","+properties["sampleNum"])
					}
				}
			}
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			
			def recordLog = !"".equals(resultExistedsb.toString())&&!"".equals(informationNotExistedsb.toString())?"结果编号已存在："+resultExistedsb.toString().replaceFirst("\\,", "")+"；相关信息未录入的："+informationNotExistedsb.toString().replaceFirst("\\,", ""):!"".equals(resultExistedsb.toString())?"结果编号已存在："+resultExistedsb.toString().replaceFirst("\\,", "")+"；":!"".equals(informationNotExistedsb.toString())?"相关信息未录入的："+informationNotExistedsb.toString().replaceFirst("\\,", ""):""
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: nameArray[0]+"(批量录入)", successNum: successNum, failedNum:failedNum, startTime:startTime, endTime: endTime,successedSample:sucessedsb.toString().replaceFirst("\\,", ""),failedSample:failedsb.toString().replaceFirst("\\,", ""),recordLog: recordLog)
			record.save(flush: true)
			render failedsb.toString().replaceFirst("\\,", "")+"###"+sucessedsb.toString().replaceFirst("\\,", "")
			//redirect(action: "listRecord", params: [showRecords: "showNewRecords"])
		}
	}
	
	def update = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		if (!params.offset) {
			params.offset = 0
		}
		if (!params.order) {
			params.order = 'asc'
		}
		if (!params.sort) {
			params.sort = 'dateCreated'
		}
		def sort = params.sort
		def order = params.order
		def offset = params.offset
		def max = params.max
		def resultInstance = Result.get(params.id)
		if (resultInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (resultInstance.version > version) {
					resultInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'message.label', default: 'Message')] as Object[], "Another user has updated this Message while you were editing")
					return
				}
			}
			resultInstance.properties = params
			
			if (!resultInstance.hasErrors() && resultInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'topic.label', default: 'resultInstance'),resultInstance.id])}"
			}
			else {
				flash.message = "${message(code: 'default.not.updated.message', args: [message(code: 'topic.label', default: 'resultInstance'),resultInstance.id])}"
			}
		}else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), params.id])}"
		}
		redirect(action:"list",params:[sort:sort,order:order,max:max,offset:offset])
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
	
	def download(filePath,realName){
		//params.filePath = "D:/shiyanshuju/pdf"
		//params.realName = "pdfZip1.zip"
		//def filePath = params.filePath
		//def realName = params.realName
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(realName, "UTF-8"))
		response.contentType = ""
		println filePath+"/"+realName
		def out = response.outputStream
		def inputStream = new FileInputStream(filePath + "/" + realName)
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
