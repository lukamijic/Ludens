package com.ludens.shared.network.query

class Fields(vararg values: String) : Query {

    companion object {

        val ALL = Fields("*")
    }

    private val query = StringBuilder().apply {
        append("fields")
        values.forEachIndexed { index, value ->
            if (index == 0) {
                append(" $value")
            } else {
                append(", $value")
            }
        }
        append(";")
    }.toString()

    override fun query(): String = query
}
