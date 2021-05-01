package com.ludens.shared.network.query.dsl

import com.ludens.shared.network.query.Fields

class FieldsBuilder : MutableList<String> by mutableListOf(), QueryBuilder {

    fun field(block: FieldBuilder.() -> Unit) {
        add(FieldBuilder().apply(block).build())
    }

    override fun build(): Fields = Fields(*(this.toTypedArray()))
}

class FieldBuilder {

    var value = ""

    fun build(): String {
        if (value.isEmpty()) throw RuntimeException("Field 'value' cannot be empty")

        return value
    }
}
