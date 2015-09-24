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

## Tasks

The cpp plugin adds a number of tasks to your project, as shown below.

**Table 1. Cpp plugin - tasks **

| Left align | Right align | Center align |
|:-----------|------------:|:------------:|
| This       |        This |     This     
| column     |      column |    column    
| will       |        will |     will     
| be         |          be |      be      
| left       |       right |    center    
| aligned    |     aligned |   aligned


------------------------------------------------
| Task name | Depends on | type | Description |
------------------------------------------------


+------------+---------+
|    Type    |  MySQL  |
+------------+---------+
| Header     | Top Row |
| Auto Align | On      |
+------------+---------+


╔═══╦════════════╦═════════════╗
║   ║ A          ║ B           ║
╠═══╬════════════╬═════════════╣
║ 1 ║ Type       ║ Unicode Art ║
║ 2 ║ Header     ║ Spreadsheet ║
║ 3 ║ Auto Align ║ Off         ║
╚═══╩════════════╩═════════════╝

 
    
    
    
    

    






