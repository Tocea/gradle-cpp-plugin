package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.CppExecTask
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
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                compileCppArgs = "-Dargs v1 --default"
                compileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["compileCpp"]
        cmake.execute()
        def output = cmake.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check compile cpp arguments"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                compileCppArgs = "-Dargs v1 --default"
                compileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["compileCpp"]
        cmake.execute()
        def output = cmake.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check testcompile cpp arguments"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                testCompileCppArgs = "-Dargs v1 --default"
                testCompileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["testCompileCpp"]
       // cmake.standardOutput =  new ByteArrayOutputStream()
        cmake.execute()
        def output = cmake.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check test cpp arguments"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                testCppArgs = "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        project.evaluate()
        project.tasks["compileCpp"].execute()
        CppExecTask cmake = project.tasks["testCpp"]
        cmake.execute()
        def output = cmake.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check test cpp arguments with custom executable"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                testCppExecPath = "echo"
                testCppArgs = "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["testCpp"]
        cmake.execute()
        def output = cmake.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check test cpp env"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                env = ["MA_VAR": "abc"]
            }
        }


        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["testCpp"]
        cmake.execute()

        then:
        cmake.environment["MA_VAR"] == "abc"

    }

    def "check test cpp arguments with custom base args"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                testCppBaseArgs = "customTest"
                testCppArgs = "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["testCpp"]
        cmake.execute()
        def output = cmake.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then:
        cmake.standardOutput.toString().contains("customTest -Dargs v1 --default")

    }

    def "check custom exec for test task"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
        }
        CppPluginExtension cpp = project.extensions["cpp"]

        cpp.exec.with {
            execPath = "echo"
            testCppExecPath = "ls"
            testCppBaseArgs = ""
            testCppArgs = ""
            testCppStandardOutput = new ByteArrayOutputStream()
            testCppExecWorkingDir="."

        }

        when:
        project.evaluate()
        CppExecTask cmake = project.tasks["testCpp"]
        cmake.execute()
        def output = cmake.standardOutput.toString()
        println "output = $output"

        then:
        project.tasks["testCpp"].executable == "ls"
        output.contains("userHome")

    }

    def "check cmake dynamicals properties"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"

        }
        CppPluginExtension cpp = project.extensions["cpp"]


        when:
        project.evaluate()
        cpp.exec.properties.each { println it.key }


        then:
        cpp.exec.properties.containsKey("compileCppArgs")

    }

    def "check desactivate build taske"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]
            cpp.buildTasksEnabled = false

        }


        when:
        project.evaluate()


        then:
        !project.tasks["check"].dependsOn.contains(project.tasks["testCpp"])


    }


    def "check activate build taske"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"

        }

        when:
        project.evaluate()
        CppPluginExtension cpp = project.extensions["cpp"]




        then:
        project.tasks["check"].dependsOn(project.tasks["testCpp"])

    }

    def "check cmake dynamicals properties 2"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"

        }
        CppPluginExtension cpp = new CppPluginExtension(project)
        cpp.exec.metaClass.abruti

        when:
        project.evaluate()
        cpp.exec.properties.each { println it.key }


        then:
        cpp.exec.properties.containsKey("compileCppArgs")

    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
