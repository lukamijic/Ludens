package com.ludens.shared.network.query

class Where(val condition: Condition) : Query {

    override fun query(): String = "where $condition;"

    sealed class Condition {

        abstract override fun toString(): String

        data class Comparison(val field: String, val operator: Operator, val parameter: Parameter) : Condition() {

            override fun toString(): String = "$field ${operator.representation} $parameter"
        }

        data class Or(val left: Condition, val right: Condition): Condition() {

            override fun toString(): String = "$left | $right"
        }

        data class And(val left: Condition, val right: Condition) : Condition() {

            override fun toString(): String = "$left & $right"
        }
    }

    sealed class Parameter {

        abstract override fun toString(): String

        sealed class SingleParameter : Parameter() {
            class IntParam(private val value: Int) : SingleParameter() {

                override fun toString(): String = value.toString()
            }

            class LongParam(private val value: Long) : SingleParameter() {

                override fun toString(): String = value.toString()
            }

            class DoubleParam(private val value: Double) : SingleParameter() {

                override fun toString(): String = value.toString()
            }

            class QuotedStringParam(private val value: String) : SingleParameter() {

                override fun toString(): String = "\"$value\""
            }
        }

        class In(vararg values: SingleParameter) : Parameter() {

            private val inParameter = StringBuilder().apply {
                append("(")
                values.forEachIndexed { index, parameter ->
                    if (index != values.lastIndex) {
                        append("$parameter,")
                    } else {
                        append("$parameter")
                    }
                }
                append(")")
            }.toString()

            override fun toString(): String = inParameter
        }

        class When(vararg values: SingleParameter) : Parameter() {

            private val inParameter = StringBuilder().apply {
                append("{")
                values.forEachIndexed { index, parameter ->
                    if (index != values.lastIndex) {
                        append("$parameter,")
                    } else {
                        append("$parameter")
                    }
                }
                append("}")
            }.toString()

            override fun toString(): String = inParameter
        }
    }

    enum class Operator(val representation: String) {
        EQUALS("="),
        NOT_EQUAL("!="),
        LESS("<"),
        MORE(">"),
        LESS_OR_EQUAL("<="),
        MORE_OR_EQUAL(">=")
    }
}
