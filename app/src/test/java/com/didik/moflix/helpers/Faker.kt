package com.didik.moflix.helpers

import io.kotest.property.Arb
import io.kotest.property.RandomSource
import io.kotest.property.arbitrary.float
import io.kotest.property.arbitrary.numericDoubles
import io.kotest.property.arbitrary.string

interface IFaker {
    val string: String
    val numericalString: String
    val float: Float
}

object Faker : IFaker {
    override val string: String = Arb.string().sample(RandomSource.Default).value
    override val numericalString: String = Arb.numericDoubles().sample(RandomSource.Default).value.toString()
    override val float: Float = Arb.float().sample(RandomSource.Default).value
}