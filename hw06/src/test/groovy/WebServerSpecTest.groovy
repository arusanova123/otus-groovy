import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import ServerConfig
import MappingConfig
import static WebServerSpec.webServer

class WebServerSpecTest {
    @Test
    void TestWebServerSpec () {
        String res = webServer {
            name = "MyTest"
            description = "Apache Tomcat"

            http {
                port = 8080
                secure = false
            }

            https {
                port = 4443
                secure = true
            }

            mappings = [
                    {
                        url = "/"
                        active = true
                    },
                    {
                        url = "/login"
                        active = false
                    }
            ]
        }
        String expectedResult = "[ServerConfig(MyTest, Apache Tomcat, [port:8080, secure:false], [port:4443, secure:true])," +
                                " MappingConfig([[url:/, active:true], [url:/login, active:false]])]"
        Assertions.assertEquals(expectedResult, res)
    }
}
