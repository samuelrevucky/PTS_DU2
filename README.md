# Commute connection search app

This project consists of a connection search simulation. The user can try to find the shortest connection giving the start and destination stop and starting time. Finding the shortest connection will result in reserving a seat in all vehicles on necessary route segments that will be used.

The app has two versions:
- **in memory only**: by giving an initial set of data all changes will remain only until the app is shut down
- **persistent**: uses an SQLite database which contains all data and will persist after the app is shut down

## How to set up

For both versions the primary class the user will use is ConnectionSearch in package main/common. 

For inMemory version see constructors of classes in package main/inMemory/. 

For persistent version JDBC driver is needed in project dependencies. After you add the driver, you can use your own database, but it has to be of structure specified in tests/persistent/datDef.sql.  
