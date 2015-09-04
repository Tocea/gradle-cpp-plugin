package com.tocea.gradle.plugins.cpp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.distribution.DistributionContainer
import org.gradle.api.tasks.bundling.Zip




/**
 * Created by jguidoux on 03/09/15.
 */
class CppPlugin implements Plugin<Project> {




    @Override
    void apply(final Project _project) {
        _project.apply(plugin: 'base')
        _project.apply(plugin: 'distribution')
        _project.apply(plugin: 'maven')



        _project.task('downloadLibs', type: DownloadLibTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)


        DistributionContainer distrib = _project.extensions.getByType(DistributionContainer)
        distrib.getByName("main").contents { from "build/tmp" }

        Zip distZip = _project.tasks["distZip"]
        distZip.classifier = "lin_x86_64"

        _project.extensions.create("cpp", CppPluginExtension)

        distZip.extension = "${_project.cpp.applicationType}"
        _project.afterEvaluate {
            // Access extension variables here, now that they are set
            configureArchive(_project, distZip)

        }

    }

    private void configureArchive(Project _project, Zip _distZip) {
        CppPluginExtension cpp = _project.extensions["cpp"]
        _distZip.classifier = cpp.archictecture

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
}


class CppPluginExtension {
    def ApplicationType applicationType = ApplicationType.clibrary
    def String archictecture = "UNKNOWN_ARCHITECTURE"
}

enum ApplicationType {
    clibrary, capplication
}
