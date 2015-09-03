package com.tocea.gradle.plugins.cpp

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


    def projecDir = new File("build/tmp")

    Project project = ProjectBuilder.builder().withProjectDir(projecDir).build()
    RepositoryHandler repositories = project.repositories
    ConfigurationContainer configurations = project.configurations
    DependencyHandler dependencies = project.dependencies
    DownloadLibTasks downloadTask = project.task('downloadLib', type: DownloadLibTasks)

    def "download junit in extlib folder"() {

        given:

        repositories.add(repositories.mavenCentral())

        configurations.create("compile")
        configurations.compile.transitive = false
//        configurations {

//            compile {
//                description = 'compile classpath'
//                transitive = false
//            }
//        }
        dependencies.add("compile", "junit:junit:4.11")
//        dependencies {
         //   compile "junit:junit:4.11"
//        }

        when:
        downloadTask.execute()
        def extLib = new File(projecDir, "build/extLib")
        def junitDir = new File(extLib, "junit-4.11")
        then:


        junitDir.exists()
        junitDir.isDirectory()
        extLib.list().length == 1


    }
}
