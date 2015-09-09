package com.tocea.gradle.plugins.cpp.tasks

import com.tocea.gradle.plugins.cpp.CppPluginUtils
import org.apache.commons.io.FilenameUtils
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 03/09/15.
 */
public class DownloadLibTask extends DefaultTask {


    @OutputDirectory
    File extLibLocation = new  File(project.projectDir, CppPluginUtils.EXT_LIB_PATH)


    @TaskAction
    void exec() {

        def files = project.configurations.compile.files

//        extLibLocation = new  File(project.cpp.extLibPath)
        changeExtLibLocation(project.cpp.extLibPath)

        files.each { File file ->
            project.copy {

                from project.zipTree(file)
                into getOutputLibDirectory(file).path
            }
        }
    }

    def changeExtLibLocation(final String _newPath) {
        checkPathValidity(_newPath)
       def  newLocation = new  File(_newPath)
        if (newLocation.absolutePath != extLibLocation.absolutePath) {
            extLibLocation.delete()
            extLibLocation = newLocation
        }

    }

    def checkPathValidity(final String _path) {
        def path = FilenameUtils.getPath(_path)
        if (!path) {
            throw  new InvalidUserDataException("path ${path} is not a valid path")
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
