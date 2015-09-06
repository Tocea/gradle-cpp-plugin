package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.configurations.ArchivesConfigurations
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import com.tocea.gradle.plugins.cpp.model.CMake
import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
import com.tocea.gradle.plugins.cpp.tasks.CustomTasks
import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTasks
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by jguidoux on 03/09/15.
 */
class CppPlugin implements Plugin<Project> {


    @Override
    void apply(final Project _project) {
        _project.apply(plugin: 'distribution')
        _project.apply(plugin: 'maven')



        _project.task('downloadLibs', type: DownloadLibTasks, group: 'build')
        _project.task('customCmake', type: CMakeTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)

        _project.extensions.create("cpp", CppPluginExtension)

        ArchivesConfigurations archiveConf = new ArchivesConfigurations(project: _project)
        archiveConf.configureDistribution()
        archiveConf.initDistZip()

        configureTasksDependencies(_project)


        _project.afterEvaluate {
            // Access extension variables here, now that they are set
            archiveConf.ConfigureDistZip()
            archiveConf.configureArtifact()

        }

    }

    private configureTasksDependencies(Project _project) {
        _project.tasks["customCmake"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["uploadArchives"].dependsOn _project.tasks["assembleDist"]
    }
}


class CppPluginExtension {
    def ApplicationType applicationType = ApplicationType.clibrary
    def String classifier = ""
    def CMake cmake = new CMake()
}

