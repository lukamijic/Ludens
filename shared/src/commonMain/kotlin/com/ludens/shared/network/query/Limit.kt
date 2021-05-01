package com.ludens.shared.network.query

data class Limit(val value: Int): Query {

    override fun query(): String = "limit $value;"
}
