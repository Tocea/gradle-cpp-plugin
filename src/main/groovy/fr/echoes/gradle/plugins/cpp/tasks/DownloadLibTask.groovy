package fr.echoes.gradle.plugins.cpp.tasks

import fr.echoes.gradle.plugins.cpp.CppPluginUtils
import org.apache.commons.io.FilenameUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * This class is used by the task 'downloadLibs'.
 *
 * <p>
 *     The downloadLibs download libraries from a known repository
 *     and place it in the 'build/extLib' directory.
 *
 *  <p>
 *   Usage exemple :
 *   <pre>
 *   compile  "fr.extern:sqlapi:4.1.4:lin_x86_64@clib"
 *   </pre>
 *   Or :
 *   <pre>
 *   compile group: "fr.extern", name: "sqlapi", version: "4.1.4", classifier: "lin_x86_64", ext: "clib"
 *   </pre>
 *   Or if you want to use an internal project dependency un multi-modules projects
 *   <pre>
 *    compile project(path: ":projectPath", configuration: "cArchives"
 *   </pre>
 *
 * Created by jguidoux on 03/09/15.
 */
public class DownloadLibTask extends DefaultTask {

    /**
     * The location where libraries are downloads.
     * <p>
     *  Default Value : 'build/extLib'
     */
    File extLibLocation

    /**
    * execute the task
    */
    @TaskAction
    void exec() {

        extLibLocation = new File(project.buildDir, CppPluginUtils.OUTPUT_DIRS[CppPluginUtils.EXT_LIB_DIR])
        def files = project.configurations.compile.files


        files.each { File file ->
            project.copy {

                from project.zipTree(file)
                println "archive ${file} has ${project.zipTree(file).files.size()} file"
                if (file.name.endsWith(CppPluginUtils.CLIB_EXTENSION)) {
                    into extLibLocation.path
                } else {
                    into getOutputLibDirectory(file).path
                }
            }
        }
    }

    def changeExtLibLocation(final String _newPath) {
        def newLocation = new File(project.buildDir, _newPath)
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
