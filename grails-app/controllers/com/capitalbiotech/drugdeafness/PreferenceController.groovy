package com.capitalbiotech.drugdeafness

import com.capitalbiotech.drugdeafness.Preference;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class PreferenceController {
    def utilsService
    
    def index() {
        redirect(action: "edit", params: params)
    }
    
    def edit() {
        def categories = Preference.executeQuery("SELECT DISTINCT(category) FROM Preference preference ORDER BY category ASC")
        def preferences = Preference.list(['sort': 'rank', 'order': 'asc'])
        def categorizedPreferences = [:]
        categories.each {category ->
            categorizedPreferences[category] = []
        }
        preferences.each {preference ->
            categorizedPreferences[(preference.category)] << preference
        }
        return [
            categories: categories,
            categorizedPreferences: categorizedPreferences
        ]
    }
    
    def update() {
        def preferences = Preference.list()
        preferences.each {preference ->
            if (preference.valueType == Preference.VALUE_TYPE_MULTIPLE_SELECTION) {
                preference.value = params.list(preference.key)?.join(";")
            }
            else {
                preference.value = params[preference.key]
            }
            preference.save(flush: true)
        }
        
        flash.message = message(code: 'default.updated.no.name.message', args: [message(code: 'preference.label')])
        redirect(action: 'edit')
    }
}
