package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.configurations.ArchivesConfigurations
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import com.tocea.gradle.plugins.cpp.tasks.CppExecTasks
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


            if (_project.cpp.buildTasksEnabled) {
                configureBuildTasksDependencies(_project)
            }

        }

    }


    private void createTasks(Project _project) {
        _project.task('downloadLibs', type: DownloadLibTasks, group: 'dependancies')
        _project.task('validateCMake', type: ValidateCMakeProjectTask, group: "validate")
        _project.task('customCmake', type: CppExecTasks, group: 'build')
        _project.task('compileCpp', type: CppExecTasks, group: 'build')
        _project.task('testCompileCpp', type: CppExecTasks, group: 'build')
        _project.task('testCpp', type: CppExecTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)
        configureTasksDependencies(_project)

    }

    def configureBuildTasks(Project _project) {
        CppExecTasks compileTask = _project.tasks["compileCpp"]
        compileTask.baseArgs = CppPluginUtils.COMPILE_CMAKE_BASE_ARG

        CppExecTasks testCompileTask = _project.tasks["testCompileCpp"]
        testCompileTask.baseArgs = CppPluginUtils.TEST_COMPILE_CMAKE_BASE_ARG

        CppExecTasks testTask = _project.tasks["testCpp"]
        testTask.baseArgs = CppPluginUtils.TEST_CMAKE_BASE_ARG

    }

    private configureTasksDependencies(Project _project) {

        _project.tasks["assemble"].dependsOn _project.tasks["assembleDist"]
        _project.tasks["build"].dependsOn _project.tasks["assemble"]

        _project.tasks["uploadArchives"].dependsOn _project.tasks["build"]


    }

    def configureBuildTasksDependencies(final Project _project) {
        _project.tasks["customCmake"].dependsOn _project.tasks["validateCMake"]
        _project.tasks["customCmake"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["compileCpp"].dependsOn _project.tasks["validateCMake"]
        _project.tasks["compileCpp"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["testCompileCpp"].dependsOn _project.tasks["compileCpp"]
        _project.tasks["testCpp"].dependsOn _project.tasks["testCompileCpp"]
        _project.tasks["check"].dependsOn _project.tasks["testCpp"]
    }


}


class CppPluginExtension {

    boolean buildTasksEnabled = true
    ApplicationType applicationType = ApplicationType.clibrary
    String classifier = ""
    String extLibPath = CppPluginUtils.EXT_LIB_PATH
    CppExecConfiguration exec

    CppPluginExtension(Project _project) {
        exec = new CppExecConfiguration()
        TaskCollection tasks = _project.tasks.withType(CppExecTasks)
        tasks.each {
            exec.metaClass."${it.name}CMakePath" = ""
            exec.metaClass."${it.name}BaseArgs" = ""
            exec.metaClass."${it.name}Args" = ""
            exec.metaClass."${it.name}StandardOutput" = null
        }
    }

}

class CppExecConfiguration {
    def cmakePath = "cmake"
    Map<String, ?> env

}
