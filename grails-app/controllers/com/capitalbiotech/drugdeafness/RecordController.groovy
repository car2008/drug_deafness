package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class RecordController {
	def utilsService
	def springSecurityService
    def index() { }
}
