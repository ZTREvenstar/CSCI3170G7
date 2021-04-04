import java.util.*;
import java.io.*;

public class Customer {
	
	public int customerInterfaceHandler() throws IOException
	{
		String OutputString = "";
		OutputString += "<This is the customer interface.>\n";
		OutputString += "---------------------------------------\n";
		OutputString += "1. Book Search.\n";
		OutputString += "2. Order Creation.\n";
		OutputString += "3. Order Altering.\n";
		OutputString += "4. Order Query.\n";
		OutputString += "5. Back to main menu.\n";
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
	
	public int performBookSearch() throws IOException
	{
		int status = 0;
		
		String OutputString = "";
		OutputString += "What do u want to search??\n";
		OutputString += "1. ISBN\n";
		OutputString += "2. Book Title\n";
		OutputString += "3. Author Name\n";
		OutputString += "4. Exit\n";
		OutputString += "Your choice?...\n";
		
		// prepare the reader which reads user inputs from the console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inputChoice = null;
		
		while(true) // loop in the interface until the user chooses to exit
		{
			// query user input
			int choice;
			while(true)
			{
				System.out.printf(OutputString);
				inputChoice = reader.readLine();
				if(inputChoice.equals("1") || inputChoice.equals("2") || inputChoice.equals("3") || inputChoice.equals("4"))
				{
					choice = inputChoice.charAt(0) - '0';
					break;
				}
				else
					System.out.println("INVALID INPUT!");
			}
			// exit
			if (choice == 4) 	
			{
				status = 4;
				return status;	
			}
			// search ISBN
			else if (choice == 1)
			{
				System.out.println("You choose to search ISBN.\n"
						         + "Input the ISBN:\n");
				String input = reader.readLine();
				
				SELECT *
				FROM book B
				WHERE B.ISBN="given"
				
				
			}
			// search book title
			else if (choice == 2)
			{
				System.out.println("You choose to search book title.\n"
						         + "Input the book title:\n");
			}
			// search author name
			else if (choice == 3)
			{
				System.out.println("You choose to search author name.\n"
							     + "Input the author name:\n");
			}
		}		
	}
	
	public static void customer_main() throws IOException
	{
		Customer myCustomerObj = new Customer();
		while (true)
		{
			int choice = myCustomerObj.customerInterfaceHandler();
			System.out.printf("Your choice is %d\n\n", choice);
		
			if (choice == 5) break;	 //back to the main page
		
			// Book Search
			if (choice == 1) 
			{
				int status = myCustomerObj.performBookSearch();
				System.out.printf("You have finished Book Search!    Status: %d\n\n", status);
			}
			
			// Order Creation 
			if (choice == 2) 
			{
				
			}	
			
			// Order Altering
			if (choice == 3) 
			{
							
			}
			
			// Order Query
			if (choice == 4)
			{
				
			}
			
		}
		
	}
		
}
