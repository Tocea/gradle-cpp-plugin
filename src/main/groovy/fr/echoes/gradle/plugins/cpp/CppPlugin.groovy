package fr.echoes.gradle.plugins.cpp

import fr.echoes.gradle.plugins.cpp.configurations.ArchivesConfigurations
import fr.echoes.gradle.plugins.cpp.configurations.CppConfiguration
import fr.echoes.gradle.plugins.cpp.extensions.CppPluginExtension
import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import fr.echoes.gradle.plugins.cpp.tasks.DownloadLibTask
import fr.echoes.gradle.plugins.cpp.tasks.InitOutputDirsTask
import fr.echoes.gradle.plugins.cpp.tasks.ValidateCMakeProjectTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.plugins.MavenRepositoryHandlerConvention
import org.gradle.api.tasks.Copy
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


        projectConfigurations(_project)
        createTasks(_project)



        _project.extensions.create("cpp", CppPluginExtension, _project)
        ArchivesConfigurations archiveConf = new ArchivesConfigurations(project: _project)
        def cppConfiguration = new CppConfiguration()
        archiveConf.configureDistribution()
        archiveConf.initCppArchives()


        _project.afterEvaluate {
            // Access extension variables here, now that they are set

            archiveConf.ConfigureDistZip()
            archiveConf.configureArtifact()
            configureTasks(_project)
            cppConfiguration.configureCppExecTask(_project)




            def enabled = _project.cpp.buildTasksEnabled
            if (enabled) {
                configureBuildTasksDependencies(_project)
            } else {
                println "no build tasks"
            }
            List dependentProjects = findDependentProjects(_project)
            _project.tasks.downloadLibs.dependsOn dependentProjects.tasks.build

        }

    }

    private configureTasks(Project _project) {

        if (_project.cpp.outPutDirs) {
            CppPluginUtils.OUTPUT_DIRS << _project.cpp.outPutDirs
            _project.tasks.initOutputDirs.outpoutDirs = CppPluginUtils.OUTPUT_DIRS
            if (CppPluginUtils.OUTPUT_DIRS[CppPluginUtils.EXT_LIB_DIR]) {
                DownloadLibTask dlTask = _project.tasks.downloadLibs
                dlTask.changeExtLibLocation(CppPluginUtils.OUTPUT_DIRS[CppPluginUtils.EXT_LIB_DIR])
            }
        }

       Copy copyHeaders =  _project.tasks.copyHeaders
        copyHeaders.from(CppPluginUtils.SOURCE_HEADERS)
        copyHeaders.into("${_project.buildDir}" +
                "/${CppPluginUtils.OUTPUT_DIRS[CppPluginUtils.TMP_DIR]}" +
                "/${CppPluginUtils.OUTPUT_TMP_DIRS.headers}")
    }

    private void projectConfigurations(Project _project) {
        _project.configurations.create("compile")
        _project.configurations.compile.transitive = false
    }


    private void createTasks(Project _project) {
        _project.task('downloadLibs', type: DownloadLibTask, group: 'dependancies')
        _project.task('validateCMake', type: ValidateCMakeProjectTask, group: "validate")
        _project.task('initOutputDirs', type: InitOutputDirsTask, group: "init")
        _project.task('copyHeaders', type: Copy)
        _project.task('compileCpp', type: CppExecTask, group: 'build')
        _project.task('testCompileCpp', type: CppExecTask, group: 'build')
        _project.task('testCpp', type: CppExecTask, group: 'build')
        _project.task('cppArchive', type: Zip, group: 'archives')
        configureInstall(_project)
        configureTasksDependencies(_project)

    }


    private configureTasksDependencies(Project _project) {


        _project.tasks.downloadLibs.dependsOn _project.tasks.initOutputDirs


        _project.tasks.cppArchive.dependsOn _project.tasks.downloadLibs
        _project.tasks.assemble.dependsOn _project.tasks.cppArchive
        _project.tasks.install.dependsOn _project.tasks.assemble
        _project.tasks.uploadArchives.dependsOn _project.tasks.build

    }

    def configureBuildTasksDependencies(final Project _project) {
        _project.tasks.compileCpp.dependsOn _project.tasks.validateCMake
        _project.tasks.compileCpp.dependsOn _project.tasks.downloadLibs
        _project.tasks.testCompileCpp.dependsOn _project.tasks.compileCpp
        _project.tasks.testCpp.dependsOn _project.tasks.testCompileCpp
        _project.tasks.check.dependsOn _project.tasks.testCpp
        _project.tasks.cppArchive.dependsOn _project.tasks.copyHeaders
        _project.tasks.cppArchive.dependsOn _project.tasks.compileCpp
    }

    private List findDependentProjects(Project _project) {
        def projectDependencies = _project.configurations.compile.allDependencies.withType(ProjectDependency)
        def dependentProjects = projectDependencies*.dependencyProject
        dependentProjects
    }

    private void configureInstall(Project project) {
        Upload installUpload = project.tasks.create("install", Upload);
        MavenRepositoryHandlerConvention repositories =
                new DslObject(installUpload.repositories).convention.getPlugin(MavenRepositoryHandlerConvention);
        repositories.mavenInstaller()
        installUpload.description = "Installs the 'archives' artifacts into the local Maven repository."
    }




}




