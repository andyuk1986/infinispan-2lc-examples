How to run the example
======================

iBook is a simple web application that uses Infinispan as a 2LC for Hibernate. The application is working with relation database
MySQL DB (you can find the iBook.sql file in root of the project - it contains the structure and sample data for running app).

The application allows users to browse through the store of the books, choose books, read short description about them
and of course add them to personal wishlist. If the wishlist is full of books, then the system allows to checkout your
books - simulator of the payment/shipment of the books.

The application shows different ways of configuration Infinispan as a 2LC for Hibernate. The example shows all configuration
options (both clustered and non-clustered) for running application under JBoss AS7. This example demonstrates the
Hibernate - Infinispan 2LC configuration in case if you use JPA.
If you want to run it under the Tomcat, please see https://github.com/andyuk1986/iBook-tomcat7.

In configuration, I will use the JBoss AS7 HA configuration.

As Infinispan is the default cache container in JBoss AS7, it allows to configure Infinispan 2LC in several ways.

  1. You can use the default configuration provided by AS7.
  2. You can create your own infinispan configuration for 2LC.

Building and deploying to JBoss AS 7
------------------------------------

1) Prepare your MySQL DB:

    `iBook` (run iBook.sql for structure and data)

   accessible with the following credentials:

    `username: root`

2) Add new datasource and driver to your JBoss AS7 configuration ($JBOSS_HOME/standalone/configuration/standalone-ha.xml):

    `<subsystem xmlns="urn:jboss:domain:datasources:1.0">
        <datasources>
                .....................
                <datasource jta="true" jndi-name="java:jboss/datasources/iBook" pool-name="iBook" enabled="true" use-java-context="true" use-ccm="true">
                    <connection-url>jdbc:mysql://localhost:3306/iBook</connection-url>
                    <driver>mysql</driver>
                    <security>
                        <user-name>root</user-name>
                    </security>
                    <statement>
                        <prepared-statement-cache-size>100</prepared-statement-cache-size>
                        <share-prepared-statements>true</share-prepared-statements>
                    </statement>
                </datasource>
                .....................
                <drivers>
                    ................
                    <driver name="mysql" module="com.mysql"/>
                </drivers>
        </datasources>
    </subsystem>`

    See $PROJECT_HOME/standalone-ha.xml.

3) For adding MySQL JDBC driver, do the following:
   3.1. Create com/mysql/main directory in $JBOSS_HOME/modules directory.
   3.2. Copy MySQL JDBC driver JAR to that directory. Make sure that JDBC driver is JDBC compliant, i.e. it contains
        META-INF/services/java.sql.Driver file.
   3.3. Add module.xml file to that directory and add mysql resources (see $PROJECT_HOME/module.xml).

4) JBoss AS7 uses the `hibernate` cache-container by default. It is defined in $JBOSS_HOME/standalone/configuration/standalone-ha.xml
   in `infinispan` subsystem. Add the following named caches to this hibernate cache-container:

     `<replicated-cache name="replicated-query" mode="ASYNC">
          <transaction mode="NONE"/>
          <eviction strategy="NONE"/>
      </replicated-cache>
      <replicated-cache name="replicated-entity" mode="SYNC">
          <eviction strategy="LRU" max-entries="10000"/>
          <expiration max-idle="100000"/>
      </replicated-cache>`

3) Start JBoss AS 7 where your application will run

    `$JBOSS_HOME/bin/standalone.sh -c standalone-ha.xml`

4) Build and deploy the application

    `mvn clean package jboss-as:deploy`

5) Go to http://localhost:8080/iBook

6) If you want to see 2LC working on the cluster and replicated 2LC, then start the second instance of JBoss, using:
   `$JBOSS_HOME/bin/standalone.sh -c standalone-ha.xml -Djboss.socket.binding.port-offset=100 -Djboss.node.name=node1`

7) Uncomment the following part in pom.xml and deploy application using the mvn command above:
   `<configuration>
        <port>10099</port>
    </configuration>`

8) Go to http://localhost:8180/iBook

9) If you want to check whether the 2LC was used for the second node and the database was not hit, follow this steps:
   9.1. Open http://localhost:9990/console/index.html (if you haven't created user yet, follow the instructions on the appeared page);
   9.2. The go to JPA (You'll see the application deployed and View link there) -> (View->). Here you can find Second level
        cache tab and Queries tab, where you can see the number of puts, misses and hits.
   9.2. If you will enter to http://localhost:10090/console/index.html (the CLI for second Jboss instance) and will see the
        same statistics, you will see that only hits count is increasing, the number of puts and misses is 0 which means the 2LC
        has been used.

