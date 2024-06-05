Inventory Management APIs
===============================
## Details
These apis serve to manage inventory details

## Bootstrap instructions
To run this server locally,

1. Install Java 17 on your local machine - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html.
2. Install Maven on your local machine - https://maven.apache.org/install.html.
3. Clone the repository using **git clone https://github.com/sivalux/Inventory-Management.git**.
4. Navigate to project root directory and open a terminal.
5. Run **mvn clean package** which will run the test and build the project.
6. Run **java -jar target/inventory-management-0.0.1-SNAPSHOT.jar** to start the application.
7. To test the APIs in swagger navigate to http://localhost:8080/swagger-ui.html.
8. Alternatively you can use an API client like postman and test the endpoints.


## Assumptions
1. While creating Inventory Detail Transaction Status should be PENDING.
2. Cannot change Transaction Status from COMPLETED to PENDING or CANCELLED to PENDING.
3. Cannot update the productId while updating Inventory Detail.
4. While updating Inventory Detail, the existing quantity should be equal to updated quantity.

If the above operation should happen(2,3,4), cancel the entry and create a new one.

ER diagram is attached in resources folder(**inventory-management\src\main\resources**).

