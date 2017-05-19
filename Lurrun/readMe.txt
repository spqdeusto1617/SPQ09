Create schema lurrundb
execute .bat
Change datanucleus properties username/pass with mysql username and pass

mvn clean compile
mvn datanucleus:schema-delete
mvn datanucleus:schema-create
mvn exec:java -Pserver

mvn exec:java -Pclient

To test (do not forget to stop the RMIRegistry) 
- mvn test

mvn clean compile
mvn datanucleus:schema-delete
mvn test
mvn datanucleus:schema-delete
mvn cobertura:cobertura
mvn checkstyle:checkstyle
mvn jdepend:generate
mvn dashboard:dashboard

(Mock tests simulation of DAO Layer)
(RMI integration tests)

To generate cobertura reports
- mvn cobertura:cobertura


Alternatively, in order to clearly see the elements involved, you may run one by one 
the difference quality assurance and performance tests and then integrate all their 
reports with the command: mvn dashboard:dashboard 
1. Run the Unit Tests: mvn test 

2. Run the cobertura check: mvn cobertura:cobertura 

3. Run the CheckStyle plugin: mvn checkstyle:checkstyle
  
4. Run the JDepend plugin: mvn jdepend:generate 

5. Run the Dashboard plugin: mvn dashboard:dashboard
cc




DOXYGEN mvn doxygen:report