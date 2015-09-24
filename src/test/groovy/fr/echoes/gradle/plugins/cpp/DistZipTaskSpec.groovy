package fr.echoes.gradle.plugins.cpp

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Zip
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

import java.util.zip.ZipFile


/**
 * Created by jguidoux on 03/09/15.
 */
class DistZipTaskSpec extends Specification {



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


    def "check zip created"() {
        given:

        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]
            version = "1.0"
            cpp.with {
                applicationType "clibrary"
                classifier "lin_x86_64"
            }
        }

        copy()


        when:
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()
        ZipFile file = new ZipFile(new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib"))
        file.entries().each { println it.name }

        then:
       !file.entries().toList().isEmpty()
        file.getEntry("test-1.0-lin_x86_64") != null
        file.getEntry("test-1.0-lin_x86_64/lib") != null
        file.getEntry("test-1.0-lin_x86_64/lib/hello") != null
        file.getEntry("test-1.0-lin_x86_64/lib/hello/Hello.so") != null
        file.getEntry("test-1.0-lin_x86_64/headers") != null
        file.getEntry("test-1.0-lin_x86_64/headers/hello") != null
        file.getEntry("test-1.0-lin_x86_64/headers/hello/hello.h") != null

    }

    def "check zip has valid extension and classifier"() {
        given:

        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            version = "1.0"

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.with {
                applicationType "clibrary"
                classifier "lin_x86_64"
            }
        }

        copy()

        when:
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then:
        new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib").exists()

    }

    def copy() {
        FileUtils.copyDirectory(new File("src/test/resources/distZipTest"), new File(projectDir, "build/tmp"))
    }

    def "check change packaging type to clib"() {
        given:
        // project.pluginManager.apply "fr.echoes.gradle.cpp"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            CppPluginExtension cpp = project.extensions["cpp"]
            version = "1.0"

            cpp.with {
                applicationType "clibrary"
                classifier "lin_x86_64"
            }

        }
        copy()

        CppPluginExtension cpp = project.extensions["cpp"]


        when:
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then:
        new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib").exists()

    }


    def "check change packaging type to capplication"() {
        given:
        // project.pluginManager.apply "fr.echoes.gradle.cpp"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            version = "1.0"

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.with {
                applicationType "capplication"
                classifier "lin_x86_64"
            }
        }
        copy()


        when:
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then:
        new File(projectDir, "build/distributions/test-1.0-lin_x86_64.zip").exists()

    }


    def "check packaging without architecture information"() {
        given:
        // project.pluginManager.apply "fr.echoes.gradle.cpp"
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            version = "1.0"

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.with {
                applicationType "capplication"

            }
        }
        copy()


        when:
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then:
        new File(projectDir, "build/distributions/test-1.0.zip").exists()

    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
