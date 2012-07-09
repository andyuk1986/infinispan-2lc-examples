How to run the example
======================

The project demonstrates how to enable clustered Infinispan 2nd level cache for Hibernate under Tomcat7.

The project is a simple iBook - store of books, which allows user to view books, add them to their wishlist and then checkout.
It uses MySQL database.

The project configuration is done for working under Tomcat server.


Configuring Tomcat7 for clustering
------------------------------------

The configuration and clustering enable should be done with this steps:

1. Enable tomcat clustering using guide http://tomcat.apache.org/tomcat-7.0-doc/cluster-howto.html (see the server.xml in the project).
Change the Membership address to 224.0.0.0 .

2. Make sure that the multicast is enabled on your machine, and add new multicast route using this command:

   `route add -net 224.0.0.0 netmask 240.0.0.0 dev lo`

3. For being able to have replicated 2nd level cache, the JGroups transport should be enabled. For some JDK impementations
it doesn't work properly, so I had to add the preferIPv4Stack property to JAVA_OPTS.
   `JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true"`

4. If you want to test application on 2 instances of Tomcat running on the same machine, modify the ports in server.xml
of two different instances of tomcat accordingly.

5. Create MySQL datasource in Tomcat context.xml (see $PROJECT_HOME/context.xml):

    ` <Resource name="jdbc/iBook" auth="Container" type="javax.sql.DataSource"
                   maxActive="100" maxIdle="30" maxWait="10000"
                   username="root" password="" driverClassName="com.mysql.jdbc.Driver"
                   url="jdbc:mysql://localhost:3306/iBook"/>`

6. Add MySQL JDBC driver jar to tomcat libraries.

7. Prepare your MySQL DB:

       `iBook` (run iBook.sql for structure and data)

      accessible with the following credentials:

       `username: root`


Deploying application
------------------------------------


1. Change the value of the following property in pom.xml to your Tomcat path:

   `<deploy.path>/usr/share/apache-tomcat-7.0.27/webapps/</deploy.path>`

2. For building and running app using, build and deploy the application with command:

    `mvn clean package install`

3. Start your Tomcat server.

4. Go to http://localhost:8080/iBook


