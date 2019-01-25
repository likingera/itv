# itv

Supermarket Checkout application

## TechnologyUsed

1. jdk1.8
2. Spring framework
3. Maven
4. h2


## Installation

1. first clone the repository 
2. Run the following command to set up the dependencies and run the all Junit `mvn clean install`

## Running integration test

1. Run the following command to set up the dependencies `mvn clean install -Dintegerationtests=run`

## Running the application

1. The application uses springboot and uses embeded tomcat container.
2. Run the main method present under the class com.itv.controller.CheckoutController
3. Open h2 consoledb to insert sample data. Url is `http://localhost:8080/h2-console`
4. Data:-
   *`INSERT INTO ITEMDETAILS VALUES(1, 'A','50');`
   *`INSERT INTO ITEMDETAILS VALUES(2, 'B','30');`
4. Use tool like Postman to hit the service.Endpoint :- `http://localhost:8080/checkout`
5. Sample request

{
	"itemsPurchased" : {"items":["A","B","A","A","B"]},
	"specialPricing" : {"offers": [ {"units":"3","itemName":"A","price":"100"} , {"units":"2","itemName":"B","price":"50"}]}
}
