import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

				String psql = "Create table book(ISBN CHAR(13),title CHAR(100),unit_price INTEGER,no_of_copies INTEGER,PRIMARY KEY (ISBN))\r\n"
				+ "				Create table customer(customer_id CHAR(10),name CHAR(50),shipping_address CHAR(200),credit_card_no CHAR(19),PRIMARY KEY (customer_id))\r\n"
				+ "				Create table orders(order_id CHAR(8),o_date INTEGER,shipping_status CHAR(1),charge INTEGER,customer_id CHAR(10),PRIMARY KEY (order_id,customer_id),FOREIGN KEY (customer_id) REFERENCES customer)\r\n"
				+ "				Create table ordering(order_id CHAR(8),ISBN CHAR(13),quantity INTEGER,PRIMARY KEY (order_id,ISBN),FOREIGN KEY (order_id) REFERENCES orders,FOREIGN KEY (ISBN) REFERENCES book)\r\n"
				+ "				Create table book_author(ISBN CHAR(13),author_name CHAR(50),PRIMARY KEY (ISBN,author_name),FOREIGN KEY (ISBN) REFERENCES book)";
				try {
					pstmt = con.prepareStatement(psql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					int updatestatus = pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			// Delete Table
			if (choice == 2) 
			{
				
			}	
			
			// Insert Data
			if (choice == 3) 
			{
				String filePath = "D:/aa.txt";

				FileInputStream fin = new FileInputStream(filePath);

				InputStreamReader reader = new InputStreamReader(fin);

				BufferedReader buffReader = new BufferedReader(reader);

				String strTmp = "";

				while((strTmp = buffReader.readLine())!=null){
				System.out.println(strTmp);

				}

				buffReader.close();
			}
			
			// Set System Date
			if (choice == 4)
			{
				
			}
			
		}
		
	}
		
}