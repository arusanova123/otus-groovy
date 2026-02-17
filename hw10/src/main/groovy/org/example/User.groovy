package org.example

@Table (name = "users")
class User {
    @Column (name = "id")
    Long id
    @Column (name = "name")
    String name
    String age

    String toString() {
        "Name: $name, Age: $age"
    }

    User (Map properties) {
        properties.each {key, val ->
            this."$key" = val
        }
    }
}
