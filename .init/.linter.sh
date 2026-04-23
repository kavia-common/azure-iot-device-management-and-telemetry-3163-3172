#!/bin/bash
cd /home/kavia/workspace/code-generation/azure-iot-device-management-and-telemetry-3163-3172/device_registration_and_telemetry_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

