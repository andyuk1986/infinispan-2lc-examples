package com.jeejl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Page which all jsp beans should extend. It contains specific methods which all beans would need.
 */
public class Page {
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    /**
     * Initializes the bean with request and response, so that later it is possible to work with them.
     * @param request           the request object.
     * @param response          the response object.
     */
    public void init(final HttpServletRequest request, final HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Gets and returns the parameter value from the request.
     * @param paramName     the name of the parameter.
     * @return              the  value of the parameter.
     */
    public String getParameter(final String paramName) {
        return request.getParameter(paramName);
    }
}
