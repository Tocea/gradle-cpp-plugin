package com.tocea.gradle.plugins.cpp.configurations

import com.tocea.gradle.plugins.cpp.CppPluginExtension
import com.tocea.gradle.plugins.cpp.CppPluginUtils
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import org.gradle.api.Project
import org.gradle.api.distribution.DistributionContainer
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

    def ConfigureDistZip() {
        Zip distZip = project.tasks["distZip"]
        configureArchive(distZip)
    }

    def configureArtifact() {
        Zip distZip = project.tasks["distZip"]
        project.artifacts {

            project.artifacts.add("archives",  distZip)
        }
    }

    private void configureArchive(Zip _distZip) {
        CppPluginExtension cpp = project.extensions["cpp"]
        _distZip.classifier = cpp.classifier

        switch (cpp.applicationType) {
            case ApplicationType.clibrary:
                configureCLibrary(_distZip)
                break
            case ApplicationType.capplication:
                configureCApplcation(_distZip)
                break
            default:
                configureCLibrary(_distZip)
                break
        }
    }

    private def configureCApplcation(final Zip _zip) {
        _zip.extension = CppPluginUtils.ZIP_EXTENSION
    }

    private def configureCLibrary(final Zip _zip) {
        _zip.extension = CppPluginUtils.CLIB_EXTENSION
    }

    def initDistZip() {
        Zip distZip = project.tasks["distZip"]
        distZip.extension = project.cpp.applicationType
    }
}
