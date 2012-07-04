package iBook.ejb.stateless.remote;

import iBook.domain.UserPayments;

import javax.ejb.Remote;

/**
 * Dao for working with UserPayments.
 */
@Remote
public interface UserPaymentRemote {
    /**
     * Saves the user's payment with all corresponding cascadings.
     * @param payments      the user's payment object.
     */
    public void saveUserPayments(UserPayments payments);

}
