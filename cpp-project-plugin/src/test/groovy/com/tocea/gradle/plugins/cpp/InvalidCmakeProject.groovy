package com.tocea.gradle.plugins.cpp

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 08/09/15.
 */
class InvalidCmakeProject extends Specification {

    @Rule
    TemporaryFolder tempFolder

    @Shared
    def projectDir

    @Shared
    Project project

    def setup() {
        projectDir = tempFolder.newFolder("validateProject")
        project = ProjectBuilder.builder().withProjectDir(projectDir).build()
    }

    @Ignore
    def "check launch exception if file CMakeLists.txt not present"() {
        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
        }

        when:
        project.tasks["validateCMake"].execute()

        then:
        thrown GradleException
    }

    @Ignore
    def "check not launch exception if file CMakeLists.txt us present"() {
        given:
        new File(project.projectDir, "CMakeLists.txt").createNewFile()
        project.with {
            apply plugin: "com.tocea.gradle.cpp"
        }

        when:
        project.tasks["validateCMake"].execute()

        then:
        notThrown GradleException
    }

}
