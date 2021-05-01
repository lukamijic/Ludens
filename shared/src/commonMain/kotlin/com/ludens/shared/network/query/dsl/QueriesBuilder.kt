package com.ludens.shared.network.query.dsl

import com.ludens.shared.network.query.CompositeQuery
import com.ludens.shared.network.query.Fields
import com.ludens.shared.network.query.Query

fun queries(block: QueriesBuilder.() -> Unit) = QueriesBuilder().apply(block).build()

class QueriesBuilder: QueryBuilder, MutableList<Query> by mutableListOf() {

    fun fields(block: FieldsBuilder.() -> Unit) = add(FieldsBuilder().apply(block).build())

    fun allFields() = add(Fields.ALL)

    fun limit(block: LimitBuilder.() -> Unit) = add(LimitBuilder().apply(block).build())

    fun offset(block: OffsetBuilder.() -> Unit) = add(OffsetBuilder().apply(block).build())

    fun sort(block: SortBuilder.() -> Unit) = add(SortBuilder().apply(block).build())

    fun where(block: WhereBuilder.() -> Unit) = add(WhereBuilder().apply(block).build())

    override fun build(): CompositeQuery = CompositeQuery(this)
}
