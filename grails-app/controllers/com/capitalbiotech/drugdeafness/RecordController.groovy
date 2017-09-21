package com.capitalbiotech.drugdeafness
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class RecordController {

    def index() { }
}
