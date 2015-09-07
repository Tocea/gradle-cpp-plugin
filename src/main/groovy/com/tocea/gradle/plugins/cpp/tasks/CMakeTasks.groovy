package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
class CMakeTasks extends DefaultTask {

    def cmakeOutput
    def appArgs
    def userOutput

    @TaskAction
    void cmake() {

        appArgs = project.cpp.cmake."${name}Args"
        userOutput = project.cpp.cmake."${name}StandardOutput"

        project.exec {
            commandLine "echo"

            if (appArgs) {
                args appArgs.split('\\s')
            }
            if (userOutput) {
                standardOutput = userOutput
                cmakeOutput = userOutput
            }
        }

    }
}
