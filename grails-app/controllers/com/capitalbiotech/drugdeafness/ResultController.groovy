package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class ResultController {

    def index() {
		redirect(action: "uploadBatch", params: params)
	}
	
	def uploadOne() {
		
	}
	
	def uploadBatch() {
		render view: 'result'
	}
	
	def showpdf() {
		render view: 'pdf'
	}
}
