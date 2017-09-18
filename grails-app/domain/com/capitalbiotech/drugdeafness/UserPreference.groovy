package com.capitalbiotech.drugdeafness

import java.util.Date;

class UserPreference {
    String     id
	Preference preference
    String     value
    User       user
    Date       dateCreated
    Date       lastUpdated
    
    String[] getValues() {
        return value?.split(";")
    }
    
	static constraints = {
	    preference nullable: false
	    user       nullable: false
	    value:     nullable: false
	}
	
	static mapping = {
        id    generator: 'uuid2'
		value column: '`preference_value`', type:'text'
	}
}
