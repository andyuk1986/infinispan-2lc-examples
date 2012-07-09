How to run the example
======================

The project is a simple Search Engine based on learned techniques to parse and index the text.

The project is written using maven, for deployment, for resolving library dependencies, etc.
The application structured in the following way:

It consists of crawler part, which starts on server startup with start up URL - jboss.org, and then recursively
goes deeper into all links which appear in the web-content. The deepness is set to 25, which means that the
deepness of the recursion is 25. The inner page indexing is done asynchronously using FutureTasks.

The start up URL is jboss.org url. When the content of the web-page is read it is passed through text parsing part.

Text parsing is done in several steps. Those are:
    1. Deletion of markups from the web-content & retreiving the information of META-tags.
    2. Tokenization and Normalization of text (making all letters lower-case, as well as removing the punctuation signs).
    3. POS tagging the text. For this Qtag tagger is used. For using this tagger, an additional Wrapper class is created
    with all necessary methods. As a result of this execution, the result is a simple XML where to each token is assigned
    a POS tag (noun, pro-noun, etc.)
    4. Then the system chooses Nouns and pronouns from the tagged text, counts the appearance of each word, then counts
    the percentage of appearance, and based on average percentage picks the keywords.
    5. The last step is the stemming of the selected keywords. For Stemming, the Porter Stemmer is used. As a result,
    by the end of the execution the system gets the roots of the keywords.

After the text is parsed, the system stores each keyword with it's according list of documents into the Infinispan cache.

Here I'm using inverted index, i.e. the data is stored in the cache with the following structure:
key - keyword,  value - list of Documents

In this application the Infinispan is used as a clustered, distributed cache with 10 virtual nodes. It is designed so that,
the put and get of the keys/values can be asynchronous. I've chosen distributed cache so that later if the application
is started on several nodes, then the cache as well as the indexes are shared.

As a wrapper of Infinispan, com.jeejl.cache.CacheSupport class is created, during which startup, Infinispan cache
initializes (only once).

I've made it asynchronous, so that the web-crawling is not very slow. But in general, the get/put mechanism for crawler
is synchronised so that system does not overwrite already inserted documents.

Two simple web-pages are created for accessing the search results. One is index page for inserting the query, and
the second page is the result page where all available web-pages are shown.

The query processing is done in the following way:
the query is parsed as it is described above (except of deletion of markups). Then the system goes through all keywords
and gets the document list from cache for each of them.
The system counts the frequency of each document and sort these documents based on their frequency in descending order,
i.e. if the document contains most part of the keywords, then it is shown higher than the other ones.


Deploying application
------------------------------------

1. Change the value of the following property in pom.xml to your Tomcat path:

   `<deploy.path>/usr/share/apache-tomcat-7.0.27/webapps/</deploy.path>`

2. For building and running app using, build and deploy the application with command:

    `mvn clean package install`

3. Start your Tomcat server.

4. Go to http://localhost:8080/jeejl



