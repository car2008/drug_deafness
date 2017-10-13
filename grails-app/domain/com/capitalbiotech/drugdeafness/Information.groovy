package com.capitalbiotech.drugdeafness

import java.util.Date;
import grails.plugin.springsecurity.annotation.Secured

class Information {
	//样本编号	姓名	性别	年龄	医院/单位	门诊号/住院号	病房/床位	送检科室	送检医生	送检样本	送检时间	联系电话	备注
	String sampleNum
	String patientName
	String gender
	String age
	String hospital
	String patientNum
	String wardBed
	String inspectionDepartment
	String inspectionDoctor
	String inspectionSample
	String inspectionTime
	String phoneNum
	String remark
	Result result
	Date dateCreated
	Date lastUpdated
	District district
	boolean hasResult
	
    static constraints = {
		sampleNum blank: true,nullable: true
		patientName blank: true,nullable: true
		gender blank: true,nullable: true
		age blank: true,nullable: true
		patientNum blank: true,nullable: true
		wardBed blank: true,nullable: true
		inspectionDoctor blank: true,nullable: true
		inspectionSample blank: true,nullable: true
		inspectionTime blank: true,nullable: true
		phoneNum blank: true,nullable: true
		remark blank: true,nullable: true
		result blank: true,nullable: true
		hospital blank: true,nullable: true
		inspectionDepartment blank: true,nullable: true
		district blank: true,nullable: true
		hasResult blank: true,nullable: true
    }
	
	static belongsTo =[district:District]
	
	static mapping = {
		remark type:'text'
	}

}
