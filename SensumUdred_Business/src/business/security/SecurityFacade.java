package business.security;

import business.common.ISecurityFacade;
import common.IUser;
import common.IUserManager;
import common.Role;

/**
 * The facade of the security Package
 *
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public class SecurityFacade implements ISecurityFacade {

    /**
     * An instance of the user manager, responsible for creating and storing the
     * registered users of the system.
     */
    private UserManager users;
    /**
     * An instance of the security manager, responsible for most of the security
     * logic.
     */
    private SecurityManager security;

    /**
     * A Constructor for the Security facade, which allows the injection of the
     * security manager and the user manager into each other.
     */
    public SecurityFacade() {
        security = new SecurityManager();
        users = new UserManager();
        security.injectUserManager(users);
        users.injectSecurityManager(security);
    }

    /**
     * A Constructor for the Security facade, which allows the injection of the
     * security manager and the user manager into each other. This constructor
     * uses an IUserManager to load users into the system.
     *
     * @param userManager The usermananger instance
     */
    public SecurityFacade(IUserManager userManager) {
        security = new SecurityManager();
        users = new UserManager(userManager);
        security.injectUserManager(users);
        users.injectSecurityManager(security);
    }

    /**
     * Logs the current user into the system, if their username and password
     * matches that of a registered user.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return user id if the log in was successful, otherwise null
     */
    @Override
    public String logIn(String username, String password) {
        return security.logIn(username, password);
    }

    /**
     * Logs the current user out of the system.
     *
     * @return user id if the log out was successful, otherwise null
     */
    @Override
    public boolean logOut() {
        return security.logOut();
    }

    /**
     * Returns true if the current user has the given role, false otherwise.
     *
     * @param role The role the current user is being checked for.
     * @return true if the current user has the given role, false otherwise
     */
    @Override
    public boolean hasAccess(Role role) {
        return security.hasAccess(role);
    }

    /**
     * Return. the user manager containing the users.
     *
     * @return the user manager containing the users
     */
    @Override
    public IUserManager getUserManager() {
        return users;
    }

    /**
     * Adds a new user to the system and returns its user id
     *
     * @param name the name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user
     * @return the user id of the new user
     */
    @Override
    public String addUser(String name, String username, String password, Role role) {
        return users.addUser(name, username, password, role);
    }

    /**
     * Gets the current logged in user
     * @return The current logged in user
     */
    @Override
    public IUser getCurrentUser() {
        return security.getCurrentUser();
    }
}
