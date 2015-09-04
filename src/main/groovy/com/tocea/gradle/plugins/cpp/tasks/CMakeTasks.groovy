package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
class CMakeTasks extends  DefaultTask {


    @TaskAction
    void customCmake() {

        project.exec {
           commandLine = "echo"
            args = ${project.cpp.cmake.cmakeArgs}
        }

    }
}
