package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ResultController {
	
	def springSecurityService
	def grailsApplication
	
    def index() {
		//render view: 'result'
		redirect(action: "listrecord", params: params)
	}
	
	def listrecord(){
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

		def currentUser = springSecurityService.currentUser
		def recordInstanceList = Record.executeQuery("SELECT record FROM Record record WHERE record.recordCatagrory='CATAGRORY_RESULT' ORDER BY record.${params.sort} ${params.order}",[offset: params.offset,max:params.max])
		
		def recordInstanceTotal = Record.countByRecordCatagrory("CATAGRORY_RESULT")
		def allRecordInstanceTotal = recordInstanceTotal
		
		render view:'reslut',model:
									[ 
									  recordInstanceList:recordInstanceList,
								      allRecordInstanceTotal:allRecordInstanceTotal,
									]
	}
	
	def uploadOne() {
		def startTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
		def currentUser = springSecurityService.currentUser
		def resultInstance = new Result(params)
		def informationInstance = Information.findBySampleNum(params.sampleNum)
		if(informationInstance) resultInstance.information = informationInstance
		if (!resultInstance.hasErrors() && resultInstance.save(flush: true)) {
			def endTime = Utils.parseSimpleDateTime(new Date().format("yyyy-MM-dd HH:mm:ss"))
			def record = new Record(uploadUser: currentUser, recordCatagrory: "CATAGRORY_RESULT", recordName: "单个录入", successNum: 1, failedNum: 0, startTime:startTime, endTime: endTime)
			record.save(flush: true)

			flash.message = "${message(code: 'default.created.message', args: [message(code: 'result.label', default: 'resultInstance'), ''])}"
			redirect(action: "index", id: resultInstance.id)
		}else {
			flash.error = renderErrors(bean: resultInstance, as: "list")
			redirect(action: "index", id: resultInstance.id, model: [resultInstance: resultInstance])
		}
	}
	
	def uploadBatch() {
		//render view: 'result'
	}
	
	def showpdf() {
		render view: 'pdf'
	}
}
