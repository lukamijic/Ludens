package com.ludens.shared.network.query.dsl

import com.ludens.shared.core.Builder
import com.ludens.shared.network.query.Query

interface QueryBuilder : Builder<Query> {

    override fun build(): Query
}
