package fr.echoes.gradle.plugins.cpp

import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

/**
 * this test class si used to test CppExecTask class
 * 
 * Created by jguidoux on 04/09/15.
 */
class CppExecTaskSpec extends Specification {


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


    def "check compileCpp  arguments"() {

        given: "task compileCpp is configured to launch 'echo -Dargs v1 --default' "
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                compileCppArgs = "-Dargs v1 --default"
                compileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when: " I launch the task compileCpp"
        project.evaluate()
        CppExecTask task = project.tasks["compileCpp"]
        task.execute()

        then: "task output must be 'gs v1 --default'"
        task.standardOutput.toString().contains("-Dargs v1 --default")

    }


    def "check testcompile cpp arguments"() {

        given: "task testCompileCpp is configured to launch 'echo -Dargs v1 --default' "
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                testCompileCppArgs = "-Dargs v1 --default"
                testCompileCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when: " I launch the task testCompileCpp"
        project.evaluate()
        CppExecTask task = project.tasks["testCompileCpp"]
        // task.standardOutput =  new ByteArrayOutputStream()
        task.execute()
        def output = task.standardOutput.toString()
        CppPluginExtension cpp = project.extensions["cpp"]
        println "output = $output"

        then: "task output must be 'gs v1 --default'"
        task.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check testCpp arguments"() {

        given: "task testCpp is configured to launch 'echo -Dargs v1 --default' "
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                testCppArgs = "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }

        when: " I launch the task testCpp"
        project.evaluate()
        project.tasks["compileCpp"].execute()
        CppExecTask task = project.tasks["testCpp"]
        task.execute()

        then: "task output must be '-Dargs v1 --default'"
        task.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check test cpp arguments with custom executable"() {

        given: "task testCpp is configured to launch 'echo -Dargs v1 --default' using testCppExecPath"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                testCppExecPath = "echo"
                testCppArgs = "-Dargs v1 --default"
                testCppStandardOutput = new ByteArrayOutputStream()
            }
        }


        when: " I launch the task testCpp"
        project.evaluate()
        CppExecTask task = project.tasks["testCpp"]
        task.execute()

        then: "task output must be '-Dargs v1 --default'"
        task.standardOutput.toString().contains("-Dargs v1 --default")

    }

    def "check test cpp env"() {

        given: "cpp extension cpp.exec.env is set with environment variable MA_VAR = 'abc' "
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.exec.with {
                execPath = "echo"
                env = ["MA_VAR": "abc"]
            }
        }


        when: "I launch the task testCpp"
        project.evaluate()
        CppExecTask task = project.tasks["testCpp"]
        task.execute()

        then: "the testCpp task must know the variable 'MA_VAR set to 'abc'"
        task.environment["MA_VAR"] == "abc"

    }

    def "check test cpp arguments with custom base args"() {
        given: "using cpp extension with testCppBaseArgs='customTest' and   testCppArgs='-Dargs v1 --default'"
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


        when: "I launch the task testCpp"
        project.evaluate()
        CppExecTask task = project.tasks["testCpp"]
        task.execute()

        then: "task output must be 'customTest -Dargs v1 --default'"
        task.standardOutput.toString().contains("customTest -Dargs v1 --default")

    }

    def "check custom exec for test task"() {
        given: "I override the exec path the execPath with testCppExecPath = \"ls\""
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
            testCppExecWorkingDir = "."

        }

        when: "I launch the task testCpp"
        project.evaluate()
        CppExecTask task = project.tasks["testCpp"]
        task.execute()
        def output = task.standardOutput.toString()
        println "output = $output"

        then: "task output must contain be 'userHome'"
        project.tasks["testCpp"].executable == "ls"
        output.contains("userHome")

    }

    def "check task dynamicals properties"() {
        given: "I apply the plugin 'fr.echoes.gradle.cpp' to a gradle project"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"

        }
        CppPluginExtension cpp = project.extensions["cpp"]


        when: "when gradle evaluate the file build.gradle"
        project.evaluate()


        then: "the property 'cpp.exec.compileCppArgs' must exist"
        cpp.exec.properties.containsKey("compileCppArgs")

    }

    def "check desactivate build taske"() {
        given: "when I deactivate the build tasks of the cpp plugin"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]
            cpp.buildTasksEnabled = false

        }


        when: "when gradle evaluate the file build.gradle"
        project.evaluate()


        then: "the 'check' task must not depend of the task 'testCpp'"
        !project.tasks["check"].dependsOn.contains(project.tasks["testCpp"])


    }


    def "check activate build taske"() {
        given: "when I do not deactivate the build tasks of the cpp plugin"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"

        }

        when: "when gradle evaluate the file build.gradle"
        project.evaluate()
        CppPluginExtension cpp = project.extensions["cpp"]




        then: "the 'check' task must not depend of the task 'testCpp'"
        project.tasks["check"].dependsOn(project.tasks["testCpp"])

    }

    def "check  adding dynamicals properties in cpp.exec extension"() {
        given: "I add foo attribute in 'cpp.exec' using metaclass groovy system"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"

        }
        CppPluginExtension cpp = new CppPluginExtension(project)
        cpp.exec.metaClass.foo = ""

        when: "when gradle evaluate the file build.gradle"
        project.evaluate()

        then: "foo attribute must exist"
        cpp.exec.properties.containsKey("foo")

    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
