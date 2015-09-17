package com.tocea.gradle.plugins.cpp

/**
 * Created by jguidoux on 03/09/15.
 */
class CppPluginUtils {

    public static final String SOURCE_HEADERS = "src/main/headers"
    public static final String ZIP_EXTENSION = "zip"
    public static final String CLIB_EXTENSION = "clib"
    public static final String EXT_LIB_DIR = "extLibDir"
    public static final String MAIN_OBJ_DIR = "mainDir"
    public static final String TEST_OBJ_DIR = "testDir"
    public static final String TMP_DIR = "tmpDir"
    public static final String REPORT_DIR = "reportDir"
    public static final String DOC_DIR = "docDir"
    public static final String COMPILE_CMAKE_BASE_ARG = ""
    public static final String TEST_COMPILE_CMAKE_BASE_ARG = ""
    public static final String TEST_CMAKE_BASE_ARG = ""
    public static Map OUTPUT_DIRS = ["mainDir": "main-obj", "testDir": "test-obj", "extLibDir": "extLib",
                                     "tmpDir" : "tmp", "report": "report", "doc": "docDir"]
    public static Map OUTPUT_TMP_DIRS = ["headers": "headers", "bin":"bin","lib": "lib",
                                         "doc":"doc", "doxygen":"doxygen"]


}
