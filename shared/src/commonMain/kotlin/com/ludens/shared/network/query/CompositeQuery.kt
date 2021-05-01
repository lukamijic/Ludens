package com.ludens.shared.network.query

class CompositeQuery(val queries: List<Query>): Query {

    private val query = StringBuilder().apply {
        queries.forEach { append("${it.query()}\n") }
    }.toString()

    override fun query(): String = query
}
