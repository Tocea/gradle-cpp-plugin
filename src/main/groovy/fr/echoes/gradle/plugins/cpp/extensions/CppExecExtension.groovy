package fr.echoes.gradle.plugins.cpp.extensions

import fr.echoes.gradle.plugins.cpp.tasks.CppExecTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskCollection

/**
 * This class set the configurations of all tasks of type {@link CppExecTask}.
 *
 * <p>
 *     In addition of basic properties, dynamically, some others properties are added.
 *     For each task of type {@link CppExecTask}, these properties are addes :
 *     <ul>
 *         <li>${task.name}ExecPath : command path te execute for this task</li>
 *         <li>${task.name}BaseArgs : arguments of the command of this task</li>
 *         <li>${task.name}Args : more arguments for the command of this task</li>
 *         <li>${task.name}StandardOutput : output of type {@link OutputStream} for the command of this task</li>
 *         <li>${task.name}ExecPath : location to execute the command for this task</li>
 *       </ul>
 *  <p>
 *   Usage example
 *   <pre>
 *    cpp.exec.with{
 *
 *    }
 *    </pre>
 *    <p>
 * Created by jguidoux on 01/10/15.
 */
class CppExecExtension {

    /**
     * The default command path to execute for each task of type {@link CppExecTask}
     * if they don't override it with the property ${task.name}ExecPath}
     *
     * <p>
     *    Usage example
     *   <pre>
     *    cpp.exec.with{
     *        execPath = "make"
     *    }
     *   </pre>
     *
     */
    def execPath = ""

    /**
     * Set the environment variable for the command execution for each task of type {@link CppExecTask}
     *  <p>
     *    Usage example
     *   <pre>
     *    cpp.exec.with{
     *        env = [JAVA_HOME: "/usr/lib/jvm/java-8-oracle"]
     *    }
     *   </pre>
     *
     */
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

