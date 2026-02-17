package org.example

class ConditionBuilder {
    List<String> conditions = []

    def or(Closure closure) {
        def inner = new ConditionBuilder()
        closure.delegate = inner
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
        conditions.add "( ${inner.conditions.join(' OR ')} )"
    }

    def and(Closure closure) {
        def inner = new ConditionBuilder()
        closure.delegate = inner
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call()
        conditions.add "( ${inner.conditions.join(' AND ')} )"
    }

    def gt (String field, Object value) {
        conditions.add ("$field > $value")
    }

    def lt (String field, Object value) {
        conditions.add ("$field < $value")
    }

    void eq (String column, Object value) {
        def operator = value? "= '$value'" : "IS NULL"
        conditions.add ("$column $operator")
    }
}