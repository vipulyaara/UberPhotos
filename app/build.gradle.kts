import Epoxy.dataBinding
import Kafka.compileSdkVersion
import Kafka.minSdkVersion
import Kotlin.kapt
import KotlinX.Coroutines.android
import com.android.build.gradle.BaseExtension
import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    id(Android.appPlugin)
    id(Kotlin.androidPlugin)
    id(Kotlin.kapt)
    id(Kotlin.androidExtensionsPlugin)
}

dependencies {
    implementation((project(":data")))

    implementation(Kotlin.stdlib)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.material)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.constraintLayout)

    androidTestImplementation(AndroidX.annotation)
    androidTestImplementation(AndroidX.Test.junit)
    androidTestImplementation(AndroidX.Test.rules)
    androidTestImplementation(AndroidX.Espresso.core)
    androidTestImplementation(AndroidX.Espresso.intents)
    androidTestImplementation(AndroidX.Espresso.contrib)
    androidTestImplementation(Testing.Mockito.kotlin)
    androidTestImplementation(JUnit.dependency)
    androidTestImplementation(AndroidX.Arch.testing)
    androidTestImplementation("org.mockito:mockito-android:2.24.0")

    testImplementation(AndroidX.annotation)
    testImplementation(AndroidX.Test.junit)
    testImplementation(AndroidX.Arch.testing)
    testImplementation(Testing.Mockito.kotlin)
    testImplementation(JUnit.dependency)
}

configure<BaseExtension> {
    compileSdkVersion(Kafka.compileSdkVersion)

    defaultConfig {
        applicationId = "com.uber.user"
        minSdkVersion(Kafka.minSdkVersion)
        targetSdkVersion(Kafka.compileSdkVersion)
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }
}
