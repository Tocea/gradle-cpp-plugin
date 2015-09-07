package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
class CMakeTasks extends DefaultTask {

    def cmakeOutput
    def baseArgs = ""
    def appArgs = ""

    @TaskAction
    void cmake() {

        appArgs = project.cpp.cmake."${name}Args"
        def userOutput = project.cpp.cmake."${name}StandardOutput"

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
            if (userOutput) {
                standardOutput = userOutput
                cmakeOutput = userOutput
            }
        }

    }
}
