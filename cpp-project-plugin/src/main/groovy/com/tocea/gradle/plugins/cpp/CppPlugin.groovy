package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.configurations.ArchivesConfigurations
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import com.tocea.gradle.plugins.cpp.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.MavenPlugin
import org.gradle.api.plugins.MavenRepositoryHandlerConvention
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.Upload
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

        MavenPlugin


        projectConfigurations(_project)
        createTasks(_project)



        _project.extensions.create("cpp", CppPluginExtension, _project)
        ArchivesConfigurations archiveConf = new ArchivesConfigurations(project: _project)
        archiveConf.configureDistribution()
        archiveConf.initCppArchives()


        _project.afterEvaluate {
            // Access extension variables here, now that they are set

            archiveConf.ConfigureDistZip()
            archiveConf.configureArtifact()



            def enabled = _project.cpp.buildTasksEnabled
            if (enabled) {
                configureBuildTasksDependencies(_project)
            } else {
                println "no build tasks"
            }

            List dependentProjects = findDependentProjects(_project)
            println "project ${_project.name} has project dependencies $dependentProjects"
            _project.tasks["downloadLibs"].dependsOn dependentProjects.tasks["build"]

        }

    }

    private void projectConfigurations(Project _project) {
        _project.configurations.create("compile")
        _project.configurations.compile.transitive = false
    }


    private void createTasks(Project _project) {
        _project.task('downloadLibs', type: DownloadLibTask, group: 'dependancies')
        _project.task('validateCMake', type: ValidateCMakeProjectTask, group: "validate")
        _project.task('initOutputDirs', type: InitOutputDirsTask, group: "init")
        _project.task('customExec', type: CppExecTask, group: 'build')
        _project.task('compileCpp', type: CppExecTask, group: 'build')
        _project.task('testCompileCpp', type: CppExecTask, group: 'build')
        _project.task('testCpp', type: CppExecTask, group: 'build')
        _project.task('cppArchive', type: Zip, group: 'archives')
        _project.task('customTask', type: CustomTask)
        configureInstall(_project)
        configureBuildTasks(_project)
        configureTasksDependencies(_project)

    }

    def configureBuildTasks(Project _project) {
        CppExecTask compileTask = _project.tasks["compileCpp"]
        compileTask.baseArgs = CppPluginUtils.COMPILE_CMAKE_BASE_ARG

        CppExecTask testCompileTask = _project.tasks["testCompileCpp"]
        testCompileTask.baseArgs = CppPluginUtils.TEST_COMPILE_CMAKE_BASE_ARG

        CppExecTask testTask = _project.tasks["testCpp"]
        testTask.baseArgs = CppPluginUtils.TEST_CMAKE_BASE_ARG

    }

    private configureTasksDependencies(Project _project) {


        _project.tasks["cppArchive"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["assemble"].dependsOn _project.tasks["cppArchive"]
        _project.tasks["install"].dependsOn _project.tasks["assemble"]
        _project.tasks["build"].dependsOn _project.tasks["install"]
        _project.tasks["uploadArchives"].dependsOn _project.tasks["build"]

    }

    def configureBuildTasksDependencies(final Project _project) {
        _project.tasks["customExec"].dependsOn _project.tasks["validateCMake"]
        _project.tasks["customExec"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["compileCpp"].dependsOn _project.tasks["validateCMake"]
        _project.tasks["compileCpp"].dependsOn _project.tasks["downloadLibs"]
        _project.tasks["testCompileCpp"].dependsOn _project.tasks["compileCpp"]
        _project.tasks["testCpp"].dependsOn _project.tasks["testCompileCpp"]
        _project.tasks["check"].dependsOn _project.tasks["testCpp"]
        _project.tasks["cppArchive"].dependsOn _project.tasks["compileCpp"]
    }

    private void configureInstall(Project project) {
        Upload installUpload = project.tasks.create("install", Upload);
        MavenRepositoryHandlerConvention repositories =
                new DslObject(installUpload.repositories).convention.getPlugin(MavenRepositoryHandlerConvention);
        repositories.mavenInstaller()
        installUpload.description = "Installs the 'archives' artifacts into the local Maven repository."
    }

    private List findDependentProjects(Project _project) {
        def projectDependencies = _project.configurations.compile.allDependencies.withType(ProjectDependency)
        println projectDependencies
        def dependentProjects = projectDependencies*.dependencyProject
        dependentProjects
    }


}


class CppPluginExtension {

    def buildTasksEnabled = true
    ApplicationType applicationType = ApplicationType.clibrary
    String classifier = ""
    String extLibPath = CppPluginUtils.EXT_LIB_PATH
    CppExecConfiguration exec

    CppPluginExtension(Project _project) {
        exec = new CppExecConfiguration()
        TaskCollection tasks = _project.tasks.withType(CppExecTask)
        tasks.each {
            exec.metaClass."${it.name}ExecPath" = ""
            exec.metaClass."${it.name}BaseArgs" = null
            exec.metaClass."${it.name}Args" = ""
            exec.metaClass."${it.name}StandardOutput" = null
        }
    }


}

class CppExecConfiguration {
    def execPath = "cmake"
    Map<String, ?> env

}
