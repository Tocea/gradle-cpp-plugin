package fr.echoes.gradle.plugins.cpp.extensions

import fr.echoes.gradle.plugins.cpp.model.ApplicationType
import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * extension to configure the gradle-cpp-plugin.
 *
 * This extension can be used like this in build.gradle file.
 * <pre>
 *     cpp {
 *
 *     }
 * </pre>
 * Created by jguidoux on 01/10/15.
 */
class CppPluginExtension {

    def buildTasksEnabled = true
    ApplicationType applicationType = ApplicationType.clibrary
    String classifier = ""
    def outPutDirs = new HashMap()
    CppExecExtension exec

    CppPluginExtension( Project _project) {
        exec = new CppExecExtension(_project)

    }





}
