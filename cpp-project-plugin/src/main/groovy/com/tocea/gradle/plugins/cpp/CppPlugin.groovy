package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.configurations.ArchivesConfigurations
import com.tocea.gradle.plugins.cpp.model.ApplicationType
import com.tocea.gradle.plugins.cpp.tasks.CppExecTask
import com.tocea.gradle.plugins.cpp.tasks.CustomTask
import com.tocea.gradle.plugins.cpp.tasks.DownloadLibTask
import com.tocea.gradle.plugins.cpp.tasks.ValidateCMakeProjectTask
import com.tocea.gradle.plugins.cpp.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * Created by jguidoux on 03/09/15.
 */
class CppPlugin implements Plugin<Project> {


    @Override
    void apply(final Project _project) {
        _project.apply(plugin: 'maven')

        createTasks(_project)

        _project.extensions.create("cpp", CppPluginExtension, _project)
        projectConfigurations(_project)

    }

    private void projectConfigurations(Project _project) {
            _project.configurations.create("compile")
            _project.configurations.compile.transitive = false
    }


    private void createTasks(Project _project) {
        _project.task('downloadLibs', type: DownloadLibTask, group: 'dependencies')
        _project.task('buildProject', type: CustomTask)
		_project.task('copyHeaders', type: CustomTask)
		_project.task('copyArchive', type: CustomTask)
		_project.task('createPackaging', type: CreateEmptyPackagingTask)
		_project.task('zipPackaging', type: CustomTask)
        configureBuildTasks(_project)
        configureTasksDependencies(_project)

    }

    def configureBuildTasks(Project _project) {
        CustomTask compileTask = _project.tasks["buildProject"]
        //compileTask.baseArgs = CppPluginUtils.COMPILE_CMAKE_BASE_ARG
    }

    private configureTasksDependencies(Project _project) {
        
		_project.tasks["copyHeaders"].dependsOn _project.tasks["downloadLibs"], _project.tasks["createPackaging"]  		
        _project.tasks["buildProject"].dependsOn _project.tasks["downloadLibs"]
		_project.tasks["buildProject"].dependsOn _project.tasks["copyHeaders"]
		_project.tasks["copyArchive"].dependsOn _project.tasks["buildProject"]		
		_project.tasks["zipPackaging"].dependsOn _project.tasks["assemble"],_project.tasks["copyArchive"]
		_project.tasks["build"].dependsOn _project.tasks["zipPackaging"]
        _project.tasks["uploadArchives"].dependsOn _project.tasks["build"]


    }




}


class CppPluginExtension {

    def buildTasksEnabled = true
    ApplicationType applicationType = ApplicationType.clibrary
    String classifier = ""
    String extLibPath = CppPluginUtils.EXT_LIB_PATH
    CppExecConfiguration exec
	String packagingPath = "build/packaging"
	String tmpPath = "build/tmp"
	String headerPath = "build/tmp/headers"
	String libPath = "build/tmp/lib"
	String binPath = "build/tmp/bin"

    CppPluginExtension(Project _project) {
        exec = new CppExecConfiguration()
       
    }

}

class CppExecConfiguration {
    def execPath = "cmake"
    Map<String, ?> env

}
