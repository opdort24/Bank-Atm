
public class ATM {

    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen; // ATM Screen
    private Keypad keypad; // ATM keypad for input
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;
    
    // constants the corresponds to users activity on the system
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;
    
    // no argument ATM constructor that initializes instance variable
    
    public ATM (){
        
        userAuthenticated = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new BankDatabase();
        
    }// end of ATM no argument constructor
    
    //start ATM
    
    public void run(){
        //welcome and authenticate user; perform transactions
        
        while (true){
            //loop while user is not yet authenticated
            while(!userAuthenticated){
                screen.displayMessageLine("\nWelcome!");
                authenticateUser();// authenticat user
            }// end while
            
            performTransactions(); // user is now authenticated
            userAuthenticated = false; // reset before next ATM session
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank you,Goodbye!");
        }// end while
        
    } // end method run
    
    // authenticating user against database
    private void authenticateUser(){
        screen.displayMessage("please enter your account number:");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\nEnter your PIN:"); //promp for pin
        int pin = keypad.getInput();
        
        userAuthenticated = 
                bankDatabase.authenticateUser(accountNumber,pin);
        
        //check wether authentication succeeded
        if (userAuthenticated){
            currentAccountNumber = accountNumber; // save user's account number
        }// end if
        else
            screen.displayMessageLine(
            "invalid account number or PIN. Please try again.");
        
            
        
    }// end method authenticate user
    
    //display the main menu and perform transactions
    private void performTransactions(){
        //local variable to store transaction currently being processed
        Transaction currentTransaction = null;
        
        boolean userExited = false; //user has not chosen to exit
        
        //loop while user has not chosen option to exit system 
        while(!userExited){
            //show main menu and get user selection
            int mainMenuSelection = displayMainMenu();
            
            //decide how to proceed based on user's menu selection
            switch (mainMenuSelection){
                //user chose to perform one of the three transaction types
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:
                    // initialize as new object of chosen type
                    currentTransaction =
                            createTransaction(mainMenuSelection);
                    
                    currentTransaction.execute(); //execute transaction
                    break;
                case EXIT://user chose to terminate session
                    screen.displayMessageLine("\nExiting the system...");
                    userExited = true; //this ATM session should end
                    break;
                default:// user did not enter an integer from 1-4
                    screen.displayMessageLine(
                    "\nYou did not enter a valid selection. Try again.");
                    break;
            }//end switch
        }//end while
    }//end method performTransactions
    
    //display the main menu and return an input selection
    private int displayMainMenu(){
        
        screen. displayMessageLine("\nMain menu:");
        screen.displayMessageLine("1 - View my balance");
        screen.displayMessageLine("2 - Withdraw cash");
        screen.displayMessageLine("3 - Deposit funds");
        screen.displayMessageLine("4 - Exit");
        screen.displayMessage("Enter a choice: ");
        return keypad.getInput(); //return user's selection
        
    }// end method displayMainMenu
    
    // return object of specified Transaction subclass 
    private Transaction createTransaction ( int type ){
        
        Transaction temp = null; //temporary Transaction variable
        
        //determine which type of Transaction to create
        switch ( type )
        {
            case BALANCE_INQUIRY: // create a new BalanceInquiry transaction
                temp = new BalanceInquiry(
                    currentAccountNumber, screen, bankDatabase );
                break;
            case WITHDRAWAL: //create new Withdrrawal transactin
                temp = new Withdrawal (currentAccountNumber, screen,
                    bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT: //create new Deposit transaction
                temp = new Deposit( currentAccountNumber, screen,
                    bankDatabase, keypad, depositSlot );
                break;
        } // end switch
        
        return temp; // return the newly created object
    } // end method createTransaction
    
    
} // end class ATM
