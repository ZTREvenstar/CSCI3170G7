import java.util.*;
import java.io.*;

public class mainHandler
{
	public void showSystemDate()
	{
		//
		//
		//
		//......
	}
	
	// This method is responsible for displaying text interface, 
	// receiving user choices and returning user choices
	public int mainInterfaceHandler() throws IOException
	{		
		String OutputString = "";
		OutputString += "The System Date is now: 0000-00-00\n";
		OutputString += "<This is the Book Ordering System.>\n";
		OutputString += "---------------------------------------\n";
		OutputString += "1. System interface.\n";
		OutputString += "2. Customer interface.\n";
		OutputString += "3. Bookstore interface.\n";
		OutputString += "4. Show System Date.\n";
		OutputString += "5. Quit the system......\n";
		OutputString += "\n";
		
		//Prepare the reader which reads user inputs from the console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		
		System.out.printf(OutputString);
		
		while(true)
		{
			System.out.println("Please enter your choice??..");
			input = reader.readLine();		
			if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5"))
			{
				int choice = input.charAt(0) - '0';
				return choice;
			}
			else
				System.out.println("INVALID INPUT!");
		}
	}
	
	// THE MAIN FUNCTION
	public static void main(String[] args) throws IOException
	{
		mainHandler myHandler = new mainHandler();
	
		while (true)
		{
			int choice = myHandler.mainInterfaceHandler();
			System.out.printf("Your choice is %d\n\n", choice);
		
			if (choice == 5) break;	 //quit the system
		
			// System interface
			if (choice == 1) system.system_main();
			
			// Customer interface
			if (choice == 2) Customer.customer_main();
			
			// Bookstore interface
			if (choice == 3) Bookstore.bookstore_main();			
			
			//Display system date
			if (choice == 4)
			{
				
			}
			
		}
	}	
}





