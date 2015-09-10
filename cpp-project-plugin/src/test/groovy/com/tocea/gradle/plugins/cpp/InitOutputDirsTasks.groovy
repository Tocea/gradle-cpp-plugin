package com.tocea.gradle.plugins.cpp

import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 10/09/15.
 */
class InitOutputDirsTasks extends Specification{


    @Rule
    TemporaryFolder tempFolder

    @Shared
    def projectDir

    @Shared
    Project project

    def setup() {
        projectDir = tempFolder.newFolder("initOutputProjectTest")
        project = ProjectBuilder.builder().withProjectDir(projectDir).build()
    }


    def "test build dir is initialised"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
        }
        when:
        project.tasks["initOutputDirs"].execute()


        then:
        project.buildDir.exists()
        new File(project.buildDir, "main-obj").exists()
        new File(project.buildDir, "test-obj").exists()
        new File(project.buildDir, "report").exists()
        new File(project.buildDir, "tmp").exists()
    }
}
