# AuthentiCareBankApi

_Running_
1. docker-compose up
2. mvn clean package
3. java -jar target/authenticarebankapi-*.jar

Note that docker-compose config isn't perfect, the app container is missing at this time due to limitations to the dev machine...

_Rabobank banking account assignment_

Write some code in Java to simulate a simple bank account. 
It should be possible to transfer and withdraw money from an account. 
It is possible to pay with either debit card or credit card. 
If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra.

Use design patterns where applicable and write some test cases as well.

Requirement / Validations:
1. A negative balance is not possible
2. Account should contain at least some user details, card details and current balance
3. One rest endpoint to see current available balance in all accounts
4. One rest endpoint to withdraw money
5. One rest endpoint to transfer money
6. One credit card or debit card is linked with one account
7. It should be able to audit transfers or withdrawals
8. Front end part is not required
9. Feel free to make some assumptions if needed & mention them in the code assignment
10. For Security, implement authentication for the API endpoints
