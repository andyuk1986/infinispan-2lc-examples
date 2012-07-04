package iBook.web;

import iBook.domain.User;
import iBook.ejb.stateless.remote.UserRemote;
import iBook.utils.Utils;
import iBook.utils.EncryptionUtils;
import iBook.web.validator.EmailValidator;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

/**
 * Bean class responsible for the Registration.
 * @author anna.manukyan
 *
 */
public class RegistrationPage extends Form {
    /**
     * Parameter specifying user name.
     */
	public static final String PARAM_USER_NAME = "userName";
    /**
     * Parameter specifying password.
     */
	public static final String PARAM_PASSWORD = "password";
    /**
     * Parameter specifying email.
     */
	public static final String PARAM_EMAIL = "email";
    /**
     * Parameter specifying confirm password.
     */
	public static final String PARAM_CONFIRM_PASSWORD = "confPassword";
    /**
     * Parameter specifying error message.
     */
	public static final String ERROR_MSGS = "errorMsgs";
    /**
     * The URL of the registration page.
     */
	public static final String WEB_URL = "register.jsp";

    /**
     * Registers user to DB.
     * @throws Exception    if something happend during redirection or forwarding.
     */
	@Override
	public void submit() throws Exception {
		List<String> errorMsgs = validateForm();
		if(errorMsgs.isEmpty()) {
			User user = new User();
			user.setUserName(getParameter(PARAM_USER_NAME));
			user.setPassword(EncryptionUtils.base64encode(getParameter(PARAM_PASSWORD)));
			user.setEmail(getParameter(PARAM_EMAIL));
			
			Utils.getInstance().getUserDao().save(user);
			
			//@TODO store in infinispan
			session.setAttribute(LOGGED_IN_USER, user);
			response.sendRedirect(INDEX_URL);
		} else {
			request.setAttribute(ERROR_MSGS, errorMsgs);
			request.getRequestDispatcher(WEB_URL).forward(request, response);
		}
		
	}

    /**
     * Validates the registration form.
     * @return      the list of error messages if any.
     */
	public List<String> validateForm() {
		String userName = getParameter(PARAM_USER_NAME);
		String password = getParameter(PARAM_PASSWORD);
		String confirmPassword = getParameter(PARAM_CONFIRM_PASSWORD);
		String email = getParameter(PARAM_EMAIL);
		
		List<String> errorMsgs = new ArrayList<String>();
		
		if(userName == null || userName.isEmpty()){
			errorMsgs.add("The username should not be empty!");
		} else if(userName.length() >10 ){
			errorMsgs.add("The username should be max 10 symbols!");
		}
		
		if(password == null || password.isEmpty()) {
			errorMsgs.add("Password should not be empty!");
		} else if(password.length() > 10) {
			errorMsgs.add("The password should be max 10 symbols!");
		}
		
		if(!password.equals(confirmPassword)) {
			errorMsgs.add("Password & Confirm Password should be the same!");
		} 
		
		EmailValidator validator = new EmailValidator();
		if(!validator.validate(email)) {
			errorMsgs.add("Please fill proper email!");
		}
		
		if(errorMsgs.isEmpty()) {
            UserRemote userInterface= null;
            try {
                userInterface = Utils.getInstance().getUserDao();
                User existingUser = userInterface.getUserByUserName(userName);
                if(existingUser != null) {
                    errorMsgs.add("Please pick another UserName!");
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }
		}
		return errorMsgs;
	}

}
