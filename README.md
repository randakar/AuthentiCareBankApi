# AuthentiCareBankApi



_Building_
1. `mvn clean package`

_Running_
1. `docker-compose up`
2. `mvn clean package`
3. `java -jar target/authenticarebankapi-*.jar`

For the endpoints, see the [Api spec](/src/main/resources/api.yml).

_Done_
1. Spring boot 3 api
2. Runs with a real database (container) underneath for both test and dev.
3. Api-first: Model classes and Controller interfaces are generated from [OpenApi spec](/src/main/resources/api.yml)
4. Uses MapStruct for (most of) the mappings.
5. Uses JPA for the queries.
6. Implements most of the functionality (see below. And the checkmarks, up top.)

_Things I wished to do but had no time to do_
1. Docker compose should also run the app in a container (lack of docker desktop and admin rights got in the way)
2. Complete integration tests. A problem with the auth setup blocked me from expanding the integration test enough.
3. Unit tests. I intended to cover this in unit tests, but time ran out due to the above (mostly).
4. Builders and immutable objects. Both the openapi-generator and JPA forced me into mutability. Not happy about that one.
5. More patterns. The transaction code is just begging for a good application of the Strategy Pattern, and ideally model classes have Builders attached.
6. Endpoints for linking things together. If you pay attention, we have endpoints to *create* things, but very few to actually link, cards or accounts to customers.
7. Jenvers. I intended to use [Jenvers](https://medium.com/@ketannabera/auditing-in-java-application-using-spring-boot-mongodb-part-2-f736e2240ed3) to add the audit logging feature. But, out of time.
8. Kafka. Ideally we'd be sending some of this data to kafka for long-term processing.



Original assignment.

_Rabobank Authenticare banking account assignment_

Write some code in Java to simulate a simple bank account. [v]
It should be possible to transfer and withdraw money from an account. [v]
It is possible to pay with either debit card or credit card. [v]
If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra. [v]

Use design patterns where applicable and write some test cases as well. [-]

Requirement / Validations:
1. A negative balance is not possible [v]
2. Account should contain at least some user details, card details and current balance [v]
3. One rest endpoint to see current available balance in all accounts [v]
4. One rest endpoint to withdraw money [v]
5. One rest endpoint to transfer money [v]
6. One credit card or debit card is linked with one account [v]
7. It should be able to audit transfers or withdrawals
8. Front end part is not required [v]
9. Feel free to make some assumptions if needed & mention them in the code assignment
10. For Security, implement authentication for the API endpoints [v]


------


