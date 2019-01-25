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
3. Use tool like Postman to hit the service
