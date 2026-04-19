// Top-level build file where you can add configuration options common to all subprojects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.chaquo.python") version "17.0.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21" apply false
}