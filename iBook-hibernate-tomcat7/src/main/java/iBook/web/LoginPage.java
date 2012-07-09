package iBook.web;

import iBook.dao.BookDao;
import iBook.dao.UserDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.Book;
import iBook.domain.User;
import iBook.utils.EncryptionUtils;

import java.util.List;

/**
 * Page responsible for login.
 */
public class LoginPage extends Form {
    /**
     * Parameter specifying user name.
     */
	public static final String PARAM_USER_NAME = "userName";
    /**
     * Parameter specifying password.
     */
	public static final String PARAM_PASSWORD = "password";
    /**
     * Parameter specifying that error occurred- used in session.
     */
	public static final String ERROR_MSG = "error";
    /**
     * Parameter specifying that error occurred.
     */
	public static final String PARAM_ERROR_MSG = "error";

	@Override
	public void submit() throws Exception {
		if(validateForm()) {
			String password = EncryptionUtils.base64encode(getParameter(PARAM_PASSWORD));

            UserDao userInterface = DaoFactory.getInstance().getUserDao();
            User user = null;
            if(userInterface != null) {
                user = userInterface.getUserByCredentials(getParameter(PARAM_USER_NAME), password);
            }

			if(user != null) {
				session.removeAttribute(ERROR_MSG);
				session.setAttribute(LOGGED_IN_USER, user);
				response.sendRedirect(INDEX_URL);
				
				return;
			} 
		} 
		
		response.sendRedirect(response.encodeRedirectURL(INDEX_URL + "?" + PARAM_ERROR_MSG + "=true"));
	}

    /**
     * Validates the login form.
     * @return      <code>true</code>, if the validation passed, otherwise <code>false</code>.
     */
	public boolean validateForm() {
		String userName = getParameter(PARAM_USER_NAME);
		String password = getParameter(PARAM_PASSWORD);
		
		boolean isPassed = true;
		
		if(userName == null || userName.isEmpty() || userName.length() >10 || 
				password == null || password.isEmpty() || password.length() > 10){
			isPassed = false;
		}
		
		return isPassed;
	}

    /**
     * Returns random 4 books to show on cover page.
     * @return      list of random books for cover page.
     */
	public List<Book> getCoverBooks() {
        List<Book> bookList = null;

        BookDao bookInterface = DaoFactory.getInstance().getBookDao();
        if(bookInterface != null) {
            bookList = bookInterface.getCoverBooks();
        }
        return bookList;
	}
}
