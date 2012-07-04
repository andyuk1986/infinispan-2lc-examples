package iBook.ejb.stateless.remote;


import javax.ejb.Remote;

/**
 * UserRemote interface for User stateless bean.
 * @author anna.manukyan
 *
 */
@Remote
public interface UserRemote {
    /**
     * Saving user in DB.
     * @param userObj		the user to save.
     */
    public void save(iBook.domain.User userObj);

    /**
     * Gets the user by ID.
     * @param id		the id to use.
     * @return			the returned user.
     */
    public iBook.domain.User getUserById(final int id);

    /**
     * Gets the user by credentials - userName and password.
     * @param userName		the user name.
     * @param password		the password.
     * @return				the found user.
     */
    public iBook.domain.User getUserByCredentials(final String userName, final String password);

    /**
     * Gets the user by user name.
     * @param userName		the username to check.
     * @return				the found user.
     */
    public iBook.domain.User getUserByUserName(final String userName);

}
