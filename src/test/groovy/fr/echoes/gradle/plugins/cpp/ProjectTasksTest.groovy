package fr.echoes.gradle.plugins.cpp

import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 07/09/15.
 */
class ProjectTasksTest extends Specification {


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

    def "find all tasks of type CppExecTask"() {

        given: "a gradle project applying che fr.echoes.gradle.cpp"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
        }

        when: "I ask for task of type 'CppExecTask'"
        TaskCollection tasks = project.tasks.withType(CppExecTask)
        tasks.each { println it.name }

        then: "the list must contain the 'compileCpp'"
        tasks.getByName("compileCpp")
    }

    def "check tasks dependencies"() {

        given: "a gradle project applying che fr.echoes.gradle.cpp"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
        }

        when: "when gradle evaluate the file build.gradle"
        project.evaluate()


        println project.tasks["build"].dependsOn.asList()

        then: "The Directed Acyclic Graph of task must be correct"
        project.tasks["compileCpp"].dependsOn.contains project.tasks["downloadLibs"]
        project.tasks["testCompileCpp"].dependsOn.contains project.tasks["compileCpp"]
        project.tasks["testCpp"].dependsOn.contains project.tasks["testCompileCpp"]
        project.tasks["check"].dependsOn.contains project.tasks["testCpp"]
        project.tasks["distZip"].dependsOn.contains project.tasks["copyHeaders"]
        project.tasks["uploadArchives"].dependsOn.contains project.tasks["build"]


    }

    def clean() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()

    }
}
