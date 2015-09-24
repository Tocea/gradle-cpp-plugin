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
class ProjectTasksTest extends Specification{



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

    def "find all tasks of type cmake"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
        }

        when:
        TaskCollection tasks = project.tasks.withType(CppExecTask)
        tasks.each{ println it.name}

        then:
        tasks.getByName("compileCpp")
    }

    def "check tasks dependencies"() {

       given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
        }

        when:
        project.evaluate()


        println project.tasks["build"].dependsOn.asList()

        then:
        project.tasks["compileCpp"].dependsOn.contains project.tasks["downloadLibs"]
        project.tasks["compileCpp"].dependsOn.contains project.tasks["validateCMake"]
        project.tasks["testCompileCpp"].dependsOn.contains project.tasks["compileCpp"]
        project.tasks["testCpp"].dependsOn.contains project.tasks["testCompileCpp"]
        project.tasks["check"].dependsOn.contains project.tasks["testCpp"]
        project.tasks["cppArchive"].dependsOn.contains project.tasks["compileCpp"]
        project.tasks["uploadArchives"].dependsOn.contains project.tasks["build"]






    }

    def clean() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()

    }
}
