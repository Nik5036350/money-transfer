# hw

How to start the hw application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/hw-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:9000`

How to run integration tests
---

1. Run `mvn clean verify`

Description
---
Api for money transfer between two accounts.

States of money transfer transaction

1. Pending - transaction registered, no actions performed
2. Committed - transaction successfully finalized, money moved between accounts
3. Failed - transaction failed, money was not transferred or rolled back to a state before the transaction

IMPORTANT RESTRICTION
----
The service does not support different currencies. Lets just assume that all transactions in USD

Availabel endpoints
---

/user

GET `http://localhost:9000/user` - get all users

GET `http://localhost:9000/user/1` - get specific user

POST `http://localhost:9000/user` - create new user

/account

GET `http://localhost:9000/account` - get all accounts

GET `http://localhost:9000/account/1` - get specific accounts

POST `http://localhost:9000/account` - create new account

/transaction

GET `http://localhost:9000/transaction` - get all transactions

GET `http://localhost:9000/transaction/1` - get specific transaction

POST `http://localhost:9000/transaction` - create new transaction

payload for new transaction (money transfer)
`  {
     "fromUser": 2,
     "toUser": 1,
     "amount": 10.0000
   }
 `

