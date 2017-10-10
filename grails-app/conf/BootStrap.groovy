import java.util.UUID

import javax.script.ScriptEngine
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager

import grails.plugin.springsecurity.SpringSecurityUtils
import com.capitalbiotech.drugdeafness.Role
import com.capitalbiotech.drugdeafness.User
import com.capitalbiotech.drugdeafness.Utils;
import com.capitalbiotech.drugdeafness.UserRole;

class BootStrap {
    def springSecurityService
    def utilsService
    def grailsApplication

    def init = { servletContext ->
        //we save time always in Asia/Shanghai timezone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))
    
        if (0 == User.count()) {
            initializeDatabase()
			//createUsersAndRoles()
        }
        
        updateDatabase()
    }
    
    def destroy = {
    
    }
    
    def updateDatabase() {
        /*def systemVersionPreference = Preference.findByKey("SYSTEM_VERSION")
        def systemVersion = systemVersionPreference["value"] as int
        
        if (systemVersion == 0) {
            //systemVersionPreference.value = 1
            //systemVersionPreference.save(flush: true)
        }*/
    }
    
    def initializeDatabase() {
        
        //users and roles
        def userRole  = new Role(authority:'ROLE_USER').save(flush: true)
        def staffRole = new Role(authority:'ROLE_STAFF').save(flush: true)
        def adminRole = new Role(authority:'ROLE_ADMIN').save(flush: true)
        
        def admin = new User(
            username: 'bioinfo@capitalbiotech.com',
            password: '123456',
            name: 'admin'
        ).save(flush: true)
        UserRole.create(admin, userRole, true)
        UserRole.create(admin, staffRole, true)
        UserRole.create(admin, adminRole, true)
        
        def user1 = new User(
            username: 'jundong@capitalbiotech.com',
            password: '123456',
            name: 'user'
        ).save(flush: true)
        UserRole.create(user1, userRole, true)
		UserRole.create(user1, staffRole, true)
		UserRole.create(user1, adminRole, true)
    }
	
	def createUsersAndRoles(){
		log.info "Creating default users and roles"
		
		def authorityRoleMap = [:]
		def propertiesList = readRawFile("roles")
		propertiesList?.each { properties ->
			def roleInstance = new Role(properties)
			if (roleInstance.hasErrors() || !roleInstance.save(flush: true)) {
				roleInstance.errors?.each { error ->
					log.error error
				}
			}
			
			authorityRoleMap[(roleInstance?.authority)] = roleInstance
		}
		
		propertiesList = readRawFile("users")
		propertiesList?.each { properties ->
			properties['password'] = springSecurityService.encodePassword(properties['password'])
			
			def userInstance = new User(properties)
			if (userInstance.hasErrors() || !userInstance.save(flush: true)) {
				userInstance.errors?.each { error ->
					log.error error
				}
			}
			
			properties['roles'].split(",")?.each { authority ->
				if (authorityRoleMap[authority] != null) {
					UserRole.create(userInstance, authorityRoleMap[authority], true)
				}
				else {
					log.error "Role not found: ${authority}"
				}
			}
		}
	}
	
	def readRawFile(fileName) {
		def rawLines = new File(BootStrap.class.getResource("raw/${fileName}").getFile()).readLines()
		if (rawLines.size() < 2) {
			return
		}
		
		def propertiesList = []
		def keys = rawLines.get(0).split("\t")
		def values = null
		def line = null
		for (int i = 1; i < rawLines.size(); i++) {
			def properties = [:]
			
			line = rawLines.get(i)
			if (Utils.isEmpty(line)) {
				continue
			}
			
			values = line.split("\t")
			for (int j = 0; j < values.size(); j++) {
				properties[(keys[j])] = values[j]
			}
			
			propertiesList << properties
		}
		
		return propertiesList
	}
	
}
