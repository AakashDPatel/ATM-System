
package atmsystem;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aakash
 */
public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    // where user store pin code not actually value but hash value
    private byte pinHash[]; 
    private ArrayList<Account> accounts;
    
    /**
     * 
     * @param firstName
     * @param lastName
     * @param pin
     * @param theBank 
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        
        this.firstName = firstName;
        this.lastName = lastName;
        
        try {
            //store pin's MD5 hash, rather than original value for security reason
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught NoSuchAlgorithmException");
            ex.printStackTrace();
            System.exit(1);
        }
        
        //get a new Unique Universal ID for a user
        this.uuid = theBank.getNewUserUUID();
        
        //create empty list of all accounts
        this.accounts = new ArrayList<Account>();
        
        //print log message
        System.out.printf("New user %s %s with ID %s created.\n", lastName, firstName, this.uuid);
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
     * @return
     */
    public String getUUID(){
        return this.uuid;
    }
    /**
     * 
     * @return 
     */
    public String getFirstName(){
        return this.firstName;
    }
    /**
     * 
     * @param aPin
     * @return 
     */
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught NoSuchAlgorithmException");
            ex.printStackTrace();
            System.exit(1);
        }
            return false;
    }
    
    /**
     *
     */
    public void printAccountsSummary(){
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d) %s\n", i+1,this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     *
     * @return
     */
    public int numAccounts() {
       return this.accounts.size();
    }

    /**
     *
     * @param actIndex
     */
    public void printAcctHistory(int actIndex) {
        this.accounts.get(actIndex).printTransHistory();
    }
    
    /**
     *
     * @param actIndex
     * @return
     */
    public double getAcctBalance(int actIndex){
        return this.accounts.get(actIndex).getBalance();
    }

    /**
     *
     * @param actIndex
     * @return
     */
    public String getAcctUUID(int actIndex) {
        return this.accounts.get(actIndex).getUUID();
    }

    /**
     *
     * @param actIndex
     * @param amount
     * @param memo
     */
    public void addAcctTransaction(int actIndex, double amount, String memo) {
        this.accounts.get(actIndex).addTransaction(amount, memo);
    }
}

