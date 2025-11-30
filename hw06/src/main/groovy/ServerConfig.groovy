import groovy.transform.ToString

@ToString
class ServerConfig {
    String name
    String description
    Map http = [:], https = [:]
}
