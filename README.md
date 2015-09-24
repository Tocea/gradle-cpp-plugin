# gradle-cpp-plugin
Gradle C/C++ plugin with C++ build tools interactions. This plugins launche C++ build tools and add Gradle capabilities like :

1. Dedendenciers management
2. Packaging
3. Upload
4. DAG of tasks
5. …

## Usage
To use the gradle-cpp-plugin, include the following in your build script:

**Exemple 1. Using the gradle-cpp-plugin**

**build.gradle**


```groovy
   
buildscript {
    repositories { 
        mavenLocal() // référerence au dépôt local qui contient le plugin
    }    
    dependencies{
	// identifiant unique du plugin
        classpath 'com.tocea.gradle.plugins:cpp-project-plugin:1.1.0-SNAPSHOT'
    }
}

apply plugin: "com.tocea.gradle.cpp"
```
## Source sets

The Cpp plugin is used to be used with this kind of structure folders

```
project
|-> build.gradle        // output folder
|-> build/
|-> src/
      |-> main/
            |-> headers/ //fichier hpp, h…
            |-> cpp/     // cpp,c files
     |-> test/
            |-> cpp/     // resources
```

## Tasks

The cpp plugin adds a number of tasks to your project, as shown below.

**Table 1. Cpp plugin - tasks**

| Task name        | Depends on      | Type    |  Description                                                    | 
| ---------------- | --------------  | ------- | --------------------------------------------------------------- |
| initOutputDirs   | -               | Task    | Initialize structure folders in project.buildDir directory      |
| downloadLibs     | initOutputDirs  | Task    | Copy project dependencies in project.buildDir/extLib directtory |
| compileCpp       | downloadLibs    | CppExecTask --> Exec    | compile source code. Need to be configured to launch the correct tool |
| testCompileCpp       | compileCpp    | CppExecTask --> Exec    | compile test source code. Need to be configured te launch the correct tool |
| testCpp       | testCompileCpp    | CppExecTask --> Exec    | jaunch test. Need to be configured to launcho the correct tool |
| distZip       | compileCpp    | Zip    | assemble the ZIP file it it's an c-application or a CLIB file tf it's a c-library |
| assemble       | all archives tassa as distJip    | Task    | Assembles the outputs of this project |
| check       | all tests taska as testCpp    | Task    | Assembles the outputs of this project |
| build       | check and assemble    | Task    | Assembles ant check this project |
| install       | build    | Upload    | upload the distZip archive in the local repository |
| uploadArchive       | build    | Upload    | upload the distZip archive in a remote repository |

**Figure 1. Cpp plugin -tasks**

![plugin tassks](images/cppPluginGraph.jpg)





 
    
    
    
    

    






