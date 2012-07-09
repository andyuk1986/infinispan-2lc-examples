The project demonstrates how to enable clustered Infinispan 2nd level cache for Hibernate under Tomcat7.

The project is a simple iBook - store of books, which allows user to view books, add them to their wishlist and then checkout.
It uses MySQL database.

The project configuration is done for working under Tomcat server.

The configuration and clustering enable should be done with this steps:

1. Enable tomcat clustering using guide http://tomcat.apache.org/tomcat-7.0-doc/cluster-howto.html (see the clustering.xml in the project).
Change the Membership address to 224.0.0.0 .

2. Make sure that the multicast is enabled on your machine, and add new multicast route using this command:

route add -net 224.0.0.0 netmask 240.0.0.0 dev lo

3. For being able to have replicated 2nd level cache, the JGroups transport should be enabled. For some JDK impementations
it doesn't work properly, so I had to add the preferIPv4Stack property to JAVA_OPTS.


