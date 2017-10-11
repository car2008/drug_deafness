package com.capitalbiotech.drugdeafness

class District {

    String code
	String title
	String description
	Date dateCreated
	Date lastUpdated
	Set<User> users
	Set<Information> informations
	
	static constraints = {
		code blank: false, unique: true
		title blank: false, unique: true
		description nullable: true
	}
	
	static hasMany = [users: User,informations: Information]
	
	static mapping = {
		description type:'text'
	}

}
