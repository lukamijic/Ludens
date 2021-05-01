package com.ludens.shared.network.query.dsl

import com.ludens.shared.core.Builder
import com.ludens.shared.network.query.Where
import com.ludens.shared.network.query.Where.*
import com.ludens.shared.network.query.Where.Condition.*
import com.ludens.shared.network.query.Where.Parameter.*
import com.ludens.shared.network.query.Where.Parameter.SingleParameter.*

fun comparison(block: ComparisonBuilder.() -> Unit) = ComparisonBuilder().apply(block).build()
fun or(block: OrBuilder.() -> Unit) = OrBuilder().apply(block).build()
fun and(block: AndBuilder.() -> Unit) = AndBuilder().apply(block).build()

fun int(block: IntBuilder.() -> Unit) = IntBuilder().apply(block).build()
fun long(block: LongBuilder.() -> Unit) = LongBuilder().apply(block).build()
fun double(block: DoubleBuilder.() -> Unit) = DoubleBuilder().apply(block).build()
fun string(block: QuotedStringBuilder.() -> Unit) = QuotedStringBuilder().apply(block).build()

fun inParameter(block: InBuilder.() -> Unit) = InBuilder().apply(block).build()
fun whenParameter(block: WhenBuilder.() -> Unit) = WhenBuilder().apply(block).build()

class WhereBuilder : QueryBuilder {
    var condition: Condition? = null

    override fun build(): Where {
        if (condition == null) throw RuntimeException("Field 'condition' must be defined.")
        return Where(condition!!)
    }
}

/** Condition Builders */
class ComparisonBuilder : Builder<Comparison> {

    var field = ""
    var operator: Operator? = null
    var parameter: Parameter? = null

    override fun build(): Comparison {
        if (field.isEmpty()) throw RuntimeException("Field 'field' must not be empty.")
        if (operator == null) throw RuntimeException("Field 'operator' must be defined")
        if (parameter == null) throw RuntimeException("Field 'parameter' must be defined")

        return Comparison(field, operator!!, parameter!!)
    }
}

class OrBuilder : Builder<Or> {

    var left: Condition? = null
    var right: Condition? = null

    override fun build(): Or {
        if (left == null) throw RuntimeException("Field 'left' must be defined")
        if (right == null) throw RuntimeException("Field 'right' must be defined")

        return Or(left!!, right!!)
    }
}

class AndBuilder : Builder<And> {

    var left: Condition? = null
    var right: Condition? = null

    override fun build(): And {
        if (left == null) throw RuntimeException("Field 'left' must be defined")
        if (right == null) throw RuntimeException("Field 'right' must be defined")

        return And(left!!, right!!)
    }
}

/** Parameter Builders */
class IntBuilder : Builder<IntParam> {
    var value: Int? = null

    override fun build(): IntParam {
        if (value == null) throw RuntimeException("Field 'value' must be defined")

        return IntParam(value!!)
    }
}

class LongBuilder : Builder<LongParam> {
    var value: Long? = null

    override fun build(): LongParam {
        if (value == null) throw RuntimeException("Field 'value' must be defined")

        return LongParam(value!!)
    }
}

class DoubleBuilder : Builder<DoubleParam> {
    var value: Double? = null

    override fun build(): DoubleParam {
        if (value == null) throw RuntimeException("Field 'value' must be defined")

        return DoubleParam(value!!)
    }
}

class QuotedStringBuilder : Builder<QuotedStringParam> {
    var value: String? = null

    override fun build(): QuotedStringParam {
        if (value == null) throw RuntimeException("Field 'value' must be defined")

        return QuotedStringParam(value!!)
    }
}

abstract class MultipleSingleParametersBuilder<T : Parameter> : Builder<T>,
    MutableList<SingleParameter> by mutableListOf() {

    fun int(block: IntBuilder.() -> Unit) = add(IntBuilder().apply(block).build())
    fun long(block: LongBuilder.() -> Unit) = add(LongBuilder().apply(block).build())
    fun double(block: DoubleBuilder.() -> Unit) = add(DoubleBuilder().apply(block).build())
    fun string(block: QuotedStringBuilder.() -> Unit) = add(QuotedStringBuilder().apply(block).build())

    override fun build(): T {
        if (this.isEmpty()) throw RuntimeException("In parameter can't be empty.")

        return createParameter()
    }

    protected abstract fun createParameter(): T
}

class InBuilder : MultipleSingleParametersBuilder<In>() {

    override fun createParameter(): In = In(*(this.toTypedArray()))
}

class WhenBuilder : MultipleSingleParametersBuilder<When>() {

    override fun createParameter(): When = When(*(this.toTypedArray()))
}

