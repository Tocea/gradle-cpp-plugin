package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.model.CMake
import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
import com.tocea.gradle.plugins.cpp.tasks.CustomTasks
import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTasks
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



       DownloadLibTasks dlTask = _project.task('downloadLibs', type: DownloadLibTasks, group: 'build')
       CMakeTasks cmake =  _project.task('cmake', type: CMakeTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)

        cmake.dependsOn dlTask


        DistributionContainer distrib = _project.extensions["distributions"]
        distrib["main"].contents {
            from "${_project.buildDir}/tmp"
        }

        Zip distZip = _project.tasks["distZip"]

        _project.extensions.create("cpp", CppPluginExtension)

        distZip.extension = "${_project.cpp.applicationType}"
        _project.afterEvaluate {
            // Access extension variables here, now that they are set
            configureArchive(_project, distZip)

        }

    }

    private void configureArchive(Project _project, Zip _distZip) {
        CppPluginExtension cpp = _project.extensions["cpp"]
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
}


class CppPluginExtension {
    def ApplicationType applicationType = ApplicationType.clibrary
    def String classifier = ""
    def CMake cmake = new CMake()
}

enum ApplicationType {
    clibrary, capplication
}
