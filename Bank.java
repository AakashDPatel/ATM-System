
package atmsystem;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Aakash
 */
public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    /**
     *
     * @param name
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }
    
    
    /**
     *
     * @return
     */
    public String getNewUserUUID() {
        
        String uuid;
        Random rng = new Random();
        boolean nonUnique;
        int len = 6;
        
        do {            
            //generate a number
            uuid = "";
            for (int i = 0; i <= len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            
            //check if uuid is not unique
            nonUnique = false;
            for (User u : this.users) {
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }
    
    /**
     *
     * @return
     */
    public String getNewAccountUUID() {
        String uuid;
        Random rng = new Random();
        boolean nonUnique;
        int len = 6;
        
        do {            
            //generate a number
            uuid = "";
            for (int i = 0; i <= len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            
            //check if uuid is not unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if(uuid.compareTo(a.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }
    
    /**
     * 
     * @param anAcct 
     */ 
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }
    /**
     * 
     * @param firstName
     * @param lastName
     * @param pin
     * @return 
     */
    public User addUser(String firstName, String lastName, String pin) {
        //Create a User object and add into our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        
        //create a saving account for the user
        Account newAccount = new Account("Saving",newUser, this);   
        
        //add to holder and bank lists
        /**
         * not adding copy of lists but both have original values
         * if changing balance or transactions will make change in both
         */
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        
        return newUser;
    }
    
    /**
     *
     * @param userID
     * @param pin
     * @return
     */
    public User userLogin(String userID, String pin) {
        //search through list of users
        for (User u : this.users){
            //check if ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        return null;
    }
    /**
     * 
     * @return 
     */
    public String getName() {
        return this.name;
    }
}
