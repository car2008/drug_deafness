package com.capitalbiotech.drugdeafness

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
