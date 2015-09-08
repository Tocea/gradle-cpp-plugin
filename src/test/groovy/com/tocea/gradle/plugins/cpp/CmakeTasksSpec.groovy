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
                cmakePath = "echo"
                customCmakeArgs = "-Dargs v1 --default"
                customCmakeStandardOutput = new ByteArrayOutputStream()
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

    def "check compile cpp arguments"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.cmake.with {
                cmakePath = "echo"
                compileCppArgs= "-Dargs v1 --default"
                compileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        CMakeTasks cmake = project.tasks["compileCpp"]
        cmake.execute()
        def output = cmake.cmakeOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.cmakeOutput.toString().contains("compile -Dargs v1 --default")

    }

    def "check testcompile cpp arguments"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.cmake.with {
                cmakePath = "echo"
                testCompileCppArgs= "-Dargs v1 --default"
                testCompileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        CMakeTasks cmake = project.tasks["testCompileCpp"]
        cmake.execute()
        def output = cmake.cmakeOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.cmakeOutput.toString().contains("testCompile -Dargs v1 --default")

    }

    def "check test cpp arguments"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.cmake.with {
                cmakePath = "echo"
                testCppArgs= "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        CMakeTasks cmake = project.tasks["testCpp"]
        cmake.execute()
        def output = cmake.cmakeOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.cmakeOutput.toString().contains("test -Dargs v1 --default")

    }

    def "check test cpp arguments with custom base arges"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.cmake.with {
                cmakePath = "echo"
                testCppBaseArgs = "customTest"
                testCppArgs= "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        CMakeTasks cmake = project.tasks["testCpp"]
        cmake.execute()
        def output = cmake.cmakeOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.cmakeOutput.toString().contains("customTest -Dargs v1 --default")

    }

    def "check cmake dynamicals properties"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"

        }
        CppPluginExtension cpp = project.extensions["cpp"]


        when:
        cpp.cmake.properties.each { println it.key}


        then:
        cpp.cmake.properties.containsKey("compileCppArgs")

    }

    def "check cmake dynamicals properties 2"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"

        }
        CppPluginExtension cpp = new CppPluginExtension(project)
        cpp.cmake.metaClass.abruti

        when:
        cpp.cmake.properties.each { println it.key}


        then:
        cpp.cmake.properties.containsKey("compileCppArgs")

    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
