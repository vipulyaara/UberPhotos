import Kafka.groupId
import com.android.build.gradle.BaseExtension
import com.dicedmelon.gradle.jacoco.android.JacocoAndroidUnitTestReportExtension
import org.jmailen.gradle.kotlinter.KotlinterExtension
import org.jmailen.gradle.kotlinter.support.ReporterType
import org.gradle.api.publish.maven.MavenPom
import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    java
    kotlin("jvm") version Kotlin.version apply false
    id(Android.libPlugin) version Android.version apply false
    id(Jacoco.Android.plugin) version Jacoco.Android.version apply false
    id(KotlinX.Serialization.plugin) version Kotlin.version apply false
    id(Ktlint.plugin) version Ktlint.version apply false

    `maven-publish`
    id(Release.Bintray.plugin) version Release.Bintray.version
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        jcenter()
    }
}

val androidModules = listOf("data")
val androidApplicationModules = listOf("app")

subprojects {
    val isAndroidModule = project.name in androidModules
    val isApplication = project.name in androidApplicationModules
    val isJvmModule = !isAndroidModule && !isApplication

    if (isJvmModule) {
        apply {
            plugin(Kotlin.plugin)
            plugin(Jacoco.plugin)
        }

        configure<JacocoPluginExtension> {
            toolVersion = Jacoco.version
        }

        dependencies {
            implementation(Kotlin.stdlib)
            testImplementation(JUnit.dependency)
        }

        configure<JavaPluginConvention> {
            sourceCompatibility = JavaVersion.VERSION_1_7
            targetCompatibility = JavaVersion.VERSION_1_7

            sourceSets {
                getByName("main").java.srcDirs("src/main/kotlin")
                getByName("test").java.srcDirs("src/main/kotlin")
            }
        }

        tasks.withType<JacocoReport> {
            reports {
                html.isEnabled = false
                xml.isEnabled = true
                csv.isEnabled = false
            }
        }
    }

    if (isAndroidModule) {
        apply {
            plugin(Android.libPlugin)
            plugin(Kotlin.androidPlugin)
            plugin(Kotlin.androidExtensionsPlugin)
            plugin(Jacoco.Android.plugin)
        }

        configure<BaseExtension> {
            compileSdkVersion(Kafka.compileSdkVersion)

            defaultConfig {
                minSdkVersion(Kafka.minSdkVersion)
                targetSdkVersion(Kafka.compileSdkVersion)
                versionCode = 1
                versionName = Kafka.publishVersion
            }

            sourceSets {
                getByName("main").java.srcDirs("src/main/kotlin")
                getByName("test").java.srcDirs("src/test/kotlin")
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_7
                setTargetCompatibility(JavaVersion.VERSION_1_7)
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    consumerProguardFiles("proguard-rules.pro")
                }
            }

            lintOptions {
                isAbortOnError = false
            }

            testOptions {
                unitTests.isReturnDefaultValues = true
            }
        }

        configure<JacocoAndroidUnitTestReportExtension> {
            csv.enabled(false)
            html.enabled(true)
            xml.enabled(true)
        }
    }

    if (!isApplication) {
        apply {
            plugin(Release.MavenPublish.plugin)
            plugin(Release.Bintray.plugin)
            plugin(Ktlint.plugin)
        }

        configure<KotlinterExtension> {
            reporters = arrayOf(ReporterType.plain.name, ReporterType.checkstyle.name)
        }
    }
}
