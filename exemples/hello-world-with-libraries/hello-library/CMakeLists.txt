cmake_minimum_required (VERSION 2.8)
project (hello-library)


# Main useful constants


SET(C_SRC "${PROJECT_SOURCE_DIR}/src/main/cpp")
SET(C_HEADERS "${PROJECT_SOURCE_DIR}/src/main/headers")
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
file(MAKE_DIRECTORY ${EXECUTABLE_OUTPUT_PATH})
file(MAKE_DIRECTORY ${LIBRARY_OUTPUT_PATH})


## Default include directories

include_directories(${C_HEADERS})
#However, the file(GLOB...) allows for wildcard additions:
file(GLOB SOURCES "${C_SRC}/*.cpp")


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

