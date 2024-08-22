plugins {
    kotlin("jvm") version "2.0.20"
    id("idea")
}

group "pluralsight.kotlin-coroutines"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
//    testImplementation "org.junit.jupiter:junit-jupiter-api:5.7.1"
//    testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:5.7.1"
}


tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile::class.java).all {
    kotlinOptions {
//        suppressWarnings = true
        jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true

//        outputDir = file("$rootDir/out/production/$name")
//        testOutputDir = file("$rootDir/out/test/$name")

        excludeDirs = setOf(file("$rootDir/out/"), file("$rootDir/gradle/"), file("$rootDir/.idea/"))
    }
}
