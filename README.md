

# Connected - server

## Configurations:

* First, create relational database(MySQL) and table **users** using **databaseScript** file.
* All parameters are listed in **application.properties** file in resources folder, make sure
your configurations are appropriate to your needs.

## Executing:

* Via the main class using IDE or by running artifact(jar file).
* To create an executable jar use **mvn clean install** command.
* **ChatServer-1.0-SNAPSHOT-jar-with-dependencies.jar** will show up in target directory.
* **java -ChatServer-1.0-SNAPSHOT-jar-with-dependencies.jar** command runs the server.

You can use **application.properties** as default or your own properties file.
Executing via the main class or by running jar file passing no parameters
 means that **application.properties** file from resources will be used. To pass
 configurations parameters from your own **application.properties** file execute
  application passing one parameter (which is path to your file), for example: 
  
**java -jar ChatServer-1.0-SNAPSHOT-jar-with-dependencies.jar /path/to/your/application.properties**.

[link to Connected - client](https://github.com/AleksanderTech/Connected-client)



