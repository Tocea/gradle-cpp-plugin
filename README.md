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
 
    
    
    
    

    






