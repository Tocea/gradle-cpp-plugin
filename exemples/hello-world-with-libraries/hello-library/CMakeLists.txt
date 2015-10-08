cmake_minimum_required (VERSION 2.8)
project (hello-library)


# Main useful constants


SET(C_SRC "${PROJECT_SOURCE_DIR}/src/main/cpp")
SET(C_HEADERS "${PROJECT_SOURCE_DIR}/src/main/headers")
SET(C_SRC_TEST "${PROJECT_SOURCE_DIR}/src/test/cpp")
cmake_minimum_required (VERSION 2.8)
project (hello-library)


# Main useful constants


SET(C_SRC "${PROJECT_SOURCE_DIR}/src/main/cpp")
SET(C_HEADERS "${PROJECT_SOURCE_DIR}/src/main/headers")

SET(VERSION "1.0.0-SNAPSHOT")
SET(PROJECT_NAME "hello-${VERSION}")
SET(PROJECT_STATICNAME "hello_static-${VERSION}")

# Default output directories 
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/bin)
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/lib)
set(TEST_OUTPUT_PATH ${PROJECT_SOURCE_DIR}) 

## Default include directories

include_directories(${C_HEADERS})

#However, the file(GLOB...) allows for wildcard additions:
file(GLOB SOURCES "${C_SRC}/*.cpp")




# -------------------------

# Usefull to get CPPUNIT ...
# I would love to find a way to have cppunit
# only required for builds where unit test make sense ...
set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake_modules/")
FIND_PACKAGE(CPPUNIT REQUIRED)


##################### tests ##################

enable_testing()


set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/tmp/bin)
set(TMP_VAR ${EXECUTABLE_OUTPUT_PATH})
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/test-obj)

SET(${C_SRC_TEST} "${PROJECT_SOURCE_DIR}/src/test/cpp" )
SET(${TEST} "${PROJECT_SOURCE_DIR}/src/test/headers")
#add_test(NAME ${PROJECT_NAME} WORKING_DIRECTORY ${C_SRC_TEST})

include_directories(${C_HEADERS} )
#include_directories(${C_SRC})
include_directories("${PROJECT_SOURCE_DIR}/src/test/headers")
#include_directories("${PROJECT_SOURCE_DIR}/src/main/cpp")

file(GLOB TEST_SOURCES "${C_SRC_TEST}/*.cpp")

add_executable(launchTest ${SOURCES} ${TEST_SOURCES})
target_link_libraries(launchTest  cppunit)
add_test(NAME aTest COMMAND launchTest) 
    

set(EXECUTABLE_OUTPUT_PATH ${TMP})

####################################"
# AMALIB STATIC
add_library (
    ${PROJECT_STATICNAME}
    STATIC
   ${SOURCES}        
)
####################################"
# AMALIB SHARED

add_library (
    ${PROJECT_NAME}
    SHARED
    ${SOURCES}
)
####################################"
## Libraries
SET(REQUIRED_LIBS, "")


####################################"
# FIN INITIALISATION VARIABLES
#


# LINK LIBRARIES

target_link_libraries( ${PROJECT_STATICNAME} ${REQUIRED_LIBS} )
target_link_libraries( ${PROJECT_NAME} ${REQUIRED_LIBS} )


# Add own output directory to library dir
LIST(APPEND CMAKE_PREFIX_PATH ${CMAKE_LIBRARY_OUTPUT_DIRECTORY})

