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
		
		String orderid = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
		{
			int choice = myBookstoreObj.bookstoreInterfaceHandler();
			
			if (choice == 4) break;	 //back to the main page
		
			// Order Update
			if (choice == 1) 
			{
				System.out.printf("Please input the order ID:");
				orderid = reader.readLine();
				//***SQL Query
				
				//if satus is N and >=1 books is ordered
				System.out.printf("the Shipping status of %s is      and      books ordered\n", orderid);
				System.out.printf("Are you sure to update the shipping status? (Yes=Y)");
				String updateornot=reader.readLine();
				if (updateornot.equals("Y")) {
					//***SQL update
					
					
					System.out.printf("Updated shipping status\n\n");
				}
				else{ 
					System.out.printf("Update is cancelled.\n\n");
				}
				//if satus is not N and >=1 books is ordered
				//System.out.printf("Sorry, you cannot update the order because the shipping status is not N or less than 1 book is ordered.");
			}
			
			// Order Query
			if (choice == 2) 
			{
				System.out.printf("Please input the Month for Order Query (e.g.2021-04):");
				String monthofquery=reader.readLine();
				//***SQL Query
				
				
			}	
			
			// N most Popular Book Query
			if (choice == 3) 
			{
				System.out.printf("Please input the N popular books number:");
				String noofpopular=reader.readLine();
				//***SQL Query
				
				
				
			}
			
		}
			
	}
}
