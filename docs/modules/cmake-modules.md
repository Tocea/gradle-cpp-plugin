#Using cmake module

## Desrcription

This page will help you to write c++ projects using cmake easily.
Three modules are provided :

* `gradle-cpp-plugin' : the main component
* `gradle-cpp-cppUnitTest` : to compile and launch test
* `Find-cppUnitTest` : Use by `gradle-cppUnitTest` to check if [cppUnit](http://cppunit.sourceforge.net/doc/cvs/cppunit_cookbook.html) is well installed on your computer.

Thes modules can be find [here](https://github.com/Tocea/gradle-cpp-plugin/tree/master/modules/cmake_modules).

## The `gradle-cpp-plugin`

This module add this features :

* the sourceset `src/main/headers` and `src/main/cpp`. 
* the usage of gradle dependencies.
* the possibility to get values in a `.properties` file.

### The sourceSets

The plugin add two sourceSets `src/main/headers` and `src/main/cpp`. 

Files in `src/main/headers` should only contains headers files. Headers files will be copied in the folder `build/tmp/headers`. All files in this sourceSet will be consequently visible for other projects. If you want to hide a header file, put it in `src/main/cpp`. 

Files in `src/main/cpp` should only contains c++ or c files. Sources are placed in a CMake variable `${SOURCES}`. This variable is usefull to create libraries :
```
add_library (
    ${PROJECT_STATICNAME}
    STATIC
   ${SOURCES}        
)

add_library (
    ${PROJECT_NAME}
    SHARED
    ${SOURCES}
)
```
or executables :
```
add_executable(${PROJECT_NAME} ${SOURCES})
```


**Good practice :** sources files in a namespace should be in a folder with the same name. 
```
project
|__src/main/headers
            |___hello
                  |__Speaker.hpp
|__src/main/cpp
            |___hello
                |___Speaker.cpp
```

### Dependencies 

The module store dinamically all static libraries comming from gradle (ex : `compile 'foo.bar:my-library:1.0@clib`) and place it in a CMake variable `${STATIC_LIBRARIES}`.

Directly, in your code, you can use API from your dependencies. To link these externals libraries to your executable or your library. add this to your `CMakelists.txt` file. 
```
add_executable(<target_name> ${SOURCES})
```

### Read properties file

Properties file are used to store a list of `keys`, `values`. One exemple of this type of file is the file `gradle.properties`. 
```
group=fr.echoes.example
version=1.0.0-SNAPSHOT
```
The module give the possility to read this type of files and find values. One useful case is to get the project version. To do this, call the function `get_property_value(FilePath PropertiesKey output_variable)` 
* `FilePath` is the location of the propertie file
* `PropertiesKey` is the property key we want the value
* `output_variable` is the variable name to store the result.

**Example :**
```
get_property_value(${CMAKE_SOURCE_DIR}/gradle.properties "version" VERSION)
```
This line return for the previous `gradle.properties` the result `1.0.0-SNAPSHOT`. 
 

### Usage Example

```
cmake_minimum_required (VERSION 2.8)
project (hello-main)

# set the path to find modules
set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake_modules/")
# include the module gradle-cpp
include(gradle-cpp)
# do not include the module gradle-cpp-cppUnitTest
# because there are no tests in the projects
# or cppUnit is not installed in your computer
#include(gradle-cpp-cppUnitTest)


# read version of the project in the gradle.propersties file
get_property_value(${CMAKE_SOURCE_DIR}/gradle.properties "version" VERSION)

SET(PROJECT_NAME "hello-main-${VERSION}")

# create an executable call 'hello-main-1.0.0-SNAPSHOT
# this executable use the sources from 'src/main/java'
add_executable(${PROJECT_NAME} ${SOURCES})

# link to this executables all static libraries dependencies coming from gradle
target_link_libraries(${PROJECT_NAME} ${STATIC_LIBRARIES})
```

## The `gradle-cpp-cppUnitTest` module

The `gradle-ccp-cppUnitTest` module had this features :
* the sourcesets `src/test/headers` and `src/test/cpp`
* checking if cppUnit is install on your computer or not (the module does not install it)
* launch test (create a rule `test` for `make` for example)

## The `Find-cppUnitTest` module

The `find-cppUnitTest` is a module to check in cppUnit is installed on your computer. You don't have to use this module directly.
This module is called by the `gradle-cpp-cppUnitTest` module.

## See examples

Examples can be found in [here](https://github.com/Tocea/gradle-cpp-plugin/tree/master/exemples).

## Notes

I'm not an expert of cmake. These modules can be improved. You help is welcomed :). You can see the next cool features or issues to fix [here](https://github.com/Tocea/gradle-cpp-plugin/issues).
