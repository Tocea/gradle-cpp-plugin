package com.tocea.gradle.plugins.cpp.configurations

import com.tocea.gradle.plugins.cpp.CppPluginUtils
import com.tocea.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * Created by jguidoux on 23/09/15.
 */
class CppConfiguraiton {

    void configureCppExecTask(Project _project) {

        TaskCollection tasks = _project.tasks.withType(CppExecTask)
        tasks.each { CppExecTask task ->
            initFields(_project, task)
        }

    }

    private void initFields(Project _project, CppExecTask _task) {
        if (_project.cpp.exec.execPath) {
            _task.execPath = _project.cpp.exec.execPath
        }
        if (_project.cpp.exec."${_task.name}ExecPath") {
            _task.execPath = _project.cpp.exec."${_task.name}ExecPath"
        }
        if (_project.cpp.exec."${_task.name}StandardOutput") {
            _task.execOutput = _project.cpp.exec."${_task.name}StandardOutput"
        }
        if (_project.cpp.exec."${_task.name}BaseArgs" != null) {
            _task.baseArgs = _project.cpp.exec."${_task.name}BaseArgs"
        }
        if (_project.cpp.exec."${_task.name}Args") {
            _task.appArguments = _project.cpp.exec."${_task.name}Args"
        }
        if (_project.cpp.exec."${_task.name}ExecWorkingDir") {
            _task.execWorkingDir = _project.cpp.exec."${_task.name}ExecWorkingDir"
        }
        if (_project.cpp.exec.env) {
            _task.envVars = _project.cpp.exec.env
        }
    }
}
