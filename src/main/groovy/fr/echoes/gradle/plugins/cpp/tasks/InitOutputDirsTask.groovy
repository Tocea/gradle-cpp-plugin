package fr.echoes.gradle.plugins.cpp.tasks

import fr.echoes.gradle.plugins.cpp.CppPluginUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * This sask init build directories with directories.
 *
 * <p>
 *  Default directories struture is
 *  project/
 *  |___build/
 *      |___main-obj/
 *      |___test-obj/
 *      |___extLib/
 *      |___reports/
 *      |___docDir/
 *      |___tmp/
 *          |___headers/
 *          |___bin/
 *          |___lib/
 *          |___doc/
 *          |___doxyger/
 *
 *
 * Created by jguidoux on 08/09/15.
 */
public class InitOutputDirsTask extends DefaultTask {


    def outpoutDirs = CppPluginUtils.OUTPUT_DIRS
    def outpoutTmpDirs = CppPluginUtils.OUTPUT_TMP_DIRS

    @TaskAction
    void initOutputs() {
        def buildDirSet = new HashSet()
        outpoutDirs.each { k,v ->
            buildDirSet << new File(project.buildDir, v)
        }
        def tmpDir =  new File(project.buildDir,  CppPluginUtils.OUTPUT_DIRS[CppPluginUtils.TMP_DIR])
        outpoutTmpDirs.each { k,v ->
            buildDirSet << new File(tmpDir, v)
        }
        outpoutDirs[CppPluginUtils.TMP_DIR]
        buildDirSet.each { File file ->
            if (!file.exists()) {
                file.mkdirs()
            }
        }


    }

}
