package com.jeejl.nlp;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for parsing the POS tagged - XML formatted text.
 * The text is prepared and written in temporary file. Later the XML is parsed using Xerces XML parser. - http://xerces.apache.org/
 */
public class POSTagParser {
    /**
     * Array which contains all POS tags, which the system will include in keyword selection.
     */
    public static final String[] POS_TAGS = new String[]{"NN", "NNS", "NP", "NPS"}; // the words choice would be done using nouns and proper nouns - singular/plural

    /**
     * Array which contains all POS tags necessary for query parsing.
    public static final String[] QUERY_TAGS = new String[]{"DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD", "NN", "NNS", "NP", "NPS", "PDT",
            "POS", "PP", "PP$", "RB", "RBR", "RBS", "RP", "SYM", "UH", "VB", "VBD", "VBG", "VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB"}; // the words choice would be done using nouns and proper nouns - singular/plural*/

    /**
     * As the QTag library POS tags the text to XML format, it is not well-formed for XML parsing. So this method
     * modifies the text to proper XML format (without touching the POS tags) and writes the output to temporary file,
     * which path is returned.
     *
     * @param taggedTextXml     XML formatted, text which is POS-tagged with QTag.
     * @return                  The absolute path of the temporary file, where the proper XML is written.
     */
    private static String prepareCorrectXmlForParsing(String taggedTextXml) {
        StringBuffer buf = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><root>") //adding root tag.
                .append(taggedTextXml).append("</root>");

        //Creating temporary file and write XML into it.
        File tmpFile = new File("temp.xml");
        PrintWriter wr = null;
        try {
            wr = new PrintWriter(tmpFile);
            wr.write(buf.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if(wr != null) {
                wr.close();
            }
            tmpFile.deleteOnExit();
        }

        return tmpFile.getAbsolutePath();
    }

    /**
     * The method prepares the POS tagged text for XML parsing. The XML is parsed using http://xerces.apache.org/ - Xerces XML.
     * Then program selects Nouns and Pronouns from the XML according to their tag, and calculates their appearance count.
     *
     * @param taggedText        XML formatted, POS tagged text.
     * @param isKeyword         true, if the method is called for query processing.
     * @return                  map, which contains word and it's appearance count.
     * @throws Exception      if something went wrong during XML parsing.
     */
    private static Map<String, Integer> parseAndCalculateAppearance(final String taggedText, final boolean isKeyword) throws Exception {
        Map<String, Integer> wordsForIndexing = new HashMap<String, Integer>();

        List<String> wordsList = selectWords(taggedText, isKeyword);

        for(String word : wordsList) {
            Integer wordCount = wordsForIndexing.get(word);

            //calculating the appearance of the word.
            if(wordCount == null) {
                wordsForIndexing.put(word, 1);
            } else {
                wordsForIndexing.put(word, ++wordCount);
            }
        }

        return wordsForIndexing;
    }

    /**
     * The method prepares the POS tagged text for XML parsing. The XML is parsed using http://xerces.apache.org/ - Xerces XML.
     * Then program selects Nouns and Pronouns from the XML according to their tag.
     *
     * @param taggedText        the POS tagged text.
     * @param isKeyword         true, if the method called for query processing.
     * @return                  the list of selected words.
     * @throws Exception        if something went wrong while xml parsing.
     */
    public static List<String> selectWords(final String taggedText, final boolean isKeyword) throws Exception {
        System.out.println("SELECTING WORDS ................ " );

        List<String> keywords = new ArrayList<String>();

        //prepares proper XML and writes it to the temporary file.
        String preparedXMLPath = prepareCorrectXmlForParsing(taggedText);
        DOMParser parser = new DOMParser();
        parser.parse(preparedXMLPath); //parses XML using Xerces parser.

        Document doc = parser.getDocument();
        NodeList nodes = doc.getElementsByTagName("w");

        //runs through the <w> elements, takes the pos attribute, which contains the word's tag.
        for(int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if(node.hasAttributes()) {
                Node tagPos = node.getAttributes().getNamedItem("pos");
                //If the tag is noun or pronoun then it is selected.
                if(tagPos != null && isIndexWord(tagPos.getTextContent())) {
                    keywords.add(node.getTextContent());
                }
            }
        }

        return keywords;
    }


    /**
     * Checks whether the selected word tag is mentioned in the constant.
     * @param tag       the tag of the selected word to check.
     * @return          true, if the tag is set in the constant, otherwise - false.
     */
    private static boolean isIndexWord(final String tag) {
        boolean isIndexWord = false;

        for(String reqTag : POS_TAGS) {
            if(reqTag.equals(tag)) {
                isIndexWord = true;
                break;
            }
        }

        return isIndexWord;
    }

    /**
     * Keyword selector - gots the tagged text as input. Calculates the percentage
     * of each word appearance, based on which calculates the middle percentage.
     * <p/>
     * The keywords are selected using the logic >= middle percent and are stored in the list.
     *
     * @param taggedContent- tagged Content.
     * @return - the list of selected keywords.
     */
    public static List<String> selectKeywords(final String taggedContent) throws Exception {
        Map<String, Integer> words2Appearance = parseAndCalculateAppearance(taggedContent, false);

        System.out.println("CALCULATING WORDS APPEARANCE ..............................");
        List<String> keywords = new ArrayList<String>();
        Map<String, Double> wordsAppearance2Percents = new HashMap<String, Double>();
        int wordCount = words2Appearance.size();

        double percentSum = 0;
        boolean isFirstIteration = true;
        //Going through each word and it's appearance count for calculation the frequency percentage.
        for (Map.Entry<String, Integer> wordEntry : words2Appearance.entrySet()) {
            String word = wordEntry.getKey();
            Integer count = wordEntry.getValue();

            //counting word appearance percentage and storing it in map for further processing.
            double percent = count * 100 / (double) wordCount;
            wordsAppearance2Percents.put(word, percent);

            //System.out.println("WORD - " + word + ", APPEARANCE COUNT - " + count + ", PERCENTAGE - " + percent);
            percentSum += percent;
        }

        System.out.println("\n THE TOTAL MATCHED WORDS COUNT: " + wordCount);

        System.out.println("Max Percent is: " + percentSum);

        double middlePercent = percentSum / wordCount;
        System.out.println("\n Selecting keywords with appearance >=" + middlePercent);

        //Running through the words with their appearance frequency and selecting words with frequency higher than middlePercent.
        for (Map.Entry<String, Double> wordEntry : wordsAppearance2Percents.entrySet()) {
            double percent = wordEntry.getValue();
            if (percent >= middlePercent) {
                String word = wordEntry.getKey();

                System.out.println("WORD - " + word + ", APPEARANCE COUNT - " + words2Appearance.get(word) + ", PERCENTAGE - " + percent);

                keywords.add(word);
            }
        }

        return keywords;
    }
}
