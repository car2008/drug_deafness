package com.capitalbiotech.drugdeafness

class Record {
	User uploadUser
	String recordName
	String recordId
	Date startTime
	Date endTime
	Integer successNum
	Integer failedNum
	String recordLog
	String comment
	
    static constraints = {
		uploadUser blank: true,nullable: true
		recordName blank: true,nullable: true
		recordId blank: true,nullable: true
		startTime blank: true,nullable: true
		endTime blank: true,nullable: true
		successNum blank: true,nullable: true
		failedNum blank: true,nullable: true
		recordLog blank: true,nullable: true
		comment  blank: true,nullable: true
    }
	
	static mapping = {
		recordLog type:'text'
		comment  type:'text'
	}
	
}
