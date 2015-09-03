package com.tocea.gradle.plugins.cpp

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by jguidoux on 03/09/15.
 */
class CppPlugin implements Plugin<Project> {


    @Override
    void apply(final Project _project) {
        _project.apply(plugin: 'base')
        _project.task('downloadLibs', type: DownloadLibTasks, group: 'build')
        _project.task('customTask', type: CustomTasks)

    }
}
