package iBook.web;

/**
 * Form class which could be used for web pages where form exists and submit is required.
 */
public abstract class Form extends Page {
    /**
     * Abstract method which should be implemented by all child classes. Should implement
     * the
     */
    public abstract void submit() throws Exception;
}
