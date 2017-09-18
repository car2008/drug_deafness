grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"

grails.project.fork = [
    test:    [maxMemory: 2048, minMemory: 64, debug: false, maxPerm: 512, daemon:true],
    run:     [maxMemory: 2048, minMemory: 64, debug: false, maxPerm: 512, forkReserve:false],
    war:     [maxMemory: 2048, minMemory: 64, debug: false, maxPerm: 512, forkReserve:false],
    console: [maxMemory: 2048, minMemory: 64, debug: false, maxPerm: 512]
]

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    inherits("global") {
        
    }
    
    log "error"
    checksums true
    legacyResolve false
    
    repositories {
        inherits true
        
        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        
        mavenRepo "https://repo.grails.org/grails/plugins"
        mavenRepo "http://snapshots.repository.codehaus.org"
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "https://repo1.maven.org/maven2"
        mavenRepo "http://mvnrepository.com"
    }

    dependencies {
        runtime 'mysql:mysql-connector-java:5.1.29'
        runtime 'commons-io:commons-io:2.4'
        runtime 'commons-codec:commons-codec:1.10'
        runtime 'net.coobird:thumbnailator:0.4.8'
    }

    plugins {
        build ":tomcat:7.0.55.3"

        compile ":hibernate4:5.0.8.RELEASE"
        compile ":spring-security-core:2.0.0"
        compile ":quartz:1.0.2"
        compile ":mail:1.0.7"
        compile ":cookie:1.4"
        compile ":rest:0.8"
        compile ":executor:0.3"
        //compile ":ziplet:0.3"
        //compile ":localizations:2.4"
        compile ":asset-pipeline:2.10.1"
        
        runtime ":resources:1.2.14"
    }
}
