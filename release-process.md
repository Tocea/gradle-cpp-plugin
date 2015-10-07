# How to release the plugin 

## Prerequisite

This page use git-flow to process release. So you need to install it : https://www.atlassian.com/git/tutorials/comparing-workflows/centralized-workflow.

## Process

1. prepare the release with git flow.

  * `git flow release start <NEW_VERSION>`

2. update version in the file build.gradle to `<NEW_VERSION>`.

3. change some informations in `readme.md` file.

  1. change the branch name in the two first link in the two first lines.
  ```markdown
[![Build Status](https://travis-ci.org/Tocea/gradle-cpp-plugin.svg?branch=develop)](https://travis-ci.org/Tocea/gradle-cpp-plugin)
[![Coverage Status](https://coveralls.io/repos/Tocea/gradle-cpp-plugin/badge.svg?branch=develop)](https://coveralls.io/r/Tocea/gradle-cpp-plugin?branch=develop)
```
  must be replaced by :
  ```markdown
[![Build Status](https://travis-ci.org/Tocea/gradle-cpp-plugin.svg?branch=master)](https://travis-ci.org/Tocea/gradle-cpp-plugin)
[![Coverage Status](https://coveralls.io/repos/Tocea/gradle-cpp-plugin/badge.svg?branch=master)](https://coveralls.io/r/Tocea/gradle-cpp-plugin?branch=master)
```
  2. change the buildScript information
  ```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "gradle.plugin.fr.echoes.gradle.plugins:cpp-project-plugin:<NEW_VERSION>-SNAPSHOT"
  }
}
  ```
  must be replaced with :
  ```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.fr.echoes.gradle.plugins:cpp-project-plugin:<NEW_VERSION>"
  }
}
  ```
4. finish the release.
  * `git flow release finish`
5. Publish the `master` branch on *github*
6. publish on the graple plugin repository.
  1. To do that. you need first to have an account and well configured on the [gradle plugin repository](https://plugins.gradle.org/)
  2. checkout the tag produce by the release process : 
    * `git checkout cpp_<NEW_VERSION>`
  3. publish the plugin with the command : `./gradlew publishPlugins -Dgradle.publish.key=<YOUR_PUBLISH_KEY> -Dgradle.publish.secret=<YOUR_SECRET_KEY>`. The secret and the public key come from your gradle account --> [see documentation](https://plugins.gradle.org/docs/submit).

## Prepare the develop version

1. update the develop branch source for the next version : <NEXT_VERSION>.
2. change some informations in `readme.md` file.

  1. change the branch name in the two first link in the two first lines.

  ```markdown
[![Build Status](https://travis-ci.org/Tocea/gradle-cpp-plugin.svg?branch=master)](https://travis-ci.org/Tocea/gradle-cpp-plugin)
[![Coverage Status](https://coveralls.io/repos/Tocea/gradle-cpp-plugin/badge.svg?branch=master)](https://coveralls.io/r/Tocea/gradle-cpp-plugin?branch=master)
```
  must be replaced by :

  ```markdown
[![Build Status](https://travis-ci.org/Tocea/gradle-cpp-plugin.svg?branch=develop)](https://travis-ci.org/Tocea/gradle-cpp-plugin)
[![Coverage Status](https://coveralls.io/repos/Tocea/gradle-cpp-plugin/badge.svg?branch=develop)](https://coveralls.io/r/Tocea/gradle-cpp-plugin?branch=develop)
```
  2. change the buildScript information.

  ```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.fr.echoes.gradle.plugins:cpp-project-plugin:<NEXT_VERSION>"
  }
}
 ```
  must be replaced with :

  ```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "gradle.plugin.fr.echoes.gradle.plugins:cpp-project-plugin:<NEXT_VERSION>-SNAPSHOT"
  }
}
  ```


