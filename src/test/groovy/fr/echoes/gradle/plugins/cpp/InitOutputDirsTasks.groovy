package fr.echoes.gradle.plugins.cpp

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 10/09/15.
 */
class InitOutputDirsTasks extends Specification {


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
        given: "a gradle project applying che fr.echoes.gradle.cpp"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
        }

        when: "when gradle evaluate the file build.gradle"
        project.tasks["initOutputDirs"].execute()


        then: "directories 'build/main-obj', 'build/main-obj', 'build/report', 'build/tmp', 'build/tmp/lib' "
        project.buildDir.exists()
        new File(project.buildDir, "main-obj").exists()
        new File(project.buildDir, "test-obj").exists()
        new File(project.buildDir, "reports").exists()
        new File(project.buildDir, "tmp").exists()
        new File(project.buildDir, "tmp/lib").exists()
    }
}
