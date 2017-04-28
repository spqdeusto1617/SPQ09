- Start MySQL
- Create MySQL schema using script

3. Run the command: "mvn clean compile"
   This builds everything, and enhances the classes 

4. Run the command: "mvn datanucleus:schema-create"
   This creates the schema for this sample.

6. Run the command: "mvn datanucleus:schema-delete"
   This deletes the schema for this sample. See note for 4 also.

mvn clean compile
mvn datanucleus:schema-create
mvn compile
mvn exec:java -Pserver

mvn exec:java -Pclient

To execute application
- launch registry.bat
- mvn compile
- mvn exec:java -Pserver
- mvn exec:java -Pclient

To test (do not forget to stop the RMIRegistry) 
- mvn test

(Mock tests simulation of DAO Layer)
(RMI integration tests)

To generate cobertura reports
- mvn cobertura:cobertura