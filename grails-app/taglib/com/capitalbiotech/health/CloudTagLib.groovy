package com.capitalbiotech.health

import grails.plugin.springsecurity.SpringSecurityUtils

import org.springframework.web.servlet.support.RequestContextUtils

import com.capitalbiotech.drugdeafness.User;

import grails.converters.JSON
import groovy.xml.MarkupBuilder

import java.text.DecimalFormat

class CloudTagLib {

    static namespace       = 'drug_deafness'
    static defaultEncodeAs = [taglib: 'text']
    
    def utilsService
    def grailsApplication
    def springSecurityService
    def groovyPagesTemplateEngine
    
    private static final SCOPES = [
        'page':        'pageScope',
        'application': 'servletContext',
        'request':     'request',
        'session':     'session',
        'flash':       'flash'
    ]
    
    def ifDevMode = { attrs, body ->
        def devMode = grailsApplication.config.cbt_health.dev_mode
        if (devMode) {
            out << body()
        }
    }
    
    def setPreferenceValue = { attrs ->
        if (attrs.key == null) {
            throwTagError("Tag [setPreferenceValue] is missing required attribute [key]")
        }
        if (attrs.var == null) {
            throwTagError("Tag [setPreferenceValue] is missing required attribute [var]")
        }
        
        def value = null
        def preference = Preference.findByKey(attrs.key)
        if (preference) {
            value = preference.value
            if (preference.type == Preference.TYPE_USER && springSecurityService.loggedIn) {
                def userPreference = preference.getUserPreference(springSecurityService.currentUser)
                value = userPreference.value
            }
        }
        
        this.pageScope."${attrs.var}" = value
    }
    
    def formatDate = { attrs ->
        def timeZonePreference = Preference.findByKey("TIMEZONE")
        attrs.timeZone = timeZonePreference.value
        
        if (timeZonePreference.type == Preference.TYPE_USER && springSecurityService.loggedIn) {
            def timeZoneUserPreference = timeZonePreference.getUserPreference(springSecurityService.currentUser)
            attrs.timeZone = timeZoneUserPreference.value
        }
        out << g.formatDate(attrs)
    }
    
    def i18nImg = { attrs ->
        if (attrs.dir == null) {
            throwTagError("Tag [i18nImg] is missing required attribute [dir]")
        }
        
        if (attrs.file == null) {
            throwTagError("Tag [i18nImg] is missing required attribute [file]")
        }
        
        if (attrs.locales == null) {
            attrs.locales = ['en', 'zh']
        }
        
        Locale requestLocale = RequestContextUtils.getLocale(request)
        def file = attrs.file
        def locale = 'en'
        if (attrs.locales.contains(requestLocale.language)) {
            locale = requestLocale.language
        }
        if (locale != 'en') {
            def extensionLocus = file.lastIndexOf('.')
            if (extensionLocus > -1) {
                file = file.substring(0, extensionLocus) + "-" + requestLocale.language + file.substring(extensionLocus)
            }
        }
    
        out << g.resource(dir: attrs.dir, file: file)
    }
    
	def setLoggedInUser = { attrs ->
		if (springSecurityService.loggedIn) {
			
			if (attrs?.var != null) {
				this.pageScope."${attrs.var}" = springSecurityService.currentUser
			}
		}
	}
	
    def ifLoggedIn = { attrs, body ->
        if (attrs.var == null) {
            throwTagError("Tag [ifUserLoggedIn] is missing required attribute [var]")
        }
        
        if (springSecurityService.loggedIn && springSecurityService.currentUser) {
            def user = User.get(springSecurityService.currentUser?.id)
            if (user) {
                out << body(["${attrs.var}": user])
            }
        }
    }
    
    def greeting = { attrs, body ->
        if (springSecurityService.loggedIn && springSecurityService.currentUser) {
            def user = User.get(springSecurityService.currentUser?.id)
            if (user && !user.greeted) {
                user.greeted = true
                user.save(flush: true)
                out << body(['user': user])
            }
        }
    }

    /**
     * Creates next/previous links to support pagination for the current controller.<br/>
     *
     * &lt;g:paginate total="${Account.count()}" /&gt;<br/>
     *
     * @attr total REQUIRED The total number of results to paginate
     * @attr action the name of the action to use in the link, if not specified the default action will be linked
     * @attr controller the name of the controller to use in the link, if not specified the current controller will be linked
     * @attr id The id to use in the link
     * @attr params A map containing request parameters
     * @attr prev The text to display for the previous link (defaults to "Previous" as defined by default.paginate.prev property in I18n messages.properties)
     * @attr next The text to display for the next link (defaults to "Next" as defined by default.paginate.next property in I18n messages.properties)
     * @attr max The number of records displayed per page (defaults to 5). Used ONLY if params.max is empty
     * @attr maxsteps The number of steps displayed for pagination (defaults to 10). Used ONLY if params.maxsteps is empty
     * @attr offset Used only if params.offset is empty
     * @attr fragment The link fragment (often called anchor tag) to use
     */
    def paginate = { attrs ->
        if (attrs.total == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }
		params.showRecords = 'showNewRecords'
        def total = attrs.int('total') ?: 0
        //log.debug(total)
        def action = (attrs.action ? attrs.action : (params.action ? params.action : "list"))
        def offset = params.int('offset') ?: 0
        def max = params.int('max')
        def maxsteps = (attrs.int('maxsteps') ?: 5)

        if (!offset) offset = (attrs.int('offset') ?: 0)
        if (!max) max = (attrs.int('max') ?: 5)

        def linkParams = [:]
        if (params) linkParams.putAll(params)
        if (attrs.params) linkParams.putAll(attrs.params)
        linkParams.offset = offset - max
        linkParams.max = max
        if (params.sort) linkParams.sort = params.sort
        if (params.order) linkParams.order = params.order

        def linkTagAttrs = [action:action]
        if (attrs.controller) {
            linkTagAttrs.controller = attrs.controller
        }
        if (attrs.id != null) {
            linkTagAttrs.id = attrs.id
        }
        if (attrs.fragment != null) {
            linkTagAttrs.fragment = attrs.fragment
        }
        linkTagAttrs.params = linkParams

        // determine paging variables
        def steps = maxsteps > 0
        int currentstep = (offset / max) + 1
        int firststep = 1
        int laststep = Math.round(Math.ceil(total / max))

        // display previous link when not on firststep
        if (currentstep > firststep) {
            //linkTagAttrs.class = 'prevLink'
            linkParams.offset = offset - max
            out << "<li>" + link(linkTagAttrs.clone()) {
                (attrs.prev ?: '<i class="fa fa-angle-left"></i>')
            } + "</li>"
        }

        // display steps when steps are enabled and laststep is not firststep
        if (steps && laststep > firststep) {
            //linkTagAttrs.class = 'step'

            // determine begin and endstep paging variables
            int beginstep = currentstep - Math.round(maxsteps / 2) + (maxsteps % 2)
            int endstep = currentstep + Math.round(maxsteps / 2) - 1

            if (beginstep < firststep) {
                beginstep = firststep
                endstep = maxsteps
            }
            if (endstep > laststep) {
                beginstep = laststep - maxsteps + 1
                if (beginstep < firststep) {
                    beginstep = firststep
                }
                endstep = laststep
            }

            // display firststep link when beginstep is not firststep
            if (beginstep > firststep) {
                linkParams.offset = 0
                out << '<li>'
                out << link(linkTagAttrs.clone()) {firststep.toString()}
                out << '</li>'
                out << '<li class="disabled"><span>..</span></span>'
            }

            // display paginate steps
            (beginstep..endstep).each { i ->
                if (currentstep == i) {
                    out << "<li class=\"active\"><a href=\"#\">${i}</a></li>"
                }
                else {
                    linkParams.offset = (i - 1) * max
                    out << "<li>" + link(linkTagAttrs.clone()) {i.toString()} + "</li>"
                }
            }

            // display laststep link when endstep is not laststep
            if (endstep < laststep) {
                out << '<li class="disabled"><span>..</span></li>'
                linkParams.offset = (laststep -1) * max
                out << '<li>'
                out << link(linkTagAttrs.clone()) { laststep.toString() }
                out << '</li>'
            }
        }

        // display next link when not on laststep
        if (currentstep < laststep) {
            //linkTagAttrs.class = 'nextLink'
            linkParams.offset = offset + max
            out << "<li>" + link(linkTagAttrs.clone()) {
                (attrs.next ?: '<i class="fa fa-angle-right"></i>')
            } + "</li>"
        }
    }
    
    def notification = {attrs ->
        if (springSecurityService.loggedIn) {
            Locale requestLocale = RequestContextUtils.getLocale(request)
            
            def notificationCount = Notification.createCriteria().get {
                projections {
                    rowCount()
                }
                eq("user", springSecurityService.currentUser)
            }
            
            if (notificationCount > 0) {
                MarkupBuilder markupBuilder = new MarkupBuilder(out)
                markupBuilder.li() {
                    a('href': g.createLink(controller: 'notification', action: 'list')) {
                        span() {
                            i('class': 'fa fa-fw fa-bell') {
                                markupBuilder.yield('', true)
                            }
                            markupBuilder.yield(" ${notificationCount}", true)
                        }
                    }
                }
            }
        }
    }
    
    def currentLanguage = {attrs ->
        Locale requestLocale = RequestContextUtils.getLocale(request)
        out << requestLocale.language
    }

    def localeText = {attrs ->
        if (attrs.text != null) {
            if (attrs.text.startsWith('[') && attrs.text.endsWith(']') && attrs.text.contains('|')) {
                try {
                    Locale requestLocale = RequestContextUtils.getLocale(request)
                    def languageText = [:]
                    attrs.text.substring(1, attrs.text.size() - 1).split("\\|").each {item ->
                        def (language, text) = item.split(":")
                        languageText[language] = text
                    }
                    if (languageText[requestLocale.language]) {
                        out << languageText[requestLocale.language]
                    }
                    else if (languageText['default']) {
                        out << languageText['default']
                    }
                    else {
                        out << attrs.text
                    }
                }
                catch (e) {
                    out << attrs.text
                }
            }
            else {
                out << attrs.text
            }
        }
    }
    
    /**
     * Renders a locale selector.
     * Adds the class <code>active</code> to the list-element of the current language.
     */
    def localeSelector = {attrs ->
        Locale requestLocale = RequestContextUtils.getLocale(request)
        Locale candidateLocale = Locale.ENGLISH
        if (requestLocale.language == Locale.ENGLISH.language) {
            candidateLocale = Locale.CHINESE
        }
        
        def params = attrs.params
        params.lang = candidateLocale.language
        MarkupBuilder markupBuilder = new MarkupBuilder(out)
        markupBuilder.a('href': createLink(controller: controllerName, action: actionName, params: params)) {
            span() {
                i('class': 'fa fa-fw fa-language') {
                    markupBuilder.yield('', true)
                }
                markupBuilder.yield(' ' + candidateLocale.getDisplayLanguage(candidateLocale), true)
            }
        }
    }
    
    def readableFileSize = { attrs ->
        if (attrs.size == null) {
            throwTagError("Tag [readableFileSize] is missing required attribute [size]")
        }
        
        out << formatFileSize(attrs.size)
    }
    
    private String formatFileSize(long size) {
        if(size <= 0) {
            return "0"
        }
        final units = ["B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB" ]
        int digitGroups = (int) (Math.log10(size)/Math.log10(1000))
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1000, digitGroups)) + " " + units[digitGroups];
    }
}
