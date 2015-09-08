package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
class CMakeTasks extends DefaultTask {

    def baseArgs = ""
    def cmakePath = "cmake"
    def appArgs = ""
    def cmakeOutput

    @TaskAction
    void cmake() {

        (appArgs, cmakeOutput) = initFields()

        project.exec {
            commandLine "echo"
            String[] cmakeArgsArray = []
            if (baseArgs) {
                cmakeArgsArray += baseArgs.split('\\s')
            }
            if (appArgs) {
                cmakeArgsArray += appArgs.split('\\s')
            }
            args cmakeArgsArray
            if (cmakeOutput) {
                standardOutput = cmakeOutput
            }
        }

    }

    private List initFields() {
        if (project.cpp.cmake.cmakePath) {
            cmakeOutput = project.cpp.cmake.cmakePath
        }
        if (project.cpp.cmake."${name}StandardOutput") {
            cmakeOutput = project.cpp.cmake."${name}StandardOutput"
        }
        appArgs = project.cpp.cmake."${name}Args"
        cmakeOutput = project.cpp.cmake."${name}StandardOutput"
        [appArgs, cmakeOutput]
    }
}
