package com.tocea.gradle.plugins.cpp.tasks

import org.apache.commons.io.FileUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by jguidoux on 03/09/15.
 */
public class CreateEmptyPackagingTask extends DefaultTask  {
	
	
	@TaskAction
	def buildPackaging() {
		
		
		def packagingPath = project.file(project.cpp.tmpPath);
		println "Creating empty packaging in $packagingPath"
		FileUtils.deleteDirectory packagingPath
		packagingPath.mkdirs()
		new File(packagingPath, "bin").mkdirs()
		new File(packagingPath, "lib").mkdirs()
		new File(packagingPath, "include").mkdirs()
		File docDir = new File(packagingPath, "doc")
		docDir.mkdirs()
		File reportDir = new File(packagingPath, "reports")
		reportDir.mkdirs()
		new File(reportDir, "doxygen").mkdirs()
	}
}

