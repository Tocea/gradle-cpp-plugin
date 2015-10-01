package fr.echoes.gradle.plugins.cpp.extensions

import fr.echoes.gradle.plugins.cpp.CppPluginUtils
import fr.echoes.gradle.plugins.cpp.model.ApplicationType
import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * Created by jguidoux on 01/10/15.
 */
class CppPluginExtension {

    def buildTasksEnabled = true
    ApplicationType applicationType = ApplicationType.clibrary
    String classifier = ""
    def outPutDirs = new HashMap()
    CppExecExtension exec

    private  Project project
    CppPluginExtension(Project _project) {
        project = _project
        exec = new CppExecExtension()
        TaskCollection tasks = _project.tasks.withType(CppExecTask)
        tasks.each {
            exec.metaClass."${it.name}ExecPath" = ""
            exec.metaClass."${it.name}BaseArgs" = null
            exec.metaClass."${it.name}Args" = ""
            exec.metaClass."${it.name}StandardOutput" = null
            exec.metaClass."${it.name}ExecWorkingDir" = null
        }
    }





}
