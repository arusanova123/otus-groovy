package org.example

import groovy.json.JsonSlurper

static void main(String[] args) {
    String jsonUrl = 'https://raw.githubusercontent.com/Groovy-Developer/groovy-homeworks/refs/heads/main/hw-5/test.json'
    String inputJson = jsonUrl.toURL().text
    def employeeBuilder = new EmployeeBuilder()
    employeeBuilder.build (inputJson +","+ inputJson)
    println "HTML: $employeeBuilder.html"
    println "\n\nXML: $employeeBuilder.xml"
}
/*
input json:
{
  "name": "Пупкин Морква Свеклович",
  "age": 22,
  "secretIdentity": "322-223",
  "powers": [100, 50, 70]
}

html:
<div>
    <div id="employee">
        <p>name</p><br/>
        <p>age</p><br/>
        <p>secretIdentity</p><br/>
        <ul id="powers">
            <li>power</li>
        </ul>
    </div>
</div>

xml:
<employee>
  <name>name</p><br/>
  <age>age</p><br/>
  <secretIdentity>secretIdentity</secretIdentity><br/>
  <powers>
    <power>powerName</power>
  </powers>
</employee>
*/

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
        handleEmployee (value)
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

    void handleEmployee (String value) {
        def js = new JsonSlurper ()
        def employee = js.parseText(value)
        String powersHtml = employee.powers.collect {"<li>$it</li>"}.join ("\n            ")
        html = """
<div>
    <div id="employee">
        <p>$employee.name</p><br/>
        <p>$employee.age</p><br/>
        <p>$employee.secretIdentity</p><br/>
        <ul id="powers">
            $powersHtml
        </ul>
    </div>
</div>"""
        String powersXml = employee.powers.collect {"<power>$it</power>"}.join ("\n    ")

        xml = """
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
}
