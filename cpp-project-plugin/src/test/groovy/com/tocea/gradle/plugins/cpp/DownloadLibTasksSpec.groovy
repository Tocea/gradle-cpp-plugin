package com.tocea.gradle.plugins.cpp

import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskExecutionException
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

    def "download junit in custom extlib folder"() {

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

           CppPluginExtension cpp = project.extensions["cpp"]

            cpp.extLibPath = "$buildDir/tartenpion"

            dependencies {
                compile "junit:junit:4.11"
            }


        }


        when:
        project.tasks["downloadLibs"].execute()
        def extLib = new File(projectDir, "build/tartenpion")
        def junitDir = new File(extLib, "junit-4.11")


        then:
        junitDir.exists()
        junitDir.isDirectory()
        extLib.list().length == 1


    }

    def "download junit in custom extlib folder and old extlib folder does not exist"() {

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

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.extLibPath = "$buildDir/tartenpion"

            dependencies {
                compile "junit:junit:4.11"
            }


        }


        when:
        project.tasks["downloadLibs"].execute()
        def extLib = new File(projectDir, "build/extLib")


        then:
        !extLib.exists()


    }


    def "download junit in custom extlib bad syntax folder"() {

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

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.extLibPath = "etiae teauisrt  aieut13»"

            dependencies {
                compile "junit:junit:4.11"
            }


        }


        when:
        project.tasks["downloadLibs"].execute()
        def extLib = new File(projectDir, "build/extLib")


        then:
        thrown TaskExecutionException


    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }


}
