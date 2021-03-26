package com.didik.coretest

import io.mockk.unmockkAll
import org.spekframework.spek2.Spek

class BaseTest : Spek({

    beforeEachTest {

    }

    afterEachTest {
        unmockkAll()
    }
})