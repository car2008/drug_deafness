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
	Information information
	//生成pdf时需要的信息（可手动填写）： 报告图片路径， 医院，检验员，审核员，pdf备注
	String resultpictureUrl
	String resulttitle
	String checker
	String assessor
	String pdfcomment
	Date dateCreated
	Date lastUpdated
	District district
	
    static constraints = {
		 sampleNum blank: false,nullable: false
		 district blank: true,nullable: true
		 location blank: true,nullable: true
		 sampleBelong blank: true,nullable: true
		 famCt blank: true,nullable: true
		 vicCt blank: true,nullable: true
		 nedCt blank: true,nullable: true
		 detectedResult blank: true,nullable: true
		 comment blank: true,nullable: true
		 information blank: true,nullable: true
		 resultpictureUrl blank: true,nullable: true
		 resulttitle blank: true,nullable: true
		 checker blank: true,nullable: true
		 assessor blank: true,nullable: true
		 pdfcomment blank: true,nullable: true
    }
	
	static mapping = {
		detectedResult type:'text'
		comment type:'text'
	}
	
	static belongsTo = [information:Information,district:District]
}
