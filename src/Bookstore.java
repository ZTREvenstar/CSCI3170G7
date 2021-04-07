import java.util.*;
import java.io.*;
import java.sql.*;

public class Bookstore {
	
	public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db7";
	public static String dbUsername = "Group7";
	public static String dbPassword = "Group7";

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
		//System.out.print("Database connection SUCCESS!!!!\n\n");

		//System.out.print("Database connection SUCCESS!!!!");

		
		
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
				
				ResultSet rs3=null;
				PreparedStatement pstmt = null;
				
				boolean idvalue= true ; 
				
				try {
					String psq3 = "SELECT * FROM orders WHERE order_id=?";
					pstmt = con.prepareStatement(psq3);
					pstmt.setString(1, orderid);
					rs3 = pstmt.executeQuery();
					
					while(rs3.next()) {
						idvalue = false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(idvalue==false) {
					System.out.println("Invalid input!");
				}
				
				else {
				ResultSet rs1=null, rs2 = null;
				//PreparedStatement pstmt = null;
				String status=null;
				int quantity=0, updateStatus1=0;
				
				//***SQL Query

				try {
					String psql = "SELECT O.shipping_status FROM orders O WHERE O.order_id=?";
					pstmt = con.prepareStatement(psql);
					pstmt.setString(1, orderid);
					rs1 = pstmt.executeQuery();
					
					String psq2 = "SELECT OL.quantity FROM ordering OL WHERE OL.order_id=?";
					pstmt = con.prepareStatement(psq2);
					pstmt.setString(1, orderid);
					rs2 = pstmt.executeQuery();
					
					while(rs1.next()) {
						status = rs1.getString("shipping_status");
					}
					while(rs2.next()) {
						quantity = rs2.getInt("quantity");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				
				if (status.equals("N") && quantity>=1) {
					System.out.printf("the Shipping status of %s is %s and %d books ordered\n", orderid,status,quantity);
					System.out.printf("Are you sure to update the shipping status? (Yes=Y)");
					String updateornot=reader.readLine();
					if (updateornot.equals("Y")) {
						
						//***SQL update
						try {
							String psq3 = "UPDATE orders OL SET OL.shipping_status='Y' WHERE OL.order_id=?";
							pstmt = con.prepareStatement(psq3);
							pstmt.setString(1, orderid);
							updateStatus1 = pstmt.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.printf("Updated shipping status\n\n");
					}
					else{ 
					System.out.printf("Update is cancelled.\n\n");
					}
				}
				else {
				System.out.printf("Sorry, you cannot update. Reason1: the order_id doesn't exist. Reason2: the shipping status is Y or less than 1 book is ordered.\n\n");
				}
			}
			}
			
			// Order Query
			if (choice == 2) 
			{
				System.out.printf("Please input the Month for Order Query (e.g.2021-04):");
				String monthofquery=reader.readLine();
				
				ResultSet rs3=null;
				PreparedStatement pstmt = null;
				
				//***SQL Query
				try {
					String psql = "SELECT * FROM orders WHERE o_date LIKE ? ORDER BY order_id";
					pstmt = con.prepareStatement(psql);
					pstmt.setString(1, monthofquery+"-__");
					rs3 = pstmt.executeQuery();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//output the result
				int chargetotal = 0;
				int counter=0;
				try {
					while(rs3.next())
					{
						counter++;
						System.out.printf("Record: %d\n", counter);
						
						String order_id = rs3.getString("order_id");
						String customer_id = rs3.getString("customer_id");
						String o_data=rs3.getString("o_date");
						int charge = rs3.getInt("charge");
						
						chargetotal=chargetotal+charge;
						
						System.out.println("order_id: " + order_id);
						System.out.println("customer_id: " + customer_id);
						System.out.println("date: " + o_data);
						System.out.printf("charge: %d\n\n", charge);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (counter!=0) 
					System.out.printf("Total charges of the month is %d\n\n", chargetotal);
				else 
					System.out.printf("No record is found\n\n");
	
				
			}	
			
			// N most Popular Book Query
			if (choice == 3) 
			{
				System.out.printf("Please input the N popular books number:");
				String input = reader.readLine();

				
				int numinput = Integer.parseInt(input);
				
				if(numinput<=0) {
					System.out.printf("Invalid input!\n");
				}
				else {
				
				//***SQL Query
				ResultSet rs4=null;
				PreparedStatement pstmt = null;
				
				try {
					String psql = "SELECT a.sum, a.ISBN "
							+ "FROM (SELECT sum(quantity) as sum,ISBN FROM ordering GROUP BY ISBN)a "
							+ "ORDER BY a.sum DESC "
							+ "LIMIT ? ";
					pstmt = con.prepareStatement(psql);
					pstmt.setInt(1, numinput);
					rs4 = pstmt.executeQuery();
							
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//get the quantity of the most unpopular book
				int smallsum=999999;
				try {
					while(rs4.next())
					{
						int quantitysum = rs4.getInt("sum");
						
						if (quantitysum<smallsum) {
							smallsum=quantitysum;
						}
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//output the result
				System.out.printf("ISBN              Title                Copies\n");
				
				ResultSet rs5=null;
				PreparedStatement pstmt1 = null;
				
				try {
					String psq2 = "SELECT b.ISBN, b.title, c.sum "
								+ "FROM book b, (SELECT sum(quantity) as sum,ISBN FROM ordering GROUP BY ISBN)c "
								+ "WHERE c.sum>=? AND c.ISBN=b.ISBN "
								+ "ORDER BY c.sum DESC, b.title, b.ISBN";
					pstmt1 = con.prepareStatement(psq2);
					pstmt1.setInt(1, smallsum);
					rs5 = pstmt1.executeQuery();
							
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					while(rs5.next())
					{
						String quantitysum = rs5.getString("sum");
						String ISBN=rs5.getString("ISBN");
						String title=rs5.getString("title");
						System.out.printf("%s     %s     %s\n", ISBN,title,quantitysum );
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
		}
	}
}
