package business.logic;

import common.IPerson;

/**
 * Contains basic information about a person
 *
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public class Person implements IPerson {

    /**
     * The id of the person
     */
    private int id;
    
    /**
     * The name of the person
     */
    private String name;

    /**
     * The phone number of the person
     */
    private String phoneNumber;

    /**
     * The email of the person
     */
    private String email;
    
    /**
     * The name of the department
     */
    private String departmentName;

    /**
     * Construct a new person
     *
     * @param name The name of the person
     * @param phoneNumber The phone number of the person
     * @param email The email of the person
     * @param departmentName The department name
     * @param id The person id
     */
    public Person(String name, String phoneNumber, String email, String departmentName, int id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.departmentName = departmentName;
        this.id = id;
    }

    /**
     * Gets the name of the person
     *
     * @return The name of the person
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the phone number of the person
     *
     * @return The phone number of the person
     */
    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Gets the email of the person
     *
     * @return The email of the person
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the department name
     * @return The department name
     */
    @Override
    public String getDepartmentName() {
        return this.departmentName;
    }

    /**
     * Gets the person id
     * @return The person id
     */
    @Override
    public int getId() {
        return id;
    }
    
    /**
     * Sets the department name
     * @param name The department name
     */
    public void setDepartmentName(String name) {
        departmentName = name;
    }

    /**
     * Sets the person id
     * @param id The person id
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
