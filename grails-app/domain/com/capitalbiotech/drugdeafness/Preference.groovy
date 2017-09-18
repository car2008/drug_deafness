package com.capitalbiotech.drugdeafness

import java.util.Date;

class Preference {
    public static final String TYPE_SYSTEM = "SYSTEM"
    public static final String TYPE_USER   = "USER"
    
    public static final String VALUE_TYPE_STRING             = "STRING"
    public static final String VALUE_TYPE_INTEGER            = "INTEGER"
    public static final String VALUE_TYPE_FLOAT              = "FLOAT"
    public static final String VALUE_TYPE_SINGLE_SELECTION   = "SINGLE_SELECTION"
    public static final String VALUE_TYPE_MULTIPLE_SELECTION = "MULTIPLE_SELECTION"
    
    public static final String CATEGORY_SYSTEM       = "A_SYSTEM"
    public static final String CATEGORY_GENERAL      = "B_GENERAL"
    public static final String CATEGORY_NOTIFICATION = "C_NOTIFICATION"
    public static final String CATEGORY_DEVELOPMENT  = "D_DEVELOPMENT"
    
    String  id
	String  key
	String  value
    String  type      = TYPE_SYSTEM
    String  valueType = VALUE_TYPE_STRING
    String  category  = CATEGORY_GENERAL
    String  option
    Boolean editable  = true
    Integer rank = 0
    Date    dateCreated
    Date    lastUpdated
    
    String[] getValues() {
        return value?.split(";")
    }
    
    String[] getOptions() {
        return option?.split(";")
    }
    
    UserPreference getUserPreference(User user) {
        if (type != TYPE_USER) {
            throw new Exception("Only Preference with type ${TYPE_USER} can return a UserPreference object.")
        }
        def userPreference = UserPreference.findByPreferenceAndUser(this, user)
        if (!userPreference) {
            userPreference = new UserPreference(preference: this, user: user, value: this.value)
            userPreference.save(flush: true)
        }
        
        return userPreference
    }

	static constraints = {
		key       nullable: false, blank: false, unique: true
        value     nullable: true,  blank: true
        type      nullable: false, inList: [TYPE_SYSTEM, TYPE_USER]
        valueType nullable: false, inList: [VALUE_TYPE_STRING, VALUE_TYPE_INTEGER, VALUE_TYPE_FLOAT, VALUE_TYPE_SINGLE_SELECTION, VALUE_TYPE_MULTIPLE_SELECTION]
        category  nullable: false, inList: [CATEGORY_SYSTEM, CATEGORY_GENERAL, CATEGORY_NOTIFICATION, CATEGORY_DEVELOPMENT]
        option    nullable: true,  blank: true
        rank      nullable: false, unique: true
	}
	
	static mapping = {
        id     generator: 'uuid2'
		key    column: '`preference_key`'
	    value  column: '`preference_value`',  type:'text'
	    option column: '`preference_option`', type:'text'
	}
}
