// represent the screen of the ATM

public class Screen {
    
    public void displayMessage(String message){
        
        System.out.println( message );
        
    }// end method displayMessage
    
   // display a meassage with a carriage return
    public void displayMessageLine( String message ){
      
        System.out.println( message );
    } // end method displayMessageLine
    
    //display dollar amount
    public void displayDollarAmount( double amount ){
        System.out.printf("$%.2f", amount );
    } // end method displayDollarAmount
            
    
    
} // end class screen
