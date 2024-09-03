# Custom Annotation:
* http://localhost:8080/custom-annotation/a --> Returns 'Slave:2.0- Data' as this is annotated by @UseSlave
* http://localhost:8080/custom-annotation/b --> Returns 'null- Data' as this is not annotated

# Event Handling:
* http://localhost:8080/event-handling/winner/teamA:
  * This endpoint creates an event.
  * There are 2 listeners to this event. 
    * **Synchronous** : Prints a message in the console before response is returned.  
    * **Asynchronous** : Prints a message in the console asynchronously after the response is returned.
