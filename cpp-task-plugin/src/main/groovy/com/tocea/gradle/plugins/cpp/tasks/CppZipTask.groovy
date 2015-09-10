package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Zip

/**
 * Created by jguidoux on 08/09/15.
 */
public class CppZipTask extends Zip {


    @TaskAction
    def doArchive() {
        println "------------ execute cpp archives ------------"
//        super.execute()
    }


}
