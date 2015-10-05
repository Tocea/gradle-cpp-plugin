cmake_minimum_required (VERSION 2.8)
project (CMakeHelloWorld)


# Main useful constants


SET(C_SRC "${PROJECT_SOURCE_DIR}/src/main/cpp")
SET(C_HEADERS "${PROJECT_SOURCE_DIR}/src/main/headers")

SET(VERSION "1.0.0-SNAPSHOT")

# Default output directories 
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/bin)
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/lib)
file(MAKE_DIRECTORY ${EXECUTABLE_OUTPUT_PATH})
file(MAKE_DIRECTORY ${LIBRARY_OUTPUT_PATH})

## Default include directories

include_directories(${C_HEADERS})
include_directories(${C_SRC})

SET(CMakeHelloWorldName "CMakeHelloWorld-${VERSION}")

add_executable(
    ${CMakeHelloWorldName}
    ${C_SRC}/Speaker.cpp
)

target_link_libraries( ${AMATEST} "")



