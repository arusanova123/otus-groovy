import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.example.EmployeeBuilder

class TestEmployeeBuilder {
    @Test
    void testHtmlFromUrl () {
        String jsonUrl = 'https://raw.githubusercontent.com/Groovy-Developer/groovy-homeworks/refs/heads/main/hw-5/test.json'
        String inputStr = jsonUrl.toURL().text

        def empBld = new EmployeeBuilder(inputStr)
        def htmlStr = empBld.htmlEmployee()

        String expectedStr = "<html>\n" +
                "  <div>\n" +
                "    <div id='employee'>\n" +
                "      <p>Пупкин Морква Свеклович</p>\n" +
                "      <br />\n" +
                "      <p>22</p>\n" +
                "      <br />\n" +
                "      <p>322-223</p>\n" +
                "      <br />\n" +
                "      <ul id='powers'>\n" +
                "        <li>100</li>\n" +
                "        <li>50</li>\n" +
                "        <li>70</li>\n" +
                "      </ul>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</html>"

        Assertions.assertEquals(expectedStr, htmlStr)
    }

    @Test
    void testXmlFromUrl () {
        String jsonUrl = 'https://raw.githubusercontent.com/Groovy-Developer/groovy-homeworks/refs/heads/main/hw-5/test.json'
        String inputStr = jsonUrl.toURL().text
        def empBld = new EmployeeBuilder(inputStr)
        def xmlStr = empBld.xmlEmployee()

        String expectedStr = "<employees>\n" +
        "  <employee>\n" +
        "    <name>Пупкин Морква Свеклович</name>\n" +
        "    <age>22</age>\n" +
        "    <secretIdentity>322-223</secretIdentity>\n" +
        "    <powers>\n" +
        "      <power>100</power>\n" +
        "      <power>50</power>\n" +
        "      <power>70</power>\n" +
        "    </powers>\n" +
        "  </employee>\n" +
        "</employees>"

        Assertions.assertEquals(expectedStr, xmlStr)
    }

    @Test
    void testHtmlList () {
        String inputStr = """
            [{
                "name": "Пупкин Морква Свеклович",
                "age": 22,
                "secretIdentity": "322-223",
                "powers": [100, 50, 70]
            },
            {
                "name": "Иванов Иван Иванович",
                "age": 25,
                "secretIdentity": "322-224",
                "powers": [10, 90]
            }]
        """
        def empBld = new EmployeeBuilder (inputStr)
        def htmlStr = empBld.htmlEmployee()

        String expectedStr = "<html>\n" +
                "  <div>\n" +
                "    <div id='employee'>\n" +
                "      <p>Пупкин Морква Свеклович</p>\n" +
                "      <br />\n" +
                "      <p>22</p>\n" +
                "      <br />\n" +
                "      <p>322-223</p>\n" +
                "      <br />\n" +
                "      <ul id='powers'>\n" +
                "        <li>100</li>\n" +
                "        <li>50</li>\n" +
                "        <li>70</li>\n" +
                "      </ul>\n" +
                "    </div>\n" +
                "    <div id='employee'>\n" +
                "      <p>Иванов Иван Иванович</p>\n" +
                "      <br />\n" +
                "      <p>25</p>\n" +
                "      <br />\n" +
                "      <p>322-224</p>\n" +
                "      <br />\n" +
                "      <ul id='powers'>\n" +
                "        <li>10</li>\n" +
                "        <li>90</li>\n" +
                "      </ul>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</html>"
        Assertions.assertEquals(expectedStr, htmlStr)
    }

    @Test
    void testXmlList () {
        String inputStr = """
            [{
                "name": "Пупкин Морква Свеклович",
                "age": 22,
                "secretIdentity": "322-223",
                "powers": [100, 50, 70]
            },
            {
                "name": "Иванов Иван Иванович",
                "age": 25,
                "secretIdentity": "322-224",
                "powers": [10, 90]
            }]
        """
        def empBld = new EmployeeBuilder (inputStr)
        def xmlStr = empBld.xmlEmployee()
        String expectedStr = "<employees>\n" +
                "  <employee>\n" +
                "    <name>Пупкин Морква Свеклович</name>\n" +
                "    <age>22</age>\n" +
                "    <secretIdentity>322-223</secretIdentity>\n" +
                "    <powers>\n" +
                "      <power>100</power>\n" +
                "      <power>50</power>\n" +
                "      <power>70</power>\n" +
                "    </powers>\n" +
                "  </employee>\n" +
                "  <employee>\n" +
                "    <name>Иванов Иван Иванович</name>\n" +
                "    <age>25</age>\n" +
                "    <secretIdentity>322-224</secretIdentity>\n" +
                "    <powers>\n" +
                "      <power>10</power>\n" +
                "      <power>90</power>\n" +
                "    </powers>\n" +
                "  </employee>\n" +
                "</employees>"

        Assertions.assertEquals(expectedStr, xmlStr)
    }
}
