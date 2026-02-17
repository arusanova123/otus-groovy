package org.example

static QueryBuilder query (Closure closure) {
    def builder = new QueryBuilder()
    closure.delegate = builder
    closure.call()
    println builder.execute().join("\r\n")
}

static void main(String[] args) {
  def result = query {
        select "name", "age"
        from (User)
        where {
            or {
                gt "age", 18
                eq "name", "Admin"
            }
        }
    }
}