package com.capitalbiotech.health
import java.text.SimpleDateFormat
class UpdateSnpTopicJob {
    static triggers = {//毫秒为单位
		//cron name: 'updateSnpTopicTrigger', cronExpression: "0 14 11 * * ?"
		//simple name: 'updateRemaindingDayBeforDueTrigger', startDelay: 60000, repeatInterval: 60000
    }
	def grailsApplication
    def concurrent = false
    def group = "default"
    def description = "Update SnpTopic"
    def execute(){
        log.debug("Updating SnpTopic...")
		println "updading................."
        SnpTopic.findAll("from SnpTopic as st where st.id>115290").each { snpTopicInstance ->
			println snpTopicInstance.id
			if(snpTopicInstance.snpId == "" || snpTopicInstance.snpId == null){
				snpTopicInstance.snpId = snpTopicInstance.snpEdia.rsid
			}
			if(snpTopicInstance.topicName == ""|| snpTopicInstance.topicName == null){
				snpTopicInstance.topicName = snpTopicInstance.topic.name
			}
			if(!snpTopicInstance.save(flush: true)){
				snpTopicInstance.errors.allErrors.each{
					println snpTopicInstance.id+":"+it
				}
			}
        }
    }
}
