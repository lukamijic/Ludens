package com.ludens.shared.network.query.dsl

import com.ludens.shared.network.query.Limit

class LimitBuilder : QueryBuilder {

    var value: Int = 0

    override fun build(): Limit {
        if (value < 0) throw RuntimeException("Field 'value' cannot be less than zero")

        return Limit(value)
    }
}
