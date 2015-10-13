cmake_minimum_required (VERSION 2.8)
project (CMakeHelloWorld)


set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake_modules/")
include(gradle-cpp)




# read version of the project in the gradle.propersties file
get_property_value(${CMAKE_SOURCE_DIR}/gradle.properties "version" VERSION)


SET(CMakeHelloWorldName "hello-world-${VERSION}")

add_executable(${CMakeHelloWorldName} ${SOURCES})




