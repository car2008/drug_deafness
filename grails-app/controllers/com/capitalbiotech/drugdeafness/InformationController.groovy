package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class InformationController {
	def springSecurityService
	
    def index() {
	}
	
	def uploadOne() {
	
	}
	
	def uploadBatch() {
	}
	
}
