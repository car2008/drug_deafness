dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
}
hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    jdbc.batch_size = 100
    cache.provider_class = "net.sf.ehcache.hibernate.EhCacheProvider"
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://127.0.0.1:3306/${appName}_dev?characterEncoding=utf8&autoReconnect=true"
            username = "root"
            password = "root"
            logSql = false
			pooled = true
            properties {
                maxActive = 100
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                maxWait = 100000
                //run the evictor every 30 minutes and evict any connections older than 30 minutes.
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                //test the connection while its idle, before borrow and return it
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://192.168.100.23:3306/${appName}?characterEncoding=utf8&autoReconnect=true"
            username = "root"
            password = ""
            pooled = true
            properties {
                maxActive = 100
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                maxWait = 100000
                //run the evictor every 30 minutes and evict any connections older than 30 minutes.
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                //test the connection while its idle, before borrow and return it
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
    }
}