package com.ludens.shared.network.query

data class Offset(val value: Int): Query {

    override fun query(): String = "offset $value;"
}
