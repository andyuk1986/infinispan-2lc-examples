package iBook.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * General page class for working with JSP.
 * @author anna.manukyan
 *
 */
public abstract class Page {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	public static final String PARAM_IS_SUBMITTED = "isSubmitted";
	public static final String LOGGED_IN_USER = "loggedInUser";
	public static final String INDEX_URL = "index.jsp";
	
	/**
	 * Initializes the http related stuff.
	 * @param request		http request.
	 * @param response		http response.
	 * @param session		http session.
	 */
	public void init(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session) {
		this.request = request;
		this.response = response;
		this.session = session;
	}
	
	/**
	 * Returns the parameter from request according to the provided key.
	 * @param key		the key of the parameter.		
	 * @return			the value of parameter from request.
	 */
	public String getParameter(final String key) {
		return request.getParameter(key);
	}
	
	/**
	 * Return the integer value for parameter from the request.
	 * @param key		the key of the parameter.
	 * @return			the returned value.
	 */
	public int getIntParameter(final String key){
		int returnVal = 0;
		
		String paramVal = request.getParameter(key);
		
		if(paramVal != null){
			try {
				returnVal = Integer.parseInt(paramVal);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return returnVal;
	}
	
	/**
	 * Checks whether the form is submitted.
	 * @return		<code>true</code>, if the form is submitted.
	 */
	public boolean isFormSubmitted() {
		boolean isSubmitted = false;
		
		String param = getParameter(PARAM_IS_SUBMITTED); 
		if(param != null && param.equals("true")){
			isSubmitted = true;
		}
		
		return isSubmitted;
	}
}
