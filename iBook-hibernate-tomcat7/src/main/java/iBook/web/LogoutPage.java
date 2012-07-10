package iBook.web;

import iBook.domain.User;
import iBook.utils.Utils;

/**
 * Page responsible for user logout.
 * @author anna.manukyan
 *
 */
public class LogoutPage extends Page {

    /**
     * Logouts user from system - i.e. invalidates the session and redirects user to Home Page.
     * @throws Exception    if something happens during redirection.
     */
	public void logout() throws Exception {
        User user = (User) session.getAttribute(LOGGED_IN_USER);
        Utils.getInstance().getBookWishList(session).finishWishList();

        session.invalidate();

		response.sendRedirect(INDEX_URL);
	}

}
