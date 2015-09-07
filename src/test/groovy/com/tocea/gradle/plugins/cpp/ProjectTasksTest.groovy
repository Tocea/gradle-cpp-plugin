package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
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
            apply plugin: "com.tocea.gradle.cpp"
        }

        when:
        TaskCollection tasks = project.tasks.withType(CMakeTasks)
        tasks.each{ println it.name}

        then:
        tasks.getByName("customCmake")
        tasks.getByName("compileCpp")
    }

    def clean() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()

    }
}
