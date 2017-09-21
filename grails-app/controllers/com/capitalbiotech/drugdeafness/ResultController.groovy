package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

import java.text.SimpleDateFormat

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Secured(['ROLE_USER'])
class ResultController {
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def utilsService
	def springSecurityService
	
    def index() {
		redirect(action:"listRecord",params: params)
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
		println recordInstanceList
		println allRecordInstanceTotal
		render view: 'result', model: [recordInstanceList: recordInstanceList,allRecordInstanceTotal:allRecordInstanceTotal,]
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
		render view: 'result'
	}
	
	def showpdf() {
		render view: 'pdf'
	}
}
