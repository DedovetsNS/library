# Application Library for school Focus-Start        

The project serves as a CRM system for managing the library. Allows you to take into account books, authors, visitors, loan books. Automatically sends a notification to the user when it is necessary to return the book.
The project consists of three modules: the main library module, the email sending module, the historian checker.

### Modules

Library  - contains the main functionality.                                              
https://github.com/DedovetsNS/library  

Mail sender - allows to send notification email.                                            
https://github.com/DedovetsNS/mail_sender      

Historian checker - is a stub of some registry of people with special access to books.
https://github.com/DedovetsNS/historian_checker    

### Used technologies
Java 11, Spring Boot, PostgresQL, Hibernate, RabbitMQ, Feign Client, REST Api, Git, Gradle, JUnit, Java Mail, Lombok, Flyway. Using RabbitMQ and Feign, both asynchronous interaction between modules and asynchronous.

To start you need to put all three modules and the docker-compose.yml file in one folder, assemble the Jar file of each module and put it at the appropriate address 
> build/libs/library.jar

Go into the directories and run the docker-compose up command
`$ docker-compose up`
