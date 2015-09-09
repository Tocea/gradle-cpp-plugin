package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.model.InvalidModelRuleDeclarationException

/**
 * Created by jguidoux on 08/09/15.
 */
class ValidateCMakeProjectTask extends DefaultTask {

    @TaskAction
    void valdateCmake() {
        if (!new File(project.projectDir, "CMakeLists.txt").exists()) {
            throw new InvalidModelRuleDeclarationException("file CMakeLists.txt must be present to use plugin com.tocea.gradle.cpp")
        }
        println "test"
    }

}
