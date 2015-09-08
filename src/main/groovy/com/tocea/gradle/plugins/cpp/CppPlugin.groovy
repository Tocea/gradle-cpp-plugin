package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.configurations.ArchivesConfigurations
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import com.tocea.gradle.plugins.cpp.tasks.CMakeTasks
import com.tocea.gradle.plugins.cpp.tasks.CustomTasks
import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTasks
import com.tocea.gradle.plugins.cpp.tasks.ValidateCMakeProjectTask
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
        _project.task('downloadLibs', type: DownloadLibTasks, group: 'dependancies')
        _project.task('validateCMake', type: ValidateCMakeProjectTask, group: "validate")
        _project.task('customCmake', type: CMakeTasks, group: 'build')
        _project.task('compileCpp', type: CMakeTasks, group: 'build')
        _project.task('testCompileCpp', type: CMakeTasks, group: 'build')
        _project.task('testCpp', type: CMakeTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)

        configureBuildTasks(_project)

        configureTasksDependencies(_project)

    }

    def configureBuildTasks(Project _project) {
        CMakeTasks compileTask = _project.tasks["compileCpp"]
        compileTask.baseArgs = CppPluginUtils.COMPILE_CMAKE_BASE_ARG

        CMakeTasks testCompileTask = _project.tasks["testCompileCpp"]
        testCompileTask.baseArgs = CppPluginUtils.TEST_COMPILE_CMAKE_BASE_ARG

        CMakeTasks testTask = _project.tasks["testCpp"]
        testTask.baseArgs = CppPluginUtils.TEST_CMAKE_BASE_ARG

    }

    private configureTasksDependencies(Project _project) {
        _project.tasks["customCmake"].dependsOn _project.tasks["validateCMake"]
        _project.tasks["customCmake"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["compileCpp"].dependsOn _project.tasks["validateCMake"]
        _project.tasks["compileCpp"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["testCompileCpp"].dependsOn _project.tasks["compileCpp"]
        _project.tasks["testCpp"].dependsOn _project.tasks["testCompileCpp"]
        _project.tasks["check"].dependsOn _project.tasks["testCpp"]
        _project.tasks["assemble"].dependsOn _project.tasks["assembleDist"]
        _project.tasks["build"].dependsOn _project.tasks["assemble"]


        _project.tasks["distZip"].dependsOn _project.tasks["compileCpp"]
        _project.tasks["uploadArchives"].dependsOn _project.tasks["assembleDist"]
//        _project.tasks["assembleDist"].dependsOn.remove _project.tasks["distTar"]
//        _project.tasks["uploadArchives"].dependsOn.remove _project.tasks["distTar"]
//        _project.tasks["uploadArchives"].dependsOn.remove _project.tasks["distJar"]
        _project.tasks["uploadArchives"].dependsOn _project.tasks["build"]


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
            cmake.metaClass."${it.name}CMakePath" = ""
            cmake.metaClass."${it.name}BaseArgs" = ""
            cmake.metaClass."${it.name}Args" = ""
            cmake.metaClass."${it.name}StandardOutput" = null
        }
    }

}

class CMake {
    def cmakePath = "cmake"
    Map<String, ?> env

}
