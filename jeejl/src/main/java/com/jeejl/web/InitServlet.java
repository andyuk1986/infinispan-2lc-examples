package com.jeejl.web;

import com.jeejl.crawler.WebCrawler;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import java.util.Timer;

/**
 * Initialization Servlet, which listens the server startup/shut down.
 * On server startup the system triggers the Web Crawler job, which will work every day.
 */
public class InitServlet extends HttpServlet implements ServletContextListener {
    private Logger logger = Logger.getLogger(InitServlet.class);
    private Timer crawlerTimer = null;

    private static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    /**
     * Starts up the web crawler job for indexing the web-pages.
     * @param sce
     */
    public void contextInitialized(ServletContextEvent sce) {
        WebCrawler crawler = new WebCrawler();
        crawlerTimer = new Timer();

        crawlerTimer.schedule(crawler, new Date(), DAY_IN_MILLIS);

        logger.info("APPLICATION INITIALIZED");
    }

    /**
     * Works on server shut down.
     * @param sce
     */
    public void contextDestroyed(ServletContextEvent sce) {
        if(crawlerTimer != null) {
            crawlerTimer.cancel();
        }
        logger.info("APPLICATION DESTROYED");
    }
}
