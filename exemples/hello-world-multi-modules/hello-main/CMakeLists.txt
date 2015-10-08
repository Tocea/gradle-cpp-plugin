cmake_minimum_required (VERSION 2.8)
project (hello-main)


# Main useful constants


SET(C_SRC "${PROJECT_SOURCE_DIR}/src/main/cpp")
SET(C_HEADERS "${PROJECT_SOURCE_DIR}/src/main/headers")

SET(VERSION "1.0.0-SNAPSHOT")
SET(PROJECT_NAME "hello-main-${VERSION}")

# include libs
SET(HELLOLIB_A "${PROJECT_SOURCE_DIR}/build/extLib/hello-library-1.0.0-SNAPSHOT/lib")
SET(HELLOLIB_HEADERS "${PROJECT_SOURCE_DIR}/build/extLib/hello-library-1.0.0-SNAPSHOT/headers")


# AMALIB STATIQUE
SET(HELLOLIB_STATICNAME "${HELLOLIB_A}/libhello_static-1.0.0-SNAPSHOT.a")


# Default output directories 
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/bin)
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/lib)
file(MAKE_DIRECTORY ${EXECUTABLE_OUTPUT_PATH})
file(MAKE_DIRECTORY ${LIBRARY_OUTPUT_PATH})

## Default include directories

include_directories(${C_HEADERS})
include_directories(${HELLOLIB_HEADERS})

#However, the file(GLOB...) allows for wildcard additions:
file(GLOB SOURCES "${C_SRC}/*.cpp")

add_executable(${PROJECT_NAME} ${SOURCES})

target_link_libraries(${PROJECT_NAME} ${HELLOLIB_STATICNAME})



