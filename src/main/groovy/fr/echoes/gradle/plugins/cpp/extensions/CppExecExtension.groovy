package fr.echoes.gradle.plugins.cpp.extensions

import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * Created by jguidoux on 01/10/15.
 */
class CppExecExtension {
    def execPath = ""
    Map<String, ?> env

    CppExecExtension(Project _project) {



        TaskCollection tasks = _project.tasks.withType(CppExecTask)
        tasks.each {
            this.metaClass."${it.name}ExecPath" = ""
            this.metaClass."${it.name}BaseArgs" = null
            this.metaClass."${it.name}Args" = ""
            this.metaClass."${it.name}StandardOutput" = null
            this.metaClass."${it.name}ExecWorkingDir" = null
        }
    }
}

