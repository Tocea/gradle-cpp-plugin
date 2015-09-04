package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.internal.file.archive.ZipFileTree
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 03/09/15.
 */
class DownloadLibTasks extends DefaultTask {


    @OutputDirectory
    File extLibLocation = new File(project.buildDir,'extLib')


    @TaskAction
    void exec() {

        def files = project.configurations.compile.files
        files.each{ File file ->
            project.copy {

                from project.zipTree(file)
                into getOutputLibDirectory(file).path
            }
        }
    }

    def simpleFileName(file) {
        file.name.replaceFirst(~/\.[^\.]+$/, '')
    }

    def getOutputLibDirectory(file) {
        def simpleName = simpleFileName(file)
        new File(extLibLocation, simpleName)
    }
}
