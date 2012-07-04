package iBook.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator class for email.
 * @author anna.manukyan
 *
 */
public class EmailValidator {
	
	/**
	 * The email regular expression.
	 */
	private static Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	/**
	 * Validates the provided email.
	 * @param email	the email to validate.
	 * @return		true, if the email is normal email address.
	 */
	public boolean validate(final String email) {
		boolean isProperEmail = false;
		
		if(email == null || email.isEmpty()) {
			isProperEmail = false;
		} else {
			Matcher matcher = emailPattern.matcher(email);
			isProperEmail = matcher.matches();
		}
		
		return isProperEmail;
	}

}
