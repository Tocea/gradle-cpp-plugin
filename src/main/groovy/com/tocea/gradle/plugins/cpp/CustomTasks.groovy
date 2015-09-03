package com.tocea.gradle.plugins.cpp

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 03/09/15.
 */
class CustomTasks extends DefaultTask  {

    @TaskAction
    showMessage() {
        println '------------showMessage-------------------'
    }
}
