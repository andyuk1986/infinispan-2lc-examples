package com.jeejl.crawler;

import com.jeejl.cache.CacheSupport;
import com.jeejl.data.Document;
import com.jeejl.nlp.ContentProcessor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebCrawler which starts on server startup, and triggers the web indexing mechanism.
 *
 * The startup URL is JBoss URL, from where the system starts index the web pages.
 * Keywords are selected and placed in the cache for further access.
 */
public class WebCrawler extends TimerTask {
    /**
     * Startup URL for indexing.
     */
    public static final String STARTUP_URL = "http://www.jboss.org/";
    /**
     * Constant which specifies how deep should the crawler search pages.
     */
    public static final int DEEPNESS_CONSTANT = 25;

    private int documentCounter = 0;
    private int deepness = 0;
    private Map<String, Document> urls2Documents = new HashMap<String, Document>();

    /**
     * Performs HTTP request using provided URL. If the URL already was scanned, then it is not added/returned any more.
     * The method returns the object of type com.jeejl.data.Document.
     *
     * @param url           the URL for scanning.
     * @return              the document which contains scanned information.
     * @throws Exception    if something went wrong during performing call to given URL.
     */
    private Document getWebSiteContent(String url) throws Exception {
        BufferedReader in = null;
        HttpURLConnection conn = null;
        StringBuffer buf = new StringBuffer();
        Document document = null;
        try {
            if(!url.startsWith("http")){
                url = "http://" + url;
            }
            URL userInputUrl = new URL(url);
            conn = (HttpURLConnection)userInputUrl.openConnection();
            conn.connect();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    buf.append(inputLine);
                }
                synchronized (urls2Documents) {
                    if(urls2Documents.get(url) == null) {
                        document = new Document();
                        document.setId(++documentCounter);
                        document.setUrl(url);
                        document.setWebContent(buf.toString());

                        urls2Documents.put(url, document);
                    }
                }
            }
        } finally {
            if(in != null) {
                in.close();
            }
            if(conn != null) {
                conn.disconnect();
            }
        }
        return document;
    }

    /**
     * Scanes web-site provided by input URL. Parsing takes place in the following way and sequence:
     * 1. System reads the whole content of the web-site of given URL.
     * 2. All links are found in the page and passed to the same method recursively and asynchronously.
     * 3. Then the page content is passed to processors which using different methods of NLP, retrieve keywords from the page.
     * 4. Then these keywords are stored in the cache. The key is the keyword, and the value is the list of documents
     * where this keyword appears.
     *
     * @param url       the URL of the web-site which is needed to be scanned.
     */
    private void parseWebSite(final String url) {
        try {
            Document document = getWebSiteContent(url);

            if(document != null) {
                List<String> newPageUrls = ContentProcessor.extractLinksFromPageAndSetTitle(document);
                if (deepness <= DEEPNESS_CONSTANT && (newPageUrls != null && newPageUrls.size() > 0)) {
                    ExecutorService executor = Executors.newFixedThreadPool((int) Math.ceil(newPageUrls.size() / 2));

                    for(int i = 0; i < newPageUrls.size(); i++) {
                        final String pageUrl = newPageUrls.get(i);

                        executor.submit(new Runnable() {
                            public void run() {
                                parseWebSite(pageUrl);
                            }
                        });
                    }
                    System.out.println("DEEPNESS IS: " + deepness);
                    deepness++;
                }

                List<String> keywords = ContentProcessor.retrieveKeywords(document.getWebContent(), true);
                CacheSupport cacheSupport = CacheSupport.getInstance();
                for(String keyword : keywords) {
                    List<Document> docList = (List<Document>) cacheSupport.getObject(keyword);
                    if(docList == null) {
                        docList = new ArrayList<Document>();
                    }
                    docList.add(document);
                    cacheSupport.putObject(keyword, docList);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: loading of ULR is failed" + url + "! " + e.getMessage());
            urls2Documents.remove(url);
        }
    }

    /**
     * TimerTask run method which triggers the main process of page scanning.
     */
    public void run() {
        parseWebSite(STARTUP_URL);
    }
}