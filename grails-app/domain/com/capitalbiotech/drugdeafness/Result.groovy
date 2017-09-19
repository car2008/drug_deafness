package com.capitalbiotech.drugdeafness

class Result {
	//位置	样品编号	样品类型	FAMCt	VICCt	NEDCt	检测结果	备注
	String sampleNum
	String location
	String sampleBelong
	String famCt
	String vicCt
	String nedCt
	String detectedResult
	String comment
	
    static constraints = {
		 sampleNum blank: false,nullable: false
		 location blank: true,nullable: true
		 sampleBelong blank: true,nullable: true
		 famCt blank: true,nullable: true
		 vicCt blank: true,nullable: true
		 nedCt blank: true,nullable: true
		 detectedResult blank: true,nullable: true
		 comment blank: true,nullable: true
    }
	
	static mapping = {
		detectedResult type:'text'
		comment type:'text'
	}
	
	static belongsTo = Information
}
