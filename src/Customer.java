import java.util.*;
import java.io.*;
import java.sql.*;

public class Customer {
	
	public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db7";
	public static String dbUsername = "Group7";
	public static String dbPassword = "Group7";
	
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
	
	
	public int performBookSearch(Connection conObj) throws IOException, SQLException
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
			if (choice == 4)  return status;	
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			// search ISBN
			// need to write query author!!!!!!!!!!!!!!!!!!
			if (choice == 1)
			{
				System.out.println("You choose to search ISBN.\n"
						         + "Input the ISBN:\n");
				String inputISBN = reader.readLine();
				
				String psql = "SELECT * FROM book B WHERE B.ISBN = ?";

				pstmt = conObj.prepareStatement(psql);
				pstmt.setString(1, inputISBN);			
				
				status = 1;
			}
			
			// search book title
			// need to write query author!!!!!!!!!!!!!!!!!!
			else if (choice == 2)
			{
				System.out.println("You choose to search book title.\n"
						         + "Input the book title:\n");
				String inputBooktitle = reader.readLine();
				
				String psql = "SELECT * FROM book B WHERE B.title = ?";
				
				pstmt = conObj.prepareStatement(psql);
				pstmt.setString(1, inputBooktitle);
				
				status = 2;
			}
			
			// search author name
			// need to write query author!!!!!!!!!!!!!!!!!!
			else if (choice == 3)
			{
				System.out.println("You choose to search author name.\n"
							     + "Input the author name:\n");
				
				//
				System.out.println("NOT REALIZED YET.\n");
				status = 3;
				continue;
			}
			
			rs = pstmt.executeQuery();
			
			// output the result
			int counter = 0;
			while(rs.next())
			{
				counter++;
				String ISBN = rs.getString("ISBN");
				String title = rs.getString("title");
				int unit_price = rs.getInt("unit_price");
				int no_of_copies = rs.getInt("no_of_copies");
				System.out.printf("======= %d-th record:\n", counter);
				System.out.println("      ISBN: " + ISBN + "   "
						         + "Book title: " + title + "   "
						         + "Unit price: " + unit_price + "   "
						         + "No_of_copies: " + no_of_copies + "   ");
			}
			if (counter == 0) System.out.println("No records found.");
		}		
	}

	
	public int createOrder(Connection conObj) throws IOException, SQLException
	{
		int status = 0;
		
		//Prepare the reader which reads user inputs from the console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));		
		
		System.out.println("Please enter your customerID??");
		
		String customerID = reader.readLine();
		//
		//
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! order tuple
		//
		//
		
		System.out.println("What books do you want to order??");
		System.out.println("Input ISBN and then the quantity.");
		System.out.println("You can press \"L\" to see ordered list, or \"F\" to finish ordering.");

		while(true)
		{
			String book_ISBN = null;
			int book_quantity = 0;
			
			System.out.print("Please enter the book's ISBN:");
			
			book_ISBN = reader.readLine();
			
			if (book_ISBN.equals("F"))	return status;
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			if (book_ISBN.equals("L"))
			{
				// see ordered list
				String psql = "SELECT OL.ISBN, OL.quantity "
						    + "FROM orders O, ordering OL "
						    + "WHERE O.customer_id = ? AND O.order_id = OL.order_id";
				
				pstmt = conObj.prepareStatement(psql);
				pstmt.setString(1, customerID);
				
				rs = pstmt.executeQuery();
				
				// output the result
				System.out.println("ISBN		 NUMBER:\n");
				while(rs.next())
				{
					String ISBN = rs.getString("ISBN");
					String title = rs.getString("quantity");
					System.out.println(ISBN + "    " + title);
				}			
				status = 1;
			}
			else
			{	
				// check if the ISBN input exists
				//String psql = "";
				
				System.out.printf("Please enter the quantity of the order");
				while (true)
				{
					book_quantity = reader.read();
				
					// check if the quantity are available
					String psql = "SELECT B.no_of_copies"
						    	+ "FROM book B"
						    	+ "WHERE B.ISBN = ?";
					pstmt = conObj.prepareStatement(psql);
					pstmt.setString(1, book_ISBN);
					rs = pstmt.executeQuery();
					int no_of_copies_available = rs.getInt("no_of_copies");
				
					if (no_of_copies_available < book_quantity)
						System.out.printf("Quantity requested exceeds the maximum number available!\n"
										+ "Please input a number <= %d.\n", no_of_copies_available);
					else
					{
						// insert records into table "ordering"
						psql = "INSERT INTO ordering VALUES (0, ?, ?)";
						pstmt = conObj.prepareStatement(psql);
						pstmt.setString(1, book_ISBN);
						pstmt.setInt(2, book_quantity);
						int updateStatus1 = pstmt.executeUpdate();
						// update records in table "orders"
						// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						psql = "UPDATE orders O" 
						     + "SET O.o_date = 19840000, O.charge = O.charge + 114514"
							 + "WHERE 0.order_id = 0";
						int updateStatus2 = pstmt.executeUpdate();
						
						System.out.printf("updateStatus1: %d      updateStatus2: %d\n", updateStatus1, updateStatus2);
						break;
					}
				}
				status = 2;
				
				
				//final check    if the table is empty then perform delete
			}
			
		}
	}

	
	public int alterOrder(Connection conObj) throws IOException, SQLException
	{
		int status = 0;
		
		
		
		
		
		
		
		
		return status;
	}
	
	
	public int queryOrder(Connection conObj) throws IOException, SQLException
	{
		int status = 0;
		
		//Prepare the reader which reads user inputs from the console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));		
				
		System.out.print("Please enter your customerID:");
		String customerID = reader.readLine();
		
		System.out.print("Please input the year:");
		String year = reader.readLine();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		

		String psql = "SELECT *"
			    	+ "FROM orders O"
			    	+ "WHERE O.customer_id = ? AND O.o_date = ?-__-__";
	
		pstmt = conObj.prepareStatement(psql);
		pstmt.setString(1, customerID);
		pstmt.setString(2, year);
	
		rs = pstmt.executeQuery();
	
		// output the result
		int counter = 0;
		while(rs.next())
		{
			counter++;
			System.out.printf("\nRecord: %d\n", counter);
			
			String order_id = rs.getString("order_id");
			String o_date = rs.getString("o_date");
			int charge = rs.getInt("charge");
			String shipping_status = rs.getString("shipping_status");
			
			System.out.println("OrderID: " + order_id);
			System.out.println("OrderDate: " + o_date);
			System.out.printf("charge: %d\n", charge);
			System.out.println("shipping status: " + shipping_status);
		}			
		status = 1;
		
		return status;
	}
	
	
	public static void customer_main() throws IOException
	{
		// Database driver issues
		Connection con = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("JAVA MYSQL DB Driver not found!");
			System.exit(0);
		}
		catch (SQLException e)
		{
			System.out.println(e);
			System.exit(0);
		}	
		System.out.print("Database connection SUCCESS!!!!");
		
		
		Customer myCustomerObj = new Customer();
		while (true)
		{
			int choice = myCustomerObj.customerInterfaceHandler();
			System.out.printf("Your choice is %d\n\n", choice);
		
			if (choice == 5) break;	 //back to the main page
		
			// Book Search
			if (choice == 1) 
			{
				int status = 0;
				try 
				{
					status = myCustomerObj.performBookSearch(con);
				} catch (IOException | SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("You have finished Book Search!    Status: %d\n\n", status);
			}
			
			// Order Creation 
			if (choice == 2) 
			{
				int status = 0;
				try 
				{
					status = myCustomerObj.createOrder(con);
				} catch (IOException | SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("You have finished Order Creation!    Status: %d\n\n", status);
			}	
			
			// Order Altering
			if (choice == 3) 
			{
				int status = 0;
				try 
				{
					status = myCustomerObj.alterOrder(con);
				} catch (IOException | SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("You have finished altering order!    Status: %d\n\n", status);			
			}
			
			// Order Query
			if (choice == 4)
			{
				int status = 0;
				try 
				{
					status = myCustomerObj.queryOrder(con);
				} catch (IOException | SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("You have finished Order Query!    Status: %d\n\n", status);
			}
			
		}
		
	}
		
}










