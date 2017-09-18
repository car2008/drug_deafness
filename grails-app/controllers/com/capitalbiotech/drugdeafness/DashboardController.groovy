package com.capitalbiotech.drugdeafness

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class DashboardController {
    def utilsService
    def springSecurityService
    
    def index() {
        
    }
}
