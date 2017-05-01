Create schema lurrundb
execute .bat
Change datanucleus properties username/pass with mysql username and pass

mvn clean compile
mvn datanucleus:schema-delete
mvn datanucleus:schema-create
mvn compile
mvn exec:java -Pserver

mvn exec:java -Pclient


To test (do not forget to stop the RMIRegistry) 
- mvn test

(Mock tests simulation of DAO Layer)
(RMI integration tests)

To generate cobertura reports
- mvn cobertura:cobertura
