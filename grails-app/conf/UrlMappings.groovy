class UrlMappings {

    static mappings = {
        
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                
            }
        }
		"/"(controller: "information", action: "index")
    }
}
