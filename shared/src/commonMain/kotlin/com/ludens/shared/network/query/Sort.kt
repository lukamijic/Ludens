package com.ludens.shared.network.query

class Sort(val sortParameter: String, val sortType: Type): Query {

    enum class Type(val representation: String) {
        ASC("asc"),
        DESC("desc")
    }

    override fun query(): String = "sort $sortParameter ${sortType.representation};"
}
