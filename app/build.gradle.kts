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

    androidTestImplementation(AndroidX.Test.junit)
    androidTestImplementation(AndroidX.Test.rules)
    androidTestImplementation(AndroidX.Espresso.core)
    androidTestImplementation(AndroidX.Espresso.intents)
    androidTestImplementation(Testing.Mockito.kotlin)

    testImplementation(Testing.Mockito.kotlin)
}

configure<BaseExtension> {
    compileSdkVersion(Kafka.compileSdkVersion)

    defaultConfig {
        applicationId = "com.user.uber"
        minSdkVersion(Kafka.minSdkVersion)
        targetSdkVersion(Kafka.compileSdkVersion)
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dataBinding {
        this.isEnabled = true
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





//apply plugin: 'com.android.application'
//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-android-extensions'
//
//android {
//    compileSdkVersion build_versions.compile_sdk as int
//    defaultConfig {
//        applicationId "com.uber.user"
//        minSdkVersion build_versions.min_sdk as int
//        targetSdkVersion build_versions.target_sdk as int
//        vectorDrawables.useSupportLibrary = true
//        versionCode 2
//        versionName "1.0"
//        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//    }
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }
//}


//testImplementation deps.testing.mockito_kotlin
////    testImplementation deps.mockito.core, { exclude group: 'net.bytebuddy' }
//        testImplementation deps.testing.junit
////    testImplementation "net.bytebuddy:byte-buddy:1.9.7"
////    testImplementation "net.bytebuddy:byte-buddy-agent:1.9.7"
////    testImplementation "org.objenesis:objenesis:2.6"
////    testImplementation "org.mockito:mockito-android:2.24.0"
//
//        androidTestImplementation deps.mockito.core, { exclude group: 'net.bytebuddy' }
//androidTestImplementation "net.bytebuddy:byte-buddy:1.9.7"
//androidTestImplementation "org.objenesis:objenesis:2.6"
//androidTestImplementation "org.mockito:mockito-android:2.24.0"
//
//androidTestImplementation deps.androidx.appcompat
//        androidTestImplementation deps.androidx.recyclerview
//        androidTestImplementation deps.androidx.cardview
//        androidTestImplementation deps.androidx.material
//
//        androidTestImplementation deps.espresso.intents
//
//        androidTestImplementation(deps.espresso.core, {
//            exclude group: 'com.android.support', module: 'support-annotations'
//            exclude group: 'com.google.code.findbugs', module: 'jsr305'
//        })
//androidTestImplementation(deps.espresso.contrib, {
//    exclude group: 'com.android.support', module: 'support-annotations'
//    exclude group: 'com.google.code.findbugs', module: 'jsr305'
//})
