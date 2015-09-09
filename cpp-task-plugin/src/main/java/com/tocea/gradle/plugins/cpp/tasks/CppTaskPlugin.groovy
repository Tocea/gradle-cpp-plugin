package com.tocea.gradle.plugins.cpp.tasks

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by jguidoux on 03/09/15.
 */
class CppTaskPlugin implements Plugin<Project> {
	
	
	@Override
	void apply(final Project _project) {
		_project.apply(plugin: 'maven')
	}
}

