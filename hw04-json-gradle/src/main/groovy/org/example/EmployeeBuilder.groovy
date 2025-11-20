package org.example

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

class EmployeeBuilder {
    List employees

    EmployeeBuilder (String value) {
        def js = new JsonSlurper()
        def json = js.parseText(value)
        employees = json instanceof List ? json : [json]
    }

    String xmlEmployee(String value) {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.employees {
            employees.each { emp ->
                employee {
                    name(emp.name)
                    age(emp.age)
                    secretIdentity(emp.secretIdentity)
                    powers {
                        emp.powers.each {
                            power(it)
                        }
                    }
                }
            }
        }
        writer.toString()
    }
    String htmlEmployee(String value) {
        def writer = new StringWriter()
        def html = new MarkupBuilder(writer)

        html.html {
            div {
                employees.each { employee ->
                    div (id: 'employee') {
                        p employee.name
                        br()
                        p employee.age
                        br()
                        p employee.secretIdentity
                        br()
                        ul (id:"powers") {
                            employee.powers.each {power ->
                                li (power)
                            }
                        }
                    }
                }
            }
        }
        writer.toString()
    }
}
