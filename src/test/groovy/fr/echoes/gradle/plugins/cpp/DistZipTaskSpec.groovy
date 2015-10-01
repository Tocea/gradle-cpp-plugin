package fr.echoes.gradle.plugins.cpp

import fr.echoes.gradle.plugins.cpp.extensions.CppPluginExtension
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Zip
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Shared
import spock.lang.Specification

import java.util.zip.ZipFile


/**
 * test the DistZipTask class
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
        given: "a cpp project of type 'clibrabry' with classifie 'lin_x86_64'"

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


        when: "I launch the distZip task"
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()
        ZipFile file = new ZipFile(new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib"))


        then: "the file 'build/distributions/test-1.0-lin_x86_64.clib' must contain the correct content"
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
        given: "a cpp project of type 'clibrabry' with classifie 'lin_x86_64' and version '1.0'"

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

        when: "I launch the distZip task"
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then: "the file 'build/distributions/test-1.0-lin_x86_64.clib' must exist with correct content"
        new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib").exists()

    }

    def copy() {
        FileUtils.copyDirectory(new File("src/test/resources/distZipTest"), new File(projectDir, "build/tmp"))
    }

    def "check change packaging type to 'clib'"() {

        given: "a cpp project of type 'clibrabry' "
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


        when: "I launch the distZip task"
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then: "the produced archive must exist and  have 'clib' extension"
        new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib").exists()
        FilenameUtils.getExtension(new File(projectDir, "build/distributions/test-1.0-lin_x86_64.clib").name) == 'clib'

    }


    def "check change packaging type to 'capplication'"() {

        given: "a cpp project of type 'clibrabry' "
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


        when: "I launch the distZip task"
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()


        then: "the produced archive must exist and  have 'zip' extension"
        new File(projectDir, "build/distributions/test-1.0-lin_x86_64.zip").exists()
        FilenameUtils.getExtension(new File(projectDir, "build/distributions/test-1.0-lin_x86_64.zip").name) == 'zip'

    }


    def "check packaging without architecture information"() {

        given: "a cpp project of type 'capplication' version '1.0' without cla "
        project.with {
            apply plugin: "fr.echoes.gradle.cpp"
            version = "1.0"

            CppPluginExtension cpp = project.extensions["cpp"]

            cpp.with {
                applicationType "capplication"

            }
        }
        copy()


        when: "I launch the distZip task"
        project.evaluate()
        Zip distZip = project.tasks["distZip"]
        distZip.execute()

        then: "the produced archive must the base name 'test-1.0'"
        FilenameUtils.getBaseName(new File(projectDir, "build/distributions/test-1.0.zip").name) == 'test-1.0'

    }

    def cleanup() {
        println('Cleaning up after a test!')
        project.tasks["clean"].execute()
    }
}
