package fr.echoes.gradle.plugins.cpp.tasks

import org.gradle.api.tasks.Exec

/**
 * This class is use for build tasks for the gradle-cpp-plugin.
 * <p>
 *  Existing CppExecTask are 'compileCpp', 'testCompileCpp', 'testCpp'.
 *  <p>
 *      User can create this CppExecTask like this :
 *   <pre>
 *       task testIntegration(type: CppExecTask)
 *       testIntegration.dependsOn testCpp
 *       check.dependsOn testIntegration
 *   </pre>
 *       User can configure this task directly. CppExecTask is of type {@link Exec},
 *       so, all properties of {@link Exec} class can be used.
 *   <pre>
 *     task testIntegration(type: CppExecTask) {
 *       workingDir = "build/test-obj"
 *       executable = "make"
 *       args  "IT"
 *     }
 *     </pre>
 *
 *    Or in the configuration of the plugin
 *  <pre>
 *      cpp.exec.with {
 *          testIntegrationExecPath = "make"
 *          testIntegrationExecWorkingDir = "build/test-obj"
 *          testIntegrationBaseArgs = "IT"
 *      }
 *   </pre>
 *
 *   <p>
 *  Created by jguidoux on 04/09/15.
 *   <p>
 *   @see org.gradle.api.tasks.Exec
 *
 *
 *
 */
public class CppExecTask extends Exec {


}
