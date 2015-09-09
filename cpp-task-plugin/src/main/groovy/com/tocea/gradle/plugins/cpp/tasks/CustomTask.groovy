package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 03/09/15.
 */
public class CustomTask extends DefaultTask  {

    @TaskAction
    showMessage() {
        println '------------showMessage-------------------'
    }
}


class GreetingPluginExtension {
    def String message = 'Hello from GreetingPlugin'
}
