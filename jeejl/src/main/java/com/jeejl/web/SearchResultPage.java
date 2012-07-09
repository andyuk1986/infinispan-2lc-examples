package com.jeejl.web;

import com.jeejl.cache.CacheSupport;
import com.jeejl.data.Document;
import com.jeejl.nlp.ContentProcessor;

import java.io.IOException;
import java.util.*;

/**
 * Bean class for result.jsp page. It processes the user's query, and returns the list of all pages
 * where the query appears.
 */
public class SearchResultPage extends Page{
    /**
     * Query parameter name.
     */
    public static final String QUERY_PARAM = "query";

    /**
     * Processes the user's search and returns the list of pages where the query appears.
     * Works with the following logic:
     * 1. Parses the user's query, stems the words and gets the roots of the words.
     * 2. Retreives the list of documents for each keyword.
     * 3. Merge the result for each keyword, increases the appearance of the document where all or some of the keywords
     * appear.
     * 4. Sorts the final result according to the appearance in descending order.
     *
     * @return              the result list which contains all documents where keywords appear.
     * @throws IOException  if something went wrong during words processing.
     */
    public List<Document> processSearchQuery() throws IOException {
        List<Document> sortedResult = new ArrayList<Document>();

        String query = getParameter(QUERY_PARAM);

        if(query != null && !query.isEmpty()) {
            List<String> keywords = ContentProcessor.retrieveKeywords(query, false);

            if(keywords != null && !keywords.isEmpty()) {
                CacheSupport cacheSupport = CacheSupport.getInstance();
                Map<Integer, Document> documentMap = new HashMap<Integer, Document>();
                for(String keyword : keywords) {
                    List<Document> documentList = (List<Document>) cacheSupport.getObject(keyword);
                    if(documentList != null && !documentList.isEmpty()) {
                        for(Document documentFromList : documentList) {
                            Document existingDoc = documentMap.get(documentFromList.getId());
                            if(existingDoc != null) {
                                int counter = existingDoc.getAppearanceCount();
                                existingDoc.setAppearanceCount(++counter);
                            } else {
                                documentMap.put(documentFromList.getId(), documentFromList);
                            }
                        }
                    }
                }

                sortedResult.addAll(documentMap.values());
                Collections.sort(sortedResult);
            }
        }

        return sortedResult;
    }
}
