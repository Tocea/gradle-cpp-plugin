package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 04/09/15.
 */
public class CppExecTask extends Exec {




//    @TaskAction
//    void exec() {
//
////        initFields()
//
//
//        def isWindows = System.properties['os.name'].toLowerCase().contains('windows')
//        def commandLinePrefix = isWindows ? ['cmd', '/c'] : []
//
//            executable execPath
//            commandLine commandLinePrefix + execPath
//            String[] cmakeArgsArray = []
//            if (execWorkingDir) {
//                workingDir = execWorkingDir
//            }
//            if (baseArgs) {
//                cmakeArgsArray += baseArgs.split('\\s')
//            }
//            if (appArguments) {
//                cmakeArgsArray += appArguments.split('\\s')
//            }
//            args cmakeArgsArray
//            if (envVars) {
//                environment = envVars
//            }
//            if (execOutput) {
//                standardOutput = execOutput
//            }
//
//
//
//    }
//
//
//    private void initFields() {
//        if (project.cpp.exec.execPath) {
//            execPath = project.cpp.exec.execPath
//        }
//        if (project.cpp.exec."${name}ExecPath") {
//            execPath = project.cpp.exec."${name}ExecPath"
//        }
//        if (project.cpp.exec."${name}StandardOutput") {
//            execOutput = project.cpp.exec."${name}StandardOutput"
//        }
//        if (project.cpp.exec."${name}BaseArgs" != null) {
//            baseArgs = project.cpp.exec."${name}BaseArgs"
//        }
//        if (project.cpp.exec."${name}Args") {
//            appArguments = project.cpp.exec."${name}Args"
//        }
//        if (project.cpp.exec."${name}ExecWorkingDir") {
//            execWorkingDir = project.cpp.exec."${name}ExecWorkingDir"
//        }
//        if (project.cpp.exec.env) {
//            envVars = project.cpp.exec.env
//        }
//    }
}
