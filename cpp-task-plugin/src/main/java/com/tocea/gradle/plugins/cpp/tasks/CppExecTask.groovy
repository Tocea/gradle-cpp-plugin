package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
class CppExecTask extends DefaultTask {

    String baseArgs = ""
    def cmakePath = "cmake"
    String appArguments = ""
    def cmakeOutput
    Map<String, ?> envVars


    @TaskAction
    void cmake() {

        initFields()

        def isWindows = System.properties['os.name'].toLowerCase().contains('windows')
        def commandLinePrefix = isWindows ? ['cmd', '/c'] : []
        project.exec {
            executable cmakePath
            commandLine commandLinePrefix + cmakePath
            String[] cmakeArgsArray = []
            if (baseArgs) {
                cmakeArgsArray += baseArgs.split('\\s')
            }
            if (appArguments) {
                cmakeArgsArray += appArguments.split('\\s')
            }
            args cmakeArgsArray
            if (envVars) {
                environment = envVars
            }
            if (cmakeOutput) {
                standardOutput = cmakeOutput
            }
        }

    }

    private void initFields() {
        if (project.cpp.exec.cmakePath) {
            cmakePath = project.cpp.exec.cmakePath
        }
        if (project.cpp.exec."${name}CMakePath") {
            cmakePath = project.cpp.exec."${name}CMakePath"
        }
        if (project.cpp.exec."${name}StandardOutput") {
            cmakeOutput = project.cpp.exec."${name}StandardOutput"
        }
        if (project.cpp.exec."${name}BaseArgs") {
            baseArgs = project.cpp.exec."${name}BaseArgs"
        }
        if (project.cpp.exec."${name}Args") {
            appArguments = project.cpp.exec."${name}Args"
        }
        if (project.cpp.exec.env) {
            envVars = project.cpp.exec.env
        }
    }
}
