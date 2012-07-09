package com.jeejl.nlp;

import com.jeejl.data.Document;
import com.jeejl.nlp.stemmer.PorterStemmer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import qtag.QTagWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Text Processor class, which performes all necessary actions with the text.
 * This includes markup deletion, tokenizing, POS tagging, appearance calculation.
 * <p/>
 * For markup deletion external library is used - JSoup; @see - http://jsoup.org/
 * For POS tagging QTag tagger is used; @see - http://phrasys.net/uob/om/software.
 */
public class ContentProcessor {
    /**
     * Regular expression for tokenizing the text.
     */
    public static final String PUNCTUATION_REG_EXP = "!#$%^&*()_+={}[],-.?/:;’‘\" \t\n";

    /**
     * Extracts all URLs from the link elements of the web page.
     * @param webDocument       the web page representing Document object.
     * @return                  the list of collected URLs.
     */
    public static List<String> extractLinksFromPageAndSetTitle(final Document webDocument) {
        List<String> newPageUrls = new ArrayList<String>();
        org.jsoup.nodes.Document htmlDoc = Jsoup.parse(webDocument.getWebContent());

        Elements titleElem = htmlDoc.getElementsByTag("title");
        if(titleElem != null && !titleElem.isEmpty()) {
            for(Element elem : titleElem) {
                webDocument.setTitle(elem.text());
            }
        }

        Elements linkElems = htmlDoc.getElementsByTag("a");
        if(linkElems != null && !linkElems.isEmpty()) {
            for (Element elem : linkElems) {
                if (elem.hasAttr("href")) {
                    String newUrl = elem.attr("href");
                    if(newUrl != null && !newUrl.isEmpty() && newUrl.contains("jboss")) {
                        newPageUrls.add(newUrl);

                        System.out.println(newUrl);
                    }
                }
            }
        }

        return newPageUrls;
    }

    /**
     * Removes the comment tags from HTML formatted text.
     *
     * @param textFromHtml HTML formatted text.
     * @return HTML formatted text without #comment tags.
     */
    private static String removeComments(String textFromHtml) {
        textFromHtml = textFromHtml.replaceAll("<!--", "");
        textFromHtml = textFromHtml.replaceAll("-->", "");

        return textFromHtml;
    }

    /**
     * Delete markup tags from the got input HTML text. For parsing HTML,
     * JSoup library is used, which parses HTML text, replaces HTML special symbols with their ASCII codes.
     * <p/>
     * As JSoup do not output the content of #comment tags, all comment tags are removed from the text beforehand,
     * for not loosing the information stored there.
     * <p/>
     * All information regarding JSoup is taken from: http://jsoup.org/
     * <p/>
     * For not loosing the information stored in META tags, the methods run through the meta tags and
     * attaches their content to the read/parsed text.
     *
     * @param textFromHtml HTML formatted input text which markup tags should be deleted.
     * @return the plain text which contains content from HTML text as well as content of meta tags.
     */
    public static String deleteMarkups(String textFromHtml) {
        System.out.println("DELETING MARKUPS ..............................");
        textFromHtml = removeComments(textFromHtml);
        StringBuffer strContent = new StringBuffer();

        if (textFromHtml != null) {
            org.jsoup.nodes.Document htmlDoc = Jsoup.parse(textFromHtml);

            Elements metaElements = htmlDoc.getElementsByTag("meta");
            for (Element elem : metaElements) {
                if (elem.hasAttr("content")) {
                    strContent.append(elem.attr("content")).append(" ");
                }
            }

            strContent.append(htmlDoc.text());
        }

        String returnValue = strContent.toString();

        //System.out.println("CONTENT OF FILE (with deleted markups): \n" + returnValue + "\n");
        return returnValue;
    }

    /**
     * Tokenises the input text. Then normalizes it by making all letters lowercase and removing punctuation signs.
     *
     * @param line the text which should be tokenized and normalized.
     * @return tokenized & normalized text.
     */
    public static String tokenizeAndNormalize(final String line) {
        System.out.println("TOKENIZING & NORMALIZING THE INPUT .......................");

        //tokenizes the text according to punctuation signs.
        StringTokenizer textTokenizer = new StringTokenizer(line, PUNCTUATION_REG_EXP, true);
        StringBuilder retval = new StringBuilder();

        String token = null;
        String prevToken = null;
        boolean isSpecialCase = false;
        while (textTokenizer.hasMoreTokens()) {
            prevToken = token;
            token = textTokenizer.nextToken();

            //if no whitespace follows the special characters, then these are special cases - do not tokenize.
            if (prevToken != null && (prevToken.equals(".") || prevToken.equals(",") || prevToken.equals("-")
                    || prevToken.equals("’"))) {
                if (token.charAt(0) != ' ') {
                    isSpecialCase = true;
                }
            }

            if (!isSpecialCase) {
                retval.append(" ");

                //Removing Punctuation signs
                token = token.replaceAll("\\W", "");
            } else {
                isSpecialCase = false;
            }

            retval.append(token);

        }

        //Makes all uppercase letters - lowercase.
        String returnVal = retval.toString().toLowerCase();
        //System.out.println("CONTENT AFTER TOKENIZATION & NORMALIZATION: \n" + returnVal + "\n");
        return returnVal;
    }

    /**
     * Performs POS tagging of the input text. The output format of the tagged text is XML.
     * This is done for convenience in future processing.
     * <p/>
     * For POS tagging, QTag tagger is used. The library is downloaded from http://phrasys.net/uob/om/software .
     *
     * @param htmlLine - the input line from the html page.
     * @return - XML formatted POS tagged text.
     * @throws java.io.IOException - is thrown, if problem occurred during QTag initialization.
     */
    public static String tagPartOfSpeach(final String htmlLine) throws IOException {
        System.out.println("POS TAGGING THE TEXT ..............................");
        //initializes QTag library.
        QTagWrapper wrapper = QTagWrapper.getInstance();
        //Sets XML as output format.
        wrapper.setOutputFormat(QTagWrapper.XML_OUTPUT);

        String taggedText = wrapper.tagString(htmlLine, false);

        //System.out.println("TAGGED CONTENT is: \n" + taggedText);
        return taggedText;
    }

    /**
     * Method which gets as input the list of selected keywords, and passes them to Porter Stemmer.
     * Each word is splitted to char array and then passed to the com.jeejl.nlp.stemmer.
     *
     * @param keywords the list of selected keywords.
     * @return the list of stemmed words.
     */
    public static List<String> stemWords(final List<String> keywords) {
        List<String> stemmedWords = new ArrayList<String>();
        //if the keywords are selected and the list is not empty, the words are stemmed, using PorterStemmer.
        if (keywords.size() > 0) {
            PorterStemmer stemmer = new PorterStemmer();
            System.out.println("\n RESULTS of STEMMING ........................");
            for (String keyword : keywords) {
                char[] keywordCharArr = keyword.toCharArray();
                for (char c : keywordCharArr) {
                    stemmer.add(c);
                }

                stemmer.stem();

                stemmedWords.add(stemmer.toString());
                System.out.println(keyword + " " + stemmer.toString());
            }
        }

        return stemmedWords;
    }

    /**
     * Retrieves the keywords from the given web content. The steps are done in the following sequence:
     *
     * 1. markups are deleted from the web content - the output is a plain text.
     * 2. the content is tokenized and normalized for further processing.
     * 3. the processed text if passed through POS tagger.
     * 4. the keywords are selected from the tagged text.
     * 5. the selected keywords are stemmed and the final output of the method is the list of keywords.
     *
     * @param webContent        the web-page content.
     * @param isWebContent      determines whether the passed content is web content or simple query.
     * @return                  the list of keywords.
     */
    public static List<String> retrieveKeywords(final String webContent, final boolean isWebContent) {
        List<String> keywordList = null;
        String siteContent = webContent;
        if(isWebContent) {
            siteContent = deleteMarkups(siteContent);
        }
        siteContent = tokenizeAndNormalize(siteContent);

        try {
            siteContent = tagPartOfSpeach(siteContent);
            if (siteContent != null) {
                List<String> keywords = null;
                if(isWebContent) {
                    keywords = POSTagParser.selectKeywords(siteContent);
                } else {
                    keywords = POSTagParser.selectWords(siteContent, true);
                }

                if(keywords != null) {
                    keywordList = stemWords(keywords);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keywordList;
    }
}

