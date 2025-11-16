package org.example
import groovy.json.JsonSlurper
import org.example.EmployeeBuilder

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

String jsonUrl = 'https://raw.githubusercontent.com/Groovy-Developer/groovy-homeworks/refs/heads/main/hw-5/test.json'
String inputJson = jsonUrl.toURL().text

def employeeBuilder = new EmployeeBuilder()
employeeBuilder.build(inputJson)
println "HTML: \n$employeeBuilder.html"
println "\n\nXML: \n$employeeBuilder.xml"

// Пример списка
inputJson = """
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

employeeBuilder.build(inputJson)
println "HTML: \n$employeeBuilder.html"
println "\n\nXML: \n$employeeBuilder.xml"