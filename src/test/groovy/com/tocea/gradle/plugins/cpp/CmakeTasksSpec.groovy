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


    def "chake cmake arguments"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.cmake.cmakeArgs = "toto"
        }


        when:
        CMakeTasks cmake = project.tasks["cmake"]
        cmake.execute()
        CppPluginExtension cpp = project.extensions["cpp"]

        then:
        cpp.cmake.cmakeArgs == "toto"


    }
}
