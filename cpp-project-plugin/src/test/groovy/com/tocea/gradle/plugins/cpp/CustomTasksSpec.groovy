package com.tocea.gradle.plugins.cpp

import com.tocea.gradle.plugins.cpp.tasks.CustomTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by jguidoux on 03/09/15.
 */
class CustomTasksSpec extends Specification {


    Project project = ProjectBuilder.builder().withProjectDir(new File("src/test/resources")).build()

    CustomTask custom = project.task('custom', type: CustomTask)


    def "download junit in extlib folder"() {


        when:

        custom.execute()

        then:

        1 == 1


    }

    def "should return 2 from first element of list"() {
        given:
        List<Integer> list = new ArrayList<>()
        when:
        list.add(1)
        then:
        1 == list.get(0)
    }


}
