import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class system {
	
	public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db7";
	public static String dbUsername = "Group7";
	public static String dbPassword = "Group7";
	
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
		system mySystemObj = new system();
		
		while (true)
		{
			int choice = mySystemObj.systemInterfaceHandler();
			System.out.printf("Your choice is %d\n\n", choice);
		
			if (choice == 5) break;	 //back to the main page
		
			// Create Table
			if (choice == 1) 
			{
				
				PreparedStatement pstmt = null;

				String psql = "Create table book("
						+ "ISBN CHAR(13),"
						+ "title CHAR(100),"
						+ "unit_price INTEGER,"
						+ "no_of_copies INTEGER,"
						+ "PRIMARY KEY (ISBN))";

				
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				psql = "Create table customer("
						+ "customer_id CHAR(10),"
						+ "name CHAR(50),"
						+ "shipping_address CHAR(200),"
						+ "credit_card_no CHAR(19),"
						+ "PRIMARY KEY (customer_id))";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
						
				
				psql = "Create table orders("
						+ "order_id CHAR(8),"
						+ "o_date CHAR(13),"
						+ "shipping_status CHAR(1),"
						+ "charge INTEGER,"
						+ "customer_id CHAR(10),"
						+ "PRIMARY KEY (order_id,customer_id),"
						+ "FOREIGN KEY (customer_id) REFERENCES customer(customer_id))";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
				

				
				psql = "Create table ordering("
						+ "order_id CHAR(8),"
						+ "ISBN CHAR(13),"
						+ "quantity INTEGER,"
						+ "PRIMARY KEY (order_id,ISBN),"
						+ "FOREIGN KEY (order_id) REFERENCES orders(order_id),"
						+ "FOREIGN KEY (ISBN) REFERENCES book(ISBN))";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}				
				
				psql = "Create table book_author("
						+ "ISBN CHAR(13),"
						+ "author_name CHAR(50),"
						+ "PRIMARY KEY (ISBN,author_name),"
						+ "FOREIGN KEY (ISBN) REFERENCES book(ISBN))";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}				
				
			}
			
			// Delete Table
			if (choice == 2) 
			{
				PreparedStatement pstmt = null;

				String psql = "DROP TABLE IF EXISTS book_author";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
				

				psql = "DROP TABLE IF EXISTS ordering";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
				psql = "DROP TABLE IF EXISTS orders";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
			
				
				psql = "DROP TABLE IF EXISTS customer";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
				psql = "DROP TABLE IF EXISTS book";
				try {
					pstmt = con.prepareStatement(psql);
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
			}
			
			// Insert Data
			if (choice == 3) 
			{
				// String array stores all txt 
				ArrayList<String> txtList = new ArrayList<String>();
				txtList.add("book.txt");
				txtList.add("customer.txt");
				txtList.add("orders.txt");
				txtList.add("ordering.txt");
				txtList.add("book_author.txt");
				
				//1111111111111book
				String fileName = txtList.get(0);
				File file= new File(fileName);
				
				// prepare the buffer reader
				BufferedReader reader= new BufferedReader(new FileReader(file));;
				
				PreparedStatement pstmt = null;
				
				// 1 represents the corresponding attribute is type String, 0 represents Int
				int StrOrIntbook[] = {1, 1, 0, 0};
				String tempString= null;
				
				// read line by line
				String sql = null;
				boolean txtNotNULL = false;
				while ((tempString = reader.readLine()) != null) // a while loop read one txt
				{
					txtNotNULL = true;
					sql = null;
					String stringarray[] = tempString.split("\\|");	
					
					sql =  "INSERT INTO book VALUES "
						 + "(";
					for (int i = 0; i < stringarray.length; i++)
					{
						if (i != 0) //Whether to add a comma
							sql += ", ";
						if (StrOrIntbook[i] == 1) // Is a String
							sql += "'" + stringarray[i] + "'";
						if (StrOrIntbook[i] == 0) // Is a Int
						    sql += stringarray[i];
					}
					sql += ")";
					
					if (txtNotNULL)
					{
						try {
							pstmt = con.prepareStatement(sql);
							int updatestatus = pstmt.executeUpdate();
						} catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}	
					
				}
				reader.close();
				
				
				//222222222customer
				fileName = txtList.get(1);
				file= new File(fileName);
				
				// prepare the buffer reader
				reader= new BufferedReader(new FileReader(file));;
				
				// 1 represents the corresponding attribute is type String, 0 represents Int
				int StrOrIntcustomer[] = {1, 1, 1, 1};
				
				// read line by line
				sql = null;
				txtNotNULL = false;
				while ((tempString = reader.readLine()) != null) // a while loop read one txt
				{
					txtNotNULL = true;
					sql = null;
					String stringarray[] = tempString.split("\\|");		
					
					sql =  "INSERT INTO customer VALUES "
						 + "(";
					for (int i = 0; i < stringarray.length; i++)
					{
						if (i != 0) //Whether to add a comma
							sql += ", ";
						if (StrOrIntcustomer[i] == 1) // Is a String
							sql += "'" + stringarray[i] + "'";
						if (StrOrIntcustomer[i] == 0) // Is a Int
						    sql += stringarray[i];
					}
					sql += ")";
					
					if (txtNotNULL)
					{
						try {
							pstmt = con.prepareStatement(sql);
							int updatestatus = pstmt.executeUpdate();
						} catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}	
					
				}
				reader.close();
				
				
				//33333333orders
				fileName = txtList.get(2);
				file= new File(fileName);
				
				// prepare the buffer reader
				reader= new BufferedReader(new FileReader(file));;
				
				// 1 represents the corresponding attribute is type String, 0 represents Int
				int StrOrIntorders[] = {1,1,1,0,1};
				
				// read line by line
				sql = null;
				txtNotNULL = false;
				while ((tempString = reader.readLine()) != null) // a while loop read one txt
				{
					txtNotNULL = true;
					sql = null;
					String stringarray[] = tempString.split("\\|");	
					
					sql =  "INSERT INTO orders VALUES "
						 + "(";
					for (int i = 0; i < stringarray.length; i++)
					{
						if (i != 0) //Whether to add a comma
							sql += ", ";
						if (StrOrIntorders[i] == 1) // Is a String
							sql += "'" + stringarray[i] + "'";
						if (StrOrIntorders[i] == 0) // Is a Int
						    sql += stringarray[i];
					}
					sql += ")";
					
					if (txtNotNULL)
					{
						try {
							pstmt = con.prepareStatement(sql);
							int updatestatus = pstmt.executeUpdate();
						} catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}	
					
				}
				reader.close();
				
				
				//444444ordering
				fileName = txtList.get(3);
				file= new File(fileName);
				
				// prepare the buffer reader
				reader= new BufferedReader(new FileReader(file));;
				
				// 1 represents the corresponding attribute is type String, 0 represents Int
				int StrOrIntordering[] = {1,1,0};
				
				// read line by line
				sql = null;
				txtNotNULL = false;
				while ((tempString = reader.readLine()) != null) // a while loop read one txt
				{
					txtNotNULL = true;
					sql = null;
					String stringarray[] = tempString.split("\\|");		
					
					sql =  "INSERT INTO ordering VALUES "
						 + "(";
					for (int i = 0; i < stringarray.length; i++)
					{
						if (i != 0) //Whether to add a comma
							sql += ", ";
						if (StrOrIntordering[i] == 1) // Is a String
							sql += "'" + stringarray[i] + "'";
						if (StrOrIntordering[i] == 0) // Is a Int
						    sql += stringarray[i];
					}
					sql += ")";
										
					if (txtNotNULL)
					{
						try {
							pstmt = con.prepareStatement(sql);
							int updatestatus = pstmt.executeUpdate();
						} catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}	
					
				}
				reader.close();
				
				
				//5555555555book_author
				fileName = txtList.get(4);
				file= new File(fileName);
				
				// prepare the buffer reader
				reader= new BufferedReader(new FileReader(file));;
				
				// 1 represents the corresponding attribute is type String, 0 represents Int
				int StrOrIntbook_author[] = {1, 1};
				
				// read line by line
				sql = null;
				txtNotNULL = false;
				while ((tempString = reader.readLine()) != null) // a while loop read one txt
				{
					txtNotNULL = true;
					sql = null;
					String stringarray[] = tempString.split("\\|");	
					
					sql =  "INSERT INTO book_author VALUES "
						 + "(";
					for (int i = 0; i < stringarray.length; i++)
					{
						if (i != 0) //Whether to add a comma
							sql += ", ";
						if (StrOrIntbook_author[i] == 1) // Is a String
							sql += "'" + stringarray[i] + "'";
						if (StrOrIntbook_author[i] == 0) // Is a Int
						    sql += stringarray[i];
					}
					sql += ")";
										
					if (txtNotNULL)
					{
						try {
							pstmt = con.prepareStatement(sql);
							int updatestatus = pstmt.executeUpdate();
						} catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}	
					
				}
				reader.close();
				
				
			}
				
			// Set System Date
			if (choice == 4)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String input = null;
				System.out.printf("Please Input the date (YYYYMMDD): ");
				input = reader.readLine();
				StringBuffer sb = new StringBuffer(); 
				sb.append(input).insert(4,"-");
				sb.insert(7,"-");
				input = sb.toString();
				
				String sql = "SELECT * FROM orders ORDER BY o_date DESC LIMIT 1";
				
				PreparedStatement pstmt = null;
				String odate = null;
				try {
					pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next())
					{
						odate = rs.getString("o_date");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.printf("Latest date in orders: %s\n", odate);
				
				String finaldate = null;
				if(input.compareTo(odate) > 0)
					finaldate = input;
				else
					finaldate = odate;
				
				System.out.println("Today is " + finaldate);
				}
			}
		
	}
}
