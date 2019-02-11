import Kotlin.kapt
import org.gradle.internal.impldep.com.google.gson.Gson

plugins {
    id(Android.libPlugin)
    id(Kotlin.androidPlugin)
    id(Kotlin.kapt)
}

dependencies {
    implementation(Kotlin.stdlib)
}
