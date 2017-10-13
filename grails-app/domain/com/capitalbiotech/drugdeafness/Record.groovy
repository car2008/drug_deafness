package com.capitalbiotech.drugdeafness

class Record {
	/**
	 * 上传人员，上传信息，上传种类（信息或者结果），上传开始时间/结束时间，成功记录数，失败记录数，备注
	 */
	User uploadUser
	String recordName
	String recordCatagrory
	String recordId
	Date startTime
	Date endTime
	Integer successNum
	Integer failedNum
	String successedSample
	String failedSample
	String recordLog
	String comment
	Date dateCreated
	Date lastUpdated
	
	public static final String CATAGRORY_RESULT = "CATAGRORY_RESULT"
	public static final String CATAGRORY_INFORMATION = "CATAGRORY_INFORMATION"

    static constraints = {
		recordCatagrory inList: [
			CATAGRORY_RESULT,
			CATAGRORY_INFORMATION,
		]
		uploadUser blank: true,nullable: true
		recordName blank: true,nullable: true
		recordId blank: true,nullable: true
		startTime blank: true,nullable: true
		endTime blank: true,nullable: true
		successNum blank: true,nullable: true
		failedNum blank: true,nullable: true
		recordLog blank: true,nullable: true
		comment  blank: true,nullable: true
		successedSample blank: true,nullable: true
		failedSample blank: true,nullable: true
    }
	
	static mapping = {
		recordLog type:'text'
		comment  type:'text'
		successedSample  type:'text'
		failedSample  type:'text'
	}
	
}
