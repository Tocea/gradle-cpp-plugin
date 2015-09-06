package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 04/09/15.
 */
class CmakeTasksSpec extends Specification {


    @Rule
    TemporaryFolder tempFolder

    @Shared
    def projectDir

    @Shared
    Project project

    def setup() {
        projectDir = tempFolder.newFolder("dlLibProjectTest")
        project = ProjectBuilder.builder().withProjectDir(projectDir).build()
    }


    def "check cmake arguments"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.cmake.with {
                cmakeArgs = "-Dargs v1 --default"
                standardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        CMakeTasks cmake = project.tasks["customCmake"]
        cmake.execute()
        def output = cmake.cmakeOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.cmakeOutput.toString().contains("-Dargs v1 --default")

    }
}
