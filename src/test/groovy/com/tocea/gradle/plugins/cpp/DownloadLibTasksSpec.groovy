package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTasks
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by jguidoux on 03/09/15.
 */
class DownloadLibTasksSpec extends Specification {


    def projecDir = new File("build/tmp/dlLibProjectTest")

    Project project = ProjectBuilder.builder().withProjectDir(projecDir).build()
    RepositoryHandler repositories = project.repositories
    ConfigurationContainer configurations = project.configurations
    DependencyHandler dependencies = project.dependencies
    DownloadLibTasks downloadTask = project.task('downloadLib', type: DownloadLibTasks)



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
        downloadTask.execute()
        def extLib = new File(projecDir, "build/extLib")
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
