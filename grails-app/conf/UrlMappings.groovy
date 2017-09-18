class UrlMappings {

    static mappings = {
        "/android-chrome-144x144.png"(uri: "/static/images/favicons/android-chrome-144x144.png")
        "/android-chrome-192x192.png"(uri: "/static/images/favicons/android-chrome-192x192.png")
        "/android-chrome-256x256.png"(uri: "/static/images/favicons/android-chrome-256x256.png")
        "/android-chrome-36x36.png"(uri: "/static/images/favicons/android-chrome-36x36.png")
        "/android-chrome-48x48.png"(uri: "/static/images/favicons/android-chrome-48x48.png")
        "/android-chrome-72x72.png"(uri: "/static/images/favicons/android-chrome-72x72.png")
        "/android-chrome-96x96.png"(uri: "/static/images/favicons/android-chrome-96x96.png")
        "/apple-touch-icon-114x114-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-114x114-precomposed.png")
        "/apple-touch-icon-114x114.png"(uri: "/static/images/favicons/apple-touch-icon-114x114.png")
        "/apple-touch-icon-120x120-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-120x120-precomposed.png")
        "/apple-touch-icon-120x120.png"(uri: "/static/images/favicons/apple-touch-icon-120x120.png")
        "/apple-touch-icon-144x144-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-144x144-precomposed.png")
        "/apple-touch-icon-144x144.png"(uri: "/static/images/favicons/apple-touch-icon-144x144.png")
        "/apple-touch-icon-152x152-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-152x152-precomposed.png")
        "/apple-touch-icon-152x152.png"(uri: "/static/images/favicons/apple-touch-icon-152x152.png")
        "/apple-touch-icon-180x180-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-180x180-precomposed.png")
        "/apple-touch-icon-180x180.png"(uri: "/static/images/favicons/apple-touch-icon-180x180.png")
        "/apple-touch-icon-57x57-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-57x57-precomposed.png")
        "/apple-touch-icon-57x57.png"(uri: "/static/images/favicons/apple-touch-icon-57x57.png")
        "/apple-touch-icon-60x60-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-60x60-precomposed.png")
        "/apple-touch-icon-60x60.png"(uri: "/static/images/favicons/apple-touch-icon-60x60.png")
        "/apple-touch-icon-72x72-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-72x72-precomposed.png")
        "/apple-touch-icon-72x72.png"(uri: "/static/images/favicons/apple-touch-icon-72x72.png")
        "/apple-touch-icon-76x76-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-76x76-precomposed.png")
        "/apple-touch-icon-76x76.png"(uri: "/static/images/favicons/apple-touch-icon-76x76.png")
        "/apple-touch-icon-precomposed.png"(uri: "/static/images/favicons/apple-touch-icon-precomposed.png")
        "/apple-touch-icon.png"(uri: "/static/images/favicons/apple-touch-icon.png")
        "/browserconfig.xml"(uri: "/static/images/favicons/browserconfig.xml")
        "/favicon-16x16.png"(uri: "/static/images/favicons/favicon-16x16.png")
        "/favicon-32x32.png"(uri: "/static/images/favicons/favicon-32x32.png")
        "/favicon.ico"(uri: "/static/images/favicons/favicon.ico")
        "/manifest.json"(uri: "/static/images/favicons/manifest.json")
        "/mstile-144x144.png"(uri: "/static/images/favicons/mstile-144x144.png")
        "/mstile-150x150.png"(uri: "/static/images/favicons/mstile-150x150.png")
        "/mstile-310x150.png"(uri: "/static/images/favicons/mstile-310x150.png")
        "/mstile-310x310.png"(uri: "/static/images/favicons/mstile-310x310.png")
        "/mstile-70x70.png"(uri: "/static/images/favicons/mstile-70x70.png")
        "/safari-pinned-tab.svg"(uri: "/static/images/favicons/safari-pinned-tab.svg")
        
        "/"(controller: "dashboard", action: "index")
        "500"(controller: "error", action: "internalServerError")
        "404"(controller: "error", action: "notFound")
        
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                
            }
        }
    }
}
