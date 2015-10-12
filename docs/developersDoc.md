# Developers documentation

## Used technologies

* Groovy
* Gradle (build tool)
* Spock (unit test)
* Jacoco (code coverage)

## Servers Urls

* Source repository -> Github : https://github.com/Tocea/gradle-cpp-plugin
* Gradle plugin repository -> https://plugins.gradle.org/plugin/fr.echoes.gradle.cpp
* Continious integration server -> Travis : https://travis-ci.org/ (connection with your github account
* Project web site -> http://tocea.github.io/gradle-cpp-plugin/
* User documentation -> Just a readme at this time : https://github.com/Tocea/gradle-cpp-plugin/blob/master/README.md'
* Release process documentation -> https://github.com/Tocea/gradle-cpp-plugin/blob/master/release-process.md
* Groovydoc -> http://tocea.github.io/gradle-cpp-plugin/groovydoc/1.2.8/
* test reports -> http://tocea.github.io/gradle-cpp-plugin/spock-reports/1.2.8/
* Code coverage -> https://coveralls.io/github/Tocea/gradle-cpp-plugin

## Notes

GroovyDoc and test reports are publish in githbub from a Travis build. This upload is done with the file [update-gh-pages.sh] (https://github.com/Tocea/gradle-cpp-plugin/blob/master/update-gh-pages.sh).
It could be a good idea to create a page `latest` for the different documentations. ex : http://tocea.github.io/gradle-cpp-plugin/groovydoc/latest/

