cmake_minimum_required (VERSION 2.8)
project (hello-main)


set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/../cmake_modules/")
include(gradle-cpp)
#include(gradle-cpp-cppUnitTest)

SET(VERSION "1.0.0-SNAPSHOT")
SET(PROJECT_NAME "hello-main-${VERSION}")


add_executable(${PROJECT_NAME} ${SOURCES})

target_link_libraries(${PROJECT_NAME} ${STATIC_LIBRARIES})



