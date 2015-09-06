package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTasks
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 03/09/15.
 */
class DownloadLibTasksSpec extends Specification {


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


    def "download junit in extlib folder"() {

        given:
        project.with {
            apply plugin: "com.tocea.gradle.cpp"

            repositories {
                repositories.mavenCentral()
            }

            configurations {
                compile {
                    description = 'compile classpath'
                    transitive = false
                }
            }

            dependencies {
                compile "junit:junit:4.11"
            }


        }


        when:
        project.tasks["downloadLibs"].execute()
        def extLib = new File(projectDir, "build/extLib")
        def junitDir = new File(extLib, "junit-4.11")


        then:
        junitDir.exists()
        junitDir.isDirectory()
        extLib.list().length == 1


    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }


}
