#!/usr/bin/env bash

SCRIPT_DIR=$(realpath $(dirname $BASH_SOURCE[0]))
MODULE_ROOT_DIR=$(realpath $SCRIPT_DIR/../)
PROJECT_ROOT_DIR=$(realpath $MODULE_ROOT_DIR/../)

GRADLE=./gradlew

main() {
  build
}

build() {
  (cd $MODULE_ROOT_DIR
    $GRADLE publish
  )
}
main
