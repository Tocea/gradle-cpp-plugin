package fr.echoes.gradle.plugins.cpp

import spock.lang.Specification


/**
 * Created by jguidoux on 07/09/15.
 */
class UtilsSpec extends Specification{

    class Person {
        def name
    }

    def "add age property to a class"() {

        given: "a person call 'halyna'"
        def person = new Person(name: "halyna")

        when: "I had dinamically the age attribute to 'halyna' ste at '2'"
        person.metaClass.age = 2

        then: "'halyna age must be '2'"
        person.age == 2
    }

    def "find properties number when added dinamcally"() {

        given: "a person call 'halyna'"
        def person = new Person(name: "halyna")

        when: "I had dinamically the age attribute to 'halyna' ste at '2'"
        person.metaClass.age = 2

        println "mon nom est ${person.name}"


        then: "'halyna properties must contain the 'age' attribute"
        person.properties.containsKey("age")
    }
}
