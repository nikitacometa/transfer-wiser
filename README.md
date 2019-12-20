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

## API

1. `GET /accounts?fromId=<fromId>&limit=<limit>` - get all accounts or limited by query parameters
2. `GET /accounts/<id>` - get account with specified id
3. `POST /accounts/create?name=<name>` - create new account with zero balance and provided name
4. `POST /accounts/<id>/deposit?amount=<amount>` - deposit specified account with specified amount
5. `POST /accounts/<id>/withdraw?amount=<amount>` - withdraw from specified account with specified amount
6. `POST /accounts/<id>/transfer?to=<receiverId>&amount=<amount>` - transfer amount from one account to another
7. `DELETE /accounts/<id>` - remove specified account
