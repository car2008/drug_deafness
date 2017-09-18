package com.capitalbiotech.drugdeafness

class Notification {
    String  id
    User    user
    Integer sum = 0
    Date    dateCreated
    Date    lastUpdated

    static constraints = {
    }
    
    static mapping = {
        id generator: 'uuid2'
    }
}
