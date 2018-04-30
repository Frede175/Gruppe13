/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 * The interface for the citizen.
 *
 * @author Andreas Mølgaard-Andersen
 * @author Lars Bjerregaard Jørgensen
 * @author Frederik Rosenberg
 * @author Mikkel Larsen
 * @author Sebastian Christensen
 * @author Kasper Schødts
 */
public interface ICitizen extends IPerson {

    /**
     * The cpr number of the citizen
     *
     * @return the cpr number
     */
    int getCpr();

    /**
     * The cpr number of the citizen
     *
     * @return the address
     */
    String getAddress();

    /**
     * The gender of the citizen
     *
     * @return the gender
     */
    Gender getGender();

    /**
     * The status of the citizens relationship
     *
     * @return the relationship status
     */
    RelationshipStatus getRelationshipStatus();
}
