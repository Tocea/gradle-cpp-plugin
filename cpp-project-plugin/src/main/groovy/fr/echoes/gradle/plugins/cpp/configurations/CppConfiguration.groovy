package fr.echoes.gradle.plugins.cpp.configurations

import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * Created by jguidoux on 23/09/15.
 */
class CppConfiguration {

    void configureCppExecTask(Project _project) {

        TaskCollection tasks = _project.tasks.withType(CppExecTask)
        tasks.each { CppExecTask task ->
            initFields(_project, task)
        }

    }

    private void initFields(Project _project, CppExecTask _task) {
        if (_project.cpp.exec.execPath) {
            def isWindows = System.properties['os.name'].toLowerCase().contains('windows')
            def commandLinePrefix = isWindows ? ['cmd', '/c'] : []
            _task.executable _project.cpp.exec.execPath
            _task.commandLine commandLinePrefix + _task.executable
        }
        if (_project.cpp.exec."${_task.name}ExecPath") {
            _task.executable _project.cpp.exec."${_task.name}ExecPath"
        }
        if (_project.cpp.exec."${_task.name}StandardOutput") {
            _task.standardOutput = new ByteArrayOutputStream()//_project.cpp.exec."${_task.name}StandardOutput"
        }
        def args = []
        if (_project.cpp.exec."${_task.name}BaseArgs" ) {
            args.addAll _project.cpp.exec."${_task.name}BaseArgs".split('\\s')
        }
        if (_project.cpp.exec."${_task.name}Args") {
            args.addAll _project.cpp.exec."${_task.name}Args".split('\\s')
        }
        _task.args args
        if (_project.cpp.exec."${_task.name}ExecWorkingDir") {
            _task.workingDir _project.cpp.exec."${_task.name}ExecWorkingDir"
        }
        if (_project.cpp.exec.env) {
            _task.environment _project.cpp.exec.env
        }
    }
}
