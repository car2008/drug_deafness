package com.capitalbiotech.drugdeafness
import org.springframework.web.servlet.support.RequestContextUtils

class IncludeController {

    def utilsService
    def grailsApplication
    
    def stylesheet() {
        Locale requestLocale = RequestContextUtils.getLocale(request)
        def locales = ['en', 'zh']
        def locale = 'en'
        if (locales.contains(requestLocale.language)) {
            locale = requestLocale.language
        }
        
        render contentType: 'text/css', template: 'stylesheet', model: ['locale': locale]
    }
    
    def javascript() {
        Locale requestLocale = RequestContextUtils.getLocale(request)
        def locales = ['en', 'zh']
        def locale = 'en'
        if (locales.contains(requestLocale.language)) {
            locale = requestLocale.language
        }
        
        render contentType: 'text/javascript', template: 'javascript', model: ['locale': locale]
    }
}
