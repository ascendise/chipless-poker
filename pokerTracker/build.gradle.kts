plugins {
    id("java-library")
    id("jvm-test-suite")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            sources {
                kotlin {
                    setSrcDirs(listOf("src/test/kotlin"))
                }
            }
            useJUnitJupiter()
        }

        register<JvmTestSuite>(TestSuiteType.INTEGRATION_TEST) {
            dependencies {
                implementation(project())
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named(TestSuiteType.INTEGRATION_TEST))
}

