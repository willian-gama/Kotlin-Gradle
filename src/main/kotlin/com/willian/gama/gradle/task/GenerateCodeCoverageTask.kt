package com.willian.gama.gradle.task

import com.willian.gama.gradle.constants.JacocoConstants.EXECUTION_DATA
import com.willian.gama.gradle.constants.JacocoConstants.KOTLIN_CLASSES
import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoReport

fun Project.generateCodeCoverageTask() {
    tasks.register("generateCodeCoverage", JacocoReport::class.java) {
        sourceDirectories.from(file(layout.projectDirectory.dir("src/main/java")))
        classDirectories.setFrom(
            files(
                fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/")) {
                    exclude(KOTLIN_CLASSES)
                }
            )
        )
        executionData.setFrom(files(
            fileTree(layout.buildDirectory) {
                include(EXECUTION_DATA)
            }
        ))

        // run unit tests and ui tests to generate code coverage report
        reports {
            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco").get())
            xml.required.set(true) // It's required for Sonar
            xml.outputLocation.set(file(layout.buildDirectory.dir("reports/jacoco/jacoco.xml")))
        }
    }
}