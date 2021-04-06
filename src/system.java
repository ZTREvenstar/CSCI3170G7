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
	
	public static String system_main() throws IOException
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

				String psql = "Create table book(ISBN CHAR(13),title CHAR(100),unit_price INTEGER,no_of_copies INTEGER,PRIMARY KEY (ISBN),FOREIGN KEY (author_name) REFERENCES book_author ON DELETE NO ACTION)"
				+ "				Create table customer(customer_id CHAR(10),name CHAR(50),shipping_address CHAR(200),credit_card_no CHAR(19),PRIMARY KEY (customer_id))"
				+ "				Create table orders(order_id CHAR(8),o_date INTEGER,shipping_status CHAR(1),charge INTEGER,customer_id CHAR(10),PRIMARY KEY (order_id,customer_id),FOREIGN KEY (customer_id) REFERENCES customer)"
				+ "				Create table ordering(order_id CHAR(8),ISBN CHAR(13),quantity INTEGER,PRIMARY KEY (order_id,ISBN),FOREIGN KEY (order_id) REFERENCES orders,FOREIGN KEY (ISBN) REFERENCES book)"
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
				PreparedStatement pstmt = null;

				String psql = "DROP TABLE IF EXISTS book;"
						+ "DROP TABLE IF EXISTS customer;"
						+ "DROP TABLE IF EXISTS orders;"
						+ "DROP TABLE IF EXISTS ordering;"
						+ "DROP TABLE IF EXISTS book_author;";
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
			
			// Insert Data
			if (choice == 3) 
			{

				
				PreparedStatement stmt = null;
				String fileName = null;
				File file= new File(fileName);
				String tempString= null;
				BufferedReader reader= null;
				try{
					System.out.println("read file line by line");
					reader= new BufferedReader(new FileReader(file));
					while ((tempString = reader.readLine()) != null) {
						String sql= ("insert into book values (tempString)");
						try{
							con=DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
							con.setAutoCommit(false);
							stmt= con.prepareStatement("load data local infile '' " + "into table loadtest fields terminated by ','");
							StringBuilder sb= new StringBuilder();
							InputStream is= new ByteArrayInputStream(sb.toString().getBytes());
							//((com.mysql.jdbc.Statement) stmt).setLocalInfileInputStream(is);
							stmt.executeUpdate(sql);
							con.commit();
							}
						catch(SQLException e) {
							e.printStackTrace();
							};}
					reader.close();
					}
				catch(IOException e) {
					e.printStackTrace();
					}
				finally{
					if (reader != null) {
						try{
							reader.close();
							}
						catch(IOException e1) {
							
						}
					}
				}
				return tempString;
				}
				
			
			// Set System Date
			if (choice == 4)
			{
				
			}
			
		}
		
	}
		
}