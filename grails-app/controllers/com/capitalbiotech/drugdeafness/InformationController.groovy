package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class InformationController {
	def springSecurityService
	
    def index() {
	}
	
	def uploadOne() {
	
	}
	
	def uploadBatch() {
	}
	
}
