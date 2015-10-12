#Using cmake module

## Desrcription

This page describe will help you to use to write c++ projects using cmake simply.
Three modules are provided :

* 'gradle-cpp-plugin' : the main component
* 'gradle-cpp-cppUnitTest' : to compile and launch test
* 'Find-cppUnitTest' : Use by 'gradle-cppUnitTest' to check if [cppUnit](http://cppunit.sourceforge.net/doc/cvs/cppunit_cookbook.html) is well installed on your computer.

## The 'gradle-cpp-plugin'

This module add this features :

* the sourceset 'src/main/headers' and 'src/main/cpp'. 
* the usage of gradle dependencies.
* the possibility to get values in a '.properties' file

### The sourceset

The plugin add two sourceset 'src/main/headers' and 'src/main/cpp'. 

Files in 'src/main/headers' should only contains headers files. Headers files will be copied in the folder 'build/tmp/headers'. All files in this sourcset will be consequently visible for other projects. If you want to hide a header file, put it in 'src/main/cpp'. 

Files in 'src/main/cpp' should only contains c++ or c files. Sources are placed in a CMake variable ${SOURCES}. This variable is usefull to create libraries :
'''
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
'''
or executables :
'''
add_executable(${PROJECT_NAME} ${SOURCES})
'''


**Good practice :** files in a namespace should be in folder with the same name. 

### Dependencies 

The module store dinamically all static libraries comming from gradle (ex : 'compile 'foo.bar:my-library:1.0@clib') and place it in a CMake variable '${STATIC_LIBRARIES}'.

Directly, in your code, you can use API from your dependencies. To link these externals libraries to your executable or your library. add this to your 'CMakelists.txt' file. 
'''
add_executable(<target_name> ${SOURCES})
'''

### Read properties file

Properties file are used to store a list of 'keys', 'values'. One exemple of this type of file is the file 'gradle.properties'. 
'''
group=fr.echoes.example
version=1.0.0-SNAPSHOT
'''
The module give the possility to read this type of files and find values. One useful case is to get the project version. To do this, call the function get_property_value(FilePath PropertiesKey output_variable)
* FilePath is the location of the propertie file
* PropertiesKey is the property key we want the value
* output_variable is the variable name to store the result.

**Example :**
'''
get_property_value(${CMAKE_SOURCE_DIR}/gradle.properties "version" VERSION)
'''
This line return for the previous 'gradle.properties' the result '1.0.0-SNAPSHOT'. 
 

### Usage Example

'''
cmake_minimum_required (VERSION 2.8)
project (hello-main)

set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake_modules/")
include(gradle-cpp)
#include(gradle-cpp-cppUnitTest)


# read version of the project in the gradle.propersties file
get_property_value(${CMAKE_SOURCE_DIR}/gradle.properties "version" VERSION)

SET(PROJECT_NAME "hello-main-${VERSION}")


add_executable(${PROJECT_NAME} ${SOURCES})

target_link_libraries(${PROJECT_NAME} ${STATIC_LIBRARIES})
'''

## The 'gradle-cpp-cppUnitTest' module

Incoming

## The 'Find-cppUnitTest' module

Incoming
