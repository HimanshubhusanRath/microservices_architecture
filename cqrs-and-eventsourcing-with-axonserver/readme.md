# CQRS (Command Query Responsibility Segregation) pattern
* This pattern is composed of two parts.
  * Command : Used for creating / updating / deleting a resource
  * Query : Used to fetch the resource
* In this project, CQRS is implemented using `Axon Framework` and `Axon Server`.

## Detailed Design:
### Aggregate
* Aggregate is used to handle all commands specific to a particular type e.g. ProductAggregate takes care of all product related commands e.g. add product, update product etc.
* Each aggregate is identified by the aggregate-identifier. So, each product would have one aggregate with unique aggregate-identifier. 

### Command
* A command created by the caller and sent using `CommandGateway` is eventually handled by the respective aggregator.
* In the aggregator, a method annotated with `@CommandHandler` handles the command and performs the required operation and also triggers an event.
* In this example, as part of this event handling (annotated by `@EventHandler`), the product is stored in the database as well as the event is implicitly sent to the Event store (Axon server).  

### Query
* A query is created by the caller and sent using `QueryGateway` is eventually handled by the respective query handler (Projection).
* In the projection, a method annotated with `@QueryHandler` handles the query and performs the required operation.
* In this example, as part of this query handling, the product is fetched from the database. 
* We can also use the Aggregator to get the details of the product without doing a database fetch. This can be done by using `event souring` where the events specific to that Aggregator can be replayed and the Aggregator's state can be built. 


### Event Sourcing
* This refers to building the Aggregate's state by replaying the events for the particular aggregate. Ex. a product aggregate's current state can be built by replaying all events for the product e.g. create-product event, update-product event etc.
* In the Aggregator, this update is taken care by the methods annotated with `@EventSourcingHandler` where each event would have one such method.


## Test this project:
Use the below APIs in the attached API collection (`CQRS_EventSourcing_AXONServer_collection.json`) to test this project.
* Command:
  * Create Product : `Add Product` 
    * Note the product-id in the response. This ID is used to fetch product details in subsequent APIs.
  * Update Product (Updates product using product-id) : `Update Product` 
* Query:
  * Get all products (Retrieved from database): `Get all products` 
  * Get all events for a product from event store directly : `Get all events for a product`
  * Load a products state from its aggregate (event sourcing) : `Load Product from Aggregate`


### NOTE:
* Axon server : `http://localhost:8024/`
  * Check events : `http://localhost:8024/#query` - Options to write custom query
  * Check commands : `http://localhost:8024/#commands` - Command names are shown along with how many times those commands have been fired
  * Check queries : `http://localhost:8024/#queries` - Query names are shown along with how many times those queries have been fired
* H2 database : `http://localhost:8081/h2-console`