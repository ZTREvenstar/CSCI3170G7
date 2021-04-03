import java.util.*;
import java.io.*;

public class system {
	
	public int systemInterfaceHandler() throws IOException
	{
		String OutputString = "";
		OutputString += "<This is the system interface.>\n";
		OutputString += "---------------------------------------\n";
		OutputString += "1. Create Table.\n";
		OutputString += "2. Delete Table.\n";
		OutputString += "3. Insert Data.\n";
		OutputString += "4. Set System Date.\n";
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
	
	public static void system_main() throws IOException
	{
		system mySystemObj = new system();
		while (true)
		{
			int choice = mySystemObj.systemInterfaceHandler();
			System.out.printf("Your choice is %d\n\n", choice);
		
			if (choice == 5) break;	 //back to the main page
		
			// Create Table
			if (choice == 1) 
			{
				
			}
			
			// Delete Table
			if (choice == 2) 
			{
				
			}	
			
			// Insert Data
			if (choice == 3) 
			{
							
			}
			
			// Set System Date
			if (choice == 4)
			{
				
			}
			
		}
		
	}
		
}
