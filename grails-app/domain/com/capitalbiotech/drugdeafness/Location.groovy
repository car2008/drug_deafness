package com.capitalbiotech.drugdeafness

class Location {

    String code
	String title
	String description
	Date dateCreated
	Date lastUpdated
	Set<User> users
	Set<Result> results
	
	static constraints = {
		code blank: false, unique: true
		title blank: false, unique: true
		description nullable: true
	}
	
	static hasMany = [users: User,results: Result]
	
	static mapping = {
		description type:'text'
	}

}
