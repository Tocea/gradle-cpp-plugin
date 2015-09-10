package com.tocea.gradle.plugins.cpp.configurations

import com.tocea.gradle.plugins.cpp.CppPluginExtension
import com.tocea.gradle.plugins.cpp.CppPluginUtils
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import org.gradle.api.Project
import org.gradle.api.distribution.DistributionContainer
import org.gradle.api.tasks.Upload
import org.gradle.api.tasks.bundling.Zip

/**
 * Created by jguidoux on 06/09/15.
 */
class ArchivesConfigurations {

    Project project

    def configureDistribution() {
        DistributionContainer distrib = project.extensions["distributions"]
        distrib["main"].contents {
            from "${project.buildDir}/tmp"
        }
    }

    def configureUpload(){
       Upload install =  project.tasks["install"]
        install.configuration = project.configurations.getByName("cArchives")
        Upload upload =  project.tasks["uploadArchives"]
        upload.configuration = project.configurations.getByName("cArchives")
    }

    def ConfigureDistZip() {
        Zip cppArchive = project.tasks["distZip"]
        configureArchive(cppArchive)
    }

    def configureArtifact() {
        project.configurations.create("cArchives")
        Zip cppArchive = project.tasks["distZip"]
        project.artifacts {

            project.artifacts.add("cArchives",  cppArchive)
        }
        configureUpload()
    }

    private void configureArchive(Zip _cppArchive) {
        CppPluginExtension cpp = project.extensions["cpp"]
        _cppArchive.classifier = cpp.classifier

        switch (cpp.applicationType) {
            case ApplicationType.clibrary:
                configureCLibrary(_cppArchive)
                break
            case ApplicationType.capplication:
                configureCApplcation(_cppArchive)
                break
            default:
                configureCLibrary(_cppArchive)
                break
        }
    }

    private def configureCApplcation(final Zip _zip) {
        _zip.extension = CppPluginUtils.ZIP_EXTENSION
    }

    private def configureCLibrary(final Zip _zip) {
        _zip.extension = CppPluginUtils.CLIB_EXTENSION
    }

    def initCppArchives() {
        Zip distZip = project.tasks["distZip"]
        distZip.extension = project.cpp.applicationType
    }
}
