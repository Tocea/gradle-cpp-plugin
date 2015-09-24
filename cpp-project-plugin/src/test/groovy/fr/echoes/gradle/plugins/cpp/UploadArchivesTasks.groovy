package fr.echoes.gradle.plugins.cpp

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 05/09/15.
 */
class UploadArchivesTask extends Specification {

    @Rule
    TemporaryFolder tempFolder

    @Shared
    def projectDir
    @Shared
    Project project

    def setup() {
        projectDir = tempFolder.newFolder("uploadTest")
        project = ProjectBuilder.builder().withProjectDir(projectDir).build()
    }

    def "check upload archives"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            version = "1.0-SNAPSHOT"
            group = "com.tocea"

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.with {
                applicationType "clibrary"
                classifier "lin_x86_64"
            }

            uploadArchives {
                repositories {
                    mavenDeployer {
                        repository(url: "file://${buildDir}/repo")
                    }
                }
            }
        }
        copy()

        when:
        project.evaluate()
        project.tasks["distZip"].execute()
        project.tasks["distTar"].execute()
        def uploadTask = project.tasks["uploadArchives"]
        uploadTask.execute()

        then:

        new File("${project.buildDir}/repo/com/tocea/test/1.0-SNAPSHOT/").
                list().any { it ==~ /test-1.0-.*-lin_x86_64.clib/ }


    }

    def "check upload archives 2"() {
        given:
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            version = "1.0-SNAPSHOT"
            group = "com.tocea"

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.applicationType "clibrary"


            cpp.classifier "lin_x86_64"


            uploadArchives {
                repositories {
                    mavenDeployer {
                        repository(url: "file://${buildDir}/repo")
                    }
                }
            }
        }
        copy()

        when:
        project.evaluate()
        project.tasks["distZip"].execute()
        project.tasks["distTar"].execute()
        def uploadTask = project.tasks["uploadArchives"]
        uploadTask.execute()

        then:

        new File("${project.buildDir}/repo/com/tocea/test/1.0-SNAPSHOT/").
                list().any { it ==~ /test-1.0-.*-lin_x86_64.clib/ }


    }

    def copy() {
        FileUtils.copyDirectory(new File("src/test/resources/distZipTest"), new File(projectDir, "build/tmp"))
    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
