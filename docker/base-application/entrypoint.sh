#!/usr/bin/env bash
exec java -jar target/base-application-0.0.1-SNAPSHOT.jar \
    -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap ${MEMORY_LIMITS}
