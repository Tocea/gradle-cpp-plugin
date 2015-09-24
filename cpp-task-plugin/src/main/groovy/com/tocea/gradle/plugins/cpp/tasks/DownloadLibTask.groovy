package com.tocea.gradle.plugins.cpp.tasks

import com.tocea.gradle.plugins.cpp.CppPluginUtils
import org.apache.commons.io.FilenameUtils
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.PublishArtifactSet
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 03/09/15.
 */
public class DownloadLibTask extends DefaultTask {


//    @OutputDirectory
    File extLibLocation = new  File(project.buildDir, CppPluginUtils.OUTPUT_DIRS[CppPluginUtils.EXT_LIB_DIR])

//    @OutputFiles
//    def libs = extLibLocation.listFiles()

    @TaskAction
    void exec() {


        def files = project.configurations.compile.files

//        extLibLocation = new  File(project.cpp.extLibPath)
       // changeExtLibLocation(project.cpp.extLibPath)

        files.each { File file ->
            project.copy {

                from project.zipTree(file)
                println "archive ${file} has ${project.zipTree(file).files.size()} file"
                if (file.name.endsWith(CppPluginUtils.CLIB_EXTENSION)) {
                    into extLibLocation.path
                } else  {
                    into getOutputLibDirectory(file).path
                }
            }
        }
    }

    def changeExtLibLocation(final String _newPath) {
       def  newLocation = new  File(project.buildDir, _newPath)
        if (newLocation.absolutePath != extLibLocation.absolutePath) {
            extLibLocation.delete()
            extLibLocation = newLocation
        }

    }

    def getOutputLibDirectory(file) {
        def simpleName = FilenameUtils.getBaseName(file.name)
        new File(extLibLocation, simpleName)
    }
}
