package qtag;

import java.io.IOException;

/**
 * Wrapper class for qtag.Qtag class which is placed in qtag-1.0.jar library(is downloaded from
 * http://phrasys.net/uob/om/software ).
 *
 * The main Qtag.java class constructor and main part of the methods,
 * is implemented using default access modifiers, which allows to access the class
 * only from the same package.
 *
 * As a result wrapper class is created in the same qtag package. The class is singleton and wrappes an Object of Qtag type.
 *
 * All necessary methods are implemented here so that other applications may use the QTag tagger without additional efforts.
 *
 * The qtag-1.0.jar have been decompiled using JavaDecompiler program (@see http://java.decompiler.free.fr/). This has been
 * done for understanding how the Qtag.java is implemented so that it is possible to reuse it in this application.
 */
public final class QTagWrapper {
    /**
     * Constant which identifies the tagged text output as XML.
     */
    public static final int XML_OUTPUT = 1;
    /**
     * Constant which identifies the tagged text output as string containing tab.
     */
    public static final int TAB_OUTPUT = 2;
    /**
     * Constant which identifies the tagged text output containing all info about tagged part of speech.
     */
    public static final int AC_OUTPUT = 3;

    /**
     * The Qtag object for which this wrapper is created.
     */
    private Qtag qtag = null;

    /**
     * The single instance variable for this class.
     */
    private static QTagWrapper wrapper = null;

    /**
     * Private constructor of Singletong QTag wrapper class.
     * Creates the instance of QTag object using english.lex library.
     * @throws java.io.IOException      is thrown if something went wrong during object initialization.
     */
    private QTagWrapper() throws IOException {
        qtag = new Qtag("lib/english");
    }

    /**
     * Static method for creating/getting the instance of this wrapper class.
     * If the method is accessed the first time, the object is newly created. After, the existing object is returned.
     * @return              the instance of this class.
     * @throws java.io.IOException  is thrown if something went wrong during Qtag initialization.
     */
    public static QTagWrapper getInstance() throws IOException {
        if(wrapper == null) {
            wrapper = new QTagWrapper();
        }

        return wrapper;
    }

    /**
     * Sets ouput format type for the tagged object.
     * @param outputFormat      The output format type, one of the defined constants above.
     */
    public void setOutputFormat(final int outputFormat) {
        qtag.setOutputFormat(outputFormat);
    }

    /**
     * Tags the parts of speech in the given input text. If tokenize variable is passed as false,
     * then the string is not tokenized before, and the last words of the sentences are not identified (e.g. bean.).
     * If the tokenize variable is passed as true, then the string is tokenized first, and then tagged, which results
     * that punctuation signs are also tagged.
     *
     * @param inputText         the input text which should be tagged.
     * @param tokenize          boolean variable, which identifies whether the input text should be tokenized before
     *                          tagging or no.
     *
     * @return                  tagged input text.
     */
    public String tagString(final String inputText, final boolean tokenize) {
        return qtag.tagLine(inputText, tokenize);
    }
}
