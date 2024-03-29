buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.buildGradle)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.daggerHiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}