cmake_minimum_required (VERSION 3.0)
project (CMakeHelloWorld)

include(${PROJECT_SOURCE_DIR}/cmake-modules/gradle-cpp.cmake)

SET(VERSION "1.0.0-SNAPSHOT")


SET(CMakeHelloWorldName "hello-world-${VERSION}")

add_executable(${CMakeHelloWorldName} ${SOURCES})




