plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.13"
}

sourceSets {
    main {
        kotlin.srcDir("src")
        dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.13")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11"
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

benchmark {
    targets {
        register("main")
    }
}
