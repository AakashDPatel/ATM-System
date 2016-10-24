
package atmsystem;

import java.util.ArrayList;

/**
 *
 * @author Aakash
 */
public class Account {
    // account type
    private String name;
    private String uuid;
    // user object that holds account
    private User holder;
    private ArrayList<Transaction> transactions;
    
    /**
     * 
     * @param name
     * @param holder
     * @param theBank 
     */
    public Account(String name, User holder, Bank theBank ) {
        this.name = name;
        this.holder = holder;
        
        //get a new Unique Universal ID for an account
        this.uuid = theBank.getNewAccountUUID();
        
        // inti transactions
        this.transactions = new ArrayList<Transaction>();
        
    }

    /**
     *
     * @return
     */
    public String getUUID() {
        return this.uuid;
    }
    
    /**
     * 
     * @return 
     */
    public String getSummaryLine(){
        double balance = this.getBalance();
        //format the summary line, depending on the weather the balance is negative
        if(balance >= 0 ){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }
    
    /**
     *
     * @return
     */
    public double getBalance(){
        double balance = 0;
        for(Transaction t: this.transactions){
            balance += t.getAmount();
        }
        //System.out.println("Balance is: "+balance);
        return balance;
    }

    /**
     *
     */
    public void printTransHistory() {
        System.out.printf("\nTransactions history for an account %s", this.uuid);
        for(int t = this.transactions.size()-1; t>=0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     *
     * @param amount
     * @param memo
     */
    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
    
}
