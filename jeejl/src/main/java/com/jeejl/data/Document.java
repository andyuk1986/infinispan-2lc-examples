package com.jeejl.data;

import java.io.Serializable;

/**
 * Document class which stores all necessary information regarding web-page.
 */
public class Document implements Serializable, Comparable {
    /**
     * Auto-increment ID of the web-page.
     */
    private int id;
    /**
     * The url of the page.
     */
    private String url;
    /**
     * The count of appearance of the web-page within the query.
     */
    private int appearanceCount;
    /**
     * The title of the web-page (is used for quick view on the page).
     */
    private String title;
    /**
     * The web content of the web-page.
     */
    private String webContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAppearanceCount() {
        return appearanceCount;
    }

    public void setAppearanceCount(int appearanceCount) {
        this.appearanceCount = appearanceCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebContent() {
        return webContent;
    }

    public void setWebContent(String webContent) {
        this.webContent = webContent;
    }

    public boolean equals(Object o) {
        boolean equal = false;

        if(o != null && o instanceof Document) {
            Document obj = (Document) o;
            if(id == obj.id) {
                equal = true;
            }
        }

        return equal;
    }

    /**
     * The hash of the URL.
     * @return      the hash of the URL.
     */
    public int hashCode() {
        return url.hashCode();
    }

    /**
     * Overriden method which is used for sorting the Document type objects by appearanceCount in descending order.
     * @param o     the object for comparison.
     * @return      0, if the objects are equal, -1, if the given object is less then this object, 1 otherwise.
     */
    public int compareTo(Object o) {
        int returnVal = -1;
        if (o != null && o instanceof Document) {
            Document obj = (Document) o;

            returnVal = new Integer(obj.getAppearanceCount()).compareTo(this.getAppearanceCount());
        }
        return returnVal;
    }
}
