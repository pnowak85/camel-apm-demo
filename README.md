# camel-apm-demo
Demo project for Camel OpenTracing with Elastic APM (OpenTrace Brudge) with JDBC interaction

This demo project uses the Apache Camel framework to create a simple `timer` route which then calls another `direct` route which does a jdbc call.
The OpenTracing component for Camel is enabled and the Elastic OpenTracing Bridge has been added.
There is also a little spring service executing another jdbc statement periodically in the background, just to ensure that the elastic jdbc integration works.

### Expected elastic/kibana output
A trace/transaction starting in the `timer` route, including the `querydb` direct route and showing the jdbc call inside the called bean.
A second trace for the spring background service with the jdbc call.

## Setup instructions
* Replace db properties in the `application.properties` file to point to an existing database.
* Insert two (different) db table names into `jdbc.countquery_table1` and `jdbc.countquery_table2` - count queries will be created with the two tables.
