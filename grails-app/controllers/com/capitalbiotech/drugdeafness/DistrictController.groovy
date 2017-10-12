package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured
@Secured(['ROLE_ADMIN'])
class DistrictController {

    def springSecurityService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[districtInstanceList: District.list(params), districtInstanceTotal: District.count()]
	}

	def create = {
		def districtInstance = new District()
		districtInstance.properties = params
		return [districtInstance: districtInstance]
	}

	def save = {
		def districtInstance = new District(params)
		
		if (!districtInstance.hasErrors() && districtInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'district.label', default: 'District'), districtInstance.id])}"
			redirect(action:"list")
		}
		else {
			render(view: "create", model: [districtInstance: districtInstance])
			return
		}
	}

	def show = {
		def districtInstance = District.get(params.id)
		if (districtInstance) {
			[districtInstance: districtInstance]
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'district.label', default: 'District'), ])}"
			redirect(action: "list")
		}
	}

	def edit = {
		def districtInstance = District.get(params.id)
		if (districtInstance) {
			[districtInstance: districtInstance,]
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'district.label', default: 'District'), uid])}"
			redirect(action: "list")
		}
	}

	def update = {
		def districtInstance = District.get(params.id)
		if (districtInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (districtInstance.version > version) {
					districtInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'district.label', default: 'District')] as Object[], "Another district has updated this District while you were editing")
					render(view: "edit", model: [districtInstance: districtInstance])
					return
				}
			}
			districtInstance.properties = params
			
			if (!districtInstance.hasErrors() && districtInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'district.label', default: 'District'), districtInstance.title])}"
				redirect(action:"show",id:districtInstance.id)
			}
			else {
				render(view: "edit", model: [districtInstance: districtInstance])
				return
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'district.label', default: 'District'), districtInstance.title])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def districtInstance = District.get(params.id)
		if (districtInstance) {
			try {
				districtInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'district.label', default: 'District'), districtInstance.title])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'district.label', default: 'District'), districtInstance.title])}"
				redirect(action:"list")
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'district.label', default: 'District'), params.id])}"
			redirect(action: "list")
		}
	}
}
