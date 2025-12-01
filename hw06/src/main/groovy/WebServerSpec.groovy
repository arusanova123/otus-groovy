import ServerConfig
import MappingConfig

class WebServerSpec {
    String name, description, port, secure, url, active
    Map http = [:], https = [:]
    List mappings
    void http (Closure closure) {
        def rehydrate = closure.rehydrate(this, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        http.port = port
        http.secure = secure
    }

    void https (Closure closure) {
        def rehydrate = closure.rehydrate(this, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        https.port = port
        https.secure = secure
    }

    Map doMapClosure (Closure closure) {
        def rehydrate = closure.rehydrate(this, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        return ['url':this.url, 'active':this.active]
    }

    static List webServer (@DelegatesTo (WebServerSpec) Closure closure) {
        def webServer = new WebServerSpec()
        def rehydrate = closure.rehydrate(webServer, this, this)
        rehydrate.setResolveStrategy(Closure.DELEGATE_ONLY)
        rehydrate ()
        webServer.with closure
        webServer.build()
    }

    List build () {
        ServerConfig serverConfig = new ServerConfig()
        MappingConfig mappingConfig = new MappingConfig()
        serverConfig.name = this.name
        serverConfig.description = this.description
        serverConfig.http = this.http
        serverConfig.https = this.https
        mappingConfig.mappings = this.mappings.collect {Closure cl -> doMapClosure(cl)}
        [serverConfig, mappingConfig]
    }
}
