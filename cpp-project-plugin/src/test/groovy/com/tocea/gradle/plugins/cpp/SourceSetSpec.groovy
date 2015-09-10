package com.tocea.gradle.plugins.cpp

import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.language.base.ProjectSourceSet
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by jguidoux on 10/09/15.
 */
class SourceSetSpec extends Specification{


    @Rule
    TemporaryFolder tempFolder

    @Shared
    def projectDir

    @Shared
    Project project

    def setup() {
        projectDir = tempFolder.newFolder("sourceSetProjectTest")
        project = ProjectBuilder.builder().withProjectDir(projectDir).build()
    }

//    @Ignore
//    def "test get sourcset convention"() {
//        given:
//        project.with {
//            apply plugin: "com.tocea.gradle.cpp"
//        }
//        project.getPluginManager().apply(BasePlugin.class);
//        when:
////        final ProjectSourceSet projectSourceSet = project.getExtensions().getByType(ProjectSourceSet.class);
//
//
//        then:
//        projectSourceSet != null
//    }
}
