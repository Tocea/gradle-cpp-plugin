
# Main useful constants
SET(MAIN_SRC "${PROJECT_SOURCE_DIR}/src/main/cpp")
SET(HEADERS "${PROJECT_SOURCE_DIR}/src/main/headers")
set(CMAKE_BINARY_DIR ${CMAKE_SOURCE_DIR}/build)


# Default output directories 
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_BINARY_DIR}/tmp/bin)
set(LIBRARY_OUTPUT_PATH ${CMAKE_BINARY_DIR}/tmp/lib)
file(MAKE_DIRECTORY ${EXECUTABLE_OUTPUT_PATH})
file(MAKE_DIRECTORY ${LIBRARY_OUTPUT_PATH})
FILE(GLOB SOURCES ${MAIN_SRC}/*.cpp)

## Default include directories

include_directories(${HEADERS})
include_directories(${MAIN_SRC})


# Variables de compilation (et valeurs par défaut)
if(NOT DEFINED LIBRARY_TYPE) # STATIC
    SET(LIBRARY_TYPE "SHARED") # STATIC|MODULE|SHARED
endif()
if(NOT DEFINED VERSION)
    SET(VERSION "1.0-SNAPSHOT") 
endif()


if(DEFINED VERSION)
    SET(SOVERSION ${VERSION}) 
endif()

if(NOT DEFINED LIB_READLINE)
    SET(LIB_READLINE "no")
endif()
if(NOT DEFINED LIB_ICONV)
    SET(LIB_ICONV "yes") 
endif()
if(NOT DEFINED LIB_GETTEXT)
    SET(LIB_GETTEXT "yes")
endif()
if(NOT DEFINED LIB_DL)
    SET(LIB_DL "no")
endif()

# DEFAULT FLAGS
if(NOT DEFINED FLAGS)
    SET(FLAGS  "-fdiagnostics-color=always")
endif()

# Initialisation specifique OS
#----------------------------------------------------------
if( ${CMAKE_SYSTEM_NAME} MATCHES Linux)
  LIST(APPEND REQUIRED_LIBS "resolv")
  SET(FLAGS "${FLAGS} -DLINUX")
endif()

if( ${CMAKE_SYSTEM_NAME} MATCHES Linux)
  SET( LIB_GETTEXT "yes")
  SET( LIB_ICONV "yes")
elseif( ${CMAKE_SYSTEM_NAME} MATCHES Windows )
  SET( LIB_GETTEXT "no")
  SET( LIB_ICONV "no")  
endif()




# Triggers des options
#--------------------------------------------------------


# Triggers simples

if ( ${LIB_READLINE})
  SET(LIB_DL "yes")
  SET(FLAGS "${FLAGS} -DREADLINE")
endif()

if ( ${LIB_GETTEXT} )
  SET(FLAGS "${FLAGS} -DGETTEXT")
  #SI AIX -lintl
endif()

if ( ${LIB_ICONV}  )
  SET(FLAGS "${FLAGS} -DICONV")
  #SI AIX -liconv
  #SI SUNOS -liconv
endif()

# Triggers complexes


if( NOT ${CMAKE_SYSTEM_NAME} MATCHES Windows AND ${LIB_DL} MATCHES "yes")
    LIST(APPEND REQUIRED_LIBS "dl")
endif()


set(CUSTOM_LIBRARY_PATH ${CMAKE_BINARY_DIR}/extLib)
MESSAGE( STATUS "CUSTOM LIBRARY PATH : ${CUSTOM_LIBRARY_PATH}" )
file(MAKE_DIRECTORY ${CUSTOM_LIBRARY_PATH})


file(GLOB sub-dir ${CUSTOM_LIBRARY_PATH}/*)
foreach(dir ${sub-dir})
    if(IS_DIRECTORY ${dir})
        set(CMAKE_PREFIX_PATH ${CMAKE_PREFIX_PATH}:${dir})        
    endif()
endforeach()

# to find libraries
file( GLOB LIB_HEADERS ${CUSTOM_LIBRARY_PATH}/*/headers )
file( GLOB STATIC_LIBRARIES ${CUSTOM_LIBRARY_PATH}/*/lib/*.a  )


## Default include headers libraries
include_directories(${LIB_HEADERS})


#------------------------------------------------------------------------------------------------------------
# CREATION DU PROFIL DE COMPILATION

SET(MULTITHREAD_FLAGS "-DRENT -D_THREAD_SAFE -L/usr/lib/threads -L/usr/lib/dce  -lpthread -DMULTITHREAD -pthread ")

# Profiles de compilation -DCMAKE_BUILD_TYPE=
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra -Wno-strict-overflow ${FLAGS}")
SET(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra -Wno-strict-overflow ${FLAGS}")

#--------------------------------------------------------
# PROFIL Release
SET(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -O3" ${CMAKE_CXX_FLAGS})
SET(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -O3" ${CMAKE_C_FLAGS})


#--------------------------------------------------------
# PROFIL Debug
SET(CMAKE_CXX_FLAGS_DEBUG  "${CMAKE_CXX_FLAGS_DEBUG} -DDEBUG -ggdb  -g3 ${CMAKE_CXX_FLAGS}")
SET(CMAKE_C_FLAGS_DEBUG "${CMAKE_C_FLAGS_DEBUG} -DDEBUG -ggdb  -g3 ${CMAKE_C_FLAGS}")




