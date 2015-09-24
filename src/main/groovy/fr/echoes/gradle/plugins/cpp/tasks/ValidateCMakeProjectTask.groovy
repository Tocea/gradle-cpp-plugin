package fr.echoes.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 08/09/15.
 */
public class ValidateCMakeProjectTask extends DefaultTask {

    @TaskAction
    void valdateCmake() {
//        if (!new File(project.projectDir, "CMakeLists.txt").exists()) {
//            throw new InvalidModelRuleDeclarationException("file CMakeLists.txt must be present to use plugin com.tocea.gradle.cpp")
//        }
//        println "test"
    }

}
