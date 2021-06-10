package com.didik.moflix.helpers

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

interface IFaker {
    val string: String
    val numericalString: String
    val int: Int
    val float: Float
    val long: Long
    val boolean: Boolean
}

object Faker : IFaker {
    override val string: String = Arb.string().next()
    override val numericalString: String = Arb.numericDoubles().next().toString()
    override val int: Int = Arb.int().next()
    override val float: Float = Arb.float().next()
    override val long: Long = Arb.long().next()
    override val boolean: Boolean = Arb.bool().next()
}