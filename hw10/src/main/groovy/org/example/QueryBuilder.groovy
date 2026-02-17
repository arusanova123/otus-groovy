package org.example

import groovy.sql.Sql
import java.sql.DriverManager

class QueryBuilder <T> {
    List selectColumns = []
    String tableName
    List conditions = []

    QueryBuilder<T> select (String... fields) {
        this.selectColumns = fields.toList()
        return this
    }

    QueryBuilder<T> from (Class <T> xclass) {
        this.tableName = xclass.getAnnotation(Table).name()
        return this
    }

    QueryBuilder<T> where (@DelegatesTo(ConditionBuilder) Closure closure) {
        def builder = new ConditionBuilder()
        closure.delegate = builder
        closure.call()
        this.conditions = builder.conditions
        return this
    }

    String buildSql () {
        StringBuilder sql = new StringBuilder()
        if (!selectColumns)
            sql.append "select * "
        else
            sql.append "select ${selectColumns.join (", ")}"
        sql.append " FROM $tableName"
        if (conditions) {
            sql.append " WHERE ${conditions.join (" ")}"
        }
        return sql
    }

    List mapResultSetToEntities (rs) {
        def res = []
        while (rs.next()) {
            def mapProperties = [:]
            selectColumns.each {
                mapProperties[it] =  rs.getString(it)
            }
            res.add (new User (mapProperties))
        }
        rs.close()
        return res
    }

    def createConnection() {
        def url = "jdbc:postgresql://localhost:5432/test"
        def username = "postgres"
        def password = "postgres"
        return DriverManager.getConnection(url, username, password)
    }

    public List <T> execute () {
        def parameters = []
        String sql = buildSql()
        def connection = createConnection()
        def stmt = connection.prepareStatement(sql)
        def rs = stmt.executeQuery()
        connection.close()
        return mapResultSetToEntities (rs)
    }
}
