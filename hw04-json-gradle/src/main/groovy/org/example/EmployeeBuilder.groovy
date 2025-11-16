package org.example

import groovy.json.JsonSlurper

class EmployeeBuilder extends BuilderSupport {
    String html, xml

    @Override
    protected void setParent(Object parent, Object child) {
    }

    @Override
    protected Object createNode(Object name) {
        return null
    }

    @Override
    protected Object createNode(Object name, Object value) {
        handleEmployee(value)
        return null
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        return null
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        return null
    }

    void handleEmployee(String value) {
        def js = new JsonSlurper()
        def json = js.parseText(value)
        List employees = json instanceof List ? json : [json]
        html = ""
        employees.each {employee->
            String powersHtml = employee.powers.collect { "\n         <li>$it</li>" }.join()
            html += """   <div id="employee">\n"""+
                    "      <p>$employee.name</p><br/>\n"+
                    "      <p>$employee.age</p><br/>\n"+
                    "      <p>$employee.secretIdentity</p><br/>\n"+
                    """      <ul id="powers"> $powersHtml \n"""+
                    "      </ul>\n"+
                    "   </div>\n"
        }
        html = "<div>\n$html</div>"
        xml = ""
        employees.each {employee->
            String powersXml = employee.powers.collect { "<power>$it</power>" }.join("\n        ")
            xml +=
"""
    <employee>
      <name>$employee.name</p><br/>
      <age>$employee.age</p><br/>
      <secretIdentity>$employee.secretIdentity</secretIdentity><br/>
      <powers>
        $powersXml
      </powers>
    </employee>
"""
        }
        xml = "<employees>$xml</employees>"
    }
}
