package com.capitalbiotech.drugdeafness

class ErrorController {
    def notFound() {
        render(view: '/404')
    }
    
    def internalServerError() {
        render(view: '/500')
    }
}
