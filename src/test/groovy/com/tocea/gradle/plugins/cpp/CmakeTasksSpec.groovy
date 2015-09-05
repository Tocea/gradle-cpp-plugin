package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by jguidoux on 04/09/15.
 */
class CmakeTasksSpec extends Specification {

    def projecDir = new File(".", "build/tmp/cmakProjectTest")

    Project project = ProjectBuilder.builder().withProjectDir(projecDir).build()


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
