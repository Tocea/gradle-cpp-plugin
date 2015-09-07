package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.configurations.ArchivesConfigurations
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import com.tocea.gradle.plugins.cpp.model.CMake
import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
import com.tocea.gradle.plugins.cpp.tasks.CustomTasks
import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTasks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * Created by jguidoux on 03/09/15.
 */
class CppPlugin implements Plugin<Project> {


    @Override
    void apply(final Project _project) {
        _project.apply(plugin: 'distribution')
        _project.apply(plugin: 'maven')



        createTasks(_project)

        _project.extensions.create("cpp", CppPluginExtension, _project)

        ArchivesConfigurations archiveConf = new ArchivesConfigurations(project: _project)
        archiveConf.configureDistribution()
        archiveConf.initDistZip()



        _project.afterEvaluate {
            // Access extension variables here, now that they are set
            archiveConf.ConfigureDistZip()
            archiveConf.configureArtifact()

        }

    }

    private void createTasks(Project _project) {
        _project.task('downloadLibs', type: DownloadLibTasks, group: 'build')
        _project.task('customCmake', type: CMakeTasks, group: 'build')
        _project.task('compileCpp', type: CMakeTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)

        configureBuildTasks(_project)

        configureTasksDependencies(_project)

    }

    def configureBuildTasks(Project _project) {
//        CMakeTasks compileTask = _project.tasks["compileCpp"]
//        compileTask.appArgs = "--compile cpp"

    }

    private configureTasksDependencies(Project _project) {
        _project.tasks["customCmake"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["uploadArchives"].dependsOn _project.tasks["assembleDist"]
    }


}


class CppPluginExtension {

    ApplicationType applicationType = ApplicationType.clibrary
    String classifier = ""
    String extLibPath = CppPluginUtils.EXT_LIB_PATH
    CMake cmake

    CppPluginExtension(Project _project) {
        cmake = new CMake()
        TaskCollection tasks = _project.tasks.withType(CMakeTasks)
        tasks.each {
            cmake.metaClass."${it.name}Args" = ""
            cmake.metaClass."${it.name}StandardOutput" = null
        }
    }



}

