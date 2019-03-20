AQ4 API Test Framework can be used to reduce test automation effort if the tests are suitable for data driver testing. 
As it built on top of TestNG and RestAssured, it provides the capability to construct the services tests using common 
APIs and executes the tests. It provides the custom listener to support a sophisticated assertion, 
not just simple string/number comparison.

System Requirements Component Value Java Version 1.8 or greater Maven 3.0 or greater TestNG Version 6.8 or greater
#==============================
# Installation Instructions
you can view the below link for the complete instructions:


Open terminal and compile the code using following command: mvn clean install -DenvType=QA 

OR

mvn clean install -DskipTests=true
     
Javadocs has been created and added to doc directory for instance Javadoc snippet for assertions API's

Test Configurations Framework provides and reads test properties from 'qa.configuration.properties' 
file User can define different test properties file using the following convention : .configuration.properties

Test Execution - command line:  mvn clean test -DenvType=QA -Dsuite=ServicesTestSuite

mvn clean test -DenvType=qa -Dsuite=FulFillmentDataSet
mvn clean test -DenvType=qa -Dsuite=UnitTestCaseMasterSuite
mvn clean test -DenvType=qa -Dsuite=FulFilmentSingleShipmentSingleSkuSingleStore
mvn clean test -DenvType=qa -Dsuite=FulfillmentMultiShipmentSingleSkuSingleStore
mvn clean test -DenvType=qa -Dsuite=FulfillmentSingleShipmentMultiSkuSingleStore
mvn clean test -DenvType=qa -Dsuite=FulfillmentMultiShipmentMultiSkuSingleStore
mvn clean test -DenvType=qa -Dsuite=UnFulfilledSingleShipmentSingleSkuNoCapacitySingleStore
mvn clean test -DenvType=qa -Dsuite=UnfulfilledSingleShipmentSingleSkuNoInventorySingleStore
mvn clean test -DenvType=qa -Dsuite=SetStore
nohup mvn clean test -DenvType=qa -Dsuite=SetStoreUtil &

mvn clean test -DenvType=qa -Dsuite=MasterSuite
