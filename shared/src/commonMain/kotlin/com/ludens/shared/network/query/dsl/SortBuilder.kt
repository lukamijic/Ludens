package com.ludens.shared.network.query.dsl

import com.ludens.shared.network.query.Sort

class SortBuilder : QueryBuilder {

    var value = ""
    var sortType = Sort.Type.ASC

    override fun build(): Sort {
        if (value.isEmpty()) throw RuntimeException("Field 'value' cannot be empty")

        return Sort(value, sortType)
    }
}
