cmake_minimum_required(VERSION 3.22.1)

project("demo")

if (NOT DEFINED FLAVOR)
    set(FLAVOR "dev")
endif()

add_library(
        secrets
        SHARED
        ${CMAKE_CURRENT_SOURCE_DIR}/src/${FLAVOR}/cpp/secrets.cpp
)

find_library(log-lib log)

target_link_libraries(
        secrets
        android
        log
)
