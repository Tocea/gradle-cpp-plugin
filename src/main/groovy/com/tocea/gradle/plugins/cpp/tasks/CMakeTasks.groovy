package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
class CMakeTasks extends DefaultTask {

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
        if (project.cpp.cmake.cmakePath) {
            cmakePath = project.cpp.cmake.cmakePath
        }
        if (project.cpp.cmake."${name}CMakePath") {
            cmakePath = project.cpp.cmake."${name}CMakePath"
        }
        if (project.cpp.cmake."${name}StandardOutput") {
            cmakeOutput = project.cpp.cmake."${name}StandardOutput"
        }
        if (project.cpp.cmake."${name}BaseArgs") {
            baseArgs = project.cpp.cmake."${name}BaseArgs"
        }
        if (project.cpp.cmake."${name}Args") {
            appArguments = project.cpp.cmake."${name}Args"
        }
        if (project.cpp.cmake.env) {
            envVars = project.cpp.cmake.env
        }
    }
}
