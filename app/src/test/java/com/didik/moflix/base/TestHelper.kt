package com.didik.moflix.base

import org.junit.Assert

infix fun Any?.shouldBe(value: Any?) {
    Assert.assertEquals(this, value)
}

infix fun Any?.shouldNotBe(value: Any?) {
    Assert.assertNotEquals(this, value)
}

fun Boolean.shouldBeTrue() {
    Assert.assertTrue(this)
}

fun Boolean.shouldBeFalse() {
    Assert.assertFalse(this)
}