package com.tocea.gradle.plugins.cpp

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.tasks.bundling.Zip
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.util.zip.ZipFile


/**
 * Created by jguidoux on 03/09/15.
 */
class DistZipTaskSpec extends Specification {


    def projecDir = new File(".", "build/tmp/distZipTest")

    Project project = ProjectBuilder.builder().withProjectDir(projecDir).build()


    def "check zip created"() {
        given:
        project.pluginManager.apply "com.tocea.gradle.cpp"
        Zip distZip = project.getTasks().getByName("distZip")

        CppPluginExtension cpp = project.extensions["cpp"]
        cpp.with {
            applicationType "clibrary"
            archictecture "lin_x86_64"
        }


        copy()

        when:
        distZip.execute()
        ZipFile file = new ZipFile(new File(projecDir, "build/distributions/test-lin_x86_64.clib"))
        file.entries().each { println it.name }
        then:
        file.entries().toList().isEmpty() == false
        file.getEntry("test-lin_x86_64") != null
        file.getEntry("test-lin_x86_64/lib") != null
        file.getEntry("test-lin_x86_64/lib/hello") != null
        file.getEntry("test-lin_x86_64/lib/hello/Hello.so") != null
        file.getEntry("test-lin_x86_64/headers") != null
        file.getEntry("test-lin_x86_64/headers/hello") != null
        file.getEntry("test-lin_x86_64/headers/hello/hello.h") != null

    }

    def "check zip has valid extension and classifier"() {
        given:
        project.pluginManager.apply "com.tocea.gradle.cpp"
        Zip distZip = project.getTasks().getByName("distZip")

        copy()

        CppPluginExtension cpp = project.extensions["cpp"]
        cpp.with {
            applicationType "clibrary"
            archictecture "lin_x86_64"
        }


        when:
        distZip.execute()

        then:
        new File(projecDir, "build/distributions/test-lin_x86_64.clib").exists()

    }

    def copy() {
        FileUtils.copyDirectory(new File("src/test/resources/distZipTest"), new File(projecDir, "build/tmp"))
    }

    def "check change packaging type to clib"() {
        given:
       // project.pluginManager.apply "com.tocea.gradle.cpp"
        project.with {
            apply plugin: "com.tocea.gradle.cpp"

        }
        Zip distZip = project.tasks["distZip"]
        copy()

        CppPluginExtension cpp = project.extensions["cpp"]
        cpp.with {
            applicationType "clibrary"
            archictecture "lin_x86_64"
        }

        when:
        project.evaluate()
        distZip.execute()

        then:
        new File(projecDir, "build/distributions/test-lin_x86_64.clib").exists()

    }


    def "check change packaging type to capplication"() {
        given:
        // project.pluginManager.apply "com.tocea.gradle.cpp"
        project.with {
            apply plugin: "com.tocea.gradle.cpp"

        }
        Zip distZip = project.tasks["distZip"]
        copy()

        CppPluginExtension cpp = project.extensions["cpp"]
        cpp.with {
            applicationType "capplication"
            archictecture "lin_x86_64"

        }

        when:
        project.evaluate()
        distZip.execute()

        then:
        new File(projecDir, "build/distributions/test-lin_x86_64.zip").exists()

    }


    def "check packaging without architecture information"() {
        given:
        // project.pluginManager.apply "com.tocea.gradle.cpp"
        project.with {
            apply plugin: "com.tocea.gradle.cpp"

        }
        Zip distZip = project.tasks["distZip"]
        copy()

        CppPluginExtension cpp = project.extensions["cpp"]
        cpp.with {
            applicationType "capplication"

        }

        when:
        project.evaluate()
        distZip.execute()

        then:
        new File(projecDir, "build/distributions/test-UNKNOWN_ARCHITECTURE.zip").exists()

    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
