package com.capitalbiotech.drugdeafness

import grails.plugin.springsecurity.annotation.Secured
import java.text.SimpleDateFormat
import grails.plugin.springsecurity.SpringSecurityUtils

@Secured(['ROLE_ADMIN','ROLE_USER'])
class UserController {
	def springSecurityService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def index = {
		redirect(action: "list", params: params)
	}

	//@Secured(['ROLE_ADMIN'])
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
	}

	//@Secured(['ROLE_ADMIN'])
	def create = {
		def districtInstanceList = District.list()
		def userInstance = new User()
		userInstance.properties = params
		return [userInstance: userInstance,districtInstanceList:districtInstanceList]
	}

	//@Secured(['ROLE_ADMIN'])
	def save = {
		def userInstance = new User(params)
		
		def authorities = request.getParameterValues('authority') as Set
		if (authorities == null) {
			authorities = ['ROLE_USER']
		}
		else if (!authorities.contains('ROLE_USER')) {
			authorities << 'ROLE_USER'
		}
		//在新建userInstance实例时已经加密了（可在User类里找到）
		/*if (userInstance.password != null && !userInstance.password.equals("")) {
			userInstance.password =  springSecurityService.encodePassword(userInstance.password)
		}*/
		if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
			authorities.each{authority ->
				def role = Role.findByAuthority(authority)
				if(role){
					UserRole.create(userInstance,role,true)
				}
			}
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
			redirect(action:"list")
		}
		else {
			render(view: "create", model: [userInstance: userInstance])
			return
		}
	}

	//@Secured(['ROLE_USER'])
	def show = {
		def uid = springSecurityService.currentUser.id
		if(params.id != null){
			uid = params.id as long
		}
		def userInstance = User.get(uid)
		if (userInstance) {
			[userInstance: userInstance]
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), uid])}"
			redirect(action: "list")
		}
	}

	//@Secured(['ROLE_USER'])
	def password = {
		def self = true
		def uid = springSecurityService.currentUser.id
		if(params.id != null){
			uid = params.id as long
		}
		if (uid != springSecurityService.currentUser.id) {
			self = false
		}
		def userInstance = User.get(uid)
		if (userInstance) {
			[userInstance: userInstance, self: self]
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), uid])}"
			redirect(action: "list")
		}
	}

	//@Secured(['ROLE_USER'])
	def edit = {
		def districtInstanceList = District.list()
		def self = true
		def uid = springSecurityService.currentUser.id
		if(SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') &&params.id != null){
			uid = params.id as long
		}
		if (uid != springSecurityService.currentUser.id) {
			self = false
		}
		def userInstance = User.get(uid)
		if (userInstance) {
			def authorities = userInstance.getAuthorities()?.collect {it.authority}
			[userInstance: userInstance, authorities: authorities, self: self,districtInstanceList:districtInstanceList]
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), uid])}"
			redirect(action: "list")
		}
	}

	//@Secured(['ROLE_USER'])
	def updatePassword = {
		def self = true
		def uid = springSecurityService.currentUser.id
		if(SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') &&params.id != null){
			uid = params.id as long
		}
		if (uid != springSecurityService.currentUser.id) {
			self = false
		}
		def userInstance = User.get(uid)
		if (params.version) {
			def version = params.version.toLong()
			if (userInstance.version > version) {
				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
				redirect(action: "password", id: uid)
			}
		}
		def newpassword = params.newPassword
		def confirmpassword = params.confirmPassword
		if(!newpassword.equals(confirmpassword)){
			flash.error = "Two inputs of new password do not match."
		}
		else if(newpassword.size() < 6){
			flash.error = "Password too short, try with length more than 6."
		}
		else{
			if(params.oldPassword){
				def oldpassword = params.oldPassword
				def savedPassword = userInstance.password
				if(!self){
					savedPassword = springSecurityService.currentUser.password
				}
				if(!springSecurityService?.passwordEncoder.isPasswordValid(savedPassword,oldpassword,null)) {
					 flash.message = 'Current password is incorrect'
				}else if(springSecurityService?.passwordEncoder.isPasswordValid(userInstance.password, newpassword,null)) {
					 flash.message = 'Please choose a different password from your current one'
				}else{
					userInstance.password = newpassword
					if(!userInstance.hasErrors() && userInstance.save(flush: true)){
						flash.message = "Password changed successfully."
					}
				}
			}
			else{
				if(self){
					flash.error = "Original password must be provided."
				}
				else {
					flash.error = "Administration password must be provided."
				}
			}
		}
		redirect(action: "password", id: uid)
	}
	//@Secured(['ROLE_USER'])
	def update = {
		def uid = springSecurityService.currentUser.id
		if(SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') &&params.id != null){
			uid = params.id as long
		}

		def userInstance = User.get(uid)
		def authorities = request.getParameterValues('authority') as Set

		if (authorities == null) {
			authorities = ['ROLE_USER']
		}
		else if (!authorities?.contains('ROLE_USER')) {
			authorities << 'ROLE_USER'
		}

		if(uid == springSecurityService.currentUser.id){
			if (SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN') && !authorities?.contains('ROLE_ADMIN')) {
				flash.error = "ROLE_ADMIN can not be removed by your self."
				render(view: "edit", model: [userInstance: userInstance, authorities: authorities])
				return
			}
		}
		if (userInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (userInstance.version > version) {
					userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
					render(view: "edit", model: [userInstance: userInstance, authorities: authorities])
					return
				}
			}
			userInstance.district=null
			params.password=userInstance.password
			userInstance.properties = params
			/*userInstance.name = params.name
			userInstance.email = params.email
			userInstance.receiveMail = ("true"==params.receiveMail?true:false)
			if(SpringSecurityUtils.ifAnyGranted('ROLE_ADMIN')){
				userInstance.username = params.username
				userInstance.avatar = params.avatar
			}*/
			
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
				UserRole.removeAll(userInstance)
				authorities.each{authority ->
					def role = Role.findByAuthority(authority)
					if(role){
						UserRole.create(userInstance,role,true)
					}
				}
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
				def currentUser = (User)springSecurityService.currentUser
				if(currentUser.getAuthoritiesString().contains("ROLE_ADMIN")){
					redirect(action: "list")
				}else{
					redirect(action:"show",id:userInstance.id)
				}
			}
			else {
				render(view: "edit", model: [userInstance: userInstance, authorities: authorities])
				return
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
			redirect(action: "list")
		}
	}

	//@Secured(['ROLE_ADMIN'])
	def delete = {
		def uid = params.id as long
		if (uid == springSecurityService.currentUser.id) {
			flash.error = "Can't delete yourself."
			redirect(action:"list")
			return
		}

		def userInstance = User.get(uid)
		if (userInstance) {
			try {
				UserRole.removeAll userInstance
				userInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])}"
				redirect(action:"list")
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), uid])}"
			redirect(action: "list")
		}
	}
	
}
