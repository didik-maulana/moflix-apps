object TestLibsVersions {
    const val mockk = "1.11.0"
    const val kotest = "4.4.3"
    const val coreTesting = "1.1.1"
    const val coroutineTest = "1.4.3"
    const val jUnit = "4.13.2"
    const val jUnitExt = "1.1.2"
    const val espresso = "3.3.0"
    const val espressoContrib = "3.0.2"
    const val testRunner = "1.3.0"
    const val testRules = "1.3.0"
}

object TestLibs {
    const val mockk = "io.mockk:mockk:${TestLibsVersions.mockk}"
    const val kotestRunner = "io.kotest:kotest-runner-junit5:${TestLibsVersions.kotest}"
    const val kotestAssertions = "io.kotest:kotest-assertions-core:${TestLibsVersions.kotest}"
    const val kotestProperty = "io.kotest:kotest-property:${TestLibsVersions.kotest}"
    const val coreTesting = "android.arch.core:core-testing:${TestLibsVersions.coreTesting}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestLibsVersions.coroutineTest}"
    const val jUnit = "junit:junit:${TestLibsVersions.jUnit}"
    const val jUnitExt = "androidx.test.ext:junit:${TestLibsVersions.jUnitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${TestLibsVersions.espresso}"
    const val espressoContrib = "com.android.support.test.espresso:espresso-contrib:${TestLibsVersions.espressoContrib}"
    const val testRunner = "androidx.test:runner:${TestLibsVersions.testRunner}"
    const val testRules = "androidx.test:rules:${TestLibsVersions.testRules}"
}