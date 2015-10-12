cmake_minimum_required (VERSION 3.0)
project (CMakeHelloWorld)


set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake_modules/")
include(gradle-cpp)

SET(VERSION "1.0.0-SNAPSHOT")


SET(CMakeHelloWorldName "hello-world-${VERSION}")

add_executable(${CMakeHelloWorldName} ${SOURCES})




