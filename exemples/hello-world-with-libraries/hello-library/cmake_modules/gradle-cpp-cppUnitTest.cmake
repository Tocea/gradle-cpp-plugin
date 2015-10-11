
SET(TEST_SRC ${PROJECT_SOURCE_DIR}/src/test/cpp)
SET(TEST_HEADERS ${PROJECT_SOURCE_DIR}/src/test/headers)
file(GLOB TEST_SOURCES ${TEST_SRC}/*.cpp)

# -------------------------

# Usefull to get CPPUNIT ...
# I would love to find a way to have cppunit
# only required for builds where unit test make sense ...
set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake_modules/")
FIND_PACKAGE(CPPUNIT REQUIRED)

##################### tests ##################

enable_testing()


set(TMP_VAR ${EXECUTABLE_OUTPUT_PATH})
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/build/test-obj)
include_directories(${TEST_HEADERS})


add_executable(launchTest ${SOURCES} ${TEST_SOURCES})
target_link_libraries(launchTest  cppunit)
add_test(NAME aTest COMMAND launchTest)