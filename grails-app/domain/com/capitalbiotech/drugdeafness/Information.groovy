package com.capitalbiotech.drugdeafness

import java.util.Date;
import grails.plugin.springsecurity.annotation.Secured

class Information {
	//样本编号	姓名	性别	年龄	医院/单位	门诊号/住院号	病房/床位	送检科室	送检医生	送检样本	送检时间	联系电话	备注
	String sampleNum
	String patientName
	boolean gender
	Integer age
	String patientNum
	String wardBed
	String inspectionDoctor
	String inspectionSample
	Date inspectionTime
	String phoneNum
	String remark
	Set<Result> results
	
    static constraints = {
		sampleNum blank: false,nullable: false
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
		results blank: true,nullable: true
    }
	
	static mapping = {
		remark type:'text'
	}
	
	static hasMany = [results: Result]

}
