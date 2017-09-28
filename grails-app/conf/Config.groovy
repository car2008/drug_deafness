grails.project.groupId = appName
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']

grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    js:            'text/javascript',
    rss:           'application/rss+xml',
    text:          'text/plain',
    multipartForm: 'multipart/form-data',
    html:          ['text/html','application/xhtml+xml'],
    json:          ['application/json', 'text/json'],
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

grails.resources.adhoc.patterns = [
    '/images/*',
    '/css/*',
    '/js/*'
]

grails.resources.adhoc.includes = [
    '/images/**',
    '/css/**',
    '/js/**'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding        = 'UTF-8'
            htmlcodec       = 'xml'  // use xml escaping instead of HTML4 escaping
            codecs {
                expression  = 'html' // escapes values inside ${}
                scriptlet   = 'html' // escapes output from scriptlets in GSPs
                taglib      = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}

grails.converters.encoding                = "UTF-8"
grails.scaffolding.templates.domainSuffix = 'Instance'   // scaffolding templates configuration
grails.json.legacy.builder                = false        // Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.enable.native2ascii                = true         // enabled native2ascii conversion of i18n properties files
grails.spring.bean.packages               = []           // packages to include in Spring bean scanning
grails.web.disable.multipart              = false        // whether to disable processing of multi part requests
grails.exceptionresolver.params.exclude   = ['password'] // request parameters to mask when logging exceptions
grails.hibernate.cache.queries            = false        // configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.pass.readonly            = false        // configure passing transaction's read-only attribute to Hibernate session, queries and criterias. set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.osiv.readonly            = false        // configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web',
           'org.codehaus.groovy.grails.web.mapping',
           'org.codehaus.groovy.grails.commons',
           'org.codehaus.groovy.grails.plugins',
           'org.codehaus.groovy.grails.orm.hibernate',
           'grails.spring',
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
           
    info   'grails.app.conf',
           'grails.app.filters',
           'grails.app.taglib',
           'grails.app.services',
           'grails.app.controllers',
           'grails.app.domain',
           'grails.app.jobs'

    debug  'com.capitalbiotech.drugdeafness'
}

grails.plugin.springsecurity.rejectIfNoRule                    = false
grails.plugin.springsecurity.fii.rejectPublicInvocations       = false
grails.plugin.springsecurity.userLookup.userDomainClassName    = 'com.capitalbiotech.drugdeafness.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.capitalbiotech.drugdeafness.UserRole'
grails.plugin.springsecurity.authority.className               = 'com.capitalbiotech.drugdeafness.Role'
grails.plugin.springsecurity.rememberMe.alwaysRemember         = false
grails.plugin.springsecurity.rememberMe.tokenValiditySeconds   = 1209600 //14 days
grails.plugin.springsecurity.rememberMe.cookieName             = 'com.capitalbiotech.drugdeafness.remember_me_cookie'
grails.plugin.springsecurity.rememberMe.key                    = 'com.capitalbiotech.drugdeafness.remember_me'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    '/':                  ['permitAll'],
    '/assets/**':         ['permitAll'],
    '/**/js/**':          ['permitAll'],
    '/**/css/**':         ['permitAll'],
    '/**/images/**':      ['permitAll'],
    '/**/fonts/**':       ['permitAll']
]

grails.mail.host         = "mail.capitalbiotech.com"
grails.mail.port         = 25
grails.mail.username     = 'bioinfo@capitalbiotech.com'
grails.mail.password     = 'jd#bioinfo#2015'
grails.mail.props        = ['mail.smtp.auth':'true',"mail.smtp.socketFactory.port":"25"]
grails.mail.default.from = '"CBT Bioinformatics"<bioinfo@capitalbiotech.com>'

grails.assets.bundle     = true

cbt_health.user.password.reset.email.expire       = 24 * 60 * 60
        
quartz {
    autoStartup = true
    jdbcStore = false
    
}

//set per-environment serverURL stem for creating absolute links
environments {
    development {
        cbt_health.dev_mode                     = true
        
        grails.logging.jul.usebridge           = true
        grails.converters.default.pretty.print = true
        cbt_health.dir.root                    = "/work/cbt_health/data"
        grails.gsp.enable.reload               = true
		project.file.upload.path               ="D:\\shiyanshuju\\bpms-project-upload"
		project.file.pdf.path                  ="D:/shiyanshuju/pdf"
    }
    production {
        cbt_health.dev_mode                     = false
        
        grails.logging.jul.usebridge           = false
        grails.converters.default.pretty.print = false
        grails.serverURL                       = "http://cloud.capitalbiotech.com/drug_deafness"
        grails.gsp.enable.reload               = true
		project.file.upload.path               ="/data"
		project.file.pdf.path                  ="/data\\pdf"
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.capitalbiotech.drugdeafness.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.capitalbiotech.drugdeafness.UserRole'
grails.plugin.springsecurity.authority.className = 'com.capitalbiotech.drugdeafness.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                ['permitAll'],
	'/index':           ['permitAll'],
	'/index.gsp':       ['permitAll'],
	'/assets/**':       ['permitAll'],
	'/**/js/**':        ['permitAll'],
	'/**/css/**':       ['permitAll'],
	'/**/images/**':    ['permitAll'],
	'/**/favicon.ico':  ['permitAll']
]

