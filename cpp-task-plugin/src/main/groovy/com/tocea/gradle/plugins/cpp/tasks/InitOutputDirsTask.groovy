package com.tocea.gradle.plugins.cpp.tasks

import com.tocea.gradle.plugins.cpp.CppPluginUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
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
