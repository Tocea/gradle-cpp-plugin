package fr.echoes.gradle.plugins.cpp.extensions

import fr.echoes.gradle.plugins.cpp.model.ApplicationType
import org.gradle.api.Project

/**
 * Extension to configure the gradle-cpp-plugin.
 * <p>
 * This extension can be used like this in build.gradle file.
 * <p>
 * usage example :
 * <pre>
 *  cpp {
 *
 *    }
 * </pre>
 * <p>
 * Created by jguidoux on 01/10/15.
 */
class CppPluginExtension {

    /**
     * Enable build tasks ('compileCpp', 'testCompileCpp', 'testCpp').
     * <p>
     *     Default value : 'true'
     *  <p>
     *      Usage example:
     *  <pre>
     *  cpp.buildTasksEnabled = false
     *  </pre>
     *
     */
    def buildTasksEnabled = true

    /**
     * Set application type.
     * <p>
     *     possible values are :
     *     <ul>
     *         <li>'clibrary' : for library application (no main). Produce a '.clib' archive</li>
     *         <li>'capplication' : for runnable application. Produce a '.zip' archive</li>
     *      </ul>
     *  <p>
     *      Default value : 'clibrary'
     *   <p>
     *    Usage example:
     *    <pre>
     *    cpp.applicationType = "capplication"
     *    </pre>
     */
    ApplicationType applicationType = ApplicationType.clibrary

    /**
     * Set application specificity.
     * <p>
     *     An application for the same version can be build :
     *     <ul>
     *         <li>for a specific OS (Windows, linux, …)</li>
     *         <li>with a specific compilator with a specific version (gcc 5.2, g++ 4.4,… )</li>
     *         <li>for a specific architecture</li>
     *         <li>for a specific client with a specific configuration</li>
     *         <li>…</li>
     *     </ul>
     *  <p>
     *   Default value : ""
     *  <p>
     *   Usage example:
     *   <pre>
     *   cpp.classifier = "linux_gcc-5.2_x86_64"
     *   </pre>
     *
     */
    String classifier = ""

    def outPutDirs = new HashMap()

    /**
     * Set the configuration of all tasks of type {@link fr.echoes.gradle.plugins.cpp.tasks.CppExecTask}. ('compileCpp', 'testCompileCpp', 'testCpp')
     *  <p>
     *   Usage example:
     *  <pre>
     *  cpp.exec.with{
     *
     *   }
     *   </pre>
     */
    CppExecExtension exec

    CppPluginExtension(Project _project) {
        exec = new CppExecExtension(_project)

    }


}
