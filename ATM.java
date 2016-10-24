
package atmsystem;

import java.util.Scanner;

/**
 *
 * @author Aakash
 */
public class ATM {

    /**
     *
     * @param args
     */
    
    public static void main(String[] args) {
    String fName;
    String lName;
    String pin;
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter Firstname: \n");
    fName = sc.nextLine();
    System.out.print("Enter Lastname: \n");
    lName = sc.nextLine();
    System.out.print("Enter pin: \n");
    pin = sc.nextLine();
    //init bank
    Bank theBank = new Bank("Bank of America");
    
    //add a user, which also creates a savings account
    User aUser = theBank.addUser(fName, lName, pin);
    
    //add a checking account for user
    Account newAccount = new Account("Checking", aUser, theBank);
    aUser.addAccount(newAccount);
    theBank.addAccount(newAccount);
    
    User curUser;
    while(true){
        //stay in the login prompt until successful login
        curUser= ATM.mainMenuPrompt(theBank,sc);
        
        //stay in main menu until user quits
        ATM.printUserMenu(curUser,sc);
    }
    }
    /**
     * 
     * @param theBank
     * @param sc
     * @return 
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        
        do{
            System.out.printf("Welcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();
            
            //try to get user object
            authUser = theBank.userLogin(userID, pin);
            if(authUser == null){
                System.out.println("Incorrect user ID/pin. Please try again.");
            }
        } while(authUser == null);
        return authUser;
    }
    
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void printUserMenu(User theUser, Scanner sc){
        //print a summary of the user's accounts
        theUser.printAccountsSummary();
        int choice;
        
        do{
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Exit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            
            if(choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while(choice < 1 || choice > 5);
        
        //process the choice
        switch(choice){
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                //gobble up rest of previous
                sc.nextLine();
                break;
        }
        if(choice != 5){
            ATM.printUserMenu(theUser, sc);
        }
    }
    
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void showTransHistory(User theUser, Scanner sc){
        int theAcct;
        do {            
            System.out.printf("Enter the number (1-%d) of the account\n"+
                    " whose transactions you want to see: ",theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct <0 || theAcct >= theUser.numAccounts());
        
        //print the transactions history
        theUser.printAcctHistory(theAcct);
    }
    
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void transferFunds(User theUser, Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        
        //get the account to transfer from
        do {            
            System.out.printf("Enter the account no (1-%d) to transfer from: ",theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct <0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        
        //get the account to transfer to
        do {            
            System.out.printf("Enter the account no (1-%d) to transfer to: ",theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct <0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct <0 || toAcct >= theUser.numAccounts());
        
        //get the amount to transfer
        do {            
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount <0 ){
                System.out.println("Insufficient balance.");
            }else if(amount > acctBal){
                System.out.printf("Amount must be less than balance of $%.02f.\n",acctBal);
            }
        } while (amount <0 || amount> acctBal);
        
        //get the amount to transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));

    }
    
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void withdrawFunds(User theUser, Scanner sc){
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        
        //get the account to transfer from
        do {            
            System.out.printf("Enter the account no (1-%d) to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct <0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        
        //get the amount to transfer
        do {            
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount <0 ){
                System.out.println("Insufficient balance.");
            }
        } while (amount <0 || amount> acctBal);
        
        //gobble up rest of previous
        sc.nextLine();
        
        //get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        //do the withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }
    
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void depositFunds(User theUser, Scanner sc){
        int toAcct;
        double amount;
        double acctBal;
        String memo;
        
        //get the account to transfer from
        do {            
            System.out.printf("Enter the account no (1-%d) to deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct <0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct <0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        
        //get the amount to transfer
        do {            
            System.out.printf("Enter the amount to deposit (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount <0 ){
                System.out.println("Insufficient balance.");
            }
        } while (amount <0);
        
        //gobble up rest of previous
        sc.nextLine();
        
        //get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        //do the deposit
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
