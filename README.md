# TransferWiser
[![Build Status](https://travis-ci.com/wackloner/transfer-wiser.svg?branch=master)](https://travis-ci.com/wackloner/transfer-wiser)

## Requirements

1. You can use Java or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 and keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require a
pre-installed container/server).
7. Demonstrate with tests that the API works as expected.

## Running
Run below command to start server listening on port 8080.

`./gradlew run`