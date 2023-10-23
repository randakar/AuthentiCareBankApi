# AuthentiCareBankApi


_Rabobank Authenticare banking account assignment_

Write some code in Java to simulate a simple bank account.
It should be possible to transfer and withdraw money from an account.
It is possible to pay with either debit card or credit card.
If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra.

Use design patterns where applicable and write some test cases as well.

Requirement / Validations:
1. A negative balance is not possible
2. Account should contain at least some user details, card details and current balance [x]
3. One rest endpoint to see current available balance in all accounts [x]
4. One rest endpoint to withdraw money [x]
5. One rest endpoint to transfer money [x]
6. One credit card or debit card is linked with one account
7. It should be able to audit transfers or withdrawals
8. Front end part is not required [x]
9. Feel free to make some assumptions if needed & mention them in the code assignment
10. For Security, implement authentication for the API endpoints [x]


------

_Building_
1. `mvn clean package`

_Running_
1. `docker-compose up`
2. `mvn clean package`
3. `java -jar target/authenticarebankapi-*.jar`

For the endpoints, see the [Api spec](/src/main/resources/api.yml).


_Limitations_

The docker-compose config isn't perfect, the app container is missing at this time due to limitations to the dev machine...

There is a known issue with the authentication failing to authorize the integration tests.
This is why the integration tests are far from complete, and is one of the main reasons behind delays getting this done.




