package com.ludens.shared.network.query.dsl

import com.ludens.shared.network.query.Offset

class OffsetBuilder : QueryBuilder {

    var value: Int = 0

    override fun build(): Offset {
        if (value < 0) throw RuntimeException("Field 'value' cannot be less than zero")

        return Offset(value)
    }
}
