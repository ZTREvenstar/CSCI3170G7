import java.util.*;
import java.io.*;

public class Bookstore {

	public int bookstoreInterfaceHandler() throws IOException
	{
		String OutputString = "";
		OutputString += "<This is the bookstore interface.>\n";
		OutputString += "---------------------------------------\n";
		OutputString += "1. Order Update.\n";
		OutputString += "2. Order Query.\n";
		OutputString += "3. N most Popular Book Query.\n";
		OutputString += "4. Back to main menu.\n";
		OutputString += "\n";
		
		//Prepare the reader which reads user inputs from the console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
				
		System.out.printf(OutputString);
		
		while(true)
		{
			System.out.println("Please enter your choice??..");
			input = reader.readLine();		
			if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4"))
			{
				int choice = input.charAt(0) - '0';
				return choice;
			}
			else
				System.out.println("INVALID INPUT!");
		}
	}
	
	public static void bookstore_main() throws IOException
	{
		Bookstore myBookstoreObj = new Bookstore();
		while (true)
		{
			int choice = myBookstoreObj.bookstoreInterfaceHandler();
			System.out.printf("Your choice is %d\n\n", choice);
			
			if (choice == 4) break;	 //back to the main page
		
			// Order Update
			if (choice == 1) 
			{
				
			}
			
			// Order Query
			if (choice == 2) 
			{
				
			}	
			
			// N most Popular Book Query
			if (choice == 3) 
			{
							
			}
			
		}
			
	}
}
