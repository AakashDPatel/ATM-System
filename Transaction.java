
package atmsystem;

import java.util.Date;

/**
 *
 * @author Aakash
 */
public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;
    
    /**
     * 
     * @param Amount
     * @param inAccount 
     */
    
    
    /**
     * 
     * @param amount
     * @param memo
     * @param inAccount 
     */
    public Transaction(double amount, String memo, Account inAccount) {
        //call two - arg constructor first
        //this(amount, inAccount);
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = memo;
        System.out.println("Amount is: "+amount);
    }
    
    /**
     *
     * @return
     */
    public double getAmount(){
        return this.amount;
    }
    
    /**
     *
     * @return
     */
    public String getSummaryLine(){
        if(this.amount >=0){
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),this.amount, this.memo);
        }else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),-this.amount, this.memo);
        }
    }
    
    
}
