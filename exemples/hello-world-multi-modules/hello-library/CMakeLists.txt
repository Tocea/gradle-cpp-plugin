cmake_minimum_required (VERSION 2.8)
project (hello-library)

set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/../cmake_modules/")
include(gradle-cpp)
include(gradle-cpp-cppUnitTest)




SET(VERSION "1.0.0-SNAPSHOT")
SET(PROJECT_NAME "hello-${VERSION}")
SET(PROJECT_STATICNAME "hello_static-${VERSION}")




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
# FIN INITIALISATION VARIABLES
#


# LINK LIBRARIES

target_link_libraries( ${PROJECT_STATICNAME} ${STATIC_LIBRARIES} )
target_link_libraries( ${PROJECT_NAME} ${STATIC_LIBRARIES} )


# Add own output directory to library dir
LIST(APPEND CMAKE_PREFIX_PATH ${CMAKE_LIBRARY_OUTPUT_DIRECTORY})

