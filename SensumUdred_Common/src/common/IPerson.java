package common;

/**
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public interface IPerson {
    /**
     * Get the name of the person
     *
     * @return name
     */
    String getName();
 
    /**
     * Get the mobile number of the person
     *
     * @return phone number
     */
    String getPhoneNumber();
 
    /**
     * Get the email of the person;
     *
     * @return email
     */
    String getEmail();
}
